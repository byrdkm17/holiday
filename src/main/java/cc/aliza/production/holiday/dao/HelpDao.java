package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Help;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class HelpDao extends BuguDao<Help> {

    public static HelpDao dao = new HelpDao();

    public HelpDao() {
        super(Help.class);
    }

    public Page<Help> findBy(Map<String, Object> params) {

        BuguQuery<Help> query = query();

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = Integer.valueOf((String) params.get("pageNumber"));
        }
        query.pageNumber(pageNumber);

        Integer pageSize = 10;
        if (params.get("pageSize") != null) {
            pageSize = Integer.valueOf((String) params.get("pageSize"));
        }
        query.pageSize(pageSize);

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Help> helpList = query.results();

        return new Page<Help>(helpList, pageNumber, pageSize, totalPage, totalRow);
    }
}
