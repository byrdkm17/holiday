package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.PlayLabel;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class PlayLabelDao extends BuguDao<PlayLabel> {

    public static PlayLabelDao dao = new PlayLabelDao();

    public PlayLabelDao() {
        super(PlayLabel.class);
    }

    public Page<PlayLabel> findBy(Map<String, Object> params) {

        BuguQuery<PlayLabel> query = query();

        if (params.get("type") != null) {
            query.is("type", params.get("type"));
        }

        Integer pageNumber = 1;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }

        Integer pageSize = 10;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }

        query.sort("{id: -1}");

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<PlayLabel> labelList = query.results();

        return new Page<PlayLabel>(labelList, pageNumber, pageSize, totalPage, totalRow);
    }
}
