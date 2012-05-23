package com.alcatel_lucent.nz.wnmsextract.parser;
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
 * Factory class to set the parser based on type of file 
 * being processed
 */
public class ALUParserFactory {
	
	public static PType DEFTYPE = PType.WNMS;
	public enum PType {WNMS,WIPS};
	
	private PType currentType;
	
	public ALUParserFactory(){
		this(DEFTYPE);
	}

	public ALUParserFactory(String type){
		this(PType.valueOf(type));
	}
	public ALUParserFactory(PType type){
		this.currentType = type;
	}
	
	
	public ALUParser getInstance(){
		switch(currentType){
		case WNMS:
			return new WNMSParser();
		case WIPS:
			return new WIPSParser();
		default:
			return new WNMSParser();
		}
	}


}

