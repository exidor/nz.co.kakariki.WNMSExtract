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

public class TestCopyNodeB extends TestCase{
	public static final String node = "NodeB";
	String dv1,tv1;
	/**
	 * Sets up the test fixture.
	 * Called before every test case method.
	 */
	@Override
	protected void setUp() {

	}

	/**
	 * Tears down the test fixture.
	 * Called after every test case method.
	 */
	@Override
	protected void tearDown() {
		// release objects under test here, if necessary
	}

	/**
	 * Test
	 */
	
	@Test
	public void testRead_int_nodeb_papwr_t(){
		assertNotNull(testReadInt("int_nodeb_papwr_t","nbid"));
	}
	
	@Test
	public void testRead_int_nodeb_t(){
		assertNotNull(testReadInt("int_nodeb_t"));
	}
	
	@Test
	public void testRead_int_cell3g_traffic_t(){
		assertNotNull(testReadInt("int_cell3g_traffic_t","rcid"));
	}

	@Test
	public void testRead_int_rnc_traffic_t(){
		assertNotNull(testReadInt("int_rnc_traffic_t"));
	}
	
	public String testReadInt(String table,String field){
		String val = TestDBUtilities.select(table,field);
		System.out.println("<"+node+">"+table+":["+val+"]");
		return val;
	}
	
	public String testReadInt(String table){
		return testReadInt(table,"id");
	}
}
