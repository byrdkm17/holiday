package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Supplier;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class SupplierDao extends BuguDao<Supplier> {

    public static SupplierDao dao = new SupplierDao();

    public SupplierDao() {
        super(Supplier.class);
    }

    public Page<Supplier> findBy(Map<String, Object> params) {

        BuguQuery<Supplier> query = query();

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }

        Integer pageSize = 10;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Supplier> supplierList = query.pageNumber(pageNumber).pageSize(pageSize).results();

        return new Page<Supplier>(supplierList, pageNumber, pageSize, totalPage, totalRow);
    }
}
