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
import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.alcatel_lucent.nz.wnmsextract.WNMSTransform;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;
import com.alcatel_lucent.nz.wnmsextract.document.NetworkType;
import com.alcatel_lucent.nz.wnmsextract.document.TransformerType;

/**
 * Main extractor worker class.
 * @author jnramsay
 *
 */
public class Extractor {

	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.Extractor");

	//some temp dir where unzipped files are stored
	//public static final String INTPATH = System.getProperty("user.dir") + "\\";

	//TODO. consider moving path declarations to FileSelector sub classes
	//since these are (will become) specific to each extraction case

	//public static final String WIN_TMP_PATH = "C:\\wnms\\temp\\";
	//public static final String WIN_TMP_PATH = "\\\\nzwels0015\\Groups\\Eng\\TNZ\\RF Engineering\\RF Planning\\Capacity\\WNMSTempFileStore\\";
	public static final String WIN_TMP_PATH = "G:\\data\\wnms\\extract\\temp\\";
	public static final String LNX_TMP_PATH = "/data/temp/";
	public static final String SUN_TMP_PATH = "/var/tmp/";

	//public static final String WIN_SRC_PATH = "\\\\nz107204-rf04\\CPV_UMTS_ARCHIVE\\";
	//public static final String WIN_SRC_PATH = "C:\\wnms\\source\\";
	public static final String WIN_SRC_PATH = "G:\\data\\wnms\\extract\\source\\";
	public static final String LNX_SRC_PATH = "/data/ftp/upload/";
	public static final String SUN_SRC_PATH = "/var/ftp/";

	public static final String WIN_CAC_PATH = "C:\\Program\\ Files\\Java\\jre\\lib\\security\\";
	public static final String LNX_CAC_PATH = "/usr/java/jre/lib/security/";
	public static final String SUN_CAC_PATH = "/usr/java/jre/lib/security/";

	public static final boolean DEF_LOG_STATE = true;

	//moved out to config file
	//public static final String rawPath = "\\\\nz107204-rf04\\CPV_UMTS_ARCHIVE\\RAW\\";
	//public static final String compressedPath = "\\\\nz107204-rf04\\CPV_UMTS_ARCHIVE\\~COMPRESSED\\";

	//private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.Extractor");

	protected Calendar cal = null;
	protected TransformerType transtype = TransformerType.AS;//def

	private NetworkType networktype;
	private FileSelector fileselector = null;

	protected Extractor(){/*not to be used*/}

	/**
	 * Main Constructor. Sets up reader instance
	 */
	public Extractor(NetworkType networktype,TransformerType transtype){
		setNetworkType(networktype);
		setTransformerType(transtype);

		switch(networktype.getReaderType()){
		case GTAHttpReader: fileselector = GTAHttpReader.getInstance();break;
		case TNZArchiveReader: fileselector = TNZArchiveReader.getInstance();break;
		}

	}

	/**
	 * Rarely used destructor method to ensure that fileselector, which will 
	 * typically be a large object, is garbage collected.
	 */
	@Override
	protected void finalize() throws Throwable {
	  fileselector = null;
	  super.finalize();
	}

	/**
	 * Processes a given set of documents for a particular day. You really need to call setCalendar,
	 * setNetworkType and setTransformerType before running this
	 * @param doctype Files are selectively processed by doctype. This parameter sets that value 
	 * @param atflag User settable parameter to turn on aggregation in the DB
	 */
	public void processDocType(DocumentType doctype, boolean atflag){
		jlog.info("Extraction Job, "+networktype+"/"+doctype);

		WNMSTransform transformer = new WNMSTransform(networktype.getDatabaseType());//, LogAppType.File);
		transformer.setAggregateTables(atflag);
		transformer.setDocumentType(doctype);
		transformer.setTransformType(transtype);

		fileselector.setDocType(doctype);
		List<File> flist = fileselector.getFileList();
		for(File file : flist){
			jlog.info("Processing WNMS/WiPS File "+file.getName());
			//if (t.process(file))deleteDocuments(file);
			if (!transformer.process(file)){
				/* skip file. remove from list will bugger up the List itself */
				//flist.remove(file);
				jlog.error(file.getName()+" is corrupt!");
			}
		}

		transformer.logRawTableChanges();
		transformer = null;
	}

