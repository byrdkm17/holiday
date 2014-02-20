package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.ArgDao;
import cc.aliza.production.holiday.entity.Arg;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class ArgController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("production", getPara("production", "line"));

        setAttr("argPage", ArgDao.dao.findBy(params));

        setAttr("production", getPara("production", "line"));
        render("/manage/base/arg/index.html");
    }

    @Before(POST.class)
    public void save() {
        String id = getPara("father");
        String name = getPara("name");

        Arg father = ArgDao.dao.findOne(id);
        Arg arg = new Arg();
        arg.setName(name);
        arg.setFather(father);
        ArgDao.dao.save(arg);

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void remove() {
        String id = getPara("id");

        ArgDao.dao.remove(id);

        renderJson(Result.exec());
    }
}
