package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;

/**
 * Created by byrdkm17@gmail.com on 2014-03-17.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
@Entity
public class Record extends SimpleEntity {

    private String type;

    private String mobile;

    private String note;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
