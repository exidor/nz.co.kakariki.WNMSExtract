package com.alcatel_lucent.nz.wnmsextract.database.inodevcc;
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
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.xmlbeans.XmlObject;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inodevcc.AtmPortType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inodevcc.INodeType;


/**
 * AtmPort table inserter. Child of INode has no data of its own as acts as
 * an intermediate step to its child table, VCC
 * @author jnramsay
 */
public class ALUAtmPortTable {

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject, String inid, Timestamp ts, int rid) {
		INodeType it = (INodeType) xmlobject;
		String iid = it.getId();

		for (AtmPortType apt : it.getAtmPortArray()) {
			ALUVccTable.insertData(ajc, apt, inid, ts, rid, iid);
		}

	}

	public static ArrayList<String> prepareHeader() {
		return null;

	}

}
