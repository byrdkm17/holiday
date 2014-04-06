package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.People;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class PeopleDao extends BuguDao<People> {

    public static PeopleDao dao = new PeopleDao();

    public PeopleDao() {
        super(People.class);
    }

    public Page<People> findBy(Map<String, Object> params) {

        BuguQuery<People> query = query();

        if (params.get("member") != null) {
            query.is("user", params.get("member"));
        }

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }
        query.pageNumber(pageNumber);

        Integer pageSize = 20;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }
        query.pageSize(pageSize);

        query.sort("{id: -1}");

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<People> adList = query.results();

        return new Page<People>(adList, pageNumber, pageSize, totalPage, totalRow);
    }
}
