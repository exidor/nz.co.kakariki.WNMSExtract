package com.alcatel_lucent.nz.wnmsextract.schedule;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Given a filemane (actually just its prefix) ConfigProperties opens and
 * load the validated/appropriate props file
 */
public class ConfigProperties implements Comparable<ConfigProperties> {

	public enum Prefix {
		PROJ, TIME, FILE, DB, FTP, HTTP
	};

	public Properties cprops;
	String pfn;

	/** Constructor that initialises from filename */
	public ConfigProperties(String pfn) {
		if (validatePropFileName(pfn)) {
			this.pfn = pfn;
			this.cprops = loadPropFile(pfn);
		} else
			System.err.println("Invalid Filename " + pfn);
	}

	/**
	 * Loads validated name propfile
	 */
	private Properties loadPropFile(String pfn) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(pfn));
		} catch (FileNotFoundException fnfe) {
			System.err.println("Can't find properties " + pfn + " : " + fnfe);
		} catch (IOException ioe) {
			System.err.println("Can't read properties " + pfn + " : " + ioe);
		}
		return props;
	}

	/**
	 * To validate a file must conform to "x[n].properties" 012345678901234
	 * eg AL01.properties, BCI_Importer.properties
	 */
	public static boolean validatePropFileName(String pfn) {
		// System.out.println(pfn.substring(pfn.length()-11));
		if (".properties".compareTo(pfn.substring(pfn.length() - 11)) == 0 && ".".compareTo(pfn.substring(0, 1)) != 0)
			return true;
		else
			return false;
	}

	/** prefix */
	public String getName() {
		return pfn.substring(0, 4);// TODO
	}

	/**
	 * Wrapper for contained property file
	 */
	public String getProperty(String s) {
		// System.out.print("Reading property "+s);
		// System.out.println(" as "+cprops.getProperty(s));
		return cprops.getProperty(s);
	}

	/**
	 * Select a certain class (prefix) of properties
	 */
	public Properties getSubset(String s) {
		Properties subset = new Properties();
		int len = s.length();
		for (Object o : cprops.keySet()) {
			String scan = (String) o;
			if (len < scan.length() && s.compareTo(scan.substring(0, len)) == 0) {
				subset.setProperty(scan, cprops.getProperty(scan));
			}
		}
		return subset;
	}

	/**
	 * Override of hashCode defines equivalence if id and name are
	 * equal.
	 */
	@Override
	public int hashCode() {
		return this.getProperty("PROJ.id").hashCode() + 31 * this.getProperty("PROJ.name").hashCode();
		// +31*31*this.getProperty("PROJ.desc");
	}

	/**
	 * Equality defined as same object type and hash of id+name
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ConfigProperties))
			return false;
		return this.compareTo((ConfigProperties) obj) == 0 ? true : false;
	}

	/**
	 * Check id and name elements for equality. Description and
	 * implementation details can change? or not? NB. this=cp optimisation
	 * according to JavaPractices.com
	 */
	@Override
	public int compareTo(ConfigProperties cp) {
		if (this == cp)
			return 0;
		return this.hashCode() - cp.hashCode();
	}

	/**
	 * toString...
	 */
	@Override
	public String toString() {
		return pfn.substring(pfn.lastIndexOf("/") + 1, pfn.length() - 11) + ":" + getProperty("PROJ.name");
	}

}