package com.alcatel_lucent.nz.wnmsextract.reader;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVStrategy;

import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities;
import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities.ColumnStructure;
import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
/*
 * "Date","Threshold","Lp 2 Ap 1","Lp 3 Ap 1","Lp 4 Ap 1","Lp 5 Ap 1","Lp 6 Ap 1","Lp 7 Ap 1","Lp 10 Ap 1","Lp 11 Ap 1","Lp 12 Ap 1","Lp 12 Ap 5","Lp 13 Ap 1","Lp 13 Ap 5"
 * "2010-08-08 11:47:01","70.0000","40.0000","37.0000","44.0000","41.0000","35.0000","38.0000","44.0000","40.0000","41.0000","1.0000","1.0000","38.0000"
 */

/**
 * This reader scrapes the default Borg webpage selecting all data from the chart backing tables.
 * Mostly superceded by the BorgSelectionReader class this is retained since the Borg default page 
 * is more likely to be active than the date selecting CGI script 
 */
@Deprecated
public class BorgBlockReader implements HttpReader {

	/*this is all a bit redundant now. consider deleteing*/
	private static final String BORG = "https://borg.anz.lucent.com/rnc_stats/";
	private static final String TABLE = "rncap_borg";
	public static final DateFormat BORG_DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DatabaseType DEF_DBT = DatabaseType.TNZ_NZRSDB;

