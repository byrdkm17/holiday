package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Goods;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

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

        if (params.get("production") != null) {
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

        query.sort("{id: -1}");
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
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Goods> goodsList = query.results();

        return new Page<Goods>(goodsList, pageNumber, pageSize, totalPage, totalRow);
    }
}
