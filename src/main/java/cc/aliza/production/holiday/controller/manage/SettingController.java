package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.dao.SettingDao;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class SettingController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("groupType", 0);

        setAttr("settingPage", SettingDao.dao.findBy(params));

        render("/manage/system/setting/index.html");
    }

}