	private enum AppProc{MASTER,TMU,RAB,PC,NI,OMU}
	/**
	 * Hardcoded files names referencing borg backing tables. Recode as needed! 
	 * Future proofed a little with addition of h4,w3,m4 & c4
	 * @author jnramsay
	 */
	private enum RncAp {
		CH_RNC01(Arrays.asList(
				"CH-RNC01-Maste_rnc_cpu_rolling.csv","CH-RNC01-tmu_rnc_cpu_rolling.csv",
				"CH-RNC01-rab_rnc_cpu_rolling.csv","CH-RNC01-pc_rnc_cpu_rolling.csv",
				"CH-RNC01-Ni_rnc_cpu_rolling.csv","CH-RNC01-Omu_rnc_cpu_rolling.csv")),
		CH_RNC02(Arrays.asList(
				"CH-RNC02-Maste_rnc_cpu_rolling.csv","CH-RNC02-tmu_rnc_cpu_rolling.csv",
				"CH-RNC02-rab_rnc_cpu_rolling.csv","CH-RNC02-pc_rnc_cpu_rolling.csv",
				"CH-RNC02-Ni_rnc_cpu_rolling.csv","CH-RNC02-Omu_rnc_cpu_rolling.csv")),
		CH_RNC03(Arrays.asList(
				"CH-RNC03-Maste_rnc_cpu_rolling.csv","CH-RNC03-tmu_rnc_cpu_rolling.csv",
				"CH-RNC03-rab_rnc_cpu_rolling.csv","CH-RNC03-pc_rnc_cpu_rolling.csv",
				"CH-RNC03-Ni_rnc_cpu_rolling.csv","CH-RNC03-Omu_rnc_cpu_rolling.csv")),
		CH_RNC04(Arrays.asList(
				"CH-RNC04-Maste_rnc_cpu_rolling.csv","CH-RNC04-tmu_rnc_cpu_rolling.csv",
				"CH-RNC04-rab_rnc_cpu_rolling.csv","CH-RNC04-pc_rnc_cpu_rolling.csv",
				"CH-RNC04-Ni_rnc_cpu_rolling.csv","CH-RNC04-Omu_rnc_cpu_rolling.csv")),
		MDR_RNC01(Arrays.asList(
				"MDR-RNC01-Maste_rnc_cpu_rolling.csv","MDR-RNC01-tmu_rnc_cpu_rolling.csv",
				"MDR-RNC01-rab_rnc_cpu_rolling.csv","MDR-RNC01-pc_rnc_cpu_rolling.csv",
				"MDR-RNC01-Ni_rnc_cpu_rolling.csv","MDR-RNC01-Omu_rnc_cpu_rolling.csv")),
		MDR_RNC02(Arrays.asList(
				"MDR-RNC02-Maste_rnc_cpu_rolling.csv","MDR-RNC02-tmu_rnc_cpu_rolling.csv",
				"MDR-RNC02-rab_rnc_cpu_rolling.csv","MDR-RNC02-pc_rnc_cpu_rolling.csv",
				"MDR-RNC02-Ni_rnc_cpu_rolling.csv","MDR-RNC02-Omu_rnc_cpu_rolling.csv")),
		MDR_RNC03(Arrays.asList(
				"MDR-RNC03-Maste_rnc_cpu_rolling.csv","MDR-RNC03-tmu_rnc_cpu_rolling.csv",
				"MDR-RNC03-rab_rnc_cpu_rolling.csv","MDR-RNC03-pc_rnc_cpu_rolling.csv",
				"MDR-RNC03-Ni_rnc_cpu_rolling.csv","MDR-RNC03-Omu_rnc_cpu_rolling.csv")),
		MDR_RNC04(Arrays.asList(
				"MDR-RNC04-Maste_rnc_cpu_rolling.csv","MDR-RNC04-tmu_rnc_cpu_rolling.csv",
				"MDR-RNC04-rab_rnc_cpu_rolling.csv","MDR-RNC04-pc_rnc_cpu_rolling.csv",
				"MDR-RNC04-Ni_rnc_cpu_rolling.csv","MDR-RNC04-Omu_rnc_cpu_rolling.csv")),
		WN_RNC01(Arrays.asList(
				"WN-RNC01-Maste_rnc_cpu_rolling.csv","WN-RNC01-tmu_rnc_cpu_rolling.csv",
				"WN-RNC01-rab_rnc_cpu_rolling.csv","WN-RNC01-pc_rnc_cpu_rolling.csv",
				"WN-RNC01-Ni_rnc_cpu_rolling.csv","WN-RNC01-Omu_rnc_cpu_rolling.csv")),
		WN_RNC02(Arrays.asList(
				"WN-RNC02-Maste_rnc_cpu_rolling.csv","WN-RNC02-tmu_rnc_cpu_rolling.csv",
				"WN-RNC02-rab_rnc_cpu_rolling.csv","WN-RNC02-pc_rnc_cpu_rolling.csv",
				"WN-RNC02-Ni_rnc_cpu_rolling.csv","WN-RNC02-Omu_rnc_cpu_rolling.csv")),
		WN_RNC03(Arrays.asList(
				"WN-RNC03-Maste_rnc_cpu_rolling.csv","WN-RNC03-tmu_rnc_cpu_rolling.csv",
				"WN-RNC03-rab_rnc_cpu_rolling.csv","WN-RNC03-pc_rnc_cpu_rolling.csv",
				"WN-RNC03-Ni_rnc_cpu_rolling.csv","WN-RNC03-Omu_rnc_cpu_rolling.csv")),
		HN_RNC01(Arrays.asList(
				"HN-RNC01-Maste_rnc_cpu_rolling.csv","HN-RNC01-tmu_rnc_cpu_rolling.csv",
				"HN-RNC01-rab_rnc_cpu_rolling.csv","HN-RNC01-pc_rnc_cpu_rolling.csv",
				"HN-RNC01-Ni_rnc_cpu_rolling.csv","HN-RNC01-Omu_rnc_cpu_rolling.csv")),
		HN_RNC02(Arrays.asList(
				"HN-RNC02-Maste_rnc_cpu_rolling.csv","HN-RNC02-tmu_rnc_cpu_rolling.csv",
				"HN-RNC02-rab_rnc_cpu_rolling.csv","HN-RNC02-pc_rnc_cpu_rolling.csv",
				"HN-RNC02-Ni_rnc_cpu_rolling.csv","HN-RNC02-Omu_rnc_cpu_rolling.csv")),
		HN_RNC03(Arrays.asList(
				"HN-RNC03-Maste_rnc_cpu_rolling.csv","HN-RNC03-tmu_rnc_cpu_rolling.csv",
				"HN-RNC03-rab_rnc_cpu_rolling.csv","HN-RNC03-pc_rnc_cpu_rolling.csv",
				"HN-RNC03-Ni_rnc_cpu_rolling.csv","HN-RNC03-Omu_rnc_cpu_rolling.csv"));
		private List<String> flist;
		RncAp(List<String> flist){
			this.flist = flist;
		}
		public String getFile(AppProc ap){
			switch (ap){
			case MASTER: return flist.get(0);
			case TMU: return flist.get(1);
			case RAB: return flist.get(2);
			case PC: return flist.get(3);
			case OMU: return flist.get(4);
			case NI: return flist.get(5);
			}
			return null;
		}

	};

