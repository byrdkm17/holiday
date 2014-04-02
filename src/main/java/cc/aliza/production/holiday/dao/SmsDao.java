package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Sms;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class SmsDao extends BuguDao<Sms> {

    public static SmsDao dao = new SmsDao();

    public SmsDao() {
        super(Sms.class);
    }

    public Page<Sms> findBy(Map<String, Object> params) {

        BuguQuery<Sms> query = query();

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

        query.sort("{id: -1}");

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Sms> smsList = query.results();

        return new Page<Sms>(smsList, pageNumber, pageSize, totalPage, totalRow);
    }

}
