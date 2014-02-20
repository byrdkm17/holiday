package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class HomeController extends Controller {

    public void index() {
        render("/manage/index.html");
    }
}
