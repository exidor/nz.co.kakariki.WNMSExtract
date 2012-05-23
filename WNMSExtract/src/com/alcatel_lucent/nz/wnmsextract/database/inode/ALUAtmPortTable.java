package com.alcatel_lucent.nz.wnmsextract.database.inode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inode.AtmPortType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inode.INodeType;

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

/*
 drop table raw_inode_atmport;
 create table raw_inode_atmport(
 inid varchar(12), 	-- Root INode
 ts timestamp,
 rid int, 	-- RncEquipment
 iid varchar(12), 	-- Inode
 apid int, 	-- AtmPort
 VSRxMaxCellRate int,
 VSTxMaxCellRate int,
 VSRxAvgCellRate float,
 VSTxAvgCellRate float,
 constraint  raw_inode_atmport_pk primary key (inid,ts,rid,iid,apid)
 );
 */

/**
 * AtmPort table inserter. Child of INodeTable
 * @author jnramsay
 */
public class ALUAtmPortTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.inode.ALUAtmPortTable");

	public static final String TABLE_NAME = "raw_inode_atmport";

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject, String inid, Timestamp ts, int rid) {
		INodeType it = (INodeType) xmlobject;
		String iid = it.getId();

		StringBuffer sql1 = new StringBuffer("INSERT INTO " + TABLE_NAME + " (");
		StringBuffer sql2 = new StringBuffer(") VALUES (");
		for (String col : prepareHeader()) {
			sql1.append(col + ",");
			sql2.append("?,");
		}
		// System.out.println();
		String sql = sql1.deleteCharAt(sql1.length() - 1).toString() + sql2.deleteCharAt(sql2.length() - 1).toString() + ")";

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ajc.getConnection();
			ps = con.prepareStatement(sql);

			for (AtmPortType apt : it.getAtmPortArray()) {

				ps.setString(1, inid);
				ps.setTimestamp(2, ts);
				ps.setInt(3, rid);
				ps.setString(4, iid);
				ps.setInt(5, Integer.parseInt(apt.getId()));
				ps.setLong(6, apt.getVSRxMaxCellRate().longValue());
				ps.setLong(7, apt.getVSTxMaxCellRate().longValue());
				ps.setFloat(8, apt.getVSRxAvgCellRate());
				ps.setFloat(9, apt.getVSTxAvgCellRate());

				jlog.debug("PS::" + ps.toString());
				ps.executeUpdate();
			}
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

	public static ArrayList<String> prepareHeader() {
		ArrayList<String> row = new ArrayList<String>();

		row.add("inid");
		row.add("ts");
		row.add("rid");
		row.add("iid");
		row.add("apid");
		row.add("VSRxMaxCellRate");
		row.add("VSTxMaxCellRate");
		row.add("VSRxAvgCellRate");
		row.add("VSTxAvgCellRate");

		return row;

	}

}
