package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Record;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by byrdkm17@gmail.com on 2014-03-17.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
public class RecordDao extends BuguDao<Record> {

    public static RecordDao dao = new RecordDao();

    public RecordDao() {
        super(Record.class);
    }

    public Page<Record> findBy(Map<String, Object> params) {

        BuguQuery<Record> query = query();

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
        List<Record> recordList = query.results();

        return new Page<Record>(recordList, pageNumber, pageSize, totalPage, totalRow);
    }
}