	/**
	 * processDocuments. Syncs and calls a FileSelector
	 * @param doctype
	 * @return
	 */
	/*
	@Deprecated
	private List<File> processDocuments(DocumentType doctype, ReaderType readtype){

		FileSelector fs = null;

		switch(readtype){
		case GTAHttpReader: fs = GTAHttpReader.getInstance();break;
		case TNZArchiveReader: fs = TNZArchiveReader.getInstance();break;
		}

		List<File> lf = null;

		if(cal==null) {
			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
		}

		boolean normal = false;
		try {
			synchronized(fs){
				jlog.info("Lock <ACQ> "+doctype+" thread : "+fs);
				fs.setCalendar(cal);
				fs.setSourcePath(new File(Extractor.chooseSourcePath()+getNetworkType()));
				fs.setTempPath(new File(Extractor.chooseTempPath()+getNetworkType()));
				fs.extract();
				lf = fs.getFileList(doctype);
				Collections.sort(lf);
				normal = true;
				jlog.info("Lock <REL> "+doctype+" thread : "+fs);
			}
		}
		finally{
			if(!normal)jlog.error("Lock < *** FAILRELEASE *** > "+doctype+" thread : "+fs);
		}

		fs = null;

		return lf;

	}
	*/

	/**
	 * prepareDocuments. Syncs and populates a FileSelector
	 * @return
	 */
	public void prepareDocuments(){

		if(cal==null) {
			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
		}

		boolean normal = false;
		try {
			synchronized(fileselector){
				jlog.info("Lock <ACQ> thread : "+fileselector);
				fileselector.setCalendar(cal);
				fileselector.setSourcePath(new File(Extractor.chooseSourcePath()+getNetworkType()));
				fileselector.setTempPath(new File(Extractor.chooseTempPath()+getNetworkType()));
				fileselector.extract();//put temp bypass when extract so dont have to re-unzip
				normal = true;
				jlog.info("Lock <REL> thread : "+fileselector);
			}
		}
		finally{
			if(!normal)jlog.error("Lock < *** FAILRELEASE *** > thread : "+fileselector);
		}


	}

	/**
	 * Other than scheduled job date (eg yesterday)
	 * @param cal
	 */
	public void setRequestDate(Calendar cal){
		this.cal = cal;
	}

	public void setTransformerType(TransformerType transtype){
		this.transtype = transtype;
	}

	public void setNetworkType(NetworkType networktype){
		this.networktype = networktype;
	}

	public String getNetworkType(){
		return this.networktype.toString();
	}

	/*
	 * File paths set by referringto OS type. Kinda flakey.
	 */
	
	public static String chooseTempPath(){
		if("Win".compareTo(System.getProperty("os.name").substring(0,3))==0)
			return WIN_TMP_PATH;
		if("Sun".compareTo(System.getProperty("os.name").substring(0,3))==0)
			return SUN_TMP_PATH;
		return LNX_TMP_PATH;
	}

	public static String chooseSourcePath(){
		if("Win".compareTo(System.getProperty("os.name").substring(0,3))==0)
			return WIN_SRC_PATH;
		if("Sun".compareTo(System.getProperty("os.name").substring(0,3))==0)
			return SUN_SRC_PATH;
		return LNX_SRC_PATH;
	}

	public static String chooseCACertsPath(){
		if("Win".compareTo(System.getProperty("os.name").substring(0,3))==0)
			return WIN_CAC_PATH;
		if("Sun".compareTo(System.getProperty("os.name").substring(0,3))==0)
			return SUN_CAC_PATH;
		return LNX_CAC_PATH;
	}

}
