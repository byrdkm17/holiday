package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.PlayComment;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class PlayCommentDao extends BuguDao<PlayComment> {

    public static PlayCommentDao dao = new PlayCommentDao();

    public PlayCommentDao() {
        super(PlayComment.class);
    }

    public Page<PlayComment> findBy(Map<String, Object> params) {

        BuguQuery<PlayComment> query = query();

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
        List<PlayComment> labelList = query.results();

        return new Page<PlayComment>(labelList, pageNumber, pageSize, totalPage, totalRow);
    }
}
