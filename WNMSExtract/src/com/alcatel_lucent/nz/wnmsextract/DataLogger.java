package com.alcatel_lucent.nz.wnmsextract;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EnumSet;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.net.SocketAppender;
/**
 * Logging output setup. Select Console, Socket or File. Socket assumes your running something like Chainsaw
 * and have the wherewithall to alter this source to modify the ouput IP to you own address
 * @author jnramsay
 * NB. Chainsaw can cause trouble with extended log feeds locking itself and the application up
 */

public class DataLogger {

	private Logger slog;
	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.DataLogger");

	//maximum for a long is 2^63-1 = 0x7FFFFFFFFFFFFFFF

	public static DateFormat LFDF = new SimpleDateFormat("yyyyMMddHHmmss");

	/** Server IP where the log reader is running */
	public static final String LOG_SERVER = "139.188.126.33";
	public static final String LOG_LOCATION = "log/";
	public static final String LOG_FILENAME = ".wnmsxtr.log";
	public static final String LOG_SIZE = "100MB";
	public static final int LOG_PORT = 4560;
	public static final Level LOG_LEVEL = Level.DEBUG;

	public enum LogAppType {File,Console,Socket};
	public static final String APP_F_NAME = "WNMSExtract.Appender.File";
	public static final String APP_C_NAME = "WNMSExtract.Appender.Console";
	public static final String APP_S_NAME = "WNMSExtract.Appender.Socket";

	private EnumSet<LogAppType> eslat;

	/**
	 * Constructor setting log level and type
	 * @param eslat Initial Log output types
	 * @param slog Log level
	 */
	public DataLogger(EnumSet<LogAppType> eslat,Logger slog){
		this.slog = slog;//slog.setLevel(LOG_LEVEL);
		this.eslat = eslat;
	}

	/**
	 * Adds operational log appenders. File, Socket or Console
	 * @param lat
	 */
	public void addLoggingAppender(LogAppType lat){

		try {
			switch(lat){
			case File:
				if(!eslat.contains(LogAppType.File)){ //slog.getAppender(APP_F_NAME)==null) {
					String lfn = LOG_LOCATION+LFDF.format(Calendar.getInstance().getTime())+LOG_FILENAME;
					RollingFileAppender fa = new RollingFileAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN),lfn);
					fa.setMaxFileSize(LOG_SIZE);
					fa.setName(APP_F_NAME);
					slog.addAppender(fa);
				}
				break;

			case Console:
				if(!eslat.contains(LogAppType.Console)){ //slog.getAppender(APP_C_NAME)==null) {
					ConsoleAppender ca = new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN),"System.out");
					ca.setName(APP_C_NAME);
					slog.addAppender(ca);
				}
				break;

			case Socket:
				if(!eslat.contains(LogAppType.Socket)){ //slog.getAppender(APP_S_NAME)==null) {
					SocketAppender sa = new SocketAppender(LOG_SERVER,LOG_PORT);
					sa.setName(APP_S_NAME);
					slog.addAppender(sa);
				}
				break;


			}
			//slog.setLevel(LOG_LEVEL);
		}
		catch(IOException ioe){
			System.err.println("Can't instantiate new logger "+ioe);
		}
		jlog.log(Level.INFO, "Initialising Logger WNMS");
		this.eslat.add(lat);

	}
}
