package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Play;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-29.
 */
public class PlayDao extends BuguDao<Play> {

    public static PlayDao dao = new PlayDao();

    public PlayDao() {
        super(Play.class);
    }

    private void execQuery(BuguQuery<Play> query, Map<String, Object> params) {

        if (params.get("type") != null) {
            query.is("type", params.get("type"));
        }

        if (StringUtils.isNotBlank(String.valueOf(params.get("category")))) {
            query.is("category", PlayCategoryDao.dao.findOne("name", params.get("category")));
        }

        if (params.get("order") != null) {
            query.sort("{" + params.get("order") + ": " + ("on".equals(params.get("desc")) ? "-1" : "1") + "}");
        } else {
            query.sort("{id: -1}");
        }

    }

    public Page<Play> findBy(Map<String, Object> params) {

        BuguQuery<Play> query = query();
        execQuery(query, params);

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

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Play> playList = query.results();

        return new Page<Play>(playList, pageNumber, pageSize, totalPage, totalRow);
    }
}
