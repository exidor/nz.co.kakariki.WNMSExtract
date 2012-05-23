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
/**
 * @author Ryan Scott
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alcatel_lucent.nz.wnmsextract.document.ALUFileFilter;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;

/** 
 * This is the old method for unzipping files. it was a bit clunky so has
 * been replaced with FileSelector. Some scripts may still rely on it 
 * however, like Ryan's excel conversion stuff */

@Deprecated
//public class FileUtilities implements FileSelector
public class FileUtilities extends FileSelector
{
	private static final String CDIR = File.separator+"~COMPRESSED"+File.separator;
	private static final String RDIR = File.separator+"RAW"+File.separator;


	//private static boolean debug = false;
	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.FileUtilities");


	private static FileUtilities fileutility;

	private List<File> xmlFiles = new ArrayList<File>();

	private Calendar cal;
	private File sourcepath;
	private File temppath;


	//Singleton class
	private FileUtilities(){}
	public static synchronized FileUtilities getInstance()
	{
		if(fileutility == null){
			fileutility = new FileUtilities();
		}
		return fileutility;
	}


	// Returns a list of files of XMLs that need to be processed
	@Override
	public void extract()
	{
		File source_raw = new File(getSourcePath().getAbsolutePath()+RDIR);
		File source_compressed = getSourcePath();//+CDIR);
		File dest_local = getTempPath();
		File dest_localdate = new File(getTempPath().getAbsolutePath()+calendarToString(cal));

		// Delete the previous days data
		deletePreviousDay(cal,getTempPath().getAbsolutePath());

		// Set up the filefilter
		//ALUFileFilter filefilter = (ALUFileFilter) doctype.getFileFilter();
		//filefilter.setAcceptDate(cal);

		jlog.info("Extract [RAW:"+source_raw+", CMP:"+source_compressed+"]");

		// Flag for if we have found data for the specified date
		boolean dataFound = false;

		// Used to store the filtered XML files



		//---------------------------------------------------------------------------
		// Check cwd for current zip
		boolean zipPresentCwd = false;
		for(File f1 : dest_local.listFiles())
		{
			// Check if we have the folder
			if(f1.getName().compareTo(calendarToString(cal))==0)
			{
				zipPresentCwd = true;
				dataFound = true;
				break;
			}
		}

		jlog.debug("Zip Present CWD "+zipPresentCwd);

		//---------------------------------------------------------------------------
		// If data isn't present in cwd we need to get it
		if (zipPresentCwd == false)
		{
			boolean zipPresentCompressed = false;

			// Check for the input zip in the compressed folder
			for(File file_in_compressed : source_compressed.listFiles())
			{
				// Check if we have the .zip file
				if(file_in_compressed.getName().compareTo(calendarToString(cal)+".zip")==0)
				{
					zipPresentCompressed = true;
					dataFound = true;
					break;
				}
			}
			// If its in the compressed folder we need to decompress it,
			jlog.debug("Zip Present ~COMPRESSED "+zipPresentCompressed);
			if(zipPresentCompressed)
			{
				decompressCompressed(source_compressed, cal, dest_local);

			}

			// Else we check if its in the RAW folder
			else
			{
				// Assuming if there is files in the RAW directory the data is there
				if ((new File(getSourcePath().getAbsolutePath()+CDIR)).listFiles().length != 0)
				{
					decompressRaw(source_raw,cal,dest_localdate);
					dataFound = true;
				}
			}
		}


		//---------------------------------------------------------------------------
		// If we have found the data and copied to temppath
		jlog.debug("Data Found "+dataFound);

		if(dataFound)
		{

			// Take a list of all the gzips
			List<File> gzips = Arrays.asList((new File(temppath+calendarToString(cal))).listFiles());

			// Decompress all the files from the temppath to XML if its not already in the cwd
			if(zipPresentCwd == false)
			{
				decompressGZip(gzips);
			}

			// Take a list of all the files after uncompressing
			List<File> all = Arrays.asList((new File(temppath+calendarToString(cal))).listFiles());

			// Loop through all the files and create a new file list of all the XML files in the temppath
			for(File f1 : all)
			{
				// Check if it is accepted by the file filter
				//if(filefilter.accept(f1))

					// Check to see if the size is zero then do what?
					xmlFiles.add(f1);
			}


		}
		// We haven't found the data anywhere, we have a problem
		else
		{
			System.out.println("Data not found");
		}

		//System.out.println("if we've made it to here extract hasn't crashed (though it may not have worked)");
		jlog.debug("XMLFILES for ?<<<");
		for(File xf : xmlFiles){
			jlog.debug(xf);
		}
		jlog.debug(">>>");

	}


