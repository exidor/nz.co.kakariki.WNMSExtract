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

/**
 * Database default connection definitions
 */
public enum ALUConnectionDefinitions {
	POSTGRESQL("jdbc:postgresql","org.postgresql.Driver",5432),//v>6.5
	FIREBIRD("jdbc:odbc:cdmaCapacityDB","org.firebirdsql.jdbc.FBDriver",3050),
	MYSQL("jdbc:mysql","com.mysql.jdbc.Driver",3306),//
	ORACLE("jdbc:oracle:oci","oracle.jdbc.driver.OracleDriver",1524),//oci=9,oci8=8
	MSSQL("jdbc:microsoft:sqlserver","com.microsoft.jdbc.sqlserver.SQLServerDriver",1433),
	MSJET("jdbc:odbc:cdmaCapacityDB","sun.jdbc.odbc.JdbcOdbcDriver",0);//no port?
	
	private final String prefix;
	private final String driver;	
	private final int port;
	private ALUConnectionDefinitions(String pr, String dr, int po){
			this.prefix = pr;
			this.driver = dr;
			this.port = po;
	}
	public String prefix(){return prefix;}
	public String driver(){return driver;}
	public int port(){return port;}
}
