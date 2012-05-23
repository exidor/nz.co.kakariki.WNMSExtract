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

public class TestParseRncCn extends TestCase{
	public static final String node = "RncCn";
	String dv1,tv1;
	/**
	 * Sets up the test fixture.
	 * Called before every test case method.
	 */
	@Override
	protected void setUp() {
		TestDBUtilities.delete("raw_rnccn_neighbouringrnc");
		TestDBUtilities.delete("raw_rnccn_rncfunction");
		TestDBUtilities.delete("raw_rnccn_utrancell");

		TestDBUtilities.delete("int_cell3g_bhi_t");
		TestDBUtilities.delete("int_cell3g_perf_t");
		TestDBUtilities.delete("int_cell3g_traffic_t");//raw_rnccn_utrancell OR raw_nodeb_hsdpaservice OR map_bts_fdd
		TestDBUtilities.delete("int_rnc_traffic_t");//raw_rnccn_utrancell OR raw_nodeb_hsdpaservice
		TestDBUtilities.delete("int_rnc_t");


		WNMSTransform t = (new WNMSTransform());
		t.setDocumentType(DocumentType.WNMS_RncCn);
		t.setDatabaseName(TestDBUtilities.DEF_DBNM);
		t.process("test/test.faulty.rnc.xml");
		tv1 = "MDR_RNC02";

	}

	/**
	 * Tears down the test fixture.
	 * Called after every test case method.
	 */
	@Override
	protected void tearDown() {
		TestDBUtilities.log("raw_rnccn_neighbouringrnc","INSERT");
		TestDBUtilities.log("raw_rnccn_rncfunction","INSERT");
		TestDBUtilities.log("raw_rnccn_utrancell","INSERT");
	}

	/**
	 * Tests reading a test xcm file.
	 */

	@Test
	public void testRead_raw_rnccn_neighbouringrnc(){
		assertEquals(testReadRaw("raw_rnccn_neighbouringrnc"),tv1);
	}

	@Test
	public void testRead_raw_rnccn_rncfunction(){
		assertEquals(testReadRaw("raw_rnccn_rncfunction"),tv1);
	}

	@Test
	public void testRead_raw_rnccn_utrancell(){
		assertEquals(testReadRaw("raw_rnccn_utrancell"),tv1);
	}

	public String testReadRaw(String table){
		String val = TestDBUtilities.select(table,"rcid");
		System.out.println("<"+node+">"+table+":["+val+","+tv1+"]");
		return val;
	}
}
