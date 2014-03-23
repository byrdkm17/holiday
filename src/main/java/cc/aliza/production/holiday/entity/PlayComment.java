package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;

import java.util.Date;

/**
 * Created by byrdkm17@gmail.com on 2014-03-21.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
@Entity
public class PlayComment extends SimpleEntity {

    private String content;

    private String mobile;

    @Ref
    private Play play;

    public Date getTime() {
        return new Date(this.getTimestamp());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }
}
