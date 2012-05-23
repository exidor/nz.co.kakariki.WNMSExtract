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

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.alcatel_lucent.nz.wnmsextract.DataLogger.LogAppType;
import com.alcatel_lucent.nz.wnmsextract.schedule.ConfigPoller;
import com.alcatel_lucent.nz.wnmsextract.schedule.ConfigProperties;
import com.alcatel_lucent.nz.wnmsextract.schedule.ConversionJob;
import com.alcatel_lucent.nz.wnmsextract.schedule.ConversionJobFactory;
import com.alcatel_lucent.nz.wnmsextract.schedule.ConversionJobScheduler;
import com.alcatel_lucent.nz.wnmsextract.schedule.ConversionListener;

/**
 * Multi threaded application entry point for WNMS parsing. This Class (the othet MAIN class) has
 * fallen into disuse. It could be revived if the app is ever run on a server with a decent amount 
 * of RAM but at present any more than a single thread will throw OutOfMemor errors. :(
 * @author jnramsay
 * TODO FTP client for as a connector type
 * TODO Jar-in-Jar classloading (!) - Not practical
 * TODO Debug messed up parser type allocation
 */
public class WNMSDataScheduler {

	//private Lock lock;
	
	private static Logger jlog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract.WNMSDataExtractor");
	private static Logger slog = Logger.getLogger("com.alcatel_lucent.nz.wnmsextract");

	/*convenience constants useful for testing but can delete most in production*/
	@SuppressWarnings("unused")
	private static final long ONE_SECOND   =       1000L;
	private static final long FIVE_SECONDS =       5000L;
	@SuppressWarnings("unused")
	private static final long ONE_MINUTE   =      60000L;
	@SuppressWarnings("unused")
	private static final long FIVE_MINUTES =     300000L;
	@SuppressWarnings("unused")
	private static final long TEN_MINUTES  =     600000L;
	@SuppressWarnings("unused")
	private static final long ONE_HOUR     =    3600000L;
	private static final long ONE_DAY      =   86400000L;
	@SuppressWarnings("unused")
	private static final long ONE_WEEK     =  604800000L;
	@SuppressWarnings("unused")
	private static final long ONE_MONTH    = 2419200000L;
	
	
	private static final int START_DELAY = 10;

	//maximum for a long is 2^63-1 = 0x7FFFFFFFFFFFFFFF

	private static final String CONF_DIR = "conf";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String TIME_FORMAT = "HH:mm:ss";
	
	public DateFormat df,tf;
	private Map<String, ConversionJob> jobs;
	private Map<String, Trigger> trigs;
	private Map<String, ConversionListener> ears;
	private List<ConfigProperties> cprops;
	private ConversionJobScheduler jobscheduler;
	private ConfigPoller poller;

	private DataLogger wdl;

	/* result data */
	//private double[][] data;
	//private double power;

	/**
	 * Instantiate jobs and triggers
	 */
	public WNMSDataScheduler(){
		this.wdl = new DataLogger(EnumSet.noneOf(LogAppType.class),slog);
		this.df = new SimpleDateFormat(DATE_FORMAT);
		this.tf = new SimpleDateFormat(TIME_FORMAT);
		this.jobs = new HashMap<String,ConversionJob>();
		this.trigs = new HashMap<String,Trigger>();
		this.ears = new HashMap<String,ConversionListener>();
		this.cprops = new ArrayList<ConfigProperties>();
		this.jobscheduler = new ConversionJobScheduler();
		this.poller = new ConfigPoller(CONF_DIR,new ArrayList<ConfigProperties>());
	}
	/**
	 * Initialise the Scheduler by reading config files
	 */
	public void init(){
		//this.lock = new ReentrantLock();
		//jobscheduler.setLock(this.lock);
		this.poller.inspect();
		schedule(this.poller.refresh());				
	}
	
