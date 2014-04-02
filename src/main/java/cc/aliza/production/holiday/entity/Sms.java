package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;

/**
 * Created by byrdkm17@gmail.com on 2014-03-30.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
@Entity
public class Sms extends SimpleEntity {

    public static final String template_yzm = "尊敬的用户，您的动态验证码是 %s，请输入以进行身份认证。该动态密码三分钟内有效。【假日风光】";

    private String mobile;

    private String content;

    // 状态：0 未发送，1 发送成功，2 发送失败
    private Integer status;

    // 发送标记
    private String sendTag;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSendTag() {
        return sendTag;
    }

    public void setSendTag(String sendTag) {
        this.sendTag = sendTag;
    }
}
