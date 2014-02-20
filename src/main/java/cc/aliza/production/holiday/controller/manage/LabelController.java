package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.LabelDao;
import cc.aliza.production.holiday.entity.Label;
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
public class LabelController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("production", getPara("production", "line"));

        setAttr("labelPage", LabelDao.dao.findBy(params));

        setAttr("production", getPara("production", "line"));
        render("/manage/base/label/index.html");
    }

    @Before(POST.class)
    public void save() {
        String id = getPara("father");
        String name = getPara("name");
        String production = getPara("production");

        Label label = new Label();
        label.setName(name);
        if (StringUtils.isNotBlank(id)) {
            Label father = LabelDao.dao.findOne(id);
            label.setFather(father);
        } else {
            label.setProduction(production);
        }

        LabelDao.dao.save(label);

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void remove() {
        String id = getPara("id");

        LabelDao.dao.remove("father", LabelDao.dao.findOne(id));

        LabelDao.dao.remove(id);

        renderJson(Result.exec());
    }
}
