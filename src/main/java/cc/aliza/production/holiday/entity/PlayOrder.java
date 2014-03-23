package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.RefList;

import java.util.List;

/**
 * Created by byrdkm17@gmail.com on 2014-03-22.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
@Entity
public class PlayOrder extends SimpleEntity {

    private String name;
    private String tel;
    @RefList
    private List<Play> plays;

    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<Play> getPlays() {
        return plays;
    }

    public void setPlays(List<Play> plays) {
        this.plays = plays;
    }
}
