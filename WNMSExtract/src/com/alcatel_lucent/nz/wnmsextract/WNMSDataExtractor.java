package com.alcatel_lucent.nz.wnmsextract;
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

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EnumSet;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
//import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.alcatel_lucent.nz.wnmsextract.DataLogger.LogAppType;
import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;
import com.alcatel_lucent.nz.wnmsextract.document.NetworkType;
import com.alcatel_lucent.nz.wnmsextract.document.TransformerType;
import com.alcatel_lucent.nz.wnmsextract.document.WIPSMapRANNetworkTransformer;
import com.alcatel_lucent.nz.wnmsextract.reader.BorgBlockReader;
import com.alcatel_lucent.nz.wnmsextract.reader.BorgSelectionReader;
import com.alcatel_lucent.nz.wnmsextract.reader.Extractor;
//import com.alcatel_lucent.nz.wnmsextract.reader.ExtractorGTAZIP;
//import com.alcatel_lucent.nz.wnmsextract.reader.ExtractorTNZTAR;
//import com.alcatel_lucent.nz.wnmsextract.reader.ExtractorTNZCPV;
//import com.alcatel_lucent.nz.wnmsextract.reader.FileUtilities;
import com.alcatel_lucent.nz.wnmsextract.reader.HttpReader;

/**
 * Single threaded application entry point for WNMS parsing. This is the file you probably want to run
 * @author jnramsay
 * TODO FTP client as a connector type
 * TODO Jar-in-Jar classloading (!) MDR
 */
@SuppressWarnings("unused")
public class WNMSDataExtractor {

	public static final String START_DATE = "2010-08-17";
	public static final String FINISH_DATE = "2010-08-18";
	//private Lock lock;
	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.WNMSDataExtractor");
	private static Logger slog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract");


	//private static final String SOURCE_PATH = "\\\\nz107204-rf04\\CPV_UMTS_ARCHIVE\\";
	//private static final DatabaseType DEF_DATABASE = DatabaseType.NPO48;

	private DatabaseType databasetype = null;
	private Calendar calendar = null;

	//private Extractor extractor = null;

	public DateFormat df,tf;

	private EnumSet<DocumentType> alldocs = null;

	//private String datapath = null, temppath = null;

	private DataLogger wdl;


	/** Flag to indicate whether to perform post load aggregation*/
	private boolean atflag = true;
	/** Flag to indicate whether to NOT process Borg tables*/
	private boolean borgstep = true;
	/** Flag to indicate whether to NOT process WNMS tables*/
	private boolean wnmsstep = true;
	/** Borg selection date range start date*/
	private String borgstartdate = null;
	/** Borg selection date range finish date*/
	private String borgfinishdate = null;
	/** Transformer type, ALU/HTML + Dynamic/Static. Sets AS as default */
	private TransformerType transtype = TransformerType.AS;//def
	/** Variable indicating network you are processing data for */
	private NetworkType networktype = null;

	/**
	 * Null constructor sets up empty logger.
	 */
	public WNMSDataExtractor(){
		wdl = new DataLogger(EnumSet.noneOf(LogAppType.class),slog);
	}

	/**
	 * Initialiser for the Scheduler
	 * atflag = Aggregate-Table-FLAG
	 */
	public void init(){
		/*
		if(this.alldocs==null){
			//all docs regardless of aggregation step
			this.alldocs = EnumSet.allOf(DocumentType.class);
		}
		 */
		//datapath = Extractor.chooseSourcePath();
		//temppath = Extractor.chooseTempPath();
		//jlog.info("DOC:"+alldocs+",SRC:"+datapath+"["+temppath+"],DST:"+this.databasetype+",DTM:"+FileUtilities.calendarToString(this.calendar));
	}


