package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.CommentDao;
import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.entity.Goods;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.text.SimpleDateFormat;
import java.util.*;

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
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar e = null;
                List<String> dates = new ArrayList<String>();
                try {
                    Date b = sdf.parse(goods.getStartTime());
                    if (calendar.before(b)) {
                        calendar.setTime(b);
                    }
                } catch (Exception ex) {
                    calendar.clone();
                }

                try {
                    e = (Calendar) calendar.clone();
                    e.setTime(sdf.parse(goods.getEndTime()));
                } catch (Exception ex) {
                    e = (Calendar) calendar.clone();
                }
                e.add(Calendar.DAY_OF_MONTH, 1);
                while (calendar.before(e)) {
                    dates.add(sdf.format(calendar.getTime()));
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
                setAttr("dates", dates);
                render("/view/detail_line.html");
            } else {
                render("/view/detail.html");
            }
        } catch (Exception e) {
            redirect("/");
        }
    }
}