	/**
	 * Scan existing props vs new props and add/delete as required
	 */
	public void schedule(ArrayList<ConfigProperties> newprops){
		//look for additions
		for(ConfigProperties np : newprops){
			if (!cprops.contains(np)){
				String pid = np.getProperty("PROJ.id");
				jlog.info("Adding "+ np);
				cprops.add(np);
				jobs.put(pid, createJob(np));
				trigs.put(pid, createTrigger(np));
				ears.put(pid, createListener(np));
				scheduleJob(jobs.get(pid),trigs.get(pid));
				attachListener(ears.get(pid));
			}
		}
		//look for deletions
		Iterator<ConfigProperties> cpiter = cprops.iterator();
		while(cpiter.hasNext()) {
			ConfigProperties cp = cpiter.next();
			if (!newprops.contains(cp)){
				String pid = cp.getProperty("PROJ.id");
				jlog.info("Deleting "+ cp);
				cancelJob(jobs.get(pid));
				deleteJob(cp);
				deleteTrigger(cp);
				deleteListener(cp);
				cpiter.remove();
			}
		}
	}
	/**
	 * Given the name of a file decide whether its a proper
	 * properties file and if so load it
	 */
	/*
    public void addTarget(String pfn){
				//scoping trouble?
				if (ConfigProperties.validatePropFileName(pfn)){
						ConfigProperties cp = new ConfigProperties(pfn);
						cprops.add(cp);
				}
    }
	*/

	public List<Integer> readProperties(){
		return new ArrayList<Integer>();
	}

