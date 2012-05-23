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
import java.util.Calendar;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVStrategy;

import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities;
import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities.ColumnStructure;
import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
/*
 * https://borg.anz.lucent.com/rnc_stats/cpu_statistics_extractor.pl?start_date=2010-09-27&end_date=2010-09-28
 * "Date","Rnc","Lp","Ap","Role","Cpuutil"
 * "2010-09-27 00:02:01","CH-RNC01","2","0","Maste","9"
 */

/**
 * Reader that scrapes Borg data from CGI script defining dates
 */
public class BorgSelectionReader implements HttpReader {

	private static final String BORG = "https://borg.anz.lucent.com/rnc_stats/cpu_statistics_extractor.pl";
	private static final String TABLE = "rncap_borg";
	public static final DateFormat BORG_DATA_DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat BORG_QUERY_DF = new SimpleDateFormat("yyyy-MM-dd");
	public static final DatabaseType DEF_DBT = DatabaseType.TNZ_NZRSDB;

	//delim, encaps, comment
	public CSVStrategy strategy;
	public DatabaseType databasetype;


	/**
	 * Constructor setting SSL certificates or bypassing them. Bypassing is easier since OPs 
	 * sometimes change their certificates requiring you to re-download and store them
	 * @param databasetype
	 */
	public BorgSelectionReader(DatabaseType databasetype){
		this.databasetype = databasetype;
		this.strategy = new CSVStrategy(',','"','#');
		System.out.println(System.getProperty("java.home"));

		SSLReaderUtilities.bypassSSLAuthentication();

		//System.setProperty("javax.net.ssl.trustStore", Extractor.chooseCACertsPath()+"cacerts");
		//System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        //System.out.println("javax.net.ssl.trustStore = "+System.getProperty("javax.net.ssl.trustStore"));
	}
	
	/** 
	 * Default cons should probably never be called
	 */
	public BorgSelectionReader(){
		this(DEF_DBT);
	}

	@Override
	public void readAll(){
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();//today
		start.add(Calendar.DATE, -1);//today-1

		readAll(start,end);
	}
	@Override
	public void readAll(String start, String end) {
		Calendar scal = Calendar.getInstance();
		scal.set(Integer.parseInt(start.substring(0,4)),
				Integer.parseInt(start.substring(5,7))-1,
				Integer.parseInt(start.substring(8)));
		Calendar ecal = Calendar.getInstance();
		ecal.set(Integer.parseInt(end.substring(0,4)),
				Integer.parseInt(end.substring(5,7))-1,
				Integer.parseInt(end.substring(8)));
		readAll(scal,ecal);
	}
	
	/**
	 * Main readAll method with calendar type args. Sets up data array and uses 
	 * DB utilities class to bulk insert
	 */
	@Override
	public void readAll(Calendar start, Calendar end) {
		ArrayList<ColumnStructure> colstruct = new ArrayList<ColumnStructure>();
		colstruct.add(ColumnStructure.VC);
		colstruct.add(ColumnStructure.TS);
		colstruct.add(ColumnStructure.IT);

		ArrayList<ArrayList<String>> dmap = new ArrayList<ArrayList<String>>();
		try {
			//URL borg = new URL(BORG+getYesterday());
			URL borg = new URL(BORG+getDateSelection(start,end));
	        URLConnection conn = borg.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

	        CSVParser parser = new CSVParser(in,strategy);
	        //if header 
	        //[just consumes header] 
	        //String[] header = 
	        parser.getLine();
	        //and body
	        String[] line = null;

	        while((line = parser.getLine())!= null){
	        	ArrayList<String> list = new ArrayList<String>();
	        	list.add(idConvert(line[1],line[2],line[3]));
	        	Calendar cal = Calendar.getInstance();
				cal.setTime(BORG_DATA_DF.parse(line[0]));
            	list.add(ALUDBUtilities.ALUDB_DF.format(cal.getTime()));
            	list.add(line[5]);

	        	dmap.add(list);

	        }
	        in.close();
		}
		catch(ArrayIndexOutOfBoundsException aiobe){
			System.err.println("Result not parseable "+aiobe);
			System.exit(1);
		}
		catch(MalformedURLException mrue){
			System.err.println("Borg Path incorrect "+mrue);
			System.exit(1);
		}
		catch(IOException ioe){
			System.err.println("Cannot read Borg file "+ioe);
			System.exit(1);
		}
		catch(ParseException pe){
			System.err.println("Cannot parse Date field "+pe);
			System.exit(1);
		}

		/* bulk insert */
		ALUDBUtilities.insert(databasetype, TABLE, colstruct, dmap);

	}

	/**
	 * Builds selection data parameter string
	 * @param start Start date
	 * @param end End date
	 * @return Returns the HTTP get/post part of the URL
	 */
	private String getDateSelection(Calendar start,Calendar end){
		return "?start_date="+BORG_QUERY_DF.format(start.getTime())
		+"&end_date="+BORG_QUERY_DF.format(end.getTime());
	}
	
	/* used but no longer
		private String getDateSelectionString(String start,String end){
			return "?start_date="+start+"&end_date="+end;
		}
	*/

	/**
	 * Aggregation trigger table writer
	 */
	@Override
	public void logRawTableChanges (){
		ALUDBUtilities.log(databasetype, TABLE, "INSERT");
	}

	public void writeAll(){}

	/**
	 * ID conversion done in code since ID stored over multiple columns.
	 * TL;DR, just easier this way and Borg is a special case
	 * @param rnc
	 * @param lp
	 * @param ap
	 * @return
	 */
	private String idConvert(String rnc, String lp, String ap){
		return "AP_"+rnc.replace("-", "_")+"/"+rnc.replace("-", "_")+"IN0/"+lp+"/"+ap;

	}


}
