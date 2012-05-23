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
 * JDC Connector implementation for TNZ NZRSDB database
 * @author jnramsay
 *
 */
public class TNZNZRSDBJDCConnector implements ALUJDCConnector{

	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.database.NZRSDBJDCConnector");

	ALUConnectionDefinitions connectiondefinitions;

	private static final String DEF_USER = "pguser";
	private static final String DEF_PASS = "pgpass";
	private static final String DEF_HOST = "139.188.42.164";
	private static final String DEF_PORT = "5432";
	private static final String DEF_DBNM = "npo";

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

	/**
	 * Definitions constructor calls initialiser
	 */
	public TNZNZRSDBJDCConnector(ALUConnectionDefinitions cd){
		setConnectionDefinitions(cd);
		init();
	}

	/**
	 * Initialiser starts up a new driver based on DEF User/pass. Connection Defs 
	 * define prefix and driver type
	 */
	public void init(){
		jlog.info("Setup connection NZRSDB for TNZ JDC :: "+url());

		try{
			new JDCConnectionDriver(driver(),url(),DEF_USER, DEF_PASS);
		}
		catch(Exception e){
			System.err.println("Error building JDCConnectionDriver :: "+e);

		}
		jlog.info("Connection complete, NZRSDB JDC :: "+url());
	}

	/**
	 * Wrapper for static DriverManager connection fetch 
	 */
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

	/**
	 * Connections defn prefix and default host etc provide the URL for 
	 * this connection
	 * @return URL string
	 */
	private String url(){
		return connectiondefinitions.prefix()+"://"+DEF_HOST+":"+DEF_PORT+"/"+DEF_DBNM;
	}

	/**
	 * Connections defn specified driver
	 * @return Driver name
	 */
	private String driver(){
		return connectiondefinitions.driver();
	}

	@Override
	public String toString(){
		return "NPO:"+connectiondefinitions.driver();
	}

}
