package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Group;
import com.bugull.mongo.BuguDao;

/**
 * Created by Jing on 14-1-29.
 */
public class GroupDao extends BuguDao<Group> {

    public static GroupDao dao = new GroupDao();

    public GroupDao() {
        super(Group.class);
    }
}
