package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import cc.aliza.production.holiday.interceptor.view.LoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-8.
 */
@Before({LoginInterceptor.class, DataInterceptor.class})
public class UserController extends Controller {

    public void order() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("member", getAttr("member"));
        setAttr("orderPage", OrderDao.dao.findBy(params));
        params.clear();
        params.put("hot", 1);
        params.put("status", 1);
        setAttr("goodsPage", GoodsDao.dao.findBy(params));
        render("/view/user/order.html");
    }

    public void collect() {
        render("/view/user/collect.html");
    }

}
