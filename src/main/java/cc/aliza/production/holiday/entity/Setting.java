package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;

/**
 * Created by Jing on 14-1-27.
 */
@Entity
public class Setting extends SimpleEntity {

    // 配置项名称
    private String name;

    // 配置项描述
    private String caption;

    // 配置项 key
    private String key;

    // 配置项 值
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Double getValueToFloat() {
        try {
            return Double.valueOf(this.value.toString());
        } catch (Exception e) {
            return 0.0;
        }
    }
}
