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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * The Borg parser. Placeholder class to parse Borg scrape data 
 * which becuase its no more complicated than CSV makes this class 
 * redundant 
 * @author jnramsay
 */
public class BORGParser implements ALUParser {

	//private static final String EXTERNAL_DTD_LOADING_FEATURE="http://apache.org/xml/features/nonvalidating/load-external-dtd";

	//parse and transform and result file
	File pfile,tfile,rfile;
	InputStream tis;

	Document document;
	//XMLStreamReader stream;

	public BORGParser(){

	}

	@Override
	public void setParseFile(String pfs){
		setParseFile(new File(pfs));
	}
	@Override
	public void setParseFile(File pfile){
		this.pfile = pfile;
	}

	@Override
	public void setTransformFile(String tfs){
		setTransformFile(new File(tfs));
	}
	@Override
	public void setTransformFile(File tfile){
		try {
			this.tis = new FileInputStream(tfile);
		} catch (FileNotFoundException fnfe) {
			System.err.println("Can't find transform file :: "+fnfe);
		}
	}
	@Override
	public void setTransformFile(InputStream tis){
		this.tis = tis;
	}

	@Override
	public void setResultFile(String rfs){
		setResultFile(new File(rfs));
	}
	@Override
	public void setResultFile(File tfile){
		this.rfile = tfile;
	}

	@Override
	public void transform(OutputStream os){
		/*
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setAttribute("http://xml.org/sax/features/namespaces", true);
		dbf.setAttribute("http://xml.org/sax/features/validation", false);
		dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(false);
		dbf.setIgnoringComments(false);
		dbf.setValidating(false);
		 */

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(false);


		TransformerFactory tf = TransformerFactory.newInstance();
		/*
		tf.setURIResolver(new URIResolver(){
			@Override
			public Source resolve(String publicId, String systemId) {
				if (systemId.equals("32.401-02.dtd")) {
					return new StreamSource(new StringReader(""));
				} 
				else {
					return null;
				}
			}});

		 */

		try {

			XMLReader reader = spf.newSAXParser().getXMLReader();
			//reader.setFeature(EXTERNAL_DTD_LOADING_FEATURE, false);
			reader.setEntityResolver(new EntityResolver()	{
				@Override
				public InputSource resolveEntity(String publicId, String systemId) {
					if (systemId.endsWith("32.401-02.dtd")) {
						return new InputSource(new StringReader(""));
					} 
					else {
						return null;
					}
				}});

			SAXSource source = new SAXSource(reader, new InputSource(new FileReader(pfile)));
			StreamResult result = new StreamResult(os);

			
			Source xslt = new StreamSource(tis);

			Transformer t = tf.newTransformer(xslt);
			//System.out.println("SR::"+source);
			t.transform(source, result);

		}
		catch(TransformerConfigurationException tce){
			System.err.println("Trouble reading/compiling XML "+pfile+" :: "+tce);
			tce.printStackTrace();
		}
		catch(TransformerException te){
			te.printStackTrace();
		} 

		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 



	}

}
