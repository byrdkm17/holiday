package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Setting;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class SettingDao extends BuguDao<Setting> {

    public static SettingDao dao = new SettingDao();

    public SettingDao() {
        super(Setting.class);
    }

    public Page<Setting> findBy(Map<String, Object> params) {

        BuguQuery<Setting> query = query();

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
        List<Setting> settings = query.pageNumber(pageNumber).pageSize(pageSize).results();

        return new Page<Setting>(settings, pageNumber, pageSize, totalPage, totalRow);
    }
}
