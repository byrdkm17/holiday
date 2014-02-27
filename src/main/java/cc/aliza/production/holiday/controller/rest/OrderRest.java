package cc.aliza.production.holiday.controller.rest;

import cc.aliza.production.holiday.commons.HolidayConstants;
import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.dao.SettingDao;
import cc.aliza.production.holiday.entity.Goods;
import cc.aliza.production.holiday.entity.Order;
import cc.aliza.production.holiday.entity.Setting;
import cc.aliza.production.holiday.entity.Traveler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        String beginDate = getPara("beginDate");
        String endDate = getPara("endDate");
        Integer price = getParaToInt("price");

        Order order = new Order();
        order.setGoodsJson(new Gson().toJson(goods));
        order.setGoods(goods);
        order.setPriceSet(priceSet);
        order.setNumber(number);
        order.setStatus(0);
        order.setBeginDate(beginDate);
        order.setEndDate(endDate);
        order.setPrice(price);

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
                    price = price + list.get(sdf.format(cur.getTime()));
                }
                order.setAmount(price.intValue());
                price = price * order.getNumber();
            }
        }
        order.setPrice(price.intValue());
        order.setStatus(1);
        if (order.getPayMethod() == 1) {
            order.setDiscount(0);
            order.setPayPrice(0);
        }
        if (order.getPayMethod() == 2) {
            Setting s = SettingDao.dao.findOne("key", "pay.preAmountDiscount");
            double discount = 0;
            if (s != null) {
                discount = Double.valueOf(s.getValue().toString());
            }
            order.setDiscount((int) Math.round(order.getPrice() * discount / 100) * 100);
            order.setPayPrice((int) Math.round((order.getPrice() - order.getDiscount()) * 0.2 / 100) * 100);
        }
        if (order.getPayMethod() == 4) {
            Setting s = SettingDao.dao.findOne("key", "pay.allAmountDiscount");
            double discount = 0;
            if (s != null) {
                discount = Double.valueOf(s.getValue().toString());
            }
            order.setDiscount((int) Math.round(order.getPrice() * discount / 100) * 100);
            order.setPayPrice(order.getPrice() - order.getDiscount());
        }

        OrderDao.dao.save(order);
        renderJson(Result.exec());
    }

}
