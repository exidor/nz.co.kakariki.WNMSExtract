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
import org.apache.log4j.Logger;


/**
 * Factory class to select JDC connector types based on the required database type
 * @author jnramsay
 *
 */
public class ALUJDCConnectorFactory {

	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnectorFactory");

	public static DatabaseType DEFTYPE = DatabaseType.TEST;

	private DatabaseType currentType;

	/**
	 * Null constructor initialising with the default DB type
	 */
	public ALUJDCConnectorFactory(){
		this(DEFTYPE);
	}
	
	//constructors

	public ALUJDCConnectorFactory(String type){
		this(DatabaseType.valueOf(type.toUpperCase()));
	}
	public ALUJDCConnectorFactory(DatabaseType type){
		this.currentType = type;
		jlog.info("DBT:"+type);
	}

	/**
	 * Returns an instance of the requested JDC connector. Defaults to Test 
	 * or null if the you request something that doesnt exist
	 * @return JDC instance
	 */
	public ALUJDCConnector getInstance(){
		ALUConnectionDefinitions cd = currentType.getConnectionDefinitions();
		//return new TestJDCConnector(cd);

		switch(currentType){
		case NPO33:
			return new NPO33JDCConnector(cd);
		case NPO48:
			return new NPO48JDCConnector(cd);
		case TNZ_NZRSDB:
			return new TNZNZRSDBJDCConnector(cd);
		case GTA_NZRSDB:
				return new GTANZRSDBJDCConnector(cd);
		case MPM:
			return new MPMJDCConnector(cd);
		case TEST:
			return new TestJDCConnector(cd);
		default:
			return null;
		}

	}

}
