package cc.aliza.production.holiday.commons;

import cc.aliza.production.holiday.commons.ext.xBeetlRenderFactory;
import com.jfinal.log.Logger;
import com.jfinal.render.IMainRenderFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Jing on 14-2-15.
 */
public class HolidayConstants {

    private static Logger logger = Logger.getLogger(HolidayConstants.class);

    public static boolean develop = true;

    public static IMainRenderFactory getRender() {
        xBeetlRenderFactory.groupTemplate.setCharset("utf-8");
        xBeetlRenderFactory.groupTemplate.setStatementStart("<!--#");
        xBeetlRenderFactory.groupTemplate.setStatementEnd("-->");
        return new xBeetlRenderFactory();
    }

    public static String getProperty(String key) {
        try {
            Properties props = new Properties();
            props.load(HolidayConstants.class.getResourceAsStream("/holiday.properties"));
            return props.getProperty(key);
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static int getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
    }
}
