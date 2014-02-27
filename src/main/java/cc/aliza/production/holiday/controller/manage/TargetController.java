package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.TargetDao;
import cc.aliza.production.holiday.entity.Target;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class TargetController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        setAttr("targetPage", TargetDao.dao.findBy(params));

        render("/manage/base/target/index.html");
    }

    @Before(POST.class)
    public void save() {
        String id = getPara("father");
        String name = getPara("name");

        Target target = new Target();
        target.setName(name);
        if (StringUtils.isNotBlank(id)) {
            Target father = TargetDao.dao.findOne(id);
            target.setFather(father);
        }

        TargetDao.dao.save(target);

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void remove() {
        String id = getPara("id");

        TargetDao.dao.remove("father", TargetDao.dao.findOne(id));

        TargetDao.dao.remove(id);

        renderJson(Result.exec());
    }
}
