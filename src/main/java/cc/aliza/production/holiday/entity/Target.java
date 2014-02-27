package cc.aliza.production.holiday.entity;

import cc.aliza.production.holiday.dao.TargetDao;
import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;

import java.util.List;

/**
 * Created by Jing on 14-2-15.
 */
@Entity
public class Target extends SimpleEntity {
    // 属性名
    private String name;

    // 父分类
    @Ref
    private Target father;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Target getFather() {
        return father;
    }

    public void setFather(Target father) {
        this.father = father;
    }

    public List<Target> getChildren() {
        return TargetDao.dao.query().is("father", this).results();
    }
}
