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

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;

/**
 * An extension of the Quartz JobDetail class that sets doctype and dbname
 * parameters in the wrapped JobDetailMap
 */
public class ConversionJob extends JobDetail {

	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.ConversionJob");
	/**
	 * transferjob TJ  has a jobdatamap JDM which stores job relavent links data etc. calling the
	 * superclass jobdetail with whose 3rd arg is ExecutableJob EJ which flags EJ as the class whose
	 * execute muthod is run at trigger time. EJ takes its parameters from the JDM set up here in TJ
	 */
	private static final long serialVersionUID = 20091113153801L;
		JobDataMap jdm;

    public ConversionJob(String name, DatabaseType dbname, DocumentType doctype){
				super(name,null,ExecutableJob.class);
				this.jdm = getJobDataMap();

				jdm.put("doctype",doctype);
				jdm.put("dbname",dbname);

				jlog.info("ConversionJob '"+name+"' Setup db="+dbname+" conv="+doctype);
		}

}
