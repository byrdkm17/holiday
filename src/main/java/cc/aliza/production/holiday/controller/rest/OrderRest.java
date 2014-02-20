package cc.aliza.production.holiday.controller.rest;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.entity.Goods;
import cc.aliza.production.holiday.entity.Order;
import cc.aliza.production.holiday.entity.Traveler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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

        Order order = new Order();
        order.setGoodsJson(new Gson().toJson(goods));
        order.setGoods(goods);
        order.setPriceSet(priceSet);
        order.setNumber(number);
        order.setStatus(0);

        String id = getSessionAttr("member");

        if (StringUtils.isBlank(id)) {
            OrderDao.dao.save(order);
        } else {
            order.setMember(MemberDao.dao.findOne(id));
            OrderDao.dao.save(order);
        }
        renderJson(Result.exec(order));
    }

    @Before(POST.class)
    public void update() {
        String id = getPara("orderID");

        Order order = OrderDao.dao.findOne(id);

        String travelerJson = getPara("traveler");

        Type travelerListType = new TypeToken<List<Traveler>>() {
        }.getType();

        List<Traveler> travelers = new Gson().fromJson(travelerJson, travelerListType);

        order.setNumber(getParaToInt("number", 0));

        order.setTravelers(travelers);

        order.setContactName(getPara("contactName"));
        order.setContactPhone(getPara("contactPhone"));

        order.setMessage(getPara("message"));
        order.setPayMethod(getParaToInt("payMethod"));

        Map priceSet = order.getPriceSetObject();

        Double price = ((Double) priceSet.get("price")) * order.getNumber();
        order.setPrice(price.intValue());
        order.setStatus(1);
        if (order.getPayMethod() == 1) {
            order.setDiscount(0);
            order.setPayPrice(0);
        }
        if (order.getPayMethod() == 2) {
            order.setDiscount((int) (order.getPrice() * 0.05));
            order.setPayPrice((int) ((order.getPrice() - order.getDiscount()) * 0.2));
        }
        if (order.getPayMethod() == 4) {
            order.setDiscount((int) (order.getPrice() * 0.07));
            order.setPayPrice(order.getPrice() - order.getDiscount());
        }

        OrderDao.dao.save(order);
        renderJson(Result.exec());
    }

}
