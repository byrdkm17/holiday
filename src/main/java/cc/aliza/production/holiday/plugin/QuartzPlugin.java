package cc.aliza.production.holiday.plugin;

import cc.aliza.production.holiday.kit.Reflect;
import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by PalG on 14-1-4.
 * Thanks
 */
public class QuartzPlugin implements IPlugin {
    private static final String JOB = "job";

    private final Logger logger = Logger.getLogger(getClass());
    private Map<String, Job> jobs = new HashMap<String, Job>();

    private Scheduler scheduler;
    private String config = "job.properties";
    private Properties properties;

    public QuartzPlugin(String config) {
        this.config = config;
    }

    public QuartzPlugin() {
    }

    @Override
    public boolean start() {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        loadProperties();
        Enumeration<Object> enums = properties.keys();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement() + "";
            if (!key.endsWith(JOB) || !isEnableJob(enable(key))) {
                continue;
            }
            String jobClassName = properties.get(key) + "";
            String jobCronExp = properties.getProperty(cronKey(key)) + "";
            Class<Job> clazz = Reflect.on(jobClassName).get();
            JobDetail job = new JobDetail(jobClassName, jobClassName, clazz);
            CronTrigger trigger = null;
            try {
                trigger = new CronTrigger(jobClassName, jobClassName, jobCronExp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                scheduler.scheduleJob(job, trigger);
                scheduler.start();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private String enable(String key) {
        return key.substring(0, key.lastIndexOf(JOB)) + "enable";
    }

    private String cronKey(String key) {
        return key.substring(0, key.lastIndexOf(JOB)) + "cron";
    }

    private boolean isEnableJob(String enableKey) {
        Object enable = properties.get(enableKey);
        return !(enable != null && "false".equalsIgnoreCase((enable + "").trim()));
    }

    private void loadProperties() {
        properties = new Properties();
        InputStream is = QuartzPlugin.class.getClassLoader().getResourceAsStream(config);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean stop() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }
}
