package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Order;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Jing on 14-1-27.
 */
public class OrderDao extends BuguDao<Order> {

    public static OrderDao dao = new OrderDao();

    public OrderDao() {
        super(Order.class);
    }

    public Page<Order> findBy(Map<String, Object> params) {

        BuguQuery<Order> query = query();

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

        if (params.get("member") != null) {
            query.is("member", params.get("member"));
        }
        if (params.get("status") != null) {
            query.is("status", params.get("status"));
        } else {
            if ("all".equals(params.get("_status"))) {
                BuguQuery<Order> q1 = dao.query();
                BuguQuery<Order> q2 = dao.query();
                q1.is("status", 1);
                q2.is("status", 2);
                query.or(q1, q2);
            }
            if ("pay".equals(params.get("_status"))) {
                query.is("status", 2);
            }
            if ("unpay".equals(params.get("_status"))) {
                query.is("status", 1);
            }
            if ("remove".equals(params.get("_status"))) {
                query.is("status", -1);
            }
        }

        if (params.get("orderKey") != null && !"商品名称".equals(params.get("orderKey"))) {

            Pattern pattern = Pattern.compile("^.*" + params.get("orderKey") + ".*$", Pattern.MULTILINE);
            query.regex("goodsJson", pattern.pattern());
        }

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Order> orderList = query.results();

        return new Page<Order>(orderList, pageNumber, pageSize, totalPage, totalRow);
    }
}