	// Returns a list of files that conform to the filter
	@SuppressWarnings("unused")
	private List<File> getFileType(DocumentType doctype, Calendar cal, File temppath)
	{
		//Set the date to filter on
		ALUFileFilter ff = (ALUFileFilter) doctype.getFileFilter();
		ff.setAcceptDate(cal);

		// Loop through all the files in the temppath
		List<File> filteredFiles = new ArrayList<File>();
		for(File f3 : (temppath).listFiles())
		{
			// If the filter is accepted add the files to the new list
			if (ff.accept(f3))
			{
				filteredFiles.add(f3);
			}
		}
		return filteredFiles;
	}

	// Copies all the files from the source directory to the dest directory
	public void decompressRaw(File source, Calendar cal, File dest)
	{
		boolean foundData = false;
		//File sourceFolder = null;

		// Try find the directory
		for(File f : source.listFiles())
		{
			if((f.getName().compareTo(calendarToString(cal))==0) && f.isDirectory())
			{
				foundData = true;
				copyFile(f, dest, true);
				break;
			}
		}

		if(!foundData)
		{
			jlog.fatal("Couldn't create directory : " + dest.getAbsolutePath()+File.separator);
		}




		return;
	}

	// Decompresses the zip from the compressed CPV to the destination
	public void decompressCompressed(File sourcepath, Calendar cal, File destpath)
	{
		File sourcezip = null;
		File destzip = null;

		// Try get the input zip, should be path + %date%.zip
		try
		{
			sourcezip  = new File(sourcepath.getAbsolutePath()+File.separator+calendarToString(cal)+".zip");

		}
		catch(NullPointerException e)
		{
			jlog.fatal("Can't find zip file" + calendarToString(cal)+".zip "+e);
			// Kill thread?
		}

		// Try open the output zip, want it to be in temppath\%date%.zip
		try
		{
			destzip = new File(destpath.getAbsolutePath()+File.separator+calendarToString(cal)+".zip");
		}
		catch(NullPointerException e)
		{
			jlog.fatal("Can't create output zip file "+e);
		}

		// Try copy the file to the temppath
		if(!copyFile(sourcezip, destzip, false))
		{
			jlog.info("Failed copying source zip "+sourcezip.getAbsolutePath());
			if(!copyFile(sourcezip, destzip, false))
			{
				jlog.info("Second attempt failed copying source zip "+sourcezip.getAbsolutePath());
				// kill thread
				return;
			}
		}


		//Recursively decompress the zip from temppath -> temppath\%date%\
		File decompressPath = new File(destpath.getAbsolutePath()+File.separator+calendarToString(cal)+File.separator);
		decompressZip(destzip,decompressPath);

		// Delete source file
		deleteFile(destzip);

	}

	// Get the checksum of a file
	public long checksum(File input)
	{
		long checksum = -1;
		 try
		 {
			 FileInputStream fis = null;
			 CheckedInputStream cis = null;
			 Adler32 adler = null;
			 fis = new FileInputStream(input);
			 adler = new Adler32();
			 cis = new CheckedInputStream(fis, adler);

		      byte[] buffer = new byte[1024];
		      while(cis.read(buffer)>=0)
		      {
		        checksum = cis.getChecksum().getValue();
		      }

		    }
		    catch(IOException e)
		    {
		    	jlog.fatal("IO Exception on " + input.getAbsolutePath() + e);
		    }

	    return checksum;

	}

