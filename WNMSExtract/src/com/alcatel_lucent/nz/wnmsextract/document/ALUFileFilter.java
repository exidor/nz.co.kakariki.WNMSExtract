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
import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File filter implementation. Used when an archive has been unzipped and we
 * need to process a particular type of file. eg selects all inode files from
 * unzip directory
 * 
 * @author jnramsay
 * 
 */
public class ALUFileFilter implements FileFilter {

	public static DateFormat df = new SimpleDateFormat("yyyyMMdd");
	private Pattern pattern;
	private Calendar datelabel = null;

	public ALUFileFilter(String pstr) {
		this.pattern = Pattern.compile(pstr);
	}

	/**
	 * Set the acceptable date for the file
	 * 
	 * @param cal
	 */
	public void setAcceptDate(Calendar cal) {
		this.datelabel = cal;
	}

	/**
	 * Set the acceptable date for the file
	 * 
	 * @param calstr
	 */
	public void setAcceptDate(String calstr) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(df.parse(calstr));
			setAcceptDate(cal);
		} catch (ParseException pe) {
			System.err.println("Can't parse supplied date string " + pe);
		}

	}

	/**
	 * File acceptance based on the date string appearing in the filename. NB.
	 * used in FileSelector class
	 */
	@Override
	public boolean accept(File file) {
		String filename = file.getName();
		Matcher matcher = pattern.matcher(filename);

		return (datelabel == null || matcher.groupCount() == 0) ? matcher.matches() : matcher.matches() && df.format(datelabel.getTime()).compareTo(matcher.group(1)) == 0;
	}

}
