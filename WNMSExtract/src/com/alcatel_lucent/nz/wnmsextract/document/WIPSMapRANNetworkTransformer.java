package com.alcatel_lucent.nz.wnmsextract.document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.alcatel_lucent.nz.wnmsextract.parser.WIPSParser;
/**
 * Document representing the WiPS converted doc that builds a geographical 
 * mapping table for RNCs in "graphml". Since locations are not included in 
 * WiPS they have to be hard coded into the XSL. The ones there at present 
 * are wrong   
 * @author jnramsay
 *
 */
public class WIPSMapRANNetworkTransformer {

	public static final String STATIC_TRANSFORMER = "conf/linkrncnb.xsl";

	public static void transform(File source, File target) {
		WIPSParser wps = new WIPSParser();
		wps.setParseFile(source);
		wps.setTransformFile(new File(STATIC_TRANSFORMER));
		wps.setResultFile(target);

		try {
			wps.transform(new FileOutputStream(target));
		} catch (FileNotFoundException fnfe) {
			System.err.println("Cannot write to specified file, "+target+" :: "+fnfe);
		}
	}

}
