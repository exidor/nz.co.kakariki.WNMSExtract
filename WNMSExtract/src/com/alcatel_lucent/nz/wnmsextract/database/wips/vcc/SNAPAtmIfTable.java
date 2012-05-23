package com.alcatel_lucent.nz.wnmsextract.database.wips.vcc;
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
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.vcc.AtmIfType;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.vcc.VccType;



/*
drop table snap_rnc_vcc;
create table snap_rnc_vcc(
rid varchar(12),
aid int,
vid varchar(6),
RxTrafficDescType varchar(32),
RxTrafficDescParmValue1 int,
RxTrafficDescParmValue2 int,
RxTrafficDescParmValue3 int,
RxTrafficDescParmValue4 int,
RxTrafficDescParmValue5 int,
TxTrafficDescType varchar(32),
TxTrafficDescParmValue1 int,
TxTrafficDescParmValue2 int,
TxTrafficDescParmValue3 int,
TxTrafficDescParmValue4 int,
TxTrafficDescParmValue5 int,
TrafficShaping varchar(32),
constraint snap_rnc_vcc_pk primary key (rid,aid,vid)
);
 */

/**
 * WiPS snapshot AtmIf table inserter. 
 * @author jnramsay
 */
public class SNAPAtmIfTable {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.database.wips.vcc.SNAPAtmIfTable");

	public static final String TABLE_NAME = "snap_rnc_vcc";

	public static void insertData(ALUJDCConnector ajc, XmlObject xmlobject,String rvid,String rid){
		AtmIfType atmif = (AtmIfType) xmlobject;
		String aid = atmif.getId();
		String rail = atmif.getRemoteAtmInterfaceLabel();

		StringBuffer sql1 = new StringBuffer("INSERT INTO "+TABLE_NAME+" (");
		StringBuffer sql2 = new StringBuffer(") VALUES (");
		for (String col : prepareHeader()){
			sql1.append(col+",");
			sql2.append("?,");
		}
		//System.out.println();
		String sql = sql1.deleteCharAt(sql1.length()-1).toString()
		+sql2.deleteCharAt(sql2.length()-1).toString()+")";

		for (VccType vcc : atmif.getVccArray()){
			int offset = 0;
			Connection con = null;
			PreparedStatement ps = null;

			try {

				con = ajc.getConnection();
				ps = con.prepareStatement(sql);


				ps.setString(1,rid);
				ps.setInt(2,Integer.parseInt(aid));
				ps.setString(3,vcc.getId());
				ps.setString(4,rail);
				
				ps.setString(5,vcc.getCorrelationTag());				
				
				ps.setString(6,vcc.getRxTrafficDescType());
				//7..11
				for(BigInteger tdt : vcc.getRxTrafficDescParm().getValueArray()){
					offset += 1;
					ps.setInt(6+offset,tdt.intValue());
				}
				ps.setString(12,vcc.getTxTrafficDescType());
				//13..17
				for(BigInteger tdt : vcc.getTxTrafficDescParm().getValueArray()){
					offset += 1;
					ps.setInt(7+offset,tdt.intValue());
				}
				ps.setString(18,vcc.getTrafficShaping());

				jlog.debug("PS::"+ps.toString());
				ps.executeUpdate();
			}
			catch(XmlValueOutOfRangeException xore){
				System.err.println("Value in transformed XML does not subscribe to XSD defn for "+TABLE_NAME+" :: "+xore);
			}
			catch(SQLException sqle){
				System.err.println("Problem mapping to DB "+TABLE_NAME+">"+ps+" :: "+sqle);
			}
			finally {
				try {
					ps.close();
					con.close();
				} catch (Exception e) {
					System.err.println("Undefined Exception :: "+e);
				}
			}

		}
	}


	public static ArrayList<String> prepareHeader() {
		ArrayList<String> row = new ArrayList<String>();

		row.add("rid");
		row.add("aid");
		row.add("vid");
		row.add("remoteAtmInterfaceLabel");
		row.add("correlationTag");
		row.add("RxTrafficDescType");
		row.add("RxTrafficDescParmValue1");
		row.add("RxTrafficDescParmValue2");
		row.add("RxTrafficDescParmValue3");
		row.add("RxTrafficDescParmValue4");
		row.add("RxTrafficDescParmValue5");
		row.add("TxTrafficDescType");
		row.add("TxTrafficDescParmValue1");
		row.add("TxTrafficDescParmValue2");
		row.add("TxTrafficDescParmValue3");
		row.add("TxTrafficDescParmValue4");
		row.add("TxTrafficDescParmValue5");
		row.add("TrafficShaping");

		return row;

	}


}
