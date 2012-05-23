package com.alcatel_lucent.nz.wnmsextract.database.wips.fddbts;

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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.fddbts.BTSEquipmentType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.fddbts.FDDCellType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.fddbts.FddBtsType;

/*
 drop table snap_fdd_local;
 create table snap_fdd_local(
 fcid varchar(8),
 lcid int,
 constraint snap_fdd_local_pk primary key (fcid,lcid)
 );
 */

/**
 * WiPS snapshot FddBts table inserter. 
 * @author jnramsay
 */
public class SNAPFddBtsTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.wips.fddbts.SNAPFddBtsTable");

	public static final String TABLE_NAME = "snap_fdd_local";


	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject) {
		FddBtsType fbt = (FddBtsType) xmlobject;
		String fbid = fbt.getId();// don't really need this

		StringBuffer sql1 = new StringBuffer("INSERT INTO " + TABLE_NAME + " (");
		StringBuffer sql2 = new StringBuffer(") VALUES (");
		for (String col : prepareHeader()) {
			sql1.append(col + ",");
			sql2.append("?,");
		}
		// System.out.println();
		String sql = sql1.deleteCharAt(sql1.length() - 1).toString()
				+ sql2.deleteCharAt(sql2.length() - 1).toString() + ")";

		for (BTSEquipmentType bet : fbt.getBTSEquipmentArray()) {
			SNAPBTSEquipmentTable.insertData(ajc, bet, fbid);
		}
		for (FDDCellType fct : fbt.getFDDCellArray()) {
			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = ajc.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1, fct.getId()); // eg. APKNU1_1
				ps.setLong(2, fct.getLongValue()); // eg. 33741

				jlog.debug("PS::" + ps.toString());
				ps.executeUpdate();
			} catch (XmlValueOutOfRangeException xore) {
				System.err.println("Value in transformed XML does not subscribe to XSD defn for "+ TABLE_NAME + " :: " + xore);
			} catch (SQLException sqle) {
				System.err.println("Problem mapping to DB "+TABLE_NAME+">"+ps+" :: "+sqle);
			} finally {
				try {
					ps.close();
					con.close();
				} catch (Exception e) {
					System.err.println("Undefined Exception :: " + e);
				}
			}
		}
	}

	public static ArrayList<String> prepareHeader() {
		ArrayList<String> row = new ArrayList<String>();

		row.add("fcid");
		row.add("lcid");

		return row;
	}



}
