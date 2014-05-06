package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.AD;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class ADDao extends BuguDao<AD> {

    public static ADDao dao = new ADDao();

    public ADDao() {
        super(AD.class);
    }

    public Page<AD> findBy(Map<String, Object> params) {

        BuguQuery<AD> query = query();

        if (params.get("position") != null) {
            query.is("position", params.get("position"));
        }

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }
        query.pageNumber(pageNumber);

        Integer pageSize = 5;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }
        query.pageSize(pageSize);

        query.sort("{id: -1}");

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / pageSize);
        List<AD> adList = query.results();

        return new Page<AD>(adList, pageNumber, pageSize, totalPage, totalRow);
    }
}
