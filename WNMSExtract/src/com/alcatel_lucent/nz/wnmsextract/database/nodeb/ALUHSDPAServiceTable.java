package com.alcatel_lucent.nz.wnmsextract.database.nodeb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.nodeb.BtsCellType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.nodeb.HSDPAServiceType;

/*
 drop table raw_nodeb_hsdpaservice;
 create table raw_nodeb_hsdpaservice(
 nbid varchar(6),
 ts timestamp,
 nbeid int,
 bcid int,
 hsid int,
 VSHsdpaReceivedCQILevel0 int,
 VSHsdpaReceivedCQILevel1 int,
 VSHsdpaReceivedCQILevel2 int,
 VSHsdpaReceivedCQILevel3 int,
 VSHsdpaReceivedCQILevel4 int,
 VSHsdpaReceivedCQILevel5 int,
 VSHsdpaReceivedCQILevel6 int,
 VSHsdpaReceivedCQILevel7 int,
 VSHsdpaReceivedCQILevel8 int,
 VSHsdpaReceivedCQILevel9 int,
 VSHsdpaReceivedCQILevel10 int,
 VSHsdpaReceivedCQILevel11 int,
 VSHsdpaReceivedCQILevel12 int,
 VSHsdpaReceivedCQILevel13 int,
 VSHsdpaReceivedCQILevel14 int,
 VSHsdpaReceivedCQILevel15 int,
 VSHsdpaReceivedCQILevel16 int,
 VSHsdpaReceivedCQILevel17 int,
 VSHsdpaReceivedCQILevel18 int,
 VSHsdpaReceivedCQILevel19 int,
 VSHsdpaReceivedCQILevel20 int,
 VSHsdpaReceivedCQILevel21 int,
 VSHsdpaReceivedCQILevel22 int,
 VSHsdpaReceivedCQILevel23 int,
 VSHsdpaReceivedCQILevel24 int,
 VSHsdpaReceivedCQILevel25 int,
 VSHsdpaReceivedCQILevel26 int,
 VSHsdpaReceivedCQILevel27 int,
 VSHsdpaReceivedCQILevel28 int,
 VSHsdpaReceivedCQILevel29 int,
 VSHsdpaReceivedCQILevel30 int,
 VSHsdpaTxDataBitsMAChsCum int,
 VSHsdpaTTIsUsed int,
 VSHsdpaTxDataBitPerUEcatUEcat6 int,
 VSHsdpaTTIperUEcatUEcat6 int,
 VSHsdpaTxDataBitPerUEcatUEcat8 int,
 VSHsdpaTTIperUEcatUEcat8 int,
 VSHsdpaTxDataBitPerUEcatUEcat10 int,
 VSHsdpaTTIperUEcatUEcat10 int,
 VSHsdpaTxDataBitPerUEcatUEcat14 int,
 VSHsdpaTTIperUEcatUEcat14 int,
 VSHsdpaTxDataBitPerUEcatUEcat15 int,
 VSHsdpaTTIperUEcatUEcat15 int,
 VSHsdpaTxDataBitPerUEcatUEcat16 int,
 VSHsdpaTTIperUEcatUEcat16 int,
 VSHsdpaTxDataBitPerUEcatUEcat17 int,
 VSHsdpaTTIperUEcatUEcat17 int,
 constraint  raw_nodeb_hsdpaservice_pk primary key (nbid,ts,nbeid,bcid,hsid)
 );
 */

/**
 * HSDPAService table inserter. Child of BtsCell table
 * @author jnramsay
 */
