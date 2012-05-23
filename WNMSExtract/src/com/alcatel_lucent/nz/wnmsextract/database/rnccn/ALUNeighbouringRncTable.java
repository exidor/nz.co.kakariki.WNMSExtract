package com.alcatel_lucent.nz.wnmsextract.database.rnccn;

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
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.rnccn.NeighbouringRncType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.rnccn.RncFunctionType;

/*
 drop table raw_rnccn_neighbouringrnc;
 create table raw_rnccn_neighbouringrnc(
 rcid varchar(12),
 ts timestamp,
 rfid int,
 nrid int,
 RABAttEstabPSTrChnNeighbRncDCH_HSDSCH int,
 RABAttEstabPSTrChnNeighbRncEDCH_HSDSCH int,
 constraint  raw_rnccn_neighbouringrnc_pk primary key (rcid,ts,rfid,nrid)
 );
 */

/**
 * NeighbouringRnc table inserter. Child of RncFunction table.
 * @author jnramsay
 */
public class ALUNeighbouringRncTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.rnccn.ALUNeighbouringRncTable");

	public static final String TABLE_NAME = "raw_rnccn_neighbouringrnc";

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject, String rcid, Timestamp ts) {
		RncFunctionType rft = (RncFunctionType) xmlobject;
		int rfid = Integer.parseInt(rft.getId());

		StringBuffer sql1 = new StringBuffer("INSERT INTO " + TABLE_NAME + " (");
		StringBuffer sql2 = new StringBuffer(") VALUES (");
		for (String col : prepareHeader()) {
			sql1.append(col + ",");
			sql2.append("?,");
		}
		// System.out.println();
		String sql = sql1.deleteCharAt(sql1.length() - 1).toString() + sql2.deleteCharAt(sql2.length() - 1).toString() + ")";

		for (NeighbouringRncType nrt : rft.getNeighbouringRncArray()) {

			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = ajc.getConnection();
				ps = con.prepareStatement(sql);

				ps.setString(1, rcid);
				ps.setTimestamp(2, ts);
				ps.setInt(3, rfid);
				ps.setInt(4, Integer.parseInt(nrt.getId()));
				ps.setLong(5, nrt.getRABAttEstabPSTrChnNeighbRncDCHHSDSCH().longValue());
				ps.setLong(6, nrt.getRABAttEstabPSTrChnNeighbRncEDCHHSDSCH().longValue());

				jlog.debug("PS::" + ps.toString());
				ps.executeUpdate();

			} catch (XmlValueOutOfRangeException xore) {
				System.err.println("Value in transformed XML does not subscribe to XSD defn for " + TABLE_NAME + " :: " + xore);
			} catch (SQLException sqle) {
				if (!sqle.toString().contains("duplicate key violates unique constraint"))
					System.err.println("Problem mapping to DB " + TABLE_NAME + ">" + ps + " :: " + sqle);
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

		row.add("rcid");
		row.add("ts");
		row.add("rfid");
		row.add("nrid");
		row.add("RABAttEstabPSTrChnNeighbRncDCH_HSDSCH");
		row.add("RABAttEstabPSTrChnNeighbRncEDCH_HSDSCH");

		return row;

	}

}
