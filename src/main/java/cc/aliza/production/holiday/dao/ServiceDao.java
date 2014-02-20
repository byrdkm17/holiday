package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Service;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class ServiceDao extends BuguDao<Service> {

    public static ServiceDao dao = new ServiceDao();

    public ServiceDao() {
        super(Service.class);
    }

    public Page<Service> findBy(Map<String, Object> params) {

        BuguQuery<Service> query = query();

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }

        Integer pageSize = 10;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }

        if (params.get("production") != null) {
            query.is("production", params.get("production"));
        }

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Service> serviceList = query.pageNumber(pageNumber).pageSize(pageSize).results();

        return new Page<Service>(serviceList, pageNumber, pageSize, totalPage, totalRow);
    }
}
