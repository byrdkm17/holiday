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
public class CarController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("production", "car");
        setAttr("labelPage", LabelDao.dao.findBy(params));

        setAttr("goodsPage", GoodsDao.dao.findBy(params));
        setAttr("count", GoodsDao.dao.count());
        render("/view/line/index.html");
    }

}
