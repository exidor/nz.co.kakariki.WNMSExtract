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
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.nodeb.BtsCellType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.nodeb.NodeBEquipmentType;

/*
 drop table raw_nodeb_btscell;
 create table raw_nodeb_btscell(
 nbid varchar(6),
 ts timestamp,
 nbeid int,
 bcid int,
 VSCellULloadTotalCum int,
 VSCellULloadTotalNbEvt int,
 VSCellULloadEDCHCum int,
 VSCellULloadEDCHNbEvt int,
 VSRadioTxCarrierPwrOperMax int,
 VSRadioTxCarrierPwrUsedAvg float,
 constraint  raw_nodeb_btscell_pk primary key (nbid,ts,nbeid,bcid)
 );
 */

/**
 * BtsCell table inserter. Child of NodeBEquipment table, Parent of HS[D|U]PAService
 * @author jnramsay
 */
public class ALUBtsCellTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.nodeb.ALUBtsCellTable");

	public static final String TABLE_NAME = "raw_nodeb_btscell";

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

		for (BtsCellType bct : nbet.getBtsCellArray()) {
			if (bct.isSetVSCellULloadTotalCum()) {
				Connection con = null;
				PreparedStatement ps = null;

				try {
					con = ajc.getConnection();
					ps = con.prepareStatement(sql);
					ps.setString(1, nbid);
					ps.setTimestamp(2, ts);
					ps.setInt(3, nbeid);
					ps.setInt(4, Integer.parseInt(bct.getId()));
					ps.setLong(5, bct.getVSCellULloadTotalCum().longValue());
					ps.setLong(6, bct.getVSCellULloadTotalNbEvt().longValue());
					ps.setLong(7, bct.getVSCellULloadEDCHCum().longValue());
					ps.setLong(8, bct.getVSCellULloadEDCHNbEvt().longValue());
					ps.setLong(9, bct.getVSRadioTxCarrierPwrOperMax().longValue());
					ps.setFloat(10, bct.getVSRadioTxCarrierPwrUsedAvg());

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
			} else {
				ALUHSDPAServiceTable.insertData(ajc, bct, nbid, ts, nbeid);
				ALUHSUPAServiceTable.insertData(ajc, bct, nbid, ts, nbeid);
			}
		}
	}

	public static ArrayList<String> prepareHeader() {
		ArrayList<String> row = new ArrayList<String>();

		row.add("nbid");
		row.add("ts");
		row.add("nbeid");
		row.add("bcid");
		row.add("VSCellULloadTotalCum");
		row.add("VSCellULloadTotalNbEvt");
		row.add("VSCellULloadEDCHCum");
		row.add("VSCellULloadEDCHNbEvt");
		row.add("VSRadioTxCarrierPwrOperMax");
		row.add("VSRadioTxCarrierPwrUsedAvg");

		return row;

	}

}
