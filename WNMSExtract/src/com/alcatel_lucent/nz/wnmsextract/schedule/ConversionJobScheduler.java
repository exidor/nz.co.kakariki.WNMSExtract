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

import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Basic quartz scheduler class takes preconfigured jobdetail and trigger instances
 * to build an active job list. Looks after running state of scheduler too though still
 * need to code a scheduler stop method
 */
public class ConversionJobScheduler {

	  private static Logger jlog = Logger.getLogger("com.alcatel-lucent.nz.wnmsextract.ConversionJobScheduler");

		SchedulerFactory schedulerfactory;
		Scheduler scheduler;

		/**
		 * Null constructor initialises scheduler
		 */
		public ConversionJobScheduler(){

				try {
						schedulerfactory = new StdSchedulerFactory();
						scheduler = schedulerfactory.getScheduler();
						scheduler.start();
						jlog.info("Starting Scheduler "+scheduler);
						System.out.println("Starting Scheduler "+scheduler);
				}
				catch(SchedulerException se){
						System.err.println("TJS Scheduler Exception : "+se);
				}
		}
		/**
		 * Queue job+trigger into the scheduler
		 */
		public void scheduleJob(JobDetail tjob, Trigger trig){
				try {
						scheduler.scheduleJob(tjob,trig);
						//System.out.println(scheduler.getJobNames(tjob.toString()));
						jlog.info("Scheduling Job "+tjob.getFullName()+"/"+scheduler.getCurrentlyExecutingJobs());
						//System.out.println("Scheduling Job "+tjob.getFullName()+"/"+scheduler.getCurrentlyExecutingJobs());
				}
				catch (SchedulerException se){
						System.err.println("Cannot Schedule Event : "+se);
				}
		}
		/**
		 * Removes a job from the scheduler
		 */
		public void cancelJob(JobDetail tjob){
				try {
						scheduler.deleteJob(tjob.getName(),Scheduler.DEFAULT_GROUP);
						jlog.info("DeScheduling Job "+tjob+"/"+scheduler.getCurrentlyExecutingJobs());
				}
				catch (SchedulerException se){
						System.err.println("Cannot Cancel Event : "+se);
				}
		}
		/**
		 * Add a listener to the scheduler. Assumes a properly configured listener
		 */
		public void attachListener(JobListener lstnr){
			try {
				scheduler.addJobListener(lstnr);
			} catch (SchedulerException se) {
				System.err.println("Cannot Attach Listener to Scheduler : "+se);
			}
		}

		/**
		 * Report on status of scheduler and its jobs+triggers
		 * @return Descriptive string
		 * @throws SchedulerException
		 */
		public String getSchedulerStatus() throws SchedulerException{
			String status = "Groups[";
			for(String jg : scheduler.getJobGroupNames()){
				status += jg+" Jobs[";
				for(String j : scheduler.getJobNames(jg)){
					//scheduler.getTriggersOfJob(j,jg).toString();
					status += j+"-Trigs(";
					for(Trigger t : scheduler.getTriggersOfJob(j,jg)){
						status += t.getNextFireTime();
					}
					status += ")-Lstnr("+scheduler.getJobListener(j)+"),";

				}
				status += "],";
			}

			return status+"]";

		}

		/**
		 * Used to pass common lock if a shared singleton class is not wanted
		 * @param lock
		 */
		public void setLock(Lock lock){
			try {
				scheduler.getContext().put("lock", lock);
			} catch (SchedulerException se) {
				se.printStackTrace();
			}
		}


}