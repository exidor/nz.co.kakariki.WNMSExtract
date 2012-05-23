package com.alcatel_lucent.nz.wnmsextract.database.wips.nodeb;

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
import java.util.ArrayList;

import org.apache.xmlbeans.XmlObject;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.nodeb.RncNodeBType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.nodeb.RncType;

/**
 * WiPS snapshot RncNodeB table inserter. 
 * @author jnramsay
 */
public class SNAPRncNodeBTable {

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject) {
		RncNodeBType rncnb = (RncNodeBType) xmlobject;
		String rnbid = rncnb.getId();// don't really need this

		for (RncType rnc : rncnb.getRncArray()) {
			SNAPRncTable.insertData(ajc, rnc, rnbid);

		}
	}

	public static ArrayList<String> prepareHeader() {
		return null;

	}

}
