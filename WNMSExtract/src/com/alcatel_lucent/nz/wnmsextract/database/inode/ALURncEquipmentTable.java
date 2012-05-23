package com.alcatel_lucent.nz.wnmsextract.database.inode;
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
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inode.RncEquipmentType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inode.RootINodeType;


/**
 * Top level table inserter RNCEquipment. No data of it's own, acting
 * as intermediate step to child INodeTable
 * @author jnramsay
 *
 */
public class ALURncEquipmentTable {

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject) {
		RootINodeType rit = (RootINodeType) xmlobject;
		Timestamp ts = Timestamp.valueOf(rit.getTimestamp().replace("/", "-"));
		String riid = rit.getId();

		for (RncEquipmentType ret : rit.getRncEquipmentArray()) {
			ALUINodeTable.insertData(ajc, ret, riid, ts);
		}

	}

	public static ArrayList<String> prepareHeader() {
		return null;
	}

}
