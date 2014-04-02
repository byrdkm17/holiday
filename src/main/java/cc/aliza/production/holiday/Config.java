package cc.aliza.production.holiday;

import cc.aliza.production.holiday.commons.HolidayConstants;
import cc.aliza.production.holiday.controller.CaptchaController;
import cc.aliza.production.holiday.controller.SystemController;
import cc.aliza.production.holiday.controller.view.LoginController;
import cc.aliza.production.holiday.controller.view.LogoutController;
import cc.aliza.production.holiday.controller.view.SignController;
import cc.aliza.production.holiday.plugin.QuartzPlugin;
import cc.aliza.production.holiday.route.ManageRoute;
import cc.aliza.production.holiday.route.RestRoute;
import cc.aliza.production.holiday.route.ViewRoute;
import com.bugull.mongo.BuguConnection;
import com.jfinal.config.*;

/**
 * Created by Jing on 14-1-27.
 */
public class Config extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(HolidayConstants.develop);
        me.setMainRenderFactory(HolidayConstants.getRender());
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/_config", SystemController.class);
        me.add("/captcha", CaptchaController.class);

        me.add("/logout", LogoutController.class);
        me.add("/login", LoginController.class);
        me.add("/sign", SignController.class);

        me.add(new ManageRoute());
        me.add(new RestRoute());
        me.add(new ViewRoute());
    }

    @Override
    public void configPlugin(Plugins me) {
        loadPropertyFile("/classes/holiday.properties");
        BuguConnection.getInstance().connect(getProperty("mongo.url"), getPropertyToInt("mongo.port"), getProperty("mongo.db"));

        QuartzPlugin qp = new QuartzPlugin();
        me.add(qp);
    }

    @Override
    public void configInterceptor(Interceptors me) {
    }

    @Override
    public void configHandler(Handlers me) {

    }

    public void afterJFinalStart() {
    }

    public void beforeJFinalStop() {
        BuguConnection.getInstance().close();
    }
}
