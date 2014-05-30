package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.CommentDao;
import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.entity.Goods;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-16.
 */
@Before(DataInterceptor.class)
public class DetailController extends Controller {
    public void index() {
        String id = getPara(0);
        try {
            Goods goods = GoodsDao.dao.findOne(id);

            BuguMapper.fetchCascade(goods, "args.father", "supplier", "services");

            setAttr("goods", goods);

            Map<String, Object> params = new HashMap<String, Object>();

            params.put("goods", goods);
            setAttr("commentPage", CommentDao.dao.findBy(params));

            params.clear();
            params.put("production", goods.getProduction());
            params.put("hot", 1);
            params.put("status", 1);
            setAttr("hotGoodsPage", GoodsDao.dao.findBy(params));

            if (goods.getProduction().equals("line")) {
                render("/view/detail_line.html");
            } else {
                render("/view/detail.html");
            }
        } catch (Exception e) {
            redirect("/");
        }
    }
}
