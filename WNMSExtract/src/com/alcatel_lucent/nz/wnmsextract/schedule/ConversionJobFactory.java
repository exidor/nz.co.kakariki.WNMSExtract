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

import org.quartz.JobDetail;

import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;

/**
 * Factory that sets up named in and out transfer points, namely an input file and
 * an output database defintion
 */
public class ConversionJobFactory extends JobDetail {


	private static final long serialVersionUID = 20091113154401L;

	/**
	 * enum defining transfer points to and from with contained properties file prefixes
	 * Where tranfer points are of the same type distinguish between them with
	 * S(source) and D(dest) suffixes
	 */

	private String projectid;
	private DocumentType doctype;
	private DatabaseType dbname;

	ConfigProperties cprops;

	/**
	 * Factory built on doctype and db from a passed in ConfigProps
	 */
	public ConversionJobFactory(ConfigProperties cprops){
		this.cprops = cprops;
		setProjectId(cprops.getProperty("PROJ.id"));

		setDBName(DatabaseType.valueOf(cprops.getProperty("DATA.db")));

		setDocType(DocumentType.valueOf(cprops.getProperty("FILE.type")));

	}

	/**
	 * getInstance. Return a configured ConversionJob instance
	 * TODO Consider moving unique functionality to instance method rather
	 * than Factory constructor
	 */
	public ConversionJob getConversionJobInstance(){
		return new ConversionJob(projectid,dbname,doctype);
	}

	public void setProjectId(String projectid) {
		this.projectid = projectid;
	}

	public String getProjectId() {
		return projectid;
	}

	public DocumentType getDoctype() {
		return doctype;
	}

	public void setDocType(DocumentType doctype) {
		this.doctype = doctype;
	}

	public DatabaseType getDbname() {
		return dbname;
	}

	public void setDBName(DatabaseType dbname) {
		this.dbname = dbname;
	}



}
