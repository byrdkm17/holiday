package cc.aliza.production.holiday.entity;

import cc.aliza.production.holiday.dao.PlayCommentDao;
import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;
import com.bugull.mongo.annotations.RefList;

import java.util.List;

/**
 * Created by byrdkm17@gmail.com on 2014-03-20.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
@Entity
public class Play extends SimpleEntity {

    // 名称
    private String name;

    // 类型
    private String type;

    // 分类
    @Ref
    private PlayCategory category;

    // 标签
    @RefList
    private List<PlayLabel> labels;

    // 图片
    @Ref
    private Image image;

    // 内容
    private String note;

    // 推荐
    private Integer like;

    // 查看
    private Integer view;

    // 想要
    private Integer want;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PlayCategory getCategory() {
        return category;
    }

    public void setCategory(PlayCategory category) {
        this.category = category;
    }

    public List<PlayLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<PlayLabel> labels) {
        this.labels = labels;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getWant() {
        return want;
    }

    public void setWant(Integer want) {
        this.want = want;
    }

    public List<PlayComment> getComments() {
        return PlayCommentDao.dao.query().is("play", this).results();
    }
}
