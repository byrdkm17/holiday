package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.CartDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.dao.SettingDao;
import cc.aliza.production.holiday.entity.Cart;
import cc.aliza.production.holiday.entity.Order;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import cc.aliza.production.holiday.interceptor.view.LoginInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 14-2-8.
 */
@Before({LoginInterceptor.class, DataInterceptor.class})
public class OrderController extends Controller {

    public void index() {
        String id = getPara(0);
        if ("cart".equals(id)) {
            String cartID = getPara(1);
            Cart cart = CartDao.dao.findOne(cartID);
            BuguMapper.fetchCascade(cart, "orders");
            setAttr("orderList", cart.getOrders());
            render("/view/payInfo.html");
        } else {
            Order order = OrderDao.dao.findOne(id);

            if (order.getStatus() == 0) {
                List<Order> l = new ArrayList<Order>();
                l.add(order);
                setAttr("orderList", l);
                render("/view/payInfo.html");
            }
            if (order.getStatus() == 1) {
                redirect("/pay/" + order.getId());
            }
            if (order.getStatus() == 2) {
                redirect("/user/order");
            }
        }

        setAttr("allAmountDiscount", SettingDao.dao.findOne("key", "pay.allAmountDiscount"));
        setAttr("preAmountDiscount", SettingDao.dao.findOne("key", "pay.preAmountDiscount"));
    }

    public void info() {

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
        render("/view/user/orderInfo.html");
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