public class ALUHSDPAServiceTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.nodeb.ALUHSDPAServiceTable");

	public static final String TABLE_NAME = "raw_nodeb_hsdpaservice";

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject, String nbid, Timestamp ts, int nbeid) {
		BtsCellType bct = (BtsCellType) xmlobject;
		int bcid = Integer.parseInt(bct.getId());

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
			for (HSDPAServiceType hst : bct.getHSDPAServiceArray()) {

				ps.setString(1, nbid);
				ps.setTimestamp(2, ts);
				ps.setInt(3, nbeid);
				ps.setInt(4, bcid);
				ps.setInt(5, Integer.parseInt(hst.getId()));
				ps.setLong(6, hst.getVSHsdpaReceivedCQILevel0().longValue());
				ps.setLong(7, hst.getVSHsdpaReceivedCQILevel1().longValue());
				ps.setLong(8, hst.getVSHsdpaReceivedCQILevel2().longValue());
				ps.setLong(9, hst.getVSHsdpaReceivedCQILevel3().longValue());
				ps.setLong(10, hst.getVSHsdpaReceivedCQILevel4().longValue());
				ps.setLong(11, hst.getVSHsdpaReceivedCQILevel5().longValue());
				ps.setLong(12, hst.getVSHsdpaReceivedCQILevel6().longValue());
				ps.setLong(13, hst.getVSHsdpaReceivedCQILevel7().longValue());
				ps.setLong(14, hst.getVSHsdpaReceivedCQILevel8().longValue());
				ps.setLong(15, hst.getVSHsdpaReceivedCQILevel9().longValue());
				ps.setLong(16, hst.getVSHsdpaReceivedCQILevel10().longValue());
				ps.setLong(17, hst.getVSHsdpaReceivedCQILevel11().longValue());
				ps.setLong(18, hst.getVSHsdpaReceivedCQILevel12().longValue());
				ps.setLong(19, hst.getVSHsdpaReceivedCQILevel13().longValue());
				ps.setLong(20, hst.getVSHsdpaReceivedCQILevel14().longValue());
				ps.setLong(21, hst.getVSHsdpaReceivedCQILevel15().longValue());
				ps.setLong(22, hst.getVSHsdpaReceivedCQILevel16().longValue());
				ps.setLong(23, hst.getVSHsdpaReceivedCQILevel17().longValue());
				ps.setLong(24, hst.getVSHsdpaReceivedCQILevel18().longValue());
				ps.setLong(25, hst.getVSHsdpaReceivedCQILevel19().longValue());
				ps.setLong(26, hst.getVSHsdpaReceivedCQILevel20().longValue());
				ps.setLong(27, hst.getVSHsdpaReceivedCQILevel21().longValue());
				ps.setLong(28, hst.getVSHsdpaReceivedCQILevel22().longValue());
				ps.setLong(29, hst.getVSHsdpaReceivedCQILevel23().longValue());
				ps.setLong(30, hst.getVSHsdpaReceivedCQILevel24().longValue());
				ps.setLong(31, hst.getVSHsdpaReceivedCQILevel25().longValue());
				ps.setLong(32, hst.getVSHsdpaReceivedCQILevel26().longValue());
				ps.setLong(33, hst.getVSHsdpaReceivedCQILevel27().longValue());
				ps.setLong(34, hst.getVSHsdpaReceivedCQILevel28().longValue());
				ps.setLong(35, hst.getVSHsdpaReceivedCQILevel29().longValue());
				ps.setLong(36, hst.getVSHsdpaReceivedCQILevel30().longValue());
				ps.setLong(37, hst.getVSHsdpaTxDataBitsMAChsCum().longValue());
				ps.setLong(38, hst.getVSHsdpaTTIsUsed().longValue());
				ps.setLong(39, hst.getVSHsdpaTxDataBitPerUEcatUEcat6().longValue());
				ps.setLong(40, hst.getVSHsdpaTTIperUEcatUEcat6().longValue());
				ps.setLong(41, hst.getVSHsdpaTxDataBitPerUEcatUEcat8().longValue());
				ps.setLong(42, hst.getVSHsdpaTTIperUEcatUEcat8().longValue());
				ps.setLong(43, hst.getVSHsdpaTxDataBitPerUEcatUEcat10().longValue());
				ps.setLong(44, hst.getVSHsdpaTTIperUEcatUEcat10().longValue());

				// counters below don't occur in GTA database, null allowed

				if ("<xml-fragment/>".compareTo(hst.xgetVSHsdpaTxDataBitPerUEcatUEcat14().toString()) == 0)
					ps.setNull(45, Types.INTEGER);
				else
					ps.setLong(45, hst.getVSHsdpaTxDataBitPerUEcatUEcat14().longValue());
				if ("<xml-fragment/>".compareTo(hst.xgetVSHsdpaTTIperUEcatUEcat14().toString()) == 0)
					ps.setNull(46, Types.INTEGER);
				else
					ps.setLong(46, hst.getVSHsdpaTTIperUEcatUEcat14().longValue());

				if ("<xml-fragment/>".compareTo(hst.xgetVSHsdpaTxDataBitPerUEcatUEcat15().toString()) == 0)
					ps.setNull(47, Types.INTEGER);
				else
					ps.setLong(47, hst.getVSHsdpaTxDataBitPerUEcatUEcat15().longValue());
				if ("<xml-fragment/>".compareTo(hst.xgetVSHsdpaTTIperUEcatUEcat15().toString()) == 0)
					ps.setNull(48, Types.INTEGER);
				else
					ps.setLong(48, hst.getVSHsdpaTTIperUEcatUEcat15().longValue());

				if ("<xml-fragment/>".compareTo(hst.xgetVSHsdpaTxDataBitPerUEcatUEcat16().toString()) == 0)
					ps.setNull(49, Types.INTEGER);
				else
					ps.setLong(49, hst.getVSHsdpaTxDataBitPerUEcatUEcat16().longValue());
				if ("<xml-fragment/>".compareTo(hst.xgetVSHsdpaTTIperUEcatUEcat16().toString()) == 0)
					ps.setNull(50, Types.INTEGER);
				else
					ps.setLong(50, hst.getVSHsdpaTTIperUEcatUEcat16().longValue());

				if ("<xml-fragment/>".compareTo(hst.xgetVSHsdpaTxDataBitPerUEcatUEcat17().toString()) == 0)
					ps.setNull(51, Types.INTEGER);
				else
					ps.setLong(51, hst.getVSHsdpaTxDataBitPerUEcatUEcat17().longValue());
				if ("<xml-fragment/>".compareTo(hst.xgetVSHsdpaTTIperUEcatUEcat17().toString()) == 0)
					ps.setNull(52, Types.INTEGER);
				else
					ps.setLong(52, hst.getVSHsdpaTTIperUEcatUEcat17().longValue());

				jlog.debug("PS::" + ps.toString());
				ps.executeUpdate();
			}
		} catch (XmlValueOutOfRangeException xore) {
			System.err.println("Value in transformed XML does not subscribe to XSD defn for " + TABLE_NAME + " with " + ps + " :: " + xore);
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

	private static ArrayList<String> prepareHeader() {
		ArrayList<String> row = new ArrayList<String>();

		row.add("nbid");
		row.add("ts");
		row.add("nbeid");
		row.add("bcid");
		row.add("hsid");

		row.add("VSHsdpaReceivedCQILevel0");
		row.add("VSHsdpaReceivedCQILevel1");
		row.add("VSHsdpaReceivedCQILevel2");
		row.add("VSHsdpaReceivedCQILevel3");
		row.add("VSHsdpaReceivedCQILevel4");
		row.add("VSHsdpaReceivedCQILevel5");
		row.add("VSHsdpaReceivedCQILevel6");
		row.add("VSHsdpaReceivedCQILevel7");
		row.add("VSHsdpaReceivedCQILevel8");
		row.add("VSHsdpaReceivedCQILevel9");
		row.add("VSHsdpaReceivedCQILevel10");
		row.add("VSHsdpaReceivedCQILevel11");
		row.add("VSHsdpaReceivedCQILevel12");
		row.add("VSHsdpaReceivedCQILevel13");
		row.add("VSHsdpaReceivedCQILevel14");
		row.add("VSHsdpaReceivedCQILevel15");
		row.add("VSHsdpaReceivedCQILevel16");
		row.add("VSHsdpaReceivedCQILevel17");
		row.add("VSHsdpaReceivedCQILevel18");
		row.add("VSHsdpaReceivedCQILevel19");
		row.add("VSHsdpaReceivedCQILevel20");
		row.add("VSHsdpaReceivedCQILevel21");
		row.add("VSHsdpaReceivedCQILevel22");
		row.add("VSHsdpaReceivedCQILevel23");
		row.add("VSHsdpaReceivedCQILevel24");
		row.add("VSHsdpaReceivedCQILevel25");
		row.add("VSHsdpaReceivedCQILevel26");
		row.add("VSHsdpaReceivedCQILevel27");
		row.add("VSHsdpaReceivedCQILevel28");
		row.add("VSHsdpaReceivedCQILevel29");
		row.add("VSHsdpaReceivedCQILevel30");
		row.add("VSHsdpaTxDataBitsMAChsCum");
		row.add("VSHsdpaTTIsUsed");
		row.add("VSHsdpaTxDataBitPerUEcatUEcat6");
		row.add("VSHsdpaTTIperUEcatUEcat6");
		row.add("VSHsdpaTxDataBitPerUEcatUEcat8");
		row.add("VSHsdpaTTIperUEcatUEcat8");
		row.add("VSHsdpaTxDataBitPerUEcatUEcat10");
		row.add("VSHsdpaTTIperUEcatUEcat10");
		row.add("VSHsdpaTxDataBitPerUEcatUEcat14");
		row.add("VSHsdpaTTIperUEcatUEcat14");
		row.add("VSHsdpaTxDataBitPerUEcatUEcat15");
		row.add("VSHsdpaTTIperUEcatUEcat15");
		row.add("VSHsdpaTxDataBitPerUEcatUEcat16");
		row.add("VSHsdpaTTIperUEcatUEcat16");
		row.add("VSHsdpaTxDataBitPerUEcatUEcat17");
		row.add("VSHsdpaTTIperUEcatUEcat17");

		return row;
	}

}
