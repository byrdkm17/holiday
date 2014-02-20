package cc.aliza.production.holiday.route;

import cc.aliza.production.holiday.controller.rest.ImageRest;
import cc.aliza.production.holiday.controller.rest.OrderRest;
import com.jfinal.config.Routes;

/**
 * Created by Jing on 14-2-15.
 */
public class RestRoute extends Routes {
    @Override
    public void config() {
        add("/rest/image", ImageRest.class);

        add("/rest/order", OrderRest.class);
    }
}
