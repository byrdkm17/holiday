package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.dao.SettingDao;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.entity.Order;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 14-4-12.
 */
@Before(DataInterceptor.class)
public class CartController extends Controller {

    public void index() {
        setAttr("allAmountDiscount", SettingDao.dao.findOne("key", "pay.allAmountDiscount"));
        setAttr("preAmountDiscount", SettingDao.dao.findOne("key", "pay.preAmountDiscount"));

        List<Order> list;

        Member member = getAttr("member");
        if (member != null) {
            BuguMapper.fetchCascade(member, "cart");
            list = member.getCart();
        } else {
            list = getSessionAttr("cart");
            if (list == null) {
                list = new ArrayList<Order>();
            }
        }
        Integer totalPrice = 0;
        for (Order o : list) {
            totalPrice += o.getPrice();
        }
        setAttr("totalPrice", totalPrice);
        setAttr("orderList", list);
        render("/view/user/cart.html");
    }

    public void remove() {
        List<Order> list;
        Member member = getAttr("member");
        Order order = OrderDao.dao.findOne(getPara(0));
        if (member != null) {
            MemberDao.dao.pull(member, "cart", order);
        } else {
            list = getSessionAttr("cart");
            if (list != null) {
                list.remove(order);
            }
        }
        redirect("/cart");
    }

    public void removeAll() {
        List<Order> list;
        Member member = getAttr("member");

        String ids = getPara("ids");
        String[] idList = StringUtils.split(ids, ",");
        for (String id : idList) {
            Order order = OrderDao.dao.findOne(id);
            if (member != null) {
                MemberDao.dao.pull(member, "cart", order);
            } else {
                list = getSessionAttr("cart");
                if (list != null) {
                    list.remove(order);
                }
            }
        }
        renderJson(Result.exec());
    }

    public void add() {
        Member member = getAttr("member");
        Order order = OrderDao.dao.findOne(getPara(0));
        if (member != null) {
            MemberDao.dao.pull(member, "cart", order);
            MemberDao.dao.push(member, "collect", order.getGoods());
            redirect("/cart");
        } else {
            redirect("/login?redirect=/cart");
        }
    }

    public void addAll() {
        Member member = getAttr("member");
        String ids = getPara("ids");
        String[] idList = StringUtils.split(ids, ",");
        for (String id : idList) {
            Order order = OrderDao.dao.findOne(id);
            MemberDao.dao.pull(member, "cart", order);
            MemberDao.dao.push(member, "collect", order.getGoods());
        }
        renderJson(Result.exec());
    }

}
