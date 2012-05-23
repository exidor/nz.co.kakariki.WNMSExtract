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
import junit.framework.TestCase;

import org.junit.Test;

import com.alcatel_lucent.nz.wnmsextract.WNMSTransform;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;

public class TestParseMapFddBts extends TestCase{
	String dv1="",dv2="",tv1,tv2;

	/**
	 * Sets up the test fixture.
	 * Called before every test case method.
	 */
	@Override
	protected void setUp() {
		TestDBUtilities.delete("snap_fdd_local");
		TestDBUtilities.delete("snap_bts_local");
		WNMSTransform t = (new WNMSTransform());
		t.setDocumentType(DocumentType.WIPS_mFB);
		t.setDatabaseName(TestDBUtilities.DEF_DBNM);
		t.process("test/test0.xcm");
		tv1 = "AROSU3_4";
		tv2 = "APKNU";

	}

	/**
	 * Tears down the test fixture.
	 * Called after every test case method.
	 */
	@Override
	protected void tearDown() {

	}

	/**
	 * Tests reading a test xcm file.
	 */
	@Test
	public void testRead_snap_fdd_local(){
		dv1 = TestDBUtilities.select("snap_fdd_local","fcid");
		System.out.println("<FddBts>mFdd::["+dv1+","+tv1+"]");
		assertEquals(dv1,tv1);
	}

	/**
	 * Tests reading a test xcm file.
	 */
	@Test
	public void testRead_snap_bts_local(){
		dv2 = TestDBUtilities.select("snap_bts_local","beid");
		System.out.println("<FddBts>mBts:::["+dv1+","+tv1+"]");
		assertEquals(dv2,tv2);
	}
}
