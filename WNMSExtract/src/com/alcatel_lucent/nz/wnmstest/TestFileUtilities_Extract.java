package com.alcatel_lucent.nz.wnmstest;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;
import com.alcatel_lucent.nz.wnmsextract.reader.FileSelector;
import com.alcatel_lucent.nz.wnmsextract.reader.TNZArchiveReader;

public class TestFileUtilities_Extract extends TestCase {
	List<File> lf = null;
	/**
	 * Sets up the test fixture.
	 */
	@Override
	protected void setUp() {
		System.out.println("NO LONGER USING FILEUTILITIES, PREFER FILEPROCESSOR");
		Calendar cal = Calendar.getInstance();

		cal.set(2010, 6-1, 9);

		System.out.println((new SimpleDateFormat("yyyyMMdd")).format(cal.getTime()));
		String frompath = "f:\\misc\\dev\\workspace\\WNMSExtract\\Test\\";
		String topath = "f:\\misc\\dev\\workspace\\WNMSExtract\\Test\\Extract\\";

		FileSelector fs = TNZArchiveReader.getInstance();
		fs.setCalendar(cal);
		fs.setSourcePath(new File(frompath));
		fs.setTempPath(new File(topath));

		fs.extract();
		
		fs.setDocType(DocumentType.WNMS_NodeB);
		lf = fs.getFileList();

	}

	/**
	 * Tears down the test fixture.
	 * Called after every test case method.
	 */
	@Override
	protected void tearDown() {

	}


	@Test
	public void testFileExtraction(){
		for (File f : lf){
			System.out.println(f.getName());
		}
	}


}