	/* getters setters*/
	
	
	public void setDatabaseType(DatabaseType databasetype) {
		this.databasetype = databasetype;
	}
	public DatabaseType getDatabaseType() {
		return databasetype;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	public Calendar getCalendar() {
		return calendar;
	}
	public EnumSet<DocumentType> getAllDocs() {
		return alldocs;
	}
	public void setAllDocs(EnumSet<DocumentType> alldocs) {
		this.alldocs = alldocs;
	}

	public static Calendar parseTimestamp(String timestamp) throws java.text.ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(timestamp));
		//jlog.info(">>>>>"+timestamp+"<<<<<<"+df.format(cal.getTime()));
		return cal;
	}

	//---------------------------------------------------------------------------

	/**
	 * Main action method. Instantiates an Extractor for WNMS and triggers processing,
	 * instantiates a Reader into the Borg server and sets up for a parse of a WiPS mapping 
	 * transformer for the NetworkGraph (which probably wont be used because its too expensive) 
	 */
	public void activate(){

		//process wnms files...
		if(wnmsstep) {
			Extractor extractor = new Extractor(networktype,transtype);

			if(calendar!=null) //otherwise extractor uses default = today-1
				extractor.setRequestDate(calendar);

			extractor.prepareDocuments();

			for(DocumentType doctype : alldocs){
				extractor.processDocType(doctype, atflag);
			}
			extractor = null;
		}

		//process borg http source...
		if (borgstep){
			/*BlockReader is the old method, retained as a backup since the selection script sometimes fails.
			 * NB. Need to maintain the RncAp enum in BorgBlockReader.java or rewrite it to be more flexible*/
			//HttpReader borg = new BorgBlockReader(databasetype);//relies on wnmsextract being run regularly since borg data expires after 2/3 days
			
			HttpReader borg = new BorgSelectionReader(databasetype);//relies on wnmsextract being run regularly since borg data expires after 2/3 days
			if(borgstartdate!=null || borgfinishdate!=null)
				borg.readAll(borgstartdate,borgfinishdate);
			else
				borg.readAll();

			if (atflag) borg.logRawTableChanges();
			borg = null;
		}

		boolean mapstep = Boolean.FALSE;
		if(mapstep){
			File source = new File("test/test0.xcm");
			File target = new File("test/testmap.xml");
			WIPSMapRANNetworkTransformer.transform(source, target);
		}

	}


	/** readArgs. Read command line arguments and parse
	 * @param args
	 * @return read with/without error
	 */
	public boolean readArgs(String[] args){
		boolean ret = true;
		boolean af = true;
		boolean bs = true;
		boolean ws = true;
		boolean vp = false;
		//boolean ns = false;
		DatabaseType df = DatabaseType.TNZ_NZRSDB;
		LogAppType lf = LogAppType.File;
		Calendar tf = Calendar.getInstance();
		tf.add(Calendar.DATE, -1);
		//DocumentType xf = null;//default is the list of all doc types so do that in init()
		String bsd = null;
		String bfd = null;

		try {
			Options opt = new Options();

			opt.addOption("h", "help", false, "Print help for this application");
			//opt.addOption("e", "extract", true,  "Extraction Source {c=CPV Archive, t=TNZ Ops TAR file, g=GTA ZIP file. No default.}");
			opt.addOption("l", "log", true,  "Logging Appender {f=File (def), c=Console, s=Socket}");
			opt.addOption("t", "time",true,  "Specific process date {yyyy-MM-dd}. Default to 'Yesterday'");
			opt.addOption("d", "database", true,  "Database to send Results {NPO48, NPO33, TEST, MPM, TNZ_NZRSDB , GTA_NZRSDB. No Default}");
			opt.addOption("x", "doctype", true,  "Document Type to parse {WNMS_NodeB, WMNS_INode, WNMS_RncCn, WIPS_mRNb, WIPS_mRVcc, WIPS_mFB, WIPS_cNbP}. Default to 'All'");
			opt.addOption("a", "aggskip", false, "Skip Aggregation Step (useful for batch requests)");
			opt.addOption("b", "borgskip", false, "Skip Borg Extraction Step (useful if Borg data is redundant)");
			opt.addOption("w", "wnmsskip", false, "Skip WNMS Extraction Step (useful if WNMS is overflowing disk)");
			opt.addOption("s", "borgstartdate", false, "Start date for selected Borg data range");
			opt.addOption("f", "borgfinishdate", false, "End date for selected Borg data range");
			opt.addOption("v", "variablexsl", false, "Use dynamic-position XSL transformer (def static)");
			opt.addOption("n", "network", true, "Selecting GTA or TNZ sets: scrape vs file read, xmlns:HTML vs xmlns namespace (will add db when mature)");

			BasicParser bp = new BasicParser();
			CommandLine cl = bp.parse(opt, args);

			if ( cl.hasOption('h') ) {
				HelpFormatter f = new HelpFormatter();
				f.printHelp("OptionsTip", opt);
				ret = false;
				System.exit(0);
			}
			else {

				//Extraction
				/*
				if (cl.hasOption('e')){
					String lval = cl.getOptionValue("e");
					if ("c".compareTo(lval.toLowerCase())==0){
						extractor = new Extractor();
					}
					else if ("t".compareTo(lval.toLowerCase())==0){
						extractor = new Extractor();
					}
					else if ("g".compareTo(lval.toLowerCase())==0){
						extractor = new Extractor();
					}
					else {
						System.out.println("Extractor specifier ["+lval+"] incorrect, default to 'TAR'");
						extractor = new Extractor();
					}

				}
				else{
					System.out.println("Extractor not specified, default to 'TAR'");
					System.out.println("*** TESTING!\n*** NO DEFAULT EXTRACOR SET UP.\nPrecaution to prevent cross network pollution, exiting");
					System.exit(1);
				}
				*/

				//Logging
				if (cl.hasOption('l')){
					String lval = cl.getOptionValue("l");
					if ("f".compareTo(lval.toLowerCase())==0){
						lf = LogAppType.File;
					}
					else if ("c".compareTo(lval.toLowerCase())==0){
						lf = LogAppType.Console;
					}
					else if ("s".compareTo(lval.toLowerCase())==0){
						lf = LogAppType.Socket;
					}
					else {
						System.out.println("Logging Appender ["+lval+"] incorrect, default to 'File'");
					}

				}
				else{
					System.out.println("Logging Appender not specified, default to 'File'");
				}

				//DateTime
				if (cl.hasOption('t')){
					try {
						tf = parseTimestamp(cl.getOptionValue("t"));
					}
					catch (java.text.ParseException pe) {
						System.err.println("Date not specified correctly. "+pe+". Using Default 'today'");
					}
				}
				else{
					System.out.println("Date not specified. Using Default 'today'");
				}

				//Database
				/*
				if (cl.hasOption('d') && DatabaseType.valueOf(cl.getOptionValue("d"))!=null){
					df = DatabaseType.valueOf(cl.getOptionValue("d"));
				}
				else {
					System.out.println("DatabaseType not specified");
					System.out.println("*** ATTENTION! ***\nNO DEFAULT DATABASE SET UP.\nOnly you can prevent cross network data pollution. Exiting");
					System.exit(1);
				}
				*/

				//Document
				if (cl.hasOption('x')){
					String xstr = cl.getOptionValue("x");
					if ("WNMS_All".compareTo(xstr)==0){
						System.out.println("DocumentType WNMS_All {RncCn,INode,NodeB,INodeVcc}");
						this.alldocs = EnumSet.of(DocumentType.WNMS_RncCn, DocumentType.WNMS_INode,DocumentType.WNMS_NodeB,DocumentType.WNMS_INodeVcc);
					}
					else if("WIPS_All".compareTo(xstr)==0){
						System.out.println("DocumentType WIPS_All {cNbP,mFB,mRNb,mRVcc}");
						this.alldocs = EnumSet.of(DocumentType.WIPS_cNbP, DocumentType.WIPS_mFB,DocumentType.WIPS_mRNb,DocumentType.WIPS_mRVcc);
					}
					else {
						//get what is requested, assuming selection is correct
						System.out.println("DocumentType {"+xstr+"}");
						this.alldocs = EnumSet.of(DocumentType.valueOf(xstr));
					}
				}
				else {
					//get all the docs, WNMS and WiPS
					System.out.println("DocumentType not specified, Default All {RncCn,INode,NodeB,INodeVcc,cNbP,mFB,mRNb,mRVcc}");
					this.alldocs = EnumSet.allOf(DocumentType.class);
				}

				/* Aggregation.
				 * if -a then SKIP aggregation and therefore skip WiPS import. Makes no
				 * sense to set -x WIPS option if also setting -a option
				 */
				if (cl.hasOption('a')){
					af = false;
				}
				else {
					System.out.println("Post insert-log/aggregation not specified. Default to TRUE");
				}

				//Borg (skip Borg processing, just do WNMS)
				if (cl.hasOption('b')){
					bs = false;
				}
				else {
					System.out.println("Borg Skip not requested. Default to TRUE");

					//borg start
					if (cl.hasOption('s')){
						bsd = cl.getOptionValue("s");
					}
					else {
						System.out.println("Borg startdate not specified. Default to Yesterday");
					}

					//borg finish
					if (cl.hasOption('f')){
						bfd = cl.getOptionValue("f");
					}
					else {
						System.out.println("Borg finishdate not specified. Default to Today");
					}

				}



				//WNMS (skip WNMS processing, just do Borg)
				if (cl.hasOption('w')){
					ws = false;
				}
				else {
					System.out.println("Process WNMS Source not specified. Default to TRUE");
				}

				//Variable position Static/Dynamic XSL transformer for WNMS
				if (cl.hasOption('v')){
					vp = true;
				}
				else {
					System.out.println("Variable/Dynamic parser not requested, default to static");
				}

				//Namespace has subdeclaration xmlns:HTML
				if (cl.hasOption('n')){
					String netw = cl.getOptionValue("n");
					if ("GTA".compareTo(netw)==0){
						this.networktype = NetworkType.GTA;
					}
					else if("TNZ".compareTo(netw)==0){
						this.networktype = NetworkType.TNZ;
					}
					else {
						System.out.println("Network Selection invalid: "+netw+", Exiting");
						System.exit(1);

					}
				}
				else {
					System.out.println("Network Selection required! Exiting");
					System.exit(1);
				}
			}
		}
		catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
		}

		this.wdl.addLoggingAppender(lf);
		this.setAggregateTables(af);
		this.setBorgStep(bs);
		this.setWNMSStep(ws);
		//if(xf!=null)this.alldocs = EnumSet.of(xf);
		this.setDatabaseType(df);
		this.setCalendar(tf);

		this.setBorgStartDate(bsd);
		this.setBorgFinishDate(bfd);

		this.setTransformerType(vp);

		jlog.info("LG:"+lf+", DT:"+tf.getTime()+", DB:"+df+", DC:"+this.alldocs+", AT:"+af+", BS:"+bs+", NT:"+networktype);
		System.out.println("LG:"+lf+", DT:"+tf.getTime()+", DB:"+df+", DC:"+this.alldocs+", AT:"+af+", BS:"+bs+", WS:"+ws+", NT:"+networktype+", VP:"+vp);

		return ret;
	}


	/** Transformertype is a 2x2 of Static/Dynamic XSL (position refs to effect XSL speed)
	 * or no namespace/subnamespace (GTA=xmlns:html, TNZ=xmlns) and require different XSLs
	 */
	private void setTransformerType(boolean vp) {
		this.transtype = networktype.getTransformerType(vp);
	}

	private void setAggregateTables(boolean af) {
		this.atflag = af;

	}

	private void setBorgStep(boolean bs) {
		this.borgstep = bs;

	}
	private void setWNMSStep(boolean ws) {
		this.wnmsstep = ws;

	}

	public void setBorgStartDate(String borgstartdate) {
		this.borgstartdate = borgstartdate;
	}

	public void setBorgFinishDate(String borgfinishdate) {
		this.borgfinishdate = borgfinishdate;
	}

	public static void main(String[] args) throws InterruptedException {
		//WNMSDataScheduler.log();
		//PropertyConfigurator.configure(new Properties(){
		//private static final long serialVersionUID = 1L;});
		//BasicConfigurator.configure();

		WNMSDataExtractor wde = new WNMSDataExtractor();
		if(wde.readArgs(args)){
			wde.init();

			jlog.info("START - DATA TRANSFER MONITOR");
			wde.activate();
			jlog.info("FINISH - DATA TRANSFER MONITOR");
		}
		wde = null;
		System.exit(0);
	}



}