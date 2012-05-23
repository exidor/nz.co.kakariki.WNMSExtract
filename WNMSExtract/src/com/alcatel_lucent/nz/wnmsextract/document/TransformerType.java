package com.alcatel_lucent.nz.wnmsextract.document;

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

/*alu:TNZ files requiring custom alu namespace prefix, html:GTA files with embedded html namespace*/
/*static:md tags referenced by static position number (faster), dynamic:tag values found with dynamic tagname lookup*/

/**
 * Basic enum for the 4 different XSL needed to transform WNMS data files. 
 */
public enum TransformerType {
	AS("alu.static.xsl"),
	AD("alu.dynamic.xsl"),
	HS("html.static.xsl"),
	HD("html.dynamic.xsl");

	String suffix;
	TransformerType(String suffix){
		this.suffix = suffix;

	}
	public String getSuffix(){return this.suffix;}

	public static TransformerType selectTransformer(boolean vp, boolean ns) {
		if(vp){
			if(ns) return TransformerType.HD;
			else return TransformerType.AD;
		}
		else{
			if(ns) return TransformerType.HS;
			else return TransformerType.AS;
		}

	}

}
