package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-8.
 */
@Before({DataInterceptor.class})
public class AllController extends Controller {

    public void index() {

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("key", getPara("key", null));
        params.put("order", getPara("order", null));
        params.put("desc", getPara("desc", null));
        params.put("status", 1);

        setAttr("order", getPara("order", null));
        setAttr("desc", getPara("desc", null));
        setAttr("key", getPara("key", null));
        setAttr("goodsPage", GoodsDao.dao.findBy(params));

        render("/view/all/index.html");
    }

}
