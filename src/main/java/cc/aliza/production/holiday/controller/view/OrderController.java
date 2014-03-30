package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.dao.SettingDao;
import cc.aliza.production.holiday.entity.Order;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import cc.aliza.production.holiday.interceptor.view.LoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by Jing on 14-2-8.
 */
@Before({LoginInterceptor.class, DataInterceptor.class})
public class OrderController extends Controller {

    public void index() {

        Order order = OrderDao.dao.findOne(getPara(0));

        if (order.getStatus() == 0) {
            setAttr("order", order);
            if ("line".equals(order.getGoodsObject().getProduction())) {
                render("/view/line/info.html");
            }
            if ("hotel".equals(order.getGoodsObject().getProduction())) {
                render("/view/hotel/info.html");
            }
            if ("car".equals(order.getGoodsObject().getProduction())) {
                render("/view/car/info.html");
            }
            if ("ticket".equals(order.getGoodsObject().getProduction())) {
                render("/view/ticket/info.html");
            }
        }
        if (order.getStatus() == 1) {
            redirect("/pay/" + order.getId());
        }
        if (order.getStatus() == 2) {
            redirect("/user/order");
        }

        setAttr("allAmountDiscount", SettingDao.dao.findOne("key", "pay.allAmountDiscount"));
        setAttr("preAmountDiscount", SettingDao.dao.findOne("key", "pay.preAmountDiscount"));
    }

    public void close() {
        String id = getPara(0);

        Order order = OrderDao.dao.findOne(id);

        if (order.getMember().equals(getAttr("member"))) {
            OrderDao.dao.set(order, "status", -1);
        }

        redirect("/user/order");
    }

    public void remove() {
        String id = getPara(0);

        Order order = OrderDao.dao.findOne(id);

        if (order.getMember().equals(getAttr("member"))) {
            OrderDao.dao.set(order, "status", 3);
        }

        redirect("/user/order");
    }

}
