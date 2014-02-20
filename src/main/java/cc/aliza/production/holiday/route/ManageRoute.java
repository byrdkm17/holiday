package cc.aliza.production.holiday.route;

import cc.aliza.production.holiday.controller.manage.*;
import com.jfinal.config.Routes;

/**
 * Created by Jing on 14-2-15.
 */
public class ManageRoute extends Routes {
    @Override
    public void config() {

        add("/manage/goods", GoodsController.class);
        add("/manage/member", MemberController.class);
        add("/manage/order", OrderController.class);

        add("/manage/base/supplier", SupplierController.class);
        add("/manage/base/service", ServiceController.class);
        add("/manage/base/arg", ArgController.class);
        add("/manage/base/label", LabelController.class);

        add("/manage/ad", ADController.class);

        add("/manage/auth", AuthController.class);
        add("/manage", HomeController.class);
    }
}
