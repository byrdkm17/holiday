package cc.aliza.production.holiday.controller.view;

import com.jfinal.core.Controller;

/**
 * Created by Jing on 14-1-27.
 */
public class LogoutController extends Controller {

    public void index() {
        getSession().invalidate();
        redirect("/");
    }

}
