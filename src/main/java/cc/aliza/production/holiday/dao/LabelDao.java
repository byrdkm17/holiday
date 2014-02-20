package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Label;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class LabelDao extends BuguDao<Label> {

    public static LabelDao dao = new LabelDao();

    public LabelDao() {
        super(Label.class);
    }

    public Page<Label> findBy(Map<String, Object> params) {

        BuguQuery<Label> query = query();

        if (params.get("production") != null) {
            query.is("production", params.get("production"));
        }

        if (params.get("other") != null) {
            query.in("production", "hotel", "car", "ticket");
        }

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }

        Integer pageSize = 10;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }

        query.sort("{id: -1}");

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Label> labelList = query.results();

        return new Page<Label>(labelList, pageNumber, pageSize, totalPage, totalRow);
    }
}
