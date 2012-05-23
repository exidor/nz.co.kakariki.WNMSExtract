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

public class TestParseCountNodeBPCM extends TestCase{
	String dv1,tv1;
	/**
	 * Sets up the test fixture.
	 * Called before every test case method.
	 */
	@Override
	protected void setUp() {
		TestDBUtilities.delete("snap_nodeb_pcm");
		WNMSTransform t = (new WNMSTransform());
		t.setDocumentType(DocumentType.WIPS_cNbP);
		t.setDatabaseName(TestDBUtilities.DEF_DBNM);
		t.process("test/test0.xcm");
		tv1 = "APKNU";

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
	public void testRead_snap_nodeb_pcm(){
		dv1 = TestDBUtilities.select("snap_nodeb_pcm","beid");
		System.out.println("<NodeBPCM>cNbP::["+dv1+","+tv1+"]");
		assertEquals(dv1,tv1);
	}
}
