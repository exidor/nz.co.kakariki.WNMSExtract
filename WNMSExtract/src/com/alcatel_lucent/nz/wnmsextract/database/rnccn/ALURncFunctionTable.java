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
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.rnccn.RncCnType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.rnccn.RncFunctionType;

/*
 drop table raw_rnccn_rncfunction;
 create table raw_rnccn_rncfunction(
 rcid varchar(12),
 ts timestamp,
 rfid int,
 VSNumberOfRabEstablishedGrantedRabCSSpeechConvAvg float,
 VSNumberOfRabEstablishedGrantedRabCsConv64Avg float,
 VSNumberOfRabEstablishedGrantedRabCsStrAvg integer,
 VSNumberOfRabEstablishedGrantedRabCSSpeechConvMax integer,
 VSNumberOfRabEstablishedGrantedRabCsConv64Max integer,
 VSNumberOfRabEstablishedGrantedRabCsStrMax integer,
 VSReceivedPagingRequestWithCoreNetworkCs integer,
 VSReceivedPagingRequestWithCoreNetworkPs integer,
 VSReceivedPagingRequestFromCoreNwCsInvalidLac integer,
 VSReceivedPagingRequestWithCoreNwPsInvalidRac integer,
 constraint raw_rnccn_rncfunction_pk primary key (rcid,ts,rfid)
 );
 */

/**
 * Top level table inserter RncFunction. Parent of NeighbouringRnc and Utrancell
 * @author jnramsay
 *
 */
public class ALURncFunctionTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.rnccn.ALURncFunctionTable");

	public static final String TABLE_NAME = "raw_rnccn_rncfunction";

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject) {
		RncCnType rnccn = (RncCnType) xmlobject;
		String rcid = rnccn.getId();
		Timestamp ts = Timestamp.valueOf(rnccn.getTimestamp().replace("/", "-"));

		StringBuffer sql1 = new StringBuffer("INSERT INTO " + TABLE_NAME + " (");
		StringBuffer sql2 = new StringBuffer(") VALUES (");
		for (String col : prepareHeader()) {
			sql1.append(col + ",");
			sql2.append("?,");
		}
		// System.out.println();
		String sql = sql1.deleteCharAt(sql1.length() - 1).toString() + sql2.deleteCharAt(sql2.length() - 1).toString() + ")";

		for (RncFunctionType rft : rnccn.getRncFunctionArray()) {
			if (rft.isSetVSNumberOfRabEstablishedGrantedRabCSSpeechConvAvg()) {
				Connection con = null;
				PreparedStatement ps = null;

				try {
					con = ajc.getConnection();
					ps = con.prepareStatement(sql);
					ps.setString(1, rcid);
					ps.setTimestamp(2, ts);
					ps.setInt(3, Integer.parseInt(rft.getId()));
					ps.setFloat(4, rft.getVSNumberOfRabEstablishedGrantedRabCSSpeechConvAvg());
					ps.setFloat(5, rft.getVSNumberOfRabEstablishedGrantedRabCsConv64Avg());
					ps.setLong(6, rft.getVSNumberOfRabEstablishedGrantedRabCsStrAvg().longValue());

					// ps.setLong(7,rft.getVSNumberOfRabEstablishedGrantedRabCSSpeechConvMax().longValue());
					String l7 = rft.xgetVSNumberOfRabEstablishedGrantedRabCSSpeechConvMax().toString();
					if (l7.contains(".")) {
						// cut fragment between > and .
						ps.setLong(7, Long.parseLong(l7.substring(14, l7.indexOf('.'))));
					} else
						ps.setLong(7, rft.getVSNumberOfRabEstablishedGrantedRabCSSpeechConvMax().longValue());

					ps.setLong(8, rft.getVSNumberOfRabEstablishedGrantedRabCsConv64Max().longValue());
					ps.setLong(9, rft.getVSNumberOfRabEstablishedGrantedRabCsStrMax().longValue());
					ps.setLong(10, rft.getVSReceivedPagingRequestWithCoreNetworkCs().longValue());
					ps.setLong(11, rft.getVSReceivedPagingRequestWithCoreNetworkPs().longValue());
					ps.setLong(12, rft.getVSReceivedPagingRequestFromCoreNwCsInvalidLac().longValue());
					ps.setLong(13, rft.getVSReceivedPagingRequestWithCoreNwPsInvalidRac().longValue());

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
				ALUUtranCellTable.insertData(ajc, rft, rcid, ts);
				ALUNeighbouringRncTable.insertData(ajc, rft, rcid, ts);

			}
		}
	}

	public static ArrayList<String> prepareHeader() {
		ArrayList<String> row = new ArrayList<String>();

		row.add("rcid");
		row.add("ts");
		row.add("rfid");
		row.add("VSNumberOfRabEstablishedGrantedRabCSSpeechConvAvg");
		row.add("VSNumberOfRabEstablishedGrantedRabCsConv64Avg");
		row.add("VSNumberOfRabEstablishedGrantedRabCsStrAvg");
		row.add("VSNumberOfRabEstablishedGrantedRabCSSpeechConvMax");
		row.add("VSNumberOfRabEstablishedGrantedRabCsConv64Max");
		row.add("VSNumberOfRabEstablishedGrantedRabCsStrMax");
		row.add("VSReceivedPagingRequestWithCoreNetworkCs");
		row.add("VSReceivedPagingRequestWithCoreNetworkPs");
		row.add("VSReceivedPagingRequestFromCoreNwCsInvalidLac");
		row.add("VSReceivedPagingRequestWithCoreNwPsInvalidRac");

		return row;

	}

}
