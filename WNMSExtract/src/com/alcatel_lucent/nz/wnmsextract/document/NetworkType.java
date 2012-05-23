package com.alcatel_lucent.nz.wnmsextract.document;

import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;

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
 * Uber Enum used to store defaults based on network. Name defines exact 
 * string to enter on CL.
 */
public enum NetworkType {
	GTA("Teleguam GTA",DatabaseType.GTA_NZRSDB,ReaderType.GTAHttpReader,true),
	TNZ("Telecom New Zealand", DatabaseType.TNZ_NZRSDB,ReaderType.TNZArchiveReader,false);

	String description;
	
	/** database enum indicating which connector to use */
	DatabaseType databasetype;
	
	/** document reader enum indicating which reader subclass to use */
	ReaderType readertype;
	
	/** indicates whether data from this network uses a namespace prefix or not */
	boolean subnamespace;


	NetworkType(String description, DatabaseType databasetype, ReaderType readertype, boolean subnamespace){
		this.description = description;
		this.databasetype = databasetype;
		this.readertype = readertype;
		this.subnamespace = subnamespace;
	}

	public String getDescription(){return this.description;}
	public DatabaseType getDatabaseType(){return this.databasetype;}
	public ReaderType getReaderType(){return this.readertype;}
	public TransformerType getTransformerType(boolean dynamic){
		return TransformerType.selectTransformer(dynamic,subnamespace);
	}

}
