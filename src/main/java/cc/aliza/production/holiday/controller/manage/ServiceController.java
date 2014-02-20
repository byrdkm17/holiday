package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.ServiceDao;
import cc.aliza.production.holiday.entity.Service;
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
public class ServiceController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("pageNumber", getParaToInt("pageNumber"));
        params.put("production", getPara("production", "line"));

        setAttr("servicePage", ServiceDao.dao.findBy(params));

        setAttr("production", getPara("production", "line"));
        render("/manage/base/service/index.html");
    }

    @Before(POST.class)
    public void save() {
        String production = getPara("production");
        String name = getPara("name");
        String id = getPara("id");

        if (StringUtils.isNotBlank(id)) {
            ServiceDao.dao.set(id, "name", name);
            ServiceDao.dao.set(id, "production", production);
        } else {
            Service service = new Service();
            service.setName(name);
            service.setProduction(production);
            ServiceDao.dao.save(service);
        }

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void remove() {
        ServiceDao.dao.remove(getPara(0));
        renderJson(Result.exec());
    }
}
