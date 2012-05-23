package com.alcatel_lucent.nz.wnmsextract.reader;
/*
 * This file is part of wnmsextract.
 *
 * wnmsextract is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * wnmsextract is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.log4j.Logger;

import com.alcatel_lucent.nz.wnmsextract.document.ALUFileFilter;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;

/**
 * FileSelector's purpose is to pull/unzip extract data from a defined source and leave
 * copies of the reqd xml files in a common directory
 * @author jnramsay
 *
 */
public abstract class FileSelector {

	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.FileSelector");

	private static final String FILENAME_DF = "yyyyMMdd";

	private static final int BUFFER = 2048;
	private static final int MIN_FILE_SIZE = 10;
	private static final int MAX_UNGZIP_RETRIES = 10;

	protected Calendar calendar;
	private File tdir;
	private File sdir;
	private String identifier;
	
	protected DocumentType doctype;

	protected int retry_counter;
	protected List<File> allfiles;

	public abstract void extract();

	/**
	 * Returns a list of files matching the requested doctype in the current extract directory
	 * @return
	 */
	public List<File> getFileList(){
		//Set the date to filter on
		ALUFileFilter ff = (ALUFileFilter) doctype.getFileFilter();
		ff.setAcceptDate(calendar);

		// Loop through all the files in the temppath
		List<File> filteredFiles = new ArrayList<File>();
		for(File f3 : (getCalTempPath()).listFiles())
		{
			// If the filter is accepted add the files to the new list
			if (ff.accept(f3))
			{
				filteredFiles.add(f3);
			}
		}
		Collections.sort(filteredFiles);
		return filteredFiles;
	}
	
	public void setDocType(DocumentType doctype){
		this.doctype = doctype;
	}
	
	public DocumentType getDocType(){
		return this.doctype;
	}
	
	public abstract String docPath();

	//the cal is the required date
	public void setCalendar(Calendar calendar){
		this.calendar = calendar;
		setTempPath(new File(Extractor.chooseTempPath()+File.separator+calendarToString(this.calendar)));
	}


	//getters setters for temp/src pathes
	public void setTempPath(File tdir) {
		this.tdir = tdir;
	}

	public File getTempPath() {
		return tdir;
	}

	//TODO. tidy this up
	//sticks a date str at the end of the temp directory
	public File getCalTempPath() {
		String calpath = getTempPath().getAbsolutePath()+File.separator+calendarToString(calendar);
		if(!(new File(calpath)).exists()){
    		(new File(calpath)).mkdir();
    	}
		return new File(calpath);
	}

	public void setSourcePath(File sdir) {
		this.sdir = sdir;
	}

	public File getSourcePath() {
		return sdir;
	}

	//sticks a date str at the end of the source directory
	public File getCalSourcePath() {
		String calpath = getSourcePath().getAbsolutePath()+File.separator+calendarToString(calendar);
		if(!(new File(calpath)).exists()){
    		(new File(calpath)).mkdir();
    	}
		return new File(calpath);
	}

	// Returns a string of the calendar in the format yyyyMMdd
	public static String calendarToString(Calendar cal)
	{
		DateFormat dateFormat = new SimpleDateFormat(FILENAME_DF);
		return(dateFormat.format(cal.getTime()));
	}

	//Unzip methods

