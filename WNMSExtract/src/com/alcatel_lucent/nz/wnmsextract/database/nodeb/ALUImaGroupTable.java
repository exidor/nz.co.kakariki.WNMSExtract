package com.alcatel_lucent.nz.wnmsextract.database.nodeb;

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
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.nodeb.ImaGroupType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.nodeb.NodeBEquipmentType;

/*
 drop table raw_nodeb_imagroup;
 create table raw_nodeb_imagroup(
 nbid varchar(6),
 ts timestamp,
 nbeid int,
 igid int,
 VSImaGroupNbReceivedCellAvg float,
 VSImaGroupNbSentCellAvg float,
 VSImaGroupNbReceivedCellMax int,
 VSImaGroupNbSentCellMax int,
 constraint  raw_nodeb_imagroup_pk primary key (nbid,ts,nbeid,igid)
 );
 */

/**
 * ImaGroup table inserter. Child of NodeBEquipment table.
 * @author jnramsay
 */
public class ALUImaGroupTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.nodeb.ALUImaGroupTable");

	public static final String TABLE_NAME = "raw_nodeb_imagroup";

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject, String nbid, Timestamp ts) {
		NodeBEquipmentType nbet = (NodeBEquipmentType) xmlobject;
		int nbeid = Integer.parseInt(nbet.getId());

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

			for (ImaGroupType igt : nbet.getImaGroupArray()) {
				ps.setString(1, nbid);
				ps.setTimestamp(2, ts);
				ps.setInt(3, nbeid);
				ps.setInt(4, Integer.parseInt(igt.getId()));
				ps.setFloat(5, igt.getVSImaGroupNbReceivedCellAvg());
				ps.setFloat(6, igt.getVSImaGroupNbSentCellAvg());
				ps.setLong(7, igt.getVSImaGroupNbReceivedCellMax().longValue());
				ps.setLong(8, igt.getVSImaGroupNbSentCellMax().longValue());

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

		row.add("nbid");
		row.add("ts");
		row.add("nbeid");
		row.add("igid");
		row.add("VSImaGroupNbReceivedCellAvg");
		row.add("VSImaGroupNbSentCellAvg");
		row.add("VSImaGroupNbReceivedCellMax");
		row.add("VSImaGroupNbSentCellMax");

		return row;

	}

}
