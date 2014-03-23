package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.PlayOrder;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-29.
 */
public class PlayOrderDao extends BuguDao<PlayOrder> {

    public static PlayOrderDao dao = new PlayOrderDao();

    public PlayOrderDao() {
        super(PlayOrder.class);
    }

    private void execQuery(BuguQuery<PlayOrder> query, Map<String, Object> params) {

        if (params.get("type") != null) {
            query.is("type", params.get("type"));
        }

        if (params.get("category") != null) {
            query.is("category", PlayCategoryDao.dao.findOne("name", params.get("category")));
        }

        if (params.get("order") != null) {
            query.sort("{" + params.get("order") + ": " + ("on".equals(params.get("desc")) ? "-1" : "1") + "}");
        } else {
            query.sort("{id: -1}");
        }

    }

    public Page<PlayOrder> findBy(Map<String, Object> params) {

        BuguQuery<PlayOrder> query = query();
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
        List<PlayOrder> playList = query.results();

        return new Page<PlayOrder>(playList, pageNumber, pageSize, totalPage, totalRow);
    }
}
