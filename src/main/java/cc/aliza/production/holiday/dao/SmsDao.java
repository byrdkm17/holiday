package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Sms;
import com.bugull.mongo.BuguDao;

/**
 * Created by Jing on 14-1-27.
 */
public class SmsDao extends BuguDao<Sms> {

    public static SmsDao dao = new SmsDao();

    public SmsDao() {
        super(Sms.class);
    }

}
