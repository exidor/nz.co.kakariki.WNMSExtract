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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
import com.alcatel_lucent.nz.wnmsextract.document.NetworkType;

/*
 * "Date","Threshold","Lp 2 Ap 1","Lp 3 Ap 1","Lp 4 Ap 1","Lp 5 Ap 1","Lp 6 Ap 1","Lp 7 Ap 1","Lp 10 Ap 1","Lp 11 Ap 1","Lp 12 Ap 1","Lp 12 Ap 5","Lp 13 Ap 1","Lp 13 Ap 5"
 * "2010-08-08 11:47:01","70.0000","40.0000","37.0000","44.0000","41.0000","35.0000","38.0000","44.0000","40.0000","41.0000","1.0000","1.0000","38.0000"
 */

/**
 * GTA extension of fileselector. Differences from the abstract include
 * URL location, SSL auth and scrape vs unzip
 */
public class GTAHttpReader extends FileSelector {

	private static final String GTA_URL = "http://usilumts01.ndc.lucent.com/~umtslogs/umtsrnclogs/SiteData/9370/custData/guamwms1/";
	private static final String GTA_USR = "jnramsay";
	private static final String GTA_PWD = "<the password i usually reserve for root logins>";

	public static final DateFormat BORG_DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//public static final DatabaseType DEF_DBT = DatabaseType.GTA_NZRSDB;


	private static GTAHttpReader reader;

	public DatabaseType databasetype;

	/** Private constructor */
	private GTAHttpReader(){
		//this.databasetype = databasetype;

		System.setProperty("javax.net.ssl.trustStore", Extractor.chooseCACertsPath()+"cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        System.out.println("javax.net.ssl.trustStore = "+System.getProperty("javax.net.ssl.trustStore"));
	}


	/** Singleton getInstance method */
	public static synchronized GTAHttpReader getInstance()
	{
		if(reader == null){
			reader = new GTAHttpReader();
		}
		return reader;
	}

	public void setDatabasetype(DatabaseType databasetype){
		this.databasetype = databasetype;
	}

	/**
	 * extract method in this context does double duty downloading files and
	 * unzipping
	 */
	@Override
	public void extract() {
		DefaultHttpClient client = new DefaultHttpClient();
		setCredentials(client);
		//List<String> nodeblinks, inodelinks, rnccnlinks;
		List<String> links = readPage(GTA_URL,client);
		for (String link : links){
			String pref = link.substring(0, 5);
			if ("NodeB".compareTo(pref)==0
					|| "INode".compareTo(pref)==0
					|| "RNCCN".compareTo(pref)==0){
		    	for (String filelink : readPage(GTA_URL+link,client)){
		    		System.out.println("A"+calendarToString(this.calendar)+"//"+filelink.substring(0, Math.min(filelink.length(),9)));
		    		if(("A"+calendarToString(this.calendar)).compareTo(filelink.substring(0, Math.min(filelink.length(),9)))==0)
		    			fetchLink(GTA_URL+link+filelink,client);
		    	}
			}
		}
		client.getConnectionManager().shutdown();
	}
	/**
	 * Use the hardcoded URL/usr/pass to set up auth on httpclient
	 * @param client http client class used throughout this connection instance
	 */
	private void setCredentials(DefaultHttpClient client){
		try {
			client.getCredentialsProvider().setCredentials(
					new AuthScope((new URL(GTA_URL)).getAuthority(), 80),
			        new UsernamePasswordCredentials(GTA_USR, GTA_PWD)
					);
		}
        catch (MalformedURLException murle) {
			System.err.println("Something wrong with the suplied URL"+murle);
		}
	}

	private List<String> readPage(String link, DefaultHttpClient client){
		List<String> links = new ArrayList<String>();
		HttpGet httpget = new HttpGet(link);
		HttpResponse response;
		HttpEntity entity;
		try {
			response = client.execute(httpget);
			entity = response.getEntity();

	        if (entity != null) {
	        	BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));

	        	try {
	        	  Parser parser = new Parser();
	        	  String line, para="";
	        	  while ( (line=in.readLine()) != null ) {
	        		  para += line;
	        	  }
	        	  parser.setInputHTML(para);

	        	  NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter (LinkTag.class));
	        	  for (int i = 0; i < list.size (); i++){
	        	    LinkTag extracted = (LinkTag)list.elementAt(i);
	        	    String extractedlink = extracted.getLink();
	        	    links.add(extractedlink);
	        	  }

	        	}
	        	catch (ParserException pe) {
	        	  pe.printStackTrace();
	        	}

		        in.close();
	        }

		} catch (ClientProtocolException cpe) {
			System.err.println("readPage. Connection Error :: "+cpe);
		} catch (IOException ioe) {
			System.err.println("readPage. IO Error :: "+ioe);
		} catch (IllegalStateException ise) {
			System.err.println("readPage. Connection not/improperly terminated :: "+ise);
		} catch (Exception e) {
			System.err.println("readPage. some other error, caught here for analysis :: "+e);
		}
		return links;

	}


	/**
	 * Scrape URL into file. Sets up correct path beforehand.
	 * @param link Location of the desired file
	 * @param client
	 */
	private void fetchLink(String link, DefaultHttpClient client){
		HttpGet httpget = new HttpGet(link);
		HttpResponse response;
		HttpEntity entity;
		try {
			response = client.execute(httpget);
			entity = response.getEntity();

	        if (entity != null) {
	        	String fn = link.substring(link.lastIndexOf("/")+1, link.length());
	        	String fp1 = Extractor.chooseTempPath()+NetworkType.GTA.toString();
	        	if(!(new File(fp1)).exists()){
	        		(new File(fp1)).mkdir();
	        	}
	        	String fp2 = fp1+File.separator+calendarToString(this.calendar);
	        	if(!(new File(fp2)).exists()){
	        		(new File(fp2)).mkdir();
	        	}

	        	File f = new File(fp2+File.separator+fn);
	        	FileOutputStream out = new FileOutputStream(f);

	        	streamCopy(entity.getContent(),out);
	        	out.close();
	        	ungzip(f);
	        }

	        //client.getConnectionManager().shutdown();
		} catch (ClientProtocolException cpe) {
			System.err.println("fetchLink. Connection Error :: "+cpe);
		} catch (IOException ioe) {
			System.err.println("fetchLink. IO Error :: "+ioe);
		} catch (IllegalStateException ise) {
			System.err.println("fetchLink. Connection not/improperly terminated :: "+ise);
		} catch (Exception e) {
			System.err.println("fetchLink. some other error, caught here for analysis :: "+e);
		}


	}

	/** 
	 * Simple stream copy
	 */
	public void streamCopy(InputStream src, OutputStream dst) throws IOException {
		byte[] buf = new byte[1024];
		int len;
		while ((len = src.read(buf)) > 0) {
			dst.write(buf, 0, len);
		}
	}
	
	/*
	public void logRawTableChanges (){
		ALUDBUtilities.log(databasetype, TABLE, "INSERT");
	}
	 */
	public void writeAll(){}
	/*
	private String idConvert(String rnc, String ap, String hd){
		//"Lp nn Ap mm"
		String[] token = hd.split(" ");
		return "AP_"+rnc+"/"+rnc+"IN0/"+token[1]+"/"+token[3];

	}
	 */
	/*
	@Override
	public List<File> getFileList(DocumentType documenttype) {
		// TODO Auto-generated method stub
		return null;
	}
	 */
	
	/**
	 * A method for identifying similarly named files based on the relative location. Used for
	 * INodeVcc files which have the same name structure and INode files
	 */
	@Override
	public String docPath(){
		return "";
	}

}
