package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Goods;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Jing on 14-1-29.
 */
public class GoodsDao extends BuguDao<Goods> {

    public static GoodsDao dao = new GoodsDao();

    public GoodsDao() {
        super(Goods.class);
    }

    private void execQuery(BuguQuery<Goods> query, Map<String, Object> params) {

        if (params.get("status") != null) {
            query.is("status", params.get("status"));
        }

        if (params.get("hot") != null) {
            query.is("hot", params.get("hot"));
        }

        if (StringUtils.isNotBlank((String) params.get("production"))) {
            query.is("production", params.get("production"));
        }

        if (params.get("discount") != null) {
            Integer discount = (Integer) params.get("discount");
            if (discount.equals(0)) {
                query.greaterThan("discount", discount);
            } else {
                query.is("discount", discount);
            }
        }

        if (params.get("time") != null) {
            BuguQuery<Goods> query1 = query();
            BuguQuery<Goods> query2 = query();
            query1.lessThanEquals("startTime", params.get("time"));
            query1.greaterThanEquals("endTime", params.get("time"));
            query2.is("startTime", "");
            query.or(query1, query2);
        }

        if (params.get("label") != null) {
            query.in("labels", LabelDao.dao.findOne("name", params.get("label")));
        }

        if (params.get("target") != null) {
            query.in("target", TargetDao.dao.findOne("name", params.get("target")));
        }

        if (params.get("key") != null) {
            BuguQuery<Goods> query1 = query();
            BuguQuery<Goods> query2 = query();
            Pattern pattern = Pattern.compile("^.*" + params.get("key") + ".*$", Pattern.MULTILINE);
            query1.regex("name", pattern.pattern());
            query2.regex("caption", pattern.pattern());
            query.or(query1, query2);
        }

        if (params.get("order") != null) {
            query.sort("{" + params.get("order") + ": " + ("on".equals(params.get("desc")) ? "-1" : "1") + "}");
        } else {
            query.sort("{id: -1}");
        }

    }

    public Page<Goods> findBy(Map<String, Object> params) {

        BuguQuery<Goods> query = query();
        execQuery(query, params);

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }
        query.pageNumber(pageNumber);

        Integer pageSize = 10;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }
        query.pageSize(pageSize);


        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / pageSize);
        List<Goods> goodsList = query.results();

        return new Page<Goods>(goodsList, pageNumber, pageSize, totalPage, totalRow);
    }
}
