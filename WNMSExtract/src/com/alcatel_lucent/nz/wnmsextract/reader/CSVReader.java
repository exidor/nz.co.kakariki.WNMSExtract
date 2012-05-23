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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVStrategy;

import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities;
import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities.ColumnStructure;

/**
 * Simple CSV reader using the Apache Commons CSV package
 * @author jnramsay
 *
 */
public class CSVReader {

	public static CSVStrategy strategy = new CSVStrategy(',','"','#');
	public static final DateFormat DATA_DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * String tokeniser method will split up a CSV file by row and column 
	 * returning a List<List<String>>
	 * @param in
	 * @param struct
	 * @return
	 */
	public static ArrayList<ArrayList<String>> read(BufferedReader in, ArrayList<ColumnStructure> struct){
		ArrayList<ArrayList<String>> dmap = new ArrayList<ArrayList<String>>();
		CSVParser parser = new CSVParser(in,CSVReader.strategy);

	    //if header
	    try {
	    	//[consume header]
			//String[] header = 
	    	parser.getLine();

		    //and body
		    String[] line = null;

		    while((line = parser.getLine())!= null){
		    	ArrayList<String> list = new ArrayList<String>();

		    	for (int i=0; i<line.length; i++){
		    		ColumnStructure cs = struct.get(i);
		    		switch (cs){
		    			case VC:
		    				list.add(line[i]);
		    				break;
		    			case TS:
		    				Calendar cal = Calendar.getInstance();
		    				cal.setTime(DATA_DF.parse(line[i]));
		    				list.add(ALUDBUtilities.ALUDB_DF.format(cal.getTime()));
		    				break;
		    			case FL:
		    				list.add(String.valueOf(validateFloat(Float.parseFloat(line[i]))));
		    				break;
		    			case IT:
		    				list.add(String.valueOf(validateInt(Integer.parseInt(line[i]))));
		    				break;
		    			default:
		    				list.add(line[i]);
		    		}
		    	}


		    	dmap.add(list);
		    }
	    }
	    catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		} catch (ParseException pe) {
			// TODO Auto-generated catch block
			pe.printStackTrace();
		}

	    return dmap;
	}
	/**
	 * Unimplemented. Validate whether a string representation of a float value
	 * makes sense and whether it is within proscribed boundsbefore attempting 
	 * to cast to float
	 * @param f
	 * @return
	 */
	public static float validateFloat(float f){return f;}
	
	/**
	 * Unimplemented. Validate whether a string representation of an int value
	 * makes sense and whether it is within proscribed boundsbefore attempting 
	 * to cast to int
	 * @param f
	 * @return
	 */
	public static float validateInt(int i){return i;}
}