	//delim, encaps, comment
	public CSVStrategy strategy;
	public DatabaseType databasetype;

	/** 
	 * Constructor, sets up SSL certs.
	 * @param databasetype
	 */
	public BorgBlockReader(DatabaseType databasetype){
		this.databasetype = databasetype;
		this.strategy = new CSVStrategy(',','"','#');

		System.setProperty("javax.net.ssl.trustStore", Extractor.chooseCACertsPath()+"cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        System.out.println("javax.net.ssl.trustStore = "+System.getProperty("javax.net.ssl.trustStore"));
	}
	public BorgBlockReader(){
		this(DEF_DBT);
	}


	@Override
	public void readAll() {
		ArrayList<ColumnStructure> colstruct = new ArrayList<ColumnStructure>();
		colstruct.add(ColumnStructure.VC);
		colstruct.add(ColumnStructure.TS);
		colstruct.add(ColumnStructure.FL);
		for (RncAp r : EnumSet.allOf(RncAp.class)){
			for (AppProc a : EnumSet.allOf(AppProc.class)){
				ArrayList<ArrayList<String>> mapmap = new ArrayList<ArrayList<String>>();
				try {
					URL borg = new URL(BORG+r.getFile(a));
			        URLConnection conn = borg.openConnection();
			        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			        CSVParser parser = new CSVParser(in,strategy);
			        //if header
			        String[] header = parser.getLine();
			        //and body
			        String[] line = null;

			        while((line = parser.getLine())!= null){

			        	Calendar cal = Calendar.getInstance();
			            cal.setTime(BORG_DF.parse(line[0]));
			            String datestr = ALUDBUtilities.ALUDB_DF.format(cal.getTime());
			            //System.out.println(r.toString()+"/"+a.toString()+"//"+line[0]+"///"+datestr);
			        	for(int i=2; i<line.length; i++){
			        		ArrayList<String> map = new ArrayList<String>();
			        		map.add(0,idConvert(r.toString(),a.toString(),header[i]));
			        		map.add(1,datestr);
			        		map.add(2,line[i]);
			        		mapmap.add(map);
			        	}

			        }
			        in.close();
				}
				catch(MalformedURLException mrue){System.err.println("Borg Path incorrect "+mrue);}
				catch(IOException ioe){System.err.println("Cannot read Borg file "+ioe);}
				catch(ParseException pe){System.err.println("Cannot parse Date field "+pe);}

				ALUDBUtilities.insert(databasetype, TABLE, colstruct, mapmap);



			}
		}

	}

	/**
	 * Aggregate table trigger method call
	 */
	@Override
	public void logRawTableChanges (){
		ALUDBUtilities.log(databasetype, TABLE, "INSERT");
	}


	/**
	 * Coded ID converter. Not inline with design philosophy to keep id 
	 * functions in DB
	 * @param rnc
	 * @param ap
	 * @param hd
	 * @return
	 */
	private String idConvert(String rnc, String ap, String hd){
		//"Lp nn Ap mm"
		String[] token = hd.split(" ");
		return "AP_"+rnc+"/"+rnc+"IN0/"+token[1]+"/"+token[3];

	}
	@Override
	public void readAll(Calendar start, Calendar end) {
		// TODO Auto-generated method stub

	}
	@Override
	public void readAll(String start, String end) {
		// TODO Auto-generated method stub

	}

}
