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
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//import com.alcatel_lucent.nz.wnmsextract.database.DatabaseType;
import com.alcatel_lucent.nz.wnmsextract.document.DocumentType;
import com.alcatel_lucent.nz.wnmsextract.reader.Extractor;

/**
 * Executable Job defining the processDocType method
 * TODO Refine execution contexts for fine grained control of different jobs. Instead
 * of 'process all INode files' consider; extract INode files, process MDR NodeB files etc
 */

public class ExecutableJob extends Extractor implements Job {

		private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.ExecutableJob");
		public ExecutableJob(){}

		/**
		 * Primary execution method. Runs processDocType for individual control over by type
		 * procesing of WNMS/WiPS files
		 */
		@Override
		public void execute(JobExecutionContext jec) throws JobExecutionException {
				JobDataMap jdm = jec.getJobDetail().getJobDataMap();


					jlog.info("Executing Job : "+jec);
					jlog.info("********* EXECUTABLE JOB DT : "+jdm.get("doctype"));
					processDocType(
							//(DatabaseType)jdm.get("dbname"),
							(DocumentType)jdm.get("doctype"),
							true);

							//,(Lock)jec.getScheduler().getContext().get("lock"));

				//jlog.info("Executing Job : "+jec);
				//processDocType((String)jdm.get("datapath"),(DatabaseType)jdm.get("dbname"),(DocumentType)jdm.get("doctype"));

		}

}
