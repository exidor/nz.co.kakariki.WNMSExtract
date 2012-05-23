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
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.alcatel_lucent.nz.wnmsextract.WNMSTransform;
import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;

@Deprecated
public class ExtractorTNZCPV extends Extractor {

	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.ExtractorTNZCPV");

	public ExtractorTNZCPV(){

	}

	/** process docs*/
	public void processDocType(DatabaseType dbname, DocumentType doctype, boolean atflag){

		jlog.info("Extraction Job, "+dbname+", "+doctype);

		WNMSTransform t = new WNMSTransform(dbname);//, LogAppType.File);
		t.setAggregateTables(atflag);
		t.setDocumentType(doctype);
		t.setTransformType(transtype);


		for(File file : processDocuments(doctype))
		{
			//String fname = file.getPath()+file.getName();
			jlog.info("Processing WNMS/WiPS File "+file.getName());
			//if (t.process(file))
			//	deleteDocuments(file);
			t.process(file);
		}

		t.logRawTableChanges();
		t = null;
		//System.out.println(error.get(1));//should throw NPE

		return;
	}

	private List<File> processDocuments(DocumentType doctype){
		FileSelector fs = TNZArchiveReader.getInstance();

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
				fs.setSourcePath(new File(Extractor.chooseSourcePath()));
				fs.setTempPath(new File(Extractor.chooseTempPath()));
				fs.extract();
				fs.setDocType(doctype);
				lf = fs.getFileList();
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

	/*
	private void deleteDocuments(File file){
		FileUtilities fu = FileUtilities.getInstance();
		synchronized(fu){
  		jlog.info("Locking FileUtilities Delete : "+fu);
  		fu.deleteFile(file);
		}
	}
	 */

	@Override
	public String toString(){return "CPV Extractor ";}//+cal;}

}
