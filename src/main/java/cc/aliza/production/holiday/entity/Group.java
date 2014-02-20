package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.EnsureIndex;
import com.bugull.mongo.annotations.Entity;

/**
 * Created by Jing on 14-1-29.
 */
@Entity
@EnsureIndex("{code:1, unique:true}")
public class Group extends SimpleEntity {

    // 组编号
    private String code;

    // 用户组类型：0 管理组，1 普通用户组
    private Integer type;

    // 用户组名称
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
