package com.alcatel_lucent.nz.wnmsextract.database;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;


/**
 * JDC Connector implementation for a 'TEST' database. NOT a unit test for JDC Connectors 
 * @author jnramsay
 *
 */
public class TestJDCConnector implements ALUJDCConnector{

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.TestJDCConnector");

	ALUConnectionDefinitions connectiondefinitions;

	private static final String DEF_USER = "pguser";
	private static final String DEF_PASS = "pgpass";
	private static final String DEF_HOST = "139.188.126.33";
	private static final String DEF_PORT = "5432";
	private static final String DEF_DBNM = "test";
	
	/*
	static {
	try{
		new JDCConnectionDriver("org.postgresql.Driver","jdbc:postgresql","pguser","pgpass");
	}
	catch(Exception e){
		System.err.println("Error building JDCConnectionDriver :: "+e);
		
	}
	}
	*/
	
	public TestJDCConnector(ALUConnectionDefinitions cd){
		setConnectionDefinitions(cd);
		init();
	}

	public void init(){
		jlog.info("Setup connection TestJDC :: "+url());
	
		try{
			new JDCConnectionDriver(driver(),url(),DEF_USER, DEF_PASS);
		}
		catch(Exception e){
			System.err.println("Error building JDCConnectionDriver :: "+e);
			
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:jdc:jdcpool");
	}


	public ALUConnectionDefinitions getConnectionDefinitions() {
		return connectiondefinitions;
	}

	public void setConnectionDefinitions(ALUConnectionDefinitions cd) {
		this.connectiondefinitions = cd;
	}

	private String url(){
		return connectiondefinitions.prefix()+"://"+DEF_HOST+":"+DEF_PORT+"/"+DEF_DBNM;
	}
	private String driver(){
		return connectiondefinitions.driver();
	}
	
	@Override
	public String toString(){
		return "Test:"+connectiondefinitions.driver();
	}

}
