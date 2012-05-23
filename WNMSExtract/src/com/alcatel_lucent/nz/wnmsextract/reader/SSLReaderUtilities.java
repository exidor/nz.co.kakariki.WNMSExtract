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
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
/**
 * Utility class that sets up a fake/empty security certificate so SSL clients don't get stressed 
 * when they dont have a matching certificate (because it hasn't been provided) and quit.  
 * @author jnramsay
 *
 */
public class SSLReaderUtilities {

	public static void bypassSSLAuthentication(){
		System.err.println("SSL bypass using fake X509 Trust Manager requested");
		try {
			SSLContext sslContext=SSLContext.getInstance("SSL");
			sslContext.init(null, new X509TrustManager[] {
					new X509TrustManager() {
						@SuppressWarnings("unused")
						public void checkClientTrusted(X509Certificate[] chain, String authType) {
						}
						@SuppressWarnings("unused")
						public void checkServerTrusted(X509Certificate[] chain, String authType) {
						}
						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						@Override
						public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
							// TODO Auto-generated method stub

						}
						@Override
						public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
							// TODO Auto-generated method stub

						}
					}}, null);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		} catch (Exception e) {

		}
	}
}
