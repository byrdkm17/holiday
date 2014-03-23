package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Custom;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class CustomDao extends BuguDao<Custom> {

    public static CustomDao dao = new CustomDao();

    public CustomDao() {
        super(Custom.class);
    }

    public Page<Custom> findBy(Map<String, Object> params) {

        BuguQuery<Custom> query = query();

        if (params.get("type") != null) {
            query.is("type", params.get("type"));
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

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / pageSize);
        List<Custom> customList = query.results();

        return new Page<Custom>(customList, pageNumber, pageSize, totalPage, totalRow);
    }
}
