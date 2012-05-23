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
import com.alcatel_lucent.nz.wnmsextract.database.inode.ALURncEquipmentTable;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inode.INodeDocument;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wnms.inode.RootINodeType;

/**
 * Document representing the WNMS INode file. Acts as the root for INode
 * the parser hierarchy 
 * @author jnramsay
 *
 */
public class WNMSINodeDocument implements ALUDocument {

	private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.document.WIPSINodeDocument");

	public static final String NETWORK_ELEMENT = "inode";

	public static final SimpleDateFormat DEF_DF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public String getTransformer(TransformerType trans) {
		return NETWORK_ELEMENT+"."+trans.getSuffix();
	}

	public ALUJDCConnector ajc;

	public WNMSINodeDocument() {

	}

	public INodeDocument read(InputStream is) {
		INodeDocument doc = null;
		ArrayList<XmlError> erl = new ArrayList<XmlError>();

		try {
			XmlOptions xo = new XmlOptions();
			xo.setLoadLineNumbers();
			xo.setLoadUseXMLReader(SAXParserFactory.newInstance().newSAXParser().getXMLReader());
			xo.setErrorListener(erl);

			doc = INodeDocument.Factory.parse(is, xo);
			jlog.debug(doc.xmlText().substring(0, 64));
		} catch (SAXException se) {
			System.err.println("SAX Error parsing INode InputStream :: " + se);
		} catch (ParserConfigurationException pce) {
			System.err
					.println("Problem with the INode Parser Config :: " + pce);
		} catch (XmlException xe) {
			System.err.println("Error parsing INode XML InputStream :: " + xe);
			for (XmlError error : erl) {
				System.err.println("Message: " + error.getMessage());
				System.err.println("LineNumber: " + error.getCursorLocation().xmlText());
			}
			System.exit(1);
		} catch (IOException ioe) {
			System.err.println("IO Error reading INode XML InputStream :: "	+ ioe);
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
		INodeDocument doc = read(is);
		// static calls to table insert classes
		RootINodeType inode = doc.getINode();
		// db.openConnection();
		ALURncEquipmentTable.insertData(ajc, inode);
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
