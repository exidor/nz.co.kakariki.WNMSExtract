package com.alcatel_lucent.nz.wnmsextract.database;
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
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Static class methods called for DB interaction. This class supercedes the FileUtilities 
 * methods which were fundamentally bugged
 * @author jnramsay
 * @version 3 
 */
public class ALUDBUtilities {

	protected static final String DEF_DBNM = "NZRSDB";
	/** First level logging table triggering first round intermediate aggregations */
	protected static final String DEF_LTAB1 = "log_process";
	/** Secod level logging table triggering report level aggregations */
	protected static final String DEF_LTAB2 = "log_aggregate";

	protected static final String EMPTY_STR = "^\\s*$";

	public static final DateFormat ALUDB_DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public enum ColumnStructure{VC,TS,FL,IT};


	/** 
	 * Line by line insert for bundled data, typically an array or a single line
	 * @param db Database connector type {@link com.alcatel_lucent.nz.wnmsextract.database.DatabaseType}
	 * @param table Target of insert
	 * @param col List of column data types
	 * @param data as a List-List-String
	 */
	public static void insert(DatabaseType db,String table, ArrayList<ColumnStructure> col, ArrayList<ArrayList<String>> data){
		String ph = ALUDBUtilities.placeholder(data.get(0).size());
		ALUJDCConnector ajc = new ALUJDCConnectorFactory(db).getInstance();
		Connection c = null;
		PreparedStatement p = null;
		try{
			c = ajc.getConnection();
			//c.setAutoCommit(false);
			p = c.prepareStatement("INSERT INTO "+table+" VALUES "+ph);
			for(ArrayList<String> row : data){
				int counter = 1;
				try {
				for(String column : row){
					switch (col.get(counter-1)){
						case VC: p.setString(counter,column);break;
						case TS: p.setTimestamp(counter, checkTimestamp(column));break;
						case IT: p.setInt(counter, checkInteger(column));break;
						case FL: p.setFloat(counter, checkFloat(column));break;
					}

					counter++;
				}
				p.execute();
				}
				catch(SQLException sqle){
					System.err.println("Error executing Insert on "+table+" with "+row+" :: "+sqle);
					//keep going, some dup key expected
				}
			}
			//p.executeBatch();
			//c.commit();


		}/*
		catch(BatchUpdateException bue){
			System.err.println("Error executing batch Insert on "+table+" :: "+bue);
			System.err.println("getNextException() = "+bue.getNextException());
			processUpdateCounts(bue.getUpdateCounts());
			try {
				c.commit();
			} catch (SQLException sqle) {
				System.err.println("Error commiting successful batch Inserts on "+table+" :: "+sqle);
			}
		}*/

		catch(SQLException sqle){
			System.err.println("Connection/Statement Error "+table+" :: "+sqle);
		}

		finally{
			try {
				p.close();
				c.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Batch insert method. Faster than line by line but less able to be debugged and
	 * not entirely trusted to skip only rows that cause an error in a batch rather 
	 * than the whole batch
	 * @param db Database connector type {@link com.alcatel_lucent.nz.wnmsextract.database.DatabaseType}
	 * @param table Target of insert
	 * @param col List of column data types
	 * @param data as a List-List-String
	 */
	public static void insertBatch(DatabaseType db,String table, ArrayList<ColumnStructure> col, ArrayList<ArrayList<String>> data){
		String ph = ALUDBUtilities.placeholder(data.get(0).size());
		ALUJDCConnector ajc = new ALUJDCConnectorFactory(db).getInstance();
		Connection c = null;
		PreparedStatement p = null;
		try{
			c = ajc.getConnection();
			c.setAutoCommit(false);
			p = c.prepareStatement("INSERT INTO "+table+" VALUES "+ph);
			for(ArrayList<String> row : data){
				int counter = 1;
				for(String column : row){
					switch (col.get(counter-1)){
						case VC: p.setString(counter,column);break;
						case TS:
							//if(Timestamp.valueOf(column).after(Timestamp.valueOf("2011-01-01 00:00:00"))){System.out.println(":::"+column);};
							p.setTimestamp(counter, checkTimestamp(column));break;
						case IT: p.setInt(counter, checkInteger(column));break;
						case FL: p.setFloat(counter, checkFloat(column));break;
					}

					counter++;
				}
				p.addBatch();
			}
			p.executeBatch();
			c.commit();


		}
		catch(BatchUpdateException bue){
			System.err.println("Error executing batch Insert on "+table+" :: "+bue);
			System.err.println("getNextException() = "+bue.getNextException());
			processUpdateCounts(bue.getUpdateCounts());
			try {
				c.commit();
			} catch (SQLException sqle) {
				System.err.println("Error commiting successful batch Inserts on "+table+" :: "+sqle);
			}
		}
		catch(SQLException sqle){
			System.err.println("Error executing Insert on "+table+" :: "+sqle);
		}
		finally{
			try {
				p.close();
				c.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Debugging informational message indicating how much of a batch was executed
	 * @param updatecounts couunt of update success.failure available from {@link  java.sql.BatchUpdateException} message
	 */
	public static void processUpdateCounts(int[] updatecounts) {
		for (int i=0; i<updatecounts.length; i++) {
			if (updatecounts[i] >= 0) {
				System.out.println("Successfully executed: "+updatecounts[i]+" = number of affected rows");
			} else if (updatecounts[i] == Statement.SUCCESS_NO_INFO) {
				System.out.println("Successfully executed: "+updatecounts[i]+" = number of affected rows not available");
			} else if (updatecounts[i] == Statement.EXECUTE_FAILED) {
				System.err.println("Failed to execute");

			}
		}
	}

	/**
	 * Deletes the contents of a tables. Useful for the configuration tables 
	 * which are only ever snapshots
	 * @param db Database to delete from
	 * @param table The table to be cleared
	 */
	public static void delete(DatabaseType db,String table){
		delete(new ALUJDCConnectorFactory(db).getInstance(),table);
	}
	/**
	 * Deletes the contents of a tables. Useful for the configuration tables 
	 * which are only ever snapshots
	 * @param ajc JDC Database connection instance to clear from
	 * @param table The table to be cleared
	 */
	public static void delete(ALUJDCConnector ajc,String table){
		Connection c = null;
		Statement s = null;
		try{
			c = ajc.getConnection();
			s = c.createStatement();
			s.execute("DELETE FROM "+table);

		}
		catch(SQLException sqle){
			System.err.println("Error executing Delete on "+table+" :: "+sqle);
		}
		finally{
			try {
				s.close();
				c.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Canned select builder. Intended for meta/parameter extraction for example; from
	 * a list of tables select the tabel to run the next query on
	 * @param db Database Type to connect to
	 * @param table Table to be queried
	 * @param field The field to pull a value, the first value from
	 * @return Result as a string
	 */
	public static String select(DatabaseType db,String table,String field){
		String res = null;
		ALUJDCConnector ajc = new ALUJDCConnectorFactory(db).getInstance();
		Connection c = null;
		Statement s = null;
		try{
			c = ajc.getConnection();
			s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT "+field+" FROM "+table+" LIMIT 1");
			r.next();
			res = r.getString(1);

		}
		catch(SQLException sqle){
			System.err.println("Error executing Select of "+field+" on "+table+" :: "+sqle);
		}
		finally{
			try {
				s.close();
				c.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/**
	 * Stores a log entry in the log_process table but more importantly
	 * triggers view to table copy rules
	 * @param tablename Table where insert/delete/update has occurred (raw_*)
	 * @param operation Modification operation on raw table
	 */
	public static void log(DatabaseType db,String table,String operation){
		ALUJDCConnector ajc = new ALUJDCConnectorFactory(db).getInstance();
		Connection c = null;
		Statement s = null;
		try{
			c = ajc.getConnection();
			s = c.createStatement();
			s.execute("INSERT INTO "+DEF_LTAB1+" VALUES(now(), '"+table+"', '"+operation+"')");

		}
		catch(SQLException sqle){
			System.err.println("Error executing Log Insert on "+DEF_LTAB1+" :: "+sqle);
		}
		finally{
			try {
				s.close();
				c.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Float type validation and conversion. High overhead but necessary on untrustworty sources
	 * @param sval String representation of desired Float
	 * @return 
	 */
	public static float checkFloat(String sval){
		float fval = 0.0f;
		try {
			fval = Float.parseFloat(sval);
		} catch (NumberFormatException nfe){
			System.err.println("Float format exception, def to 0.0f :: "+nfe);
		}
		return fval;
		//return sval.matches(EMPTY_STR)?0:Integer.parseInt(sval);
	}

	/**
	 * Double type validation and conversion. High overhead but necessary on untrustworty sources
	 * @param sval String representation of desired Double
	 * @return 
	 */
	public static double checkDouble(String sval){
		double dval = 0.0d;
		try {
			dval = Double.parseDouble(sval);
		} catch (NumberFormatException nfe){
			System.err.println("Double format exception, def to 0.0d :: "+nfe);
		}
		return dval;
		//return sval.matches(EMPTY_STR)?0:Integer.parseInt(sval);
	}


	/**
	 * Integer type validation and conversion. High overhead but necessary on untrustworty sources
	 * @param sval String representation of desired Integer
	 * @return 
	 */
	public static int checkInteger(String sval){
		int ival = 0;
		try {
			ival = Integer.parseInt(sval);
		} catch (NumberFormatException nfe){
			System.err.println("Int format exception, def to 0 :: "+nfe);
		}
		return ival;
		//return sval.matches(EMPTY_STR)?0:Integer.parseInt(sval);
	}

	/**
	 * Long type validation and conversion. High overhead but necessary on untrustworty sources
	 * @param sval String representation of desired Long
	 * @return 
	 */
	public static long checkLong(String sval){
		long lval = 0L;
		try {
			lval = Long.parseLong(sval);
		} catch (NumberFormatException nfe){
			System.err.println("Long format exception, def to 0L :: "+nfe);
		}
		return lval;
		//return sval.matches(EMPTY_STR)?0:Integer.parseInt(sval);
	}


	/**
	 * Timestamp type validation and conversion. High overhead but necessary on untrustworty sources
	 * @param sval String representation of desired Timestamp
	 * @return Calendar instance reflecting string interpreted as Timestamp in {@link com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities} ALU_DF format 
	 */
	public static Timestamp checkTimestamp(String sval){
		Calendar cal = Calendar.getInstance();
		cal.set(2000,0,1);
		Timestamp tval = new Timestamp(cal.getTime().getTime());
		try {
			tval = new Timestamp(ALUDB_DF.parse(sval).getTime());
		} catch (ParseException pe){
			System.err.println("Timestamp parse exception, def to '2000-01-01' :: "+pe);
		} catch (NumberFormatException nfe){
			System.err.println("Timestamp format exception, def to '2000-01-01' :: "+nfe);
		}
		return tval;

	}

	/**
	 * Generates a statement argument field placeholder string
	 * such as (?,?,?,?,?,?)
	 * @param p Number of placeholders to generate
	 * @return String of comma seperated '?'
	 */
	private static String placeholder(int p){
			String ps = "(";
			for (int i=0; i<p; i++){
					ps+="?,";
			}
			return ps.substring(0,ps.length()-1)+")";
	}

}