	/**
	 * Create a new ConversionJob according to the provided props
	 */
	public ConversionJob createJob(ConfigProperties cprop){
		ConversionJobFactory cjf = new ConversionJobFactory(cprop);
		//add new job with proj.id as name
		jlog.info("Create Job "+cprop.getProperty("PROJ.id"));
		return cjf.getConversionJobInstance();
		//jobs.put(cprop.getProperty("PROJ.id"),tjf.getInstance());

	}	
	/**
	 * Remove a ConversionJob described by the provided props
	 */
	public void deleteJob(ConfigProperties cprop){
		jobs.remove(cprop.getProperty("PROJ.id"));
		jlog.info("Delete Job "+cprop.getProperty("PROJ.id"));
	}
	/**
	 * Parse time props to scheduler trigger
	 */
	public Trigger createTrigger(ConfigProperties cprop){
		int repeat;
		long interval;
		Calendar sd = calculateStartTime(cprop);
		Calendar ed = calculateEndTime(cprop);
		String name = cprop.getProperty("PROJ.name");

		String rstr = cprop.getProperty("TIME.repeat");
		String istr = cprop.getProperty("TIME.interval");
		String fstr = cprop.getProperty("TIME.frequency");

		//repeat this many times or forever
		if(rstr!=null)
			repeat = Integer.parseInt(rstr);
		else
			repeat = SimpleTrigger.REPEAT_INDEFINITELY;

		//repeat every interval milliseconds or daily
		//interval overrides frequency
		if(istr!=null)
			interval = Long.parseLong(istr);
		else if (fstr!=null)
			interval = ONE_DAY/Long.parseLong(fstr);
		else
			interval = ONE_DAY;

		jlog.info("Create Trigger n="+name+" sd="+sd.getTime()+" ed="+ed.getTime()+" r="+repeat+" i="+interval);

		Trigger trigger = new SimpleTrigger(name, null,sd.getTime(),ed.getTime(),repeat,interval);
		trigger.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT);
		return trigger;


	}
	
	/**
	 * Remove trigger property
	 * @param cprop
	 */
	public void deleteTrigger(ConfigProperties cprop){
		trigs.remove(cprop.getProperty("PROJ.id"));
	}
	
	/**
	 * Create a new ConversionListener according to the provided props
	 */
	public ConversionListener createListener(ConfigProperties cprop){
		jlog.info("Create Listener "+cprop.getProperty("PROJ.id"));
		return new ConversionListener(cprop.getProperty("PROJ.id"));
	}	
	/**
	 * Remove a ConversionListener described by the provided props
	 */
	public void deleteListener(ConfigProperties cprop){
		ears.remove(cprop.getProperty("PROJ.id"));
		jlog.info("Delete Listener "+cprop.getProperty("PROJ.id"));
	}
	
	/**
	 * Read scheduler start date/time from cprops and return as Calendar instance
	 * @param cprop
	 * @return
	 */
	public Calendar calculateStartTime(ConfigProperties cprop){
		Calendar d = Calendar.getInstance();
		String sd = cprop.getProperty("TIME.startdate");
		String st = cprop.getProperty("TIME.starttime");
		
		//SD defined
		if(sd!=null){
			d.setTime(df.parse(sd,new ParsePosition(0)));
		}
		//SD not defined, use current date
		
		//ST defined
		if(st!=null){
			Calendar t = Calendar.getInstance();
			t.setTime(tf.parse(st,new ParsePosition(0)));
			//take the starttime and add it to the startdate
			d.add(Calendar.HOUR_OF_DAY,t.get(Calendar.HOUR_OF_DAY));
			d.add(Calendar.MINUTE,t.get(Calendar.MINUTE));
			d.add(Calendar.SECOND,t.get(Calendar.SECOND));
		}
		//ST not defined use current plus a small delay to avoid misfires
		else {
			d.add(Calendar.SECOND,START_DELAY);
		}
		return d;

	}
	
	/**
	 * Read scheduler end date/time from cprops and return as Calendar instance
	 * @param cprop
	 * @return
	 */
	public Calendar calculateEndTime(ConfigProperties cprop){
		Calendar d = Calendar.getInstance();
		String ed = cprop.getProperty("TIME.enddate");
		String et = cprop.getProperty("TIME.endtime");
		
		//ED defined
		if(ed!=null){
			d.setTime(df.parse(ed,new ParsePosition(0)));
		}
		//ED not defined, set to 1 year ahead
		else {
			d.add(Calendar.YEAR,1);
		}
		
		//ET defined
		if(et!=null){
			Calendar t = Calendar.getInstance();
			t.setTime(tf.parse(et,new ParsePosition(0)));
			//take the endtime and add it to enddate
			d.add(Calendar.HOUR_OF_DAY,t.get(Calendar.HOUR_OF_DAY));
			d.add(Calendar.MINUTE,t.get(Calendar.MINUTE));
			d.add(Calendar.SECOND,t.get(Calendar.SECOND));
		}
		//ET not defined, use current time
		
		return d;
		
	}


	/**
	 * From the configprops create new schedules
	 */
	public void scheduleJob(ConversionJob tjob, Trigger trig){
		jobscheduler.scheduleJob(tjob,trig);
		jlog.info("Schedule Job "+tjob.getFullName()+"/"+trig.getFullName());
	}
	
	/**
	 * Attach a listener to the job scheduler
	 * @param lstnr
	 */
	public void attachListener(JobListener lstnr){
		jobscheduler.attachListener(lstnr);
		jlog.info("Listen Job "+lstnr.getName());
	}

	/**
	 * Remove a conversion job from the scheduler
	 */
	public void cancelJob(ConversionJob tjob){
		jobscheduler.cancelJob(tjob);
		//jlog.info("Cancel Job "+tjob.getFullName());
	}
	/*
	private String formatCalendar(Calendar c){
		return String.valueOf(c.get(Calendar.YEAR))
		+"/"+String.valueOf(c.get(Calendar.MONTH))
		+"/"+String.valueOf(c.get(Calendar.DAY_OF_MONTH))
		+" "+String.valueOf(c.get(Calendar.HOUR_OF_DAY))
		+":"+String.valueOf(c.get(Calendar.MINUTE))
		+":"+String.valueOf(c.get(Calendar.SECOND));
	}
	*/
	
	/*get/set the config props poller*/
	
	public ConfigPoller getPoller() {
		return poller;
	}
	public void setPoller(ConfigPoller poller) {
		this.poller = poller;
	}
	
	//---------------------------------------------------------------------------
	
	/**
	 * Main loop method. Starts timers triggering executable jobs
	 */
	public void activateTimers(){

		while(true){
			try {
				Thread.sleep(FIVE_SECONDS);
				//System.out.print(".");
				System.out.println(jobscheduler.getSchedulerStatus());
				//if the props list changes
				if(poller.inspect()!=0){
					schedule(this.poller.refresh());
				}

				/*
				for (String k : trigs.keySet()){
					System.out.println(k+"//"+formatCalendar(Calendar.getInstance())+"->"+trigs.get(k).getNextFireTime());
				}
				*/
			}
			catch(InterruptedException ie){
				System.err.println("Interrupted Exception : " + ie);
				throw new RuntimeException(ie);
			}
			catch(SchedulerException se){
				System.err.println("Scheduler Exception : " + se);
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		//WNMSDataScheduler.log();
		
		WNMSDataScheduler wds = new WNMSDataScheduler();
		wds.wdl.addLoggingAppender(LogAppType.Console);
		wds.init();
		
		jlog.info("START - DATA TRANSFER MONITOR");
		wds.activateTimers();
	}

}