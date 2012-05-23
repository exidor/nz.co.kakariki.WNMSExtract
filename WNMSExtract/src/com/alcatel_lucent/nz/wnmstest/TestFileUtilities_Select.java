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
import java.util.Calendar;
import java.util.EnumSet;

import junit.framework.TestCase;

import org.junit.Test;

import com.alcatel_lucent.nz.wnmsextract.WNMSDataExtractor;
import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;

public class TestFileUtilities_Select extends TestCase {

	/**
	 * Sets up the test fixture.
	 */
	@Override
	protected void setUp() {

		Calendar cal = Calendar.getInstance();
		cal.set(2010, 5, 16);
		WNMSDataExtractor wde = new WNMSDataExtractor();
		wde.setAllDocs(EnumSet.of(DocumentType.WIPS_mFB));
		wde.setDatabaseType(DatabaseType.TEST);
		wde.setCalendar(cal);
		wde.init();
		wde.activate();

		
	}

	/**
	 * Tears down the test fixture.
	 * Called after every test case method.
	 */
	@Override
	protected void tearDown() {
		
	}

	
	@Test
	public void testFileSelection(){
		System.out.println("test print TestFileUtilities_Select");
	}
	

}
