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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities;
import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnectorFactory;
import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
import com.alcatel_lucent.nz.wnmsextract.document.ALUDocument;
import com.alcatel_lucent.nz.wnmsextract.document.ALUDocumentFactory;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;
import com.alcatel_lucent.nz.wnmsextract.document.TransformerType;
import com.alcatel_lucent.nz.wnmsextract.parser.ALUParser;
import com.alcatel_lucent.nz.wnmsextract.parser.ALUParserFactory;
import com.alcatel_lucent.nz.wnmsextract.reader.LogWriter;

import javax.xml.transform.TransformerException;

/**
 * WNMSTRansform. Primary interface to Transform applications. To use get an instance of this class, set
 * the destination database, the log output required and run the process method on the file you want to
 * parse. Naturally the parse file and the document type must agree. To finalise parsing a group of files
 * view to table aggregations can be performed by calling the db log method for the current document.
 * <p>WNMSTransform t = new WNMSTransform(DatabaseType.NPO48, LogAppType.File);<br/>
 * t.setTransformType(DocumentType.WNMS_INode);<br/>
 * t.process("somefile.inode.xml");<br/>
 * t.logRawTableChanges();</p>
 */
public class WNMSTransform implements LogWriter{

	public final static String DEF_PATH = "/conf/";

	public List<String[]> transformpath;
	private DatabaseType dbtype;
	//private EnumSet<LogAppType> latype = EnumSet.noneOf(LogAppType.class);
	private DocumentType doctype;
	private boolean atflag = true;
	private TransformerType transtype = TransformerType.AS;//default TNZ Static
	private ALUJDCConnector ajc;

	//private static Logger slog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract");
	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.WNMSTransform");

	/**
	 * Null constructor sets up NPO as Database and File type Logging as default
	 */
	public WNMSTransform(){this(DatabaseType.TNZ_NZRSDB);}//,LogAppType.File);}

	/**
	 * Primary Constructor taking Database and Logging definitions/
	 * @param dbtype The name of the DB to send the results to (must be detailed in DatabaseType enum)
	 * @param latype The type of logging to enable (must be detailed in LogAppType enum)
	 */
	public WNMSTransform(DatabaseType dbtype){//,LogAppType latype){
		setDatabaseName(dbtype);
	}

	public void setAggregateTables(boolean atflag) {
		this.atflag = atflag;
	}

	//set DEST DB, optional will DEF to NPO
	public void setDatabaseName(DatabaseType dbtype){
		this.dbtype = dbtype;
		jlog.info("DB "+dbtype);
		ajc = new ALUJDCConnectorFactory(this.dbtype).getInstance();
	}

	/**
	 * Set the doctype and thus the XSL transformer
	 * @param doctype
	 */
	public void setDocumentType(DocumentType doctype){
		this.doctype = doctype;
		jlog.info("TR "+doctype);
	}
	/**
	 * Transformer Type defines whether to use Dynamic (safe but slow) XSL
	 * stylesheet of the static XSL (faster but needs to be changed if the 
	 * format of the WNMS XML changes)
	 * @param transtype
	 */
	public void setTransformType(TransformerType transtype){
		this.transtype = transtype;
	}

	/**
	 * Main process method. Initialises a document and appropriate parser taking as
	 * input a parsefile name
	 * @param pfname Name of file to parse.
	 */
	public boolean process(String pfname){return process(new File(pfname));}
	public boolean process(File pfile){
		//ttype WNMS_NodeB, WNMS_INode, WNMS_RncCn, WIPS_Snapshot
		ALUDocument d = new ALUDocumentFactory(this.doctype).getInstance();
		ALUParser p = new ALUParserFactory(this.doctype.toString().substring(0, 4)).getInstance();

		p.setParseFile(pfile);
		//comment this out in development environment
		System.out.println("<Processing> "+pfile.getName());
		//p.setTransformFile(WNMSTransform.class.getClass().getResourceAsStream(DEF_PATH+d.getTransformer()));


		InputStream is = WNMSTransform.class.getClass().getResourceAsStream(DEF_PATH+d.getTransformer(transtype));
		if(is==null){
			try {
				is = new FileInputStream(DEF_PATH.substring(1,DEF_PATH.length())+d.getTransformer(transtype));
			} catch (FileNotFoundException fnfe) {
				System.err.println("Can't find transform file :: "+fnfe);
			}
		}
		//BufferedReader br = new BufferedReader(new InputStreamReader(is));
		//System.out.println(br.readLine());
		p.setTransformFile(is);

		//TODO attempt to fix null connection instances in document insert calls
		if(ajc==null) ajc = new ALUJDCConnectorFactory(this.dbtype).getInstance();
		d.setConnector(ajc);
		if (this.doctype.clean()) d.clean(this.doctype);

		//------------------
		try {

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			p.transform(out);
			//System.out.println(out);
			d.process(
					new ByteArrayInputStream(out.toByteArray())
			);
			out.close();
		} catch (TransformerException te){//| TransformerConfigurationException tce){
			//System.err.println("TCE. Trouble reading/compiling XML "+pfile+" :: "+te);//TE catches TE and TCE
			jlog.error("TE. Exception reading/compiling extracted XML "+pfile+". File is probably faulty, Skipping! :: "+te);
			return false;
		} catch (IOException ioe) {
			//System.err.println("Error closing file-stream"+ioe);
			jlog.error("Error closing file-stream"+ioe);
			return false;
		}
		jlog.info("Completed "+pfile.getName());

		return true;

	}

