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

import com.alcatel_lucent.nz.wnmsextract.database.ALUDBUtilities;
import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;
import com.alcatel_lucent.nz.wnmsextract.database.wips.vcc.SNAPRncVccTable;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.vcc.MapRncVccDocument;
import com.alcatel_lucent.nz.wnmsextract.xmlbeans.wips.vcc.RncVccType;
/**
 * Document representing the WiPS converted extracting VCC ids 
 * @author jnramsay
 *
 */
public class WIPSMapRncVccDocument implements ALUDocument {

	private static Logger jlog = Logger
			.getLogger("com.alcatel-lucent.nz.wnmsextract.document.WIPSMapRncVccDocument");

	public static final String STATIC_TRANSFORMER = "maprncvcc.xsl";

	public static final SimpleDateFormat DEF_DF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public String getTransformer(TransformerType trans) {
		return STATIC_TRANSFORMER;
	}

	// public ALUDatabase db;
	public ALUJDCConnector ajc;

	@Override
	public void clean(DocumentType dt){
		for (String table : dt.getTList()){
			ALUDBUtilities.delete(ajc, table);
		}

	}

	@Override
	public void process(InputStream is) {
		MapRncVccDocument doc = read(is);
		// static calls to table insert classes
		RncVccType rncvcc = doc.getMapRncVcc();
		// db.openConnection();
		SNAPRncVccTable.insertData(ajc, rncvcc);
		// db.closeConnection();
	}

	public MapRncVccDocument read(InputStream is) {
		MapRncVccDocument doc = null;
		ArrayList<XmlError> erl = new ArrayList<XmlError>();

		try {
			XmlOptions xo = new XmlOptions();
			xo.setLoadLineNumbers();
			xo.setLoadUseXMLReader(SAXParserFactory.newInstance()
					.newSAXParser().getXMLReader());
			xo.setErrorListener(erl);

			doc = MapRncVccDocument.Factory.parse(is, xo);
			jlog.debug(doc.xmlText().substring(0, 64));
		}
		catch (SAXException se) {
			System.err.println("SAX Error parsing RncVcc InputStream :: " + se);
		}
		catch (ParserConfigurationException pce) {
			System.err.println("Problem with the RncVcc Parser Config :: "
					+ pce);
		}
		catch (XmlException xe) {
			System.err.println("Error parsing RncVcc XML InputStream :: " + xe);
			for (XmlError error : erl) {
				System.err.println("Message: " + error.getMessage());
				System.err.println("LineNumber: "
						+ error.getCursorLocation().xmlText());
			}
			System.exit(1);
		}
		catch (IOException ioe) {
			System.err.println("IO Error reading RncVcc XML InputStream :: "
					+ ioe);
		}
		return doc;

	}

	@Override
	public void setConnector(ALUJDCConnector ajc) {
		this.ajc = ajc;
	}
}
