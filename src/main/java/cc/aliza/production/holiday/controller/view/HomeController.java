package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.ADDao;
import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.LabelDao;
import cc.aliza.production.holiday.entity.Goods;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-16.
 */
@Before(DataInterceptor.class)
public class HomeController extends Controller {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("status", 1);
        params.put("discount", 0);
        params.put("pageSize", 9);
        setAttr("discountGoodsPage", GoodsDao.dao.findBy(params));

        params.clear();
        params.put("pageSize", 8);
        params.put("production", "line");
        params.put("status", 1);
        Page<Goods> hotLineGoodsPage = GoodsDao.dao.findBy(params);
        BuguMapper.fetchCascade(hotLineGoodsPage.getList(), "labels");
        setAttr("hotLineGoodsPage", hotLineGoodsPage);

        params.clear();
        params.put("production", "line");
        setAttr("hotLineLabelPage", LabelDao.dao.findBy(params));

        params.clear();
        params.put("production", "hotel");
        setAttr("hotHotelLabelPage", LabelDao.dao.findBy(params));

        params.clear();
        params.put("production", "ticket");
        setAttr("hotTicketLabelPage", LabelDao.dao.findBy(params));

        params.clear();
        params.put("production", "car");
        setAttr("hotCarLabelPage", LabelDao.dao.findBy(params));

        params.clear();
        params.put("pageSize", 15);
        params.put("production", "hotel");
        params.put("status", 1);
        setAttr("hotHotelGoodsPage", GoodsDao.dao.findBy(params));

        params.clear();
        params.put("pageSize", 48);
        params.put("production", "ticket");
        params.put("status", 1);
        setAttr("hotTicketGoodsPage", GoodsDao.dao.findBy(params));

        params.clear();
        params.put("pageSize", 36);
        params.put("production", "car");
        params.put("status", 1);
        setAttr("hotCarGoodsPage", GoodsDao.dao.findBy(params));

        params.clear();
        params.put("position", "LB");
        setAttr("LBADPage", ADDao.dao.findBy(params));

        setAttr("HF1AD", ADDao.dao.findOne("position", "HF1"));
        setAttr("HF2AD", ADDao.dao.findOne("position", "HF2"));
        setAttr("HF3AD", ADDao.dao.findOne("position", "HF3"));

        render("/view/index.html");
    }
}
