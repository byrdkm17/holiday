package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Setting;
import com.bugull.mongo.BuguDao;

/**
 * Created by Jing on 14-1-27.
 */
public class SettingDao extends BuguDao<Setting> {

    public static SettingDao dao = new SettingDao();

    public SettingDao() {
        super(Setting.class);
    }
}
