package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.entity.Order;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import cc.aliza.production.holiday.interceptor.view.LoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by Jing on 14-2-8.
 */
@Before({LoginInterceptor.class, DataInterceptor.class})
public class PayController extends Controller {

    public void index() {

        String id = getPara(0);

        Order order = OrderDao.dao.findOne(id);

        if (order.getMember() == null) {
            OrderDao.dao.set(id, "member", getAttr("member"));
        } else {
            if (!order.getMember().equals(order.getMember())) {
                redirect("/");
                return;
            }
        }

        setAttr("order", order);
        render("/view/pay.html");
    }

    public void doPay() {
        Order order = OrderDao.dao.findOne(getPara(0));
        OrderDao.dao.set(order, "status", 2);
        GoodsDao.dao.inc(order.getGoods(), "sales", 1);
        redirect("/pay/done/" + getPara(0));
    }

    public void done() {
        String id = getPara(0);
        Order order = OrderDao.dao.findOne(id);
        if (order.getStatus() == 2) {
            setAttr("order", order);
            render("/view/payDone.html");
        } else {
            redirect("/user/order");
        }
    }

}
