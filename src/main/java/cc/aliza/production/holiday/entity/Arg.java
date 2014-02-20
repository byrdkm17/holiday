package cc.aliza.production.holiday.entity;

import cc.aliza.production.holiday.dao.ArgDao;
import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;

import java.util.List;

/**
 * Created by Jing on 14-2-15.
 */
@Entity
public class Arg extends SimpleEntity {

    // 属性名
    private String name;

    // 所属分类
    private String production;

    // 父分类
    @Ref
    private Arg father;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public Arg getFather() {
        return father;
    }

    public void setFather(Arg father) {
        this.father = father;
    }

    public List<Arg> getChildren() {
        return ArgDao.dao.query().is("father", this).results();
    }
}
