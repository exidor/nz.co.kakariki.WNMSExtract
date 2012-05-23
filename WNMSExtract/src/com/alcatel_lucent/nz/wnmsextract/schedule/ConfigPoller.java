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

import java.io.File;
import java.util.ArrayList;

/**
 * ConfigPoller watches a the conf directory for new additions or deletions and
 * updates the active properties list.
 */
public class ConfigPoller {

	private File cdir;
	private ArrayList<ConfigProperties> cprops;

	public ConfigPoller(String sdir, ArrayList<ConfigProperties> cprops) {
		this(new File(sdir), cprops);
	}

	public ConfigPoller(File cdir, ArrayList<ConfigProperties> cprops) {
		this.cdir = cdir;
		this.cprops = cprops;
	}

	/**
	 * Given the name of a file decide whether its a proper properties
	 * file and if so load it
	 */
	public int inspect() {
		int cf = 0;
		String[] flist = cdir.list();
		// scoping trouble?
		ArrayList<ConfigProperties> review = new ArrayList<ConfigProperties>();
		for (int i = 0; i < flist.length; i++) {
			String pfn = cdir.getAbsolutePath() + "/" + flist[i];
			if (ConfigProperties.validatePropFileName(pfn)) {
				ConfigProperties cp = new ConfigProperties(pfn);
				review.add(cp);
			}
		}
		// rely on collection compareto using element hashcodes
		cf = cprops.hashCode() - review.hashCode();
		if (cf != 0)
			cprops = review;
		return cf;
	}

	/**
	 * refresh. fetch new/old cprops
	 */
	public ArrayList<ConfigProperties> refresh() {
		return cprops;
	}

}