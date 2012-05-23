package com.alcatel_lucent.nz.wnmstest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.xml.transform.TransformerException;

import com.alcatel_lucent.nz.wnmsextract.parser.ALUParser;
import com.alcatel_lucent.nz.wnmsextract.parser.ALUParserFactory;

public class TestStandaloneXSLParse {

	public static final String PATH = "g:/data/test/";
	public static final String XML_FILE = "rnccn.xml";
	public static final String XSL_FILE1 = "rnccn.dynamic.xsl";
	public static final String RES_FILE1 = "rnccn.dynamicparse.xml";

	public static final String XSL_FILE2 = "rnccn.static.xsl";
	public static final String RES_FILE2 = "rnccn.staticparse.xml";

	private void parse(String xsl, String res){
		ALUParser p = new ALUParserFactory("WNMS").getInstance();
		p.setParseFile(PATH+XML_FILE);
		p.setTransformFile(PATH+xsl);
		//p.setResultFile(PATH+RES_FILE);


		OutputStream out;
		try {
			out = new FileOutputStream(PATH+res);
			p.transform(out);
			out.close();
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		catch (TransformerException te) {
			te.printStackTrace();
		}
	}

	public static void main(String[] args){

		TestStandaloneXSLParse t = new TestStandaloneXSLParse();

		System.out.println("start");

		System.out.println((new Date()).toString());
		//t.parse(XSL_FILE1,RES_FILE1);
		System.out.println((new Date()).toString());//265s
		t.parse(XSL_FILE2,RES_FILE2);
		System.out.println((new Date()).toString());//47s

		System.out.println("end");


		//comparison for 7 RNC with 24 files per RNC
		//12.3hr dynamic 2.3hr static!

	}
}
