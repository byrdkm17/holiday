package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Jing on 14-2-17.
 */
@Entity
public class AD extends SimpleEntity {

    // 广告位置
    private String position;

    // 状态
    private Integer status;

    // 广告图片
    @Ref
    private Image image;

    // 广告链接
    private String url;

    // 广告名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPosition_text() {
        if ("NAV".equals(this.position)) {
            return "导航推荐";
        }
        if ("LB".equals(this.position)) {
            return "首页轮播";
        }
        if ("HF1".equals(this.position)) {
            return "首页横幅 1";
        }
        if ("HF2".equals(this.position)) {
            return "首页横幅 2";
        }
        if ("HF3".equals(this.position)) {
            return "首页横幅 3";
        }
        if ("LINELB".equals(this.position)) {
            return "跟团游轮播";
        }
        if ("LINEBLOCK".equals(this.position)) {
            return "跟团游子广告";
        }
        if ("TICKETLB".equals(this.position)) {
            return "门票轮播";
        }
        if ("TICKETBLOCK".equals(this.position)) {
            return "门票子广告";
        }
        if ("CARLB".equals(this.position)) {
            return "自由行轮播";
        }
        if ("CARBLOCK".equals(this.position)) {
            return "自由行子广告";
        }
        if ("HOTELLB".equals(this.position)) {
            return "酒店轮播";
        }
        if ("HOTELBLOCK".equals(this.position)) {
            return "酒店子广告";
        }
        if ("JD1".equals(this.position)) {
            return "首页酒店 1";
        }
        if ("JD2".equals(this.position)) {
            return "首页酒店 2";
        }
        if ("JD3".equals(this.position)) {
            return "首页酒店 3";
        }
        if ("JD4".equals(this.position)) {
            return "首页酒店 4";
        }
        if ("FOOD11".equals(this.position)) {
            return "美食横幅";
        }
        if ("FOOD21".equals(this.position)) {
            return "美景横幅";
        }
        if ("FOOD31".equals(this.position)) {
            return "特产横幅";
        }
        if ("FOOD41".equals(this.position)) {
            return "人文横幅";
        }
        return StringUtils.EMPTY;
    }
}
