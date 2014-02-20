package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Group;
import cc.aliza.production.holiday.entity.Member;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguMapper;
import com.bugull.mongo.BuguQuery;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-1-27.
 */
public class MemberDao extends BuguDao<Member> {

    public static MemberDao dao = new MemberDao();

    public MemberDao() {
        super(Member.class);
    }

    public Page<Member> findBy(Map<String, Object> params) {

        BuguQuery<Member> query = query();

        if (params.get("groupType") != null) {
            List<Group> groups = GroupDao.dao.query().is("type", params.get("groupType")).results();
            query = query.in("group", groups);
        }

        Integer pageNumber = 0;
        if (params.get("pageNumber") != null) {
            pageNumber = (Integer) params.get("pageNumber");
        }

        Integer pageSize = 10;
        if (params.get("pageSize") != null) {
            pageSize = (Integer) params.get("pageSize");
        }

        int totalRow = (int) query.count();
        int totalPage = (int) Math.ceil((double) totalRow / 10);
        List<Member> members = query.pageNumber(pageNumber).pageSize(pageSize).results();

        BuguMapper.fetchCascade(members, "group");

        return new Page<Member>(members, pageNumber, pageSize, totalPage, totalRow);
    }
}
