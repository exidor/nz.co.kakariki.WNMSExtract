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

public class TestParseNodeB extends TestCase{
	public static final String node = "NodeB";
	String dv1,tv1;
	/**
	 * Sets up the test fixture.
	 * Called before every test case method.
	 */
	@Override
	protected void setUp() {
		TestDBUtilities.delete("raw_nodeb_btscell");
		TestDBUtilities.delete("raw_nodeb_ccm");
		TestDBUtilities.delete("raw_nodeb_cem");
		TestDBUtilities.delete("raw_nodeb_hsdpaservice");
		TestDBUtilities.delete("raw_nodeb_hsupaservice");
		TestDBUtilities.delete("raw_nodeb_imagroup");
		TestDBUtilities.delete("raw_nodeb_ipran");
		TestDBUtilities.delete("raw_nodeb_nodebequipment");
		TestDBUtilities.delete("raw_nodeb_pa");

		TestDBUtilities.delete("int_nodeb_papwr_t");
		TestDBUtilities.delete("int_nodeb_t");
		TestDBUtilities.delete("int_cell3g_traffic_t");//raw_rnccn_utrancell OR raw_nodeb_hsdpaservice OR map_bts_fdd
		TestDBUtilities.delete("int_rnc_traffic_t");//raw_rnccn_utrancell OR raw_nodeb_hsdpaservice


		WNMSTransform t = new WNMSTransform();
		t.setDocumentType(DocumentType.WNMS_NodeB);
		t.setDatabaseName(TestDBUtilities.DEF_DBNM);
		t.process("test/test.nodeb.xml");
		tv1 = "MMMUU";

	}

	/**
	 * Tears down the test fixture.
	 * Called after every test case method.
	 */
	@Override
	protected void tearDown() {
		TestDBUtilities.log("raw_nodeb_btscell","INSERT");
		TestDBUtilities.log("raw_nodeb_ccm","INSERT");
		TestDBUtilities.log("raw_nodeb_cem","INSERT");
		TestDBUtilities.log("raw_nodeb_hsdpaservice","INSERT");
		TestDBUtilities.log("raw_nodeb_hsupaservice","INSERT");
		TestDBUtilities.log("raw_nodeb_imagroup","INSERT");
		TestDBUtilities.log("raw_nodeb_ipran","INSERT");
		TestDBUtilities.log("raw_nodeb_nodebequipment","INSERT");
		TestDBUtilities.log("raw_nodeb_pa","INSERT");
	}

	/**
	 * Test
	 */

	@Test
	public void testRead_raw_nodeb_btscell(){
		assertEquals(testReadRaw("raw_nodeb_btscell"),tv1);
	}

	@Test
	public void testRead_raw_nodeb_ccm(){
		assertEquals(testReadRaw("raw_nodeb_ccm"),tv1);
	}

	@Test
	public void testRead_raw_nodeb_cem(){
		assertEquals(testReadRaw("raw_nodeb_cem"),tv1);
	}

	@Test
	public void testRead_raw_nodeb_hsdpaservice(){
		assertEquals(testReadRaw("raw_nodeb_hsdpaservice"),tv1);
	}

	@Test
	public void testRead_raw_nodeb_hsupaservice(){
		assertEquals(testReadRaw("raw_nodeb_hsupaservice"),tv1);
	}

	@Test
	public void testRead_raw_nodeb_imagroup(){
		assertEquals(testReadRaw("raw_nodeb_imagroup"),tv1);
	}

	@Test
	public void testRead_raw_nodeb_ipran(){
		assertEquals(testReadRaw("raw_nodeb_ipran"),tv1);
	}

	@Test
	public void testRead_raw_nodeb_nodebequipment(){
		assertEquals(testReadRaw("raw_nodeb_nodebequipment"),tv1);
	}

	@Test
	public void testRead_raw_nodeb_pa(){
		assertEquals(testReadRaw("raw_nodeb_pa"),tv1);
	}

	public String testReadRaw(String table){
		String val = TestDBUtilities.select(table,"nbid");
		System.out.println("<"+node+">"+table+":["+val+","+tv1+"]");
		return val;
	}

}
