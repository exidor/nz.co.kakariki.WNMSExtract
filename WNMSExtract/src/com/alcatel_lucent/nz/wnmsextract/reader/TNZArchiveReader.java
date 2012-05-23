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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

/*
 * FileProcess workflow.
 * 1) Instantiate a new instance
 * 2) Set date of interest with setCalendar(Calendar c)
 * 3) Call extract() to process selected date .tar
 * 	[SD/yyyyMMdd.tar -> TD/yyyMMdd/*.gz -> TD/yyyyMMdd/*.xml]
 * 4) Call fetchFileList(Doctype d) to return a list of available XML files
 */

/**
 * TNZ extension of fileselector. Differences from the abstract include
 * URL location, SSL auth and scrape vs unzip
 */
public class TNZArchiveReader extends FileSelector {

	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.TNZArchiveReader");

	//private static final int MIN_FILE_SIZE = 10;

	//private static final int MAX_UNGZIP_RETRIES = 100;

	/** Constant defining the number of files expected in an archive to consider it to have unzipped
	 * fully and/or not be corrupt */
	private static final int ENOUGH_FILES = 10000;

	//private static final int BUFFER = 2048;

	//private List<File> allfiles;

	//private int retry_counter;

	private static TNZArchiveReader reader;

	/** Singleton getInstance method */
	public static synchronized TNZArchiveReader getInstance()
	{
		if(reader == null){
			reader = new TNZArchiveReader();
		}
		return reader;
	}

	/** Private Constructor */
	private TNZArchiveReader(){

		this.retry_counter = 0;

		//default cal is yesterday
		this.calendar = Calendar.getInstance();
		this.calendar.add(Calendar.DATE,-1);

		//setSourcePath(new File(Extractor.chooseSourcePath()));
		//setTempPath(new File(Extractor.chooseTempPath()+"/"+calendarToString(this.calendar)));

		this.allfiles = new ArrayList<File>();

	}

	/**
	 * Extractor method which when called will check source_dir for new valid tar
	 * and process it to temp_dir for reading by parser
	 */
	@Override
	public void extract() {
		File f = null;
		try {
			//if the temp dir (eg c:\temp\TNZ) doesn't exist, create it
			if (checkAvailability(getTempPath().getAbsolutePath()) == null)
				getTempPath().mkdir();
			//if the date specific temp dir (eg C:\temp\TNZ\20101231) doesn't exist, create it
			if (checkAvailability(getCalTempPath().getAbsolutePath()) == null)
				getCalTempPath().mkdir();
			//if the temp dir is ~empty (less than N files in it) fill it
			if(getCalTempPath().listFiles().length<ENOUGH_FILES){
				//if tar is not available check for zip
				if((f = checkAvailability(getCalSourcePath()+"-VCC.tar")) == null){
					//if zip is not available quit
					if((f = checkAvailability(getCalSourcePath()+".zip")) == null){
						jlog.error("no zip/tar found matching "+expectedFileName());
						throw new FileNotFoundException("No tar/zip available for "+expectedFileName());
					}
					else
						unzip1(f);
				}
				else
					untar(f);
			}

		}
		catch (FileNotFoundException fnfe){
			System.err.println("Source file/data not avaliable "+fnfe);
			System.err.println("primary:"+getSourcePath()+File.separator+expectedFileName()+"(not necessarily the missing file)");
			System.exit(1);
		}
		//return f1;
	}


	//utility classes. this functionality has moved to the superclass. these are kept as comments
	//since their operation is slightly different and might be needed if file formats change again


/*	*//**
	 * extract tarfile to constituent parts processing gzips along the way
	 * yyyyMMdd.tar->/yyyyMMdd/INode-CH_RNC01/A2010...gz
	 *//*
	private void untar(File tarfile) throws FileNotFoundException {

		try {
			TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(tarfile));
			TarArchiveEntry t1 = null;
			while ((t1 = tais.getNextTarEntry())!=null){
				if(t1.isDirectory()){you can make directories here if you want}
				else{
					String fn = t1.getName().substring(t1.getName().lastIndexOf("/"));
					File f = new File(getTempPath()+fn);
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

					File unz = ungzip(f);
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

	*//**
	 * extract tarfile to constituent parts processing gzips along the way
	 * yyyyMMdd.zip->/yyyyMMdd/INode-CH_RNC01/A2010...zip
	 *//*
	private static void unzip1(File zipfile) throws FileNotFoundException {

		try {
			ZipArchiveInputStream zais = new ZipArchiveInputStream(new FileInputStream(zipfile));
			ZipArchiveEntry z1 = null;
			while ((z1 = zais.getNextZipEntry())!=null){
				if(z1.isDirectory()){you can make directories here if you want}
				else{
					String fn = z1.getName().substring(z1.getName().lastIndexOf("/"));
					File f = new File(getTempPath()+fn);
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

					File unz = unzip2(f);
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


	*//**
	 * ungzip. Given a gzip stream, decompress and store in file in temp_dir
	 *//*
	private File ungzip(File gzf) throws FileNotFoundException {
		//File f = null;
		String rename = gzf.getAbsolutePath().replaceFirst("\\.gz", ".xml");
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

	*//**
	 * ungzip. Given a gzip stream, decompress and store in file in temp_dir
	 *//*
	private File unzip2(File zf) throws FileNotFoundException {
		//File f = null;
		String rename = zf.getAbsolutePath().replaceFirst("\\.zip", ".xml");
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
	*/

	/**
	 * Check for file or directory in location. Return it if it exists
	 * @param file
	 * @return File returns if available
	 */
	private File checkAvailability(String file){
		if ((new File(file)).exists()){
			return new File(file);
		}
		else
			return null;
	}
	/*
	private boolean checkMinFileSize(File f){
		if (f.length()<MIN_FILE_SIZE) return true;
		return false;
	}
	 */

	private String expectedFileName(){
		return calendarToString(calendar);
	}

	/**
	 * A method for identifying similarly named files based on the relative location. Used for
	 * INodeVcc files which have the same name structure and INode files but are found in a different
	 * directory
	 * @return Path fragment
	 */
	@Override
	public String docPath(){
		switch(doctype){
		case WNMS_INodeVcc:
			return "account";
		default:
			return "stats";
		}
	}



}
