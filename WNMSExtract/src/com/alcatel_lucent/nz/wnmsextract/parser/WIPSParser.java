package com.alcatel_lucent.nz.wnmsextract.parser;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

/**
 * WiPS parser class. Simple implementation of XML parser wrapper since 
 * WiPS data is well formed and no hacks are required to bypass missing DTDs 
 * or recover from corruption errors. SAX parser due to size of WiPS snapshots.
 * @author jnramsay
 *
 */
public class WIPSParser implements ALUParser {

	// parse and transform and result file
	File pfile, tfile, rfile;
	InputStream tis;

	Document document;
	XMLStreamReader stream;

	public WIPSParser() {

	}

	@Override
	public void setParseFile(String pfs) {
		setParseFile(new File(pfs));
	}

	@Override
	public void setParseFile(File pfile) {
		this.pfile = pfile;
	}

	@Override
	public void setTransformFile(String tfs) {
		setTransformFile(new File(tfs));
	}

	@Override
	public void setTransformFile(File tfile) {
		try {
			this.tis = new FileInputStream(tfile);
		} catch (FileNotFoundException fnfe) {
			System.err.println("Can't find transform file :: " + fnfe);
		}
	}

	@Override
	public void setTransformFile(InputStream tis) {
		this.tis = tis;
	}

	@Override
	public void setResultFile(String rfs) {
		setResultFile(new File(rfs));
	}

	@Override
	public void setResultFile(File tfile) {
		this.rfile = tfile;
	}

	@Override
	public void transform(OutputStream os) {

		TransformerFactory tf = TransformerFactory.newInstance();

		try {

			Source source = new StreamSource(pfile);
			StreamResult result = new StreamResult(os);

			Source xslt = new StreamSource(tis);

			// Result r = new StreamResult(new
			// Some-kind-of-OutputStream());return r;

			// tf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd",
			// false);
			Transformer t = tf.newTransformer(xslt);
			t.transform(source, result);

		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}

	}

}
