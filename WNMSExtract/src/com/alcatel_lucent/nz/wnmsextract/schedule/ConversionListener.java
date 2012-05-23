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
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;


/**
 * Custom job listener class.
 * TODO Reports status and attempts restart of misfires but could feed an external
 * job rescheduler that checks for known errors. The builtin method doesnt always
 * restart jobs reliably
 * @author jnramsay
 *
 */
public class ConversionListener implements JobListener {
	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.schedule.ConversionListener");
	private String name;

	public ConversionListener(String name){
		setName(name);
	}

	private void setName(String name) {
		this.name = name;

	}
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext jec) {
		System.out.println("L1"+name);

	}

	@Override
	public void jobToBeExecuted(JobExecutionContext jec) {
		System.out.println("L2"+name);

	}

	/**
	 * Job completion method useful for catching misfires
	 */
	@Override
	public void jobWasExecuted(JobExecutionContext jec, JobExecutionException jee) {
		if (jee != null) {
			jlog.error(name+" reports Job Misfire for job scheduled at =" +jec.getScheduledFireTime());
			JobDetail job = jec.getJobDetail();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 10);
			Trigger trig = new	SimpleTrigger(job.getName() + "-RETRY", job.getGroup(), cal.getTime());
			try {
				jec.getScheduler().scheduleJob(job, trig);
				jlog.trace(name+" rescheduling Job at " + cal.getTime());
			} catch (SchedulerException se) {
				se.printStackTrace();
			}
		}
		else {
			jlog.trace(name+" reports successful Job Execution");
		}

		System.out.println("L3"+name);

	}

	@Override
	public String toString(){
		return name;

	}




}
