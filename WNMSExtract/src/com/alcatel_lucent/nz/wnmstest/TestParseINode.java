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

public class TestParseINode extends TestCase{
	public static final String node = "INode";
	String dv1,tv1;
	/**
	 * Sets up the test fixture.
	 * Called before every test case method.
	 */
	@Override
	protected void setUp() {
		TestDBUtilities.delete("raw_inode_ap");
		TestDBUtilities.delete("raw_inode_atmport");
		TestDBUtilities.delete("raw_inode_ethernet");
		TestDBUtilities.delete("raw_inode_lp");

		TestDBUtilities.delete("int_rnc_ap_t");
		TestDBUtilities.delete("int_atmport_t");
		TestDBUtilities.delete("int_etherlp_t");
		TestDBUtilities.delete("int_lp_t");

		WNMSTransform t = (new WNMSTransform());
		t.setDocumentType(DocumentType.WNMS_INode);
		t.setDatabaseName(TestDBUtilities.DEF_DBNM);
		t.process("test/test.inode.xml");
		tv1 = "MDR_RNC02";

	}

	/**
	 * Tears down the test fixture.
	 * Called after every test case method.
	 */
	@Override
	protected void tearDown() {
		TestDBUtilities.log("raw_inode_ap","INSERT");
		TestDBUtilities.log("raw_inode_atmport","INSERT");
		TestDBUtilities.log("raw_inode_ethernet","INSERT");
		TestDBUtilities.log("raw_inode_lp","INSERT");
	}

	/**
	 * Tests
	 */
	@Test
	public void testRead_raw_inode_ap(){
		assertEquals(testReadRaw("raw_inode_ap"),tv1);
	}

	@Test
	public void testRead_raw_inode_atmport(){
		assertEquals(testReadRaw("raw_inode_atmport"),tv1);
	}

	@Test
	public void testRead_raw_inode_ethernet(){
		assertEquals(testReadRaw("raw_inode_ethernet"),tv1);
	}

	@Test
	public void testRead_raw_inode_lp(){
		assertEquals(testReadRaw("raw_inode_lp"),tv1);
	}

	public String testReadRaw(String table){
		String val = TestDBUtilities.select(table,"inid");
		System.out.println("<"+node+">"+table+":["+val+","+tv1+"]");
		return val;
	}

}
