package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Yzm;
import com.bugull.mongo.BuguDao;

/**
 * Created by Jing on 14-1-27.
 */
public class YzmDao extends BuguDao<Yzm> {

    public static YzmDao dao = new YzmDao();

    public YzmDao() {
        super(Yzm.class);
    }

}
