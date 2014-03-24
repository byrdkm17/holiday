package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.entity.Goods;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import cc.aliza.production.holiday.interceptor.view.LoginInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-2-8.
 */
@Before({LoginInterceptor.class, DataInterceptor.class})
public class UserController extends Controller {

    public void order() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("member", getAttr("member"));
        params.put("pageNumber", getAttr("pageNumber"));
        params.put("pageSize", 5);
        params.put("orderKey", getPara("orderKey"));
        params.put("member", getAttr("member"));
        setAttr("orderPage", OrderDao.dao.findBy(params));
        params.clear();
        params.put("hot", 1);
        params.put("status", 1);
        setAttr("goodsPage", GoodsDao.dao.findBy(params));
        render("/view/user/order.html");
    }

    public void collect() {
        Member member = getAttr("member");
        BuguMapper.fetchCascade(member, "collect");
        List<Goods> collect = member.getCollect();
        List<Goods> nc = new ArrayList<Goods>();
        if (getPara("search") != null) {
            for (Goods goods : collect) {
                if (StringUtils.indexOf(goods.getName(), getPara("search")) > -1) {
                    nc.add(goods);
                    continue;
                }
                if (StringUtils.indexOf(goods.getCaption(), getPara("search")) > -1) {
                    nc.add(goods);
                }
            }
        } else {
            nc = collect;
        }

        setAttr("collect", nc);
        render("/view/user/collect.html");
    }

}
