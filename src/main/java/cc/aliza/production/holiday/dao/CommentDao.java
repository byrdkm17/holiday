package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Comment;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class CommentDao extends BuguDao<Comment> {

    public static CommentDao dao = new CommentDao();

    public CommentDao() {
        super(Comment.class);
    }

    public Page<Comment> findBy(Map<String, Object> params) {

        BuguQuery<Comment> query = query();

        if (params.get("goods") != null) {
            query.is("goods", params.get("goods"));
        }

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
        List<Comment> commentList = query.results();

        return new Page<Comment>(commentList, pageNumber, pageSize, totalPage, totalRow);
    }
}
