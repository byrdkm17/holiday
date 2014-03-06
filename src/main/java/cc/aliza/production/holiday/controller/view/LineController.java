package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.LabelDao;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-8.
 */
@Before({DataInterceptor.class})
public class LineController extends Controller {

    public void index() {

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("production", "line");
        setAttr("labelPage", LabelDao.dao.findBy(params));


        params.put("label", getPara("label", null));
        params.put("target", getPara("target", null));
        params.put("order", getPara("order", null));
        params.put("desc", getPara("desc", null));
        params.put("key", getPara("key", null));
        params.put("status", 1);

        setAttr("order", getPara("order", null));
        setAttr("desc", getPara("desc", null));
        setAttr("queryLabel", getPara("label", null));
        setAttr("queryTarget", getPara("target", null));
        setAttr("goodsPage", GoodsDao.dao.findBy(params));
        setAttr("count", GoodsDao.dao.query().is("status", 1).is("production", "line").count());
        render("/view/line/index.html");
    }

}
