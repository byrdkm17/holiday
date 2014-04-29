package cc.aliza.production.holiday.route;

import cc.aliza.production.holiday.controller.view.*;
import com.jfinal.config.Routes;

/**
 * Created by Jing on 14-2-15.
 */
public class ViewRoute extends Routes {
    @Override
    public void config() {

        add("/detail", DetailController.class);
        add("/order", OrderController.class);
        add("/pay", PayController.class);
        add("/user", UserController.class);

        add("/cart", CartController.class);

        add("/line", LineController.class);
        add("/car", CarController.class);
        add("/hotel", HotelController.class);
        add("/ticket", TicketController.class);
        add("/all", AllController.class);

        add("/custom", CustomController.class);

        //add("/play", PlayController.class);

        add("/play", NewPlayController.class);

        add("/help", HelpController.class);

        add("/", HomeController.class);
    }
}
