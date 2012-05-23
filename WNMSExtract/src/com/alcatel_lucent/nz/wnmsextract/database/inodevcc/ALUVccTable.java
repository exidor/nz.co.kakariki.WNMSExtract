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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inodevcc.AtmPortType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inodevcc.VccType;

/*
 DROP TABLE raw_inode_vcc;

 CREATE TABLE raw_inode_vcc
 (
 inid character varying(12) NOT NULL,
 ts timestamp without time zone NOT NULL,
 rid integer NOT NULL,
 iid character varying(12) NOT NULL,
 apid integer NOT NULL,
 vid character varying(6) NOT NULL,
 vsacvccingresscellcountclp0 integer,
 vsacvccingresscellcountclp01 integer,
 vsacvccegresscellcountclp0 integer,
 vsacvccegresscellcountclp01 integer,
 vsacvccingressdiscardedclp0 integer,
 vsacvccingressdiscardedclp01 integer,
 vsacvccegressdiscardedclp0 integer,
 vsacvccegressdiscardedclp01 integer,
 CONSTRAINT raw_inode_vcc_pk PRIMARY KEY (inid, ts, rid, iid, apid, vid)
 )
 */

/**
 * VCC table inserter. Child of AtmPortTable
 * @author jnramsay
 */
public class ALUVccTable {
	public static final String TABLE_NAME = "raw_inode_vcc";

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject, String inid, Timestamp ts, int rid, String iid) {
		AtmPortType apt = (AtmPortType) xmlobject;
		int apid = Integer.parseInt(apt.getId());

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

			for (VccType vt : apt.getVccArray()) {

				ps.setString(1, inid);
				ps.setTimestamp(2, ts);
				ps.setInt(3, rid);
				ps.setString(4, iid);
				ps.setInt(5, apid);
				ps.setString(6, vt.getId());
				ps.setFloat(7, vt.getVSAcVccIngressCellCountClp0());
				ps.setFloat(8, vt.getVSAcVccIngressCellCountClp01());
				ps.setFloat(9, vt.getVSAcVccEgressCellCountClp0());
				ps.setFloat(10, vt.getVSAcVccEgressCellCountClp01());
				ps.setFloat(11, vt.getVSAcVccIngressDiscardedClp0());
				ps.setFloat(12, vt.getVSAcVccIngressDiscardedClp01());
				ps.setFloat(13, vt.getVSAcVccEgressDiscardedClp0());
				ps.setFloat(14, vt.getVSAcVccEgressDiscardedClp01());

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
		row.add("vid");
		row.add("VSAcVccIngressCellCountClp0");
		row.add("VSAcVccIngressCellCountClp01");
		row.add("VSAcVccEgressCellCountClp0");
		row.add("VSAcVccEgressCellCountClp01");
		row.add("VSAcVccIngressDiscardedClp0");
		row.add("VSAcVccIngressDiscardedClp01");
		row.add("VSAcVccEgressDiscardedClp0");
		row.add("VSAcVccEgressDiscardedClp01");

		return row;

	}

}
