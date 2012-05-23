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
import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities;
import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;


public class TestDBUtilities extends ALUDBUtilities {
	
	protected static final DatabaseType DEF_DBNM = DatabaseType.TEST;
	protected static final String DEF_LTAB = "log_process";

	public static String select(String table,String field){
		return ALUDBUtilities.select(DEF_DBNM,table,field);
	}
	public static void delete(String table){
		ALUDBUtilities.delete(DEF_DBNM,table);
	}
	public static void log(String table,String operation){
		ALUDBUtilities.log(DEF_DBNM,table,operation);
	}
}
