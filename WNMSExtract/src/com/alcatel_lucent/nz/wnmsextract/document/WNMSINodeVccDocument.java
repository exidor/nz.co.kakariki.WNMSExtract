package com.alcatel_lucent.nz.wnmsextract.document;

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
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.xml.sax.SAXException;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.database.inodevcc.ALURncEquipmentTable;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inodevcc.INodeVccDocument;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inodevcc.RootINodeVccType;
/**
 * Document representing the WNMS INodeVcc file. Acts as the root for INodeVcc
 * the parser hierarchy 
 * @author jnramsay
 *
 */
public class WNMSINodeVccDocument implements ALUDocument {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.document.WIPSINodeVccDocument");

	public static final String NETWORK_ELEMENT = "inodevcc";

	public static final SimpleDateFormat DEF_DF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public String getTransformer(TransformerType trans) {
		return NETWORK_ELEMENT+"."+trans.getSuffix();
	}

	public ALUJDCConnector ajc;

	public WNMSINodeVccDocument() {

	}

	public INodeVccDocument read(InputStream is) {
		INodeVccDocument doc = null;
		ArrayList<XmlError> erl = new ArrayList<XmlError>();

		try {
			XmlOptions xo = new XmlOptions();
			xo.setLoadLineNumbers();
			xo.setLoadUseXMLReader(SAXParserFactory.newInstance().newSAXParser().getXMLReader());
			xo.setErrorListener(erl);

			doc = INodeVccDocument.Factory.parse(is, xo);
			jlog.debug(doc.xmlText().substring(0, 64));
		} catch (SAXException se) {
			System.err.println("SAX Error parsing INodeVcc InputStream :: " + se);
		} catch (ParserConfigurationException pce) {
			System.err
					.println("Problem with the INodeVcc Parser Config :: " + pce);
		} catch (XmlException xe) {
			System.err.println("Error parsing INodeVcc XML InputStream :: " + xe);
			for (XmlError error : erl) {
				System.err.println("Message: " + error.getMessage());
				System.err.println("LineNumber: " + error.getCursorLocation().xmlText());
			}
			System.exit(1);
		} catch (IOException ioe) {
			System.err.println("IO Error reading INodeVcc XML InputStream :: "	+ ioe);
		}
		return doc;

	}

	public void executeQuery(String nodebquery) {
		System.out.println(nodebquery);
	}

	@Override
	public void clean(DocumentType dt) {
		// don't clean
	}

	@Override
	public void process(InputStream is) {
		INodeVccDocument doc = read(is);
		// static calls to table insert classes
		RootINodeVccType inodevcc = doc.getINodeVcc();
		// db.openConnection();
		ALURncEquipmentTable.insertData(ajc, inodevcc);
		// db.closeConnection();
	}

	/*
	 * private Calendar parseCal(String tstr){ Calendar cal =
	 * Calendar.getInstance(); Date d1 = null; try { d1 = DEF_DF.parse(tstr); }
	 * catch (ParseException pe) {
	 * System.err.println("Couldn't parse timestamp string, using null :: "+pe);
	 * } cal.setTime(d1); return cal; }
	 */
	/*
	 * @Override public void setDatabase(ALUDatabase db) { this.db = db; }
	 */

	@Override
	public void setConnector(ALUJDCConnector ajc) {
		this.ajc = ajc;
	}

}
