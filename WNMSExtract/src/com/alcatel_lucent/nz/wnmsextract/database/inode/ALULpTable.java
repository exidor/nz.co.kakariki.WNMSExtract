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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inode.INodeType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inode.LpType;

/*
 drop table raw_inode_lp;
 create table raw_inode_lp(
 inid varchar(12), 	-- Root INode
 ts timestamp,
 rid int, 	-- RncEquipment
 iid varchar(12), 	-- Inode
 lpid int, 	-- Lp
 VSCpuUtilAvg float,
 VSCpuUtilAvgMax float,
 VSCpuUtilAvgMin float,
 constraint  raw_inode_lp_pk primary key (inid,ts,rid,iid,lpid)
 );
 */

/**
 * Lp table inserter. Child of INodeTable
 * @author jnramsay
 */
public class ALULpTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.inode.ALULpTable");

	public static final String TABLE_NAME = "raw_inode_lp";

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

		for (LpType lp : it.getLpArray()) {

			if (lp.getApArray().length > 0) {
				ALUApTable.insertData(ajc, lp, inid, ts, rid, iid);
			} else if (lp.getEthernetArray().length > 0) {
				ALUEthernetTable.insertData(ajc, lp, inid, ts, rid, iid);
			} else {

				Connection con = null;
				PreparedStatement ps = null;

				try {
					con = ajc.getConnection();
					ps = con.prepareStatement(sql);

					ps.setString(1, inid);
					ps.setTimestamp(2, ts);
					ps.setInt(3, rid);
					ps.setString(4, iid);
					ps.setInt(5, lp.getId());

					ps.setFloat(6, lp.getVSCpuUtilAvg());
					ps.setFloat(7, lp.getVSCpuUtilAvgMax());
					ps.setFloat(8, lp.getVSCpuUtilAvgMin());

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

	}

	public static ArrayList<String> prepareHeader() {
		ArrayList<String> row = new ArrayList<String>();

		row.add("inid");
		row.add("ts");
		row.add("rid");
		row.add("iid");
		row.add("lpid");
		row.add("VSCpuUtilAvg");
		row.add("VSCpuUtilAvgMax");
		row.add("VSCpuUtilAvgMin");

		return row;

	}

}
