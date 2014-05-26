package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Arg;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class ArgDao extends BuguDao<Arg> {

    public static ArgDao dao = new ArgDao();

    public ArgDao() {
        super(Arg.class);
    }

    public Page<Arg> findBy(Map<String, Object> params) {

        BuguQuery<Arg> query = query();

        if (params.get("production") != null) {
            query.is("production", params.get("production"));

        }

        int totalRow = (int) query.count();
        List<Arg> argList = query.results();

        return new Page<Arg>(argList, 1, totalRow, 1, totalRow);
    }
}
