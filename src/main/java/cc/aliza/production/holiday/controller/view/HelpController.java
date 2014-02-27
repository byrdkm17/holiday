package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.HelpDao;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by Jing on 14-2-27.
 */
@Before(DataInterceptor.class)
public class HelpController extends Controller {

    public void index() {
        String id = getPara();
        setAttr("help", HelpDao.dao.findOne(id));
        render("/view/help.html");
    }

}
