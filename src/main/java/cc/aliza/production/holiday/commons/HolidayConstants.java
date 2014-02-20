package cc.aliza.production.holiday.commons;

import cc.aliza.production.holiday.commons.ext.xBeetlRenderFactory;
import com.jfinal.log.Logger;
import com.jfinal.render.IMainRenderFactory;

import java.io.IOException;
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
}
