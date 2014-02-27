package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Target;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class TargetDao extends BuguDao<Target> {

    public static TargetDao dao = new TargetDao();

    public TargetDao() {
        super(Target.class);
    }

    public Page<Target> findBy(Map<String, Object> params) {

        BuguQuery<Target> query = query();

        query.notExistsField("father");

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }

        Integer pageSize = 999;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }

        query.sort("{id: -1}");

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Target> targetList = query.results();

        return new Page<Target>(targetList, pageNumber, pageSize, totalPage, totalRow);
    }
}