	/**
	 * <p>Logs insertions of DType identified raw tables to the log_process table in the
	 * named database. <b>IMPORTANT</b> logging to this 'rules' based tables triggers
	 * view to table aggregations.</p>
	 *
	 * <table border=1>
	 * <thead>
	 * <tr><th></th><th>raw<br/>inode<br/>atmport</th><th>raw<br/>inode<br/>ethernet</th><th>raw<br/>inode<br/>lp</th><th>raw<br/>inode<br/>ap</th><th>raw<br/>nodeb<br/>ipran</th><th>raw<br/>nodeb<br/>pa</th><th>raw<br/>nodeb<br/>ccm</th><th>raw<br/>nodeb<br/>cem</th><th>raw<br/>nodeb<br/>btscell</th><th>raw<br/>nodeb<br/>hsdpaservice</th><th>raw<br/>nodeb<br/>imagroup</th><th>raw<br/>rnccn<br/>utrancell</th><th>map<br/>bts<br/>fdd</th><th>snap<br/>nodeb<br/>pcm</th></tr>
	 * </thead>
	 * <tbody>
	 * <tr><td>int_atmport_t</td><td>x</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	 * <tr><td>int_cell3g_bhi_t</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>x</td><td></td><td></td></tr>
	 * <tr><td>int_cell3g_perf_t</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>x</td><td></td><td></td></tr>
	 * <tr><td>int_cell3g_t</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>x</td><td></td><td></td><td>x</td><td>x</td><td></td></tr>
	 * <tr><td>int_cell3g_traffic_t</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>x</td><td></td><td>x</td><td>x</td><td></td></tr>
	 * <tr><td>int_etherlp_t</td><td></td><td>x</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	 * <tr><td>int_ipran_t</td><td></td><td></td><td></td><td></td><td>x</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	 * <tr><td>int_lp_t</td><td></td><td></td><td>x</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	 * <tr><td>int_nodeb_papwr_t</td><td></td><td></td><td></td><td></td><td></td><td>x</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	 * <tr><td>int_nodeb_t</td><td></td><td></td><td></td><td></td><td></td><td></td><td>x</td><td>x</td><td></td><td></td><td>x</td><td></td><td></td><td>x</td></tr>
	 * <tr><td>int_rnc_ap_t</td><td></td><td></td><td></td><td>x</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
	 * <tr><td>int_rnc_t</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>x</td><td></td><td></td></tr>
	 * <tr><td>int_rnc_traffic_t</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>x</td><td></td><td>x</td><td>x</td><td></td></tr>
	 * </tbody>
	 * </table>
	 */
	@Override
	public void logRawTableChanges(){
		if(atflag){
			for(String raw : this.doctype.getTList()){
				jlog.debug("Log "+raw);
				ALUDBUtilities.log(dbtype, raw, "INSERT");
			}
		}
	}


	/**
	 * Called if the Transformer class is called as a main. Useful for one off XML parse jobs
	 * @param args
	 */
	public void processcli(String[] args){
		String xf=null,sf=null,of = null;
		try {
			Options opt = new Options();

			opt.addOption("h", "help",false, "Print help for this application");
			opt.addOption("x", "xml", true, "The XCM file to load");
			opt.addOption("s", "xsl", true, "The stylesheet to apply");
			opt.addOption("o", true, "The output CSV to generate");

			BasicParser bp = new BasicParser();
			CommandLine cl = bp.parse(opt, args);

			ALUParser p = new ALUParserFactory("WIPS").getInstance();

			if ( cl.hasOption('h') ) {
				HelpFormatter f = new HelpFormatter();
				f.printHelp("OptionsTip", opt);
			}
			else {
				//XCM snapshot
				if (cl.hasOption('x')){
					xf = cl.getOptionValue("x");
				}
				else{
					System.err.println("XML File not Specified");
					System.exit(1);
				}

				//stylesheet
				if (cl.hasOption('s')){
					sf = cl.getOptionValue("s");
				}
				else{
					System.err.println("XSL File not Specified");
					System.exit(1);
				}

				//output
				if (cl.hasOption('o')){
					of = cl.getOptionValue("o");
				}
				else {
					String sfn = sf.substring(sf.lastIndexOf("\\")+1, sf.length()-4);
					of = xf.substring(0,xf.length()-4)+"."+sfn+".csv";
				}

				System.out.println(xf+" + "+sf+" -> "+of);

				p.setParseFile(xf);
				p.setTransformFile(sf);
				p.setResultFile(of);
				//how is this ever supposed to work if you never call transform?!?

			}
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
		WNMSTransform w = new WNMSTransform();
		w.processcli(args);


	}
}
