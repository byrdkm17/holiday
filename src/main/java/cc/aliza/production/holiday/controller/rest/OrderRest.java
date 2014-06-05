package cc.aliza.production.holiday.controller.rest;

import cc.aliza.production.holiday.commons.HolidayConstants;
import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.*;
import cc.aliza.production.holiday.entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jing on 14-2-1.
 */

public class OrderRest extends Controller {

    @Before(POST.class)
    public void save() {

        String goodsID = getPara("goodsID");
        Goods goods = GoodsDao.dao.findOne(goodsID);

        String priceSet = getPara("priceSet");
        Integer number = getParaToInt("number");
        Integer number1 = getParaToInt("number1");
        Integer number2 = getParaToInt("number2");
        String beginDate = getPara("beginDate");
        String endDate = getPara("endDate");
        String date = getPara("date");
        Integer price = getParaToInt("price");

        Order order = new Order();
        order.setGoodsJson(new Gson().toJson(goods));
        order.setGoods(goods);
        order.setPriceSet(priceSet);
        order.setNumber(number);
        order.setNumber1(number1);
        order.setNumber2(number2);
        order.setStatus(0);
        order.setBeginDate(beginDate);
        order.setEndDate(endDate);
        order.setDate(date);
        order.setPrice(price);

        String id = getSessionAttr("member");
        int total;
        if (StringUtils.isBlank(id)) {
            OrderDao.dao.save(order);
            List<Order> orders = getSessionAttr("cart");
            if (orders == null) {
                orders = new ArrayList<Order>();
            }
            orders.add(order);
            setSessionAttr("cart", orders);
            total = orders.size();
        } else {
            order.setMember(MemberDao.dao.findOne(id));
            OrderDao.dao.save(order);
            if (getParaToBoolean("pre")) {
                MemberDao.dao.push(id, "cart", order);
            }
            Member member = MemberDao.dao.findOne(id);
            List<Order> oo = member.getCart();
            if (oo != null) {
                total = oo.size();
            } else {
                total = 0;
            }
        }
        if (getParaToBoolean("pre")) {
            Map<String, Object> r = new HashMap<String, Object>();
            r.put("success", true);
            r.put("total", total);
            renderJson(r);
        } else {
            order.setGoods(null);
            renderJson(Result.exec(order));
        }
    }

    @Before(POST.class)
    public void updateCart() {
        String OrdersJson = getPara("orders");
        Type type = new TypeToken<List<Order>>() {
        }.getType();
        List<Order> ol = new Gson().fromJson(OrdersJson, type);
        List<Order> list = new ArrayList<Order>();
        Integer total = 0;
        String id = getSessionAttr("member");
        Member member = MemberDao.dao.findOne(id);
        String travelerJson = getPara("traveler");

        Type travelerListType = new TypeToken<List<Traveler>>() {
        }.getType();

        List<Traveler> travelers = new Gson().fromJson(travelerJson, travelerListType);

        for (Order o : ol) {
            Order oo = OrderDao.dao.findOne(o.getId());
            oo = upOrder(oo, travelers, o.getNumber(), getPara("contactName"), getPara("contactPhone"), getPara("message"), getParaToInt("payMethod"));
            OrderDao.dao.save(oo);
            list.add(oo);
            total += oo.getPrice() * oo.getNumber();
        }

        Cart cart = new Cart();
        cart.setOrders(list);
        cart.setMember(member);
        cart.setStatus(1);
        cart.setPayPrice(total);

        CartDao.dao.insert(cart);

        renderJson(Result.exec(cart));

    }

    @Before(POST.class)
    @SuppressWarnings("unchecked")
    public void update() {
        String id = getPara("orderID");

        Order order = OrderDao.dao.findOne(id);

        String travelerJson = getPara("traveler");

        Type travelerListType = new TypeToken<List<Traveler>>() {
        }.getType();

        List<Traveler> travelers = new Gson().fromJson(travelerJson, travelerListType);

        order = upOrder(order, travelers, getParaToInt("number", 0), getPara("contactName"), getPara("contactPhone"), getPara("message"), getParaToInt("payMethod"));
        OrderDao.dao.save(order);
        renderJson(Result.exec());
    }

    private Order upOrder(Order order, List<Traveler> travelers, Integer number, String contactName, String contactPhone, String message, Integer payMethod) {

        order.setNumber(number);

        order.setTravelers(travelers);

        order.setContactName(contactName);
        order.setContactPhone(contactPhone);

        order.setMessage(message);
        order.setPayMethod(payMethod);

        Map priceSet = order.getPriceSetObject();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String beginDate = order.getBeginDate();
        String endDate = order.getEndDate();
        Date begin, end;
        try {
            begin = sdf.parse(beginDate);
        } catch (ParseException e) {
            begin = new Date();
        }
        try {
            end = sdf.parse(endDate);
        } catch (ParseException e) {
            end = new Date();
        }

        int day = HolidayConstants.getDaysBetween(begin, end) + 1;
        Double price = 0.0;
        if (order.getGoodsObject().getPriceType().equals(1)) {
            price = ((Double) priceSet.get("price")) * day;
            order.setAmount(price.intValue());
            price = price * order.getNumber();
        } else {
            Map<String, Double> list = (Map<String, Double>) priceSet.get("priceList");
            if (list == null) {
                price = 0.0;
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(begin);
                for (int i = 0; i < day; i++) {
                    Calendar cur = (Calendar) calendar.clone();
                    cur.add(Calendar.DATE, i);
                    if (list.containsKey(sdf.format(cur.getTime()))) {
                        price = price + list.get(sdf.format(cur.getTime()));
                    } else {
                        price = price + 0;
                    }

                }
                order.setAmount(price.intValue());
                price = price * order.getNumber();
            }
        }
        order.setPrice(price.intValue());
        if (order.getPayMethod() != null) {
            order.setStatus(1);
            String id = getSessionAttr("member");
            Member member = MemberDao.dao.findOne(id);
            MemberDao.dao.pull(member, "cart", order);
        }
        if (order.getPayMethod() == null) {
            order.setDiscount(0);
            order.setPayPrice(0);
        } else if (order.getPayMethod() == 1) {
            order.setDiscount(0);
            order.setPayPrice(0);
        } else if (order.getPayMethod() == 2) {
            Setting s = SettingDao.dao.findOne("key", "pay.preAmountDiscount");
            double discount = 0;
            if (s != null) {
                discount = Double.valueOf(s.getValue().toString());
            }
            order.setDiscount((int) Math.round(order.getPrice() * discount / 100) * 100);
            order.setPayPrice((int) Math.round((order.getPrice() - order.getDiscount()) * 0.2 / 100) * 100);
        } else if (order.getPayMethod() == 4) {
            Setting s = SettingDao.dao.findOne("key", "pay.allAmountDiscount");
            double discount = 0;
            if (s != null) {
                discount = Double.valueOf(s.getValue().toString());
            }
            order.setDiscount((int) Math.round(order.getPrice() * discount / 100) * 100);
            order.setPayPrice(order.getPrice() - order.getDiscount());
        }

        return order;
    }

}