	/**
	 * Top unzip method. extract tarfile to constituent parts processing gzips 
	 * along the way e.g. yyyyMMdd.zip->/yyyyMMdd/INode-CH_RNC01/A2010...zip
	 */
	protected void unzip1(File zipfile) throws FileNotFoundException {

		try {
			ZipArchiveInputStream zais = new ZipArchiveInputStream(new FileInputStream(zipfile));
			ZipArchiveEntry z1 = null;
			while ((z1 = zais.getNextZipEntry())!=null){
				if(z1.isDirectory()){
					/*hack to add vcc identifier because fucking ops cant rename a simple file*/
					if (z1.getName().contains("account")) identifier = ".vcc";
					else identifier = "";
				}
				else{
					String fn = z1.getName().substring(z1.getName().lastIndexOf("/"));
					File f = new File(getCalTempPath()+fn);
					FileOutputStream fos = new FileOutputStream(f);
					BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);


					int n = 0;
					byte[] content = new byte[BUFFER];
					while (-1 != (n = zais.read(content))) {
					    fos.write(content, 0, n);
					}

					bos.flush();
					bos.close();
					fos.close();

					File unz = null;
					if (f.getName().endsWith("zip"))
						unz = unzip3(f);
					else unz = ungzip(f);

					if(unz!=null)
						allfiles.add(unz);
					f.delete();
				}
			}
			zais.close();
		}
		catch(IOException ioe){
			jlog.fatal("IO read error :: "+ioe);
		}


	}

	/**
	 * The nested unzip method. Given a zip stream, decompress and store in file in temp_dir.
	 * Replaced by unzip3.
	 */
	protected File unzip2(File zf) throws FileNotFoundException {
		//File f = null;
		String rename = zf.getAbsolutePath().replaceFirst("\\.zip", identifier+".xml");//.replaceFirst("\\.gz", ".xml");
		File f = new File(rename);
		try {
			FileInputStream fis = new FileInputStream(zf);
			FileOutputStream fos = new FileOutputStream(rename);
			ZipInputStream zin = new ZipInputStream(fis);
			final byte[] content = new byte[BUFFER];
			int n = 0;
			while (-1 != (n = zin.read(content))) {
			    fos.write(content, 0, n);
			}

			fos.flush();
			fos.close();

			fis.close();
			zin.close();
		}
		catch (IOException ioe) {
			jlog.error("Error processing Zip "+zf+" Excluding! :: "+ioe);
			return null;
		}
		//try again... what could go wrong
		if (checkMinFileSize(f) && retry_counter<MAX_UNGZIP_RETRIES){
			retry_counter++;
			f.delete();
			f = unzip2(zf);
		}
		return f;
	}

	protected File unzip3(File zf) throws FileNotFoundException {
		//File f = null;
		String rename = zf.getAbsolutePath().replaceFirst("\\.zip", identifier+".xml");//.replaceFirst("\\.gz", ".xml");
		File f = new File(rename);
		try {
			FileInputStream fis = new FileInputStream(zf);

			ZipInputStream zin = new ZipInputStream(fis);
			ZipEntry ze;

			final byte[] content = new byte[BUFFER];

			while((ze = zin.getNextEntry())!=null){
				f = new File(getCalTempPath()+File.separator+ze.getName());
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos, content.length);

				int n = 0;
				while (-1 != (n = zin.read(content))) {
				    bos.write(content, 0, n);
				}
				bos.flush();
				bos.close();
			}


			fis.close();
			zin.close();
		}
		catch (IOException ioe) {
			jlog.error("Error processing Zip "+zf+" Excluding! :: "+ioe);
			return null;
		}
		//try again... what could go wrong
		/*
		if (checkMinFileSize(f) && retry_counter<MAX_UNGZIP_RETRIES){
			retry_counter++;
			f.delete();
			f = unzip2(zf);
		}
		*/
		return f;
	}

	/**
	 * extract tarfile to constituent parts processing gzips along the way
	 * yyyyMMdd.tar->/yyyyMMdd/INode-CH_RNC01/A2010...gz
	 */
	protected void untar(File tf) throws FileNotFoundException {

		try {
			TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(tf));
			TarArchiveEntry t1 = null;
			while ((t1 = tais.getNextTarEntry())!=null){
				if(t1.isDirectory()){
					if (t1.getName().contains("account")) identifier = ".vcc";
					else identifier = "";
				}
				else{
					String fn = t1.getName().substring(t1.getName().lastIndexOf("/"));
					File f = new File(getCalTempPath()+fn);
					FileOutputStream fos = new FileOutputStream(f);
					BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);


					int n = 0;
					byte[] content = new byte[BUFFER];
					while (-1 != (n = tais.read(content))) {
					    fos.write(content, 0, n);
					}

					bos.flush();
					bos.close();
					fos.close();

					File unz = null;
					if (f.getName().endsWith("zip"))
						unz = unzip3(f);
					else unz = ungzip(f);

					if(unz!=null)
						allfiles.add(unz);
					f.delete();
				}
			}
			tais.close();
		}
		catch(IOException ioe){
			jlog.fatal("IO read error :: "+ioe);
		}


	}

	/**
	 * ungzip. Given a gzip stream, decompress and store in file in temp_dir
	 */
	protected File ungzip(File gzf) throws FileNotFoundException {
		//File f = null;
		String rename = gzf.getAbsolutePath().replaceFirst("\\.gz", identifier+".xml");
		File f = new File(rename);
		try {
			FileInputStream fis = new FileInputStream(gzf);
			FileOutputStream fos = new FileOutputStream(rename);
			GzipCompressorInputStream gzin = new GzipCompressorInputStream(fis);
			final byte[] content = new byte[BUFFER];
			int n = 0;
			while (-1 != (n = gzin.read(content))) {
			    fos.write(content, 0, n);
			}

			fos.flush();
			fos.close();

			fis.close();
			gzin.close();
		}
		catch (IOException ioe) {
			jlog.error("Error processing GZip "+gzf+" Excluding! :: "+ioe);
			return null;
		}
		//try again... what could go wrong
		if (checkMinFileSize(f) && retry_counter<MAX_UNGZIP_RETRIES){
			retry_counter++;
			f.delete();
			f = ungzip(gzf);
		}
		return f;
	}

	private boolean checkMinFileSize(File f){
		if (f.length()<MIN_FILE_SIZE) return true;
		return false;
	}

}