	// Recursively decompress a Zip, will decompress to the temppath
	public void decompressZip (File inputZipPath, File zipPath)
	{
		int BUFFER = 2048;
		List<File> zipFiles = new ArrayList<File>();

		try
		{
			zipPath.mkdir();
		}
		catch(SecurityException e)
		{
			jlog.fatal("Security exception when creating " + zipPath.getName());

		}
		ZipFile zipFile = null;
		boolean isZip = true;

		// Open Zip file for reading (should be in temppath)
		try
		{
			zipFile = new ZipFile(inputZipPath, ZipFile.OPEN_READ);
		}
		catch(IOException e)
		{
			jlog.fatal("IO exception in " + inputZipPath.getName());
		}

		// Create an enumeration of the entries in the zip file
		Enumeration<? extends ZipEntry> zipFileEntries = zipFile.entries();
		if (isZip)
		{
			// Process each entry
			while (zipFileEntries.hasMoreElements())
			{
				// Get a zip file entry
				ZipEntry entry = zipFileEntries.nextElement();

				String currentEntry = entry.getName();
				File destFile = null;

				// destFile should be pointing to temppath\%date%\
				try
				{
					destFile = new File(zipPath.getAbsolutePath(), currentEntry);
					destFile = new File(zipPath.getAbsolutePath(), destFile.getName());
				}
				catch(NullPointerException e)
				{
					jlog.fatal("File not found" + destFile.getName());
				}


				// If the entry is a .zip add it to the list so that it can be extracted
				if (currentEntry.endsWith(".zip"))
				{
					zipFiles.add(destFile);
				}

				try
				{
					// Extract file if not a directory
					if (!entry.isDirectory())
					{
						// Stream the zip entry
						BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));

						int currentByte;
						// establish buffer for writing file
						byte data[] = new byte[BUFFER];
						FileOutputStream fos = null;

						// Write the current file to disk
						try
						{
							fos = new FileOutputStream(destFile);
						}

						catch(FileNotFoundException e)
						{
							jlog.fatal("File not found " + destFile.getName());
						}

						catch(SecurityException e)
						{
							jlog.fatal("Access denied to " + destFile.getName());
						}

						BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

						// read and write until last byte is encountered
						while ((currentByte = is.read(data, 0, BUFFER)) != -1)
						{
							dest.write(data, 0, currentByte);
						}
						dest.flush();
						dest.close();
						is.close();

					}
				}

				catch (IOException ioe)
				{
					jlog.fatal("IO exception in  " + zipFile.getName());
				}
			}
			try
			{
				zipFile.close();
			}
			catch (IOException e)
			{
				jlog.fatal("IO exception when closing  " + zipFile.getName());
			}
		}

		// Recursively decompress the list of zip files
		for(File f : zipFiles)
		{
			decompressZip(f, zipPath);
		}




		return;
	}


	// Decompress a list of Gzips to the same folder they came from!!!
	public void decompressGZip(List<File> fileList)
	{
		List<File> failedFiles = new ArrayList<File>();

		for (File f1 : fileList)
		{
			if(f1.getName().endsWith(".gz"))
			{
				// if it fails
				if(!decompressGZipFile(f1))
				{
					failedFiles.add(f1);
				}
			}
		}


		// Check for failed files
		if(failedFiles.size()>0)
		{
			for (File f1 : failedFiles)
			{
				if(f1.getName().endsWith(".gz"))
				{
					// if it fails again the file is probably a dud
					if(!decompressGZipFile(f1))
					{
						jlog.fatal(".gz file not extracted: " + f1.getName());
						deleteFile(f1);
					}
				}
				// Should never get here
				else
				{
					jlog.fatal("Failed extraction, file being deleted: " + f1.getName());
					deleteFile(f1);

				}
			}
		}

		return;
	}

	// Decompresses a GZIP file
	public boolean decompressGZipFile(File input)
	{
		// Open the compressed file
		GZIPInputStream in = null;

		// If the file is of zero length delete it
		if(input.length()==0)
		{
			deleteFile(input);
			return true;
		}

		try
		{
			in = new GZIPInputStream(new FileInputStream(input));
		}
		catch (FileNotFoundException e)
		{
			jlog.fatal(".gz file not found: " + input);
			e.printStackTrace();
			return false;
		}
		catch (IOException e)
		{
			jlog.fatal("IO exception on: " + input);
			e.printStackTrace();
			return false;
		}

		// Open the output file
		String target = input.getAbsolutePath().replace(".gz", ".xml");
		OutputStream out = null;
		try
		{
			out = new FileOutputStream(target);
		}
		catch (FileNotFoundException e)
		{
			jlog.fatal("File not found: " + target);
			e.printStackTrace();
			return false;
		}

		// Transfer bytes from the compressed file to the output file
		byte[] buf = new byte[1024];
		int len;
		try
		{
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
		}
		catch (IOException e)
		{
			jlog.fatal("IO exception when writing to " + target);
			e.printStackTrace();
			return false;
		}
		finally
		{
			// Close the file and stream
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				jlog.fatal("Can't close input stream");
				e.printStackTrace();
				return false;
			}
			try
			{
				out.close();
			}
			catch (IOException e)
			{
				jlog.fatal("Can't close output stream");
				e.printStackTrace();
				return false;
			}
		}

		// Check if the gzip file size == 0
		File outputFile = new File(target);

		// If the file is of zero length something bad happened
		if(outputFile.length()==0)
		{
			return false;
		}



		// Delete the file
		deleteFile(input);
		return true;

	}

	// Delete a file, input a string of the path
	@SuppressWarnings("unused")
	private void deleteFile(String filename)
	{
		deleteFile(new File(filename));
	}

	// Delete a file
	public void deleteFile(File file)
	{
		file.delete();
		return;
	}

	// Copy a file to another
	public boolean copyFile(File in, File out, boolean filesOnly)
	{
		if(in.isDirectory())
		{
			try
			{
				if(filesOnly)
				{
					String [] params = {"zip", "gz"};
					for(Object obj : FileUtils.listFiles(in, params, true))
					{
						File x = (File)obj;
						if(x.getName().endsWith(".zip"))
						{
							decompressZip(x, out);
						}
						FileUtils.copyFileToDirectory(x,out);
					}
				}
				else
				{
					FileUtils.copyDirectory(in, out);
				}
			}
			catch (IOException e)
			{
				jlog.fatal("Failed to copy directory: " + in.getAbsolutePath());
				return false;
			}
		}
		else
		{
			try
			{
				FileUtils.copyFile(in, out);
			}
			catch (IOException e)
			{
				jlog.fatal("Failed to copy file: " + in.getAbsolutePath());
				return false;
			}
		}
		return true;

	}


	// Returns a string of the calendar in the format yyyyMMdd
	public static String calendarToString(Calendar cal)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return(dateFormat.format(cal.getTime()));
	}

	/**
	 * Deletes any files downloaded from the previous day
	 * @param cal. The day being processed NOT the day being deleted
	 */
		public static boolean deletePreviousDay(Calendar today, String temppath){
			Calendar yesterday = (Calendar)today.clone();
			yesterday.add(Calendar.DATE, -2);
			File to_delete = new File(temppath+calendarToString(yesterday));
			return to_delete.delete();

		}

	//Autogenerated/replaced interface matching methods

	//@Override
	@Override
	public void setCalendar(Calendar cal) {
		this.cal = cal;
	}

	/**
	 * fetchFileList. Returns a list of files matching the requested doctype in the current extract directory
	 * @param doctype
	 * @return
	 */
	//@Override
	public List<File> getFileList(DocumentType doctype){
		//Set the date to filter on
		ALUFileFilter ff = (ALUFileFilter) doctype.getFileFilter();
		ff.setAcceptDate(cal);

		// Loop through all the files in the temppath
		List<File> filteredFiles = new ArrayList<File>();
		for(File f3 : (getTempPath()).listFiles())
		{
			// If the filter is accepted add the files to the new list
			if (ff.accept(f3))
			{
				filteredFiles.add(f3);
			}
		}
		return filteredFiles;
	}

	//autogen/replaced interface methods

	//@Override
	@Override
	public void setSourcePath(File sourcepath) {
		this.sourcepath = sourcepath;

	}

	@Override
	public File getSourcePath(){
		return this.sourcepath;
	}

	//@Override
	@Override
	public void setTempPath(File temppath) {
		this.temppath = temppath;

	}

	@Override
	public File getTempPath(){
		return this.temppath;
	}
	
	
	@Override
	public String docPath() {
		// TODO Auto-generated method stub
		return null;
	}

}