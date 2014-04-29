package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Cart;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class CartDao extends BuguDao<Cart> {

    public static CartDao dao = new CartDao();

    public CartDao() {
        super(Cart.class);
    }

    public Page<Cart> findBy(Map<String, Object> params) {

        BuguQuery<Cart> query = query();

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

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Cart> orderList = query.results();

        return new Page<Cart>(orderList, pageNumber, pageSize, totalPage, totalRow);
    }
}
