package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.EnsureIndex;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;
import com.bugull.mongo.annotations.RefList;

import java.util.Date;
import java.util.List;

/**
 * Created by Jing on 14-2-15.
 */
@Entity
@EnsureIndex("{username:1, unique:true}")
public class Member extends SimpleEntity {

    // 用户组
    @Ref
    private Group group;

    // 最后一次登录时间
    private Date lastLoginTime;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 状态
    private Integer status;

    // 用户头像
    @Ref
    private Image header;

    // 所在城市
    private String city;

    // 真实姓名
    private String realName;

    // 性别：0 位置，1 男性，2 女性
    private Integer sex;

    // 邮箱
    private String email;

    // 邮箱绑定
    private Integer bindEmail;

    // 手机
    private String mobile;

    // 手机绑定
    private Integer bindMobile;

    // 证件类型：1 身份证
    private Integer cardType;

    // 证件号码
    private String cardNo;

    // 生日
    private String birthday;

    @RefList
    private List<Goods> collect;

    public List<Goods> getCollect() {
        return collect;
    }

    public void setCollect(List<Goods> collect) {
        this.collect = collect;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Image getHeader() {
        return header;
    }

    public void setHeader(Image header) {
        this.header = header;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getBindEmail() {
        return bindEmail;
    }

    public void setBindEmail(Integer bindEmail) {
        this.bindEmail = bindEmail;
    }

    public Integer getBindMobile() {
        return bindMobile;
    }

    public void setBindMobile(Integer bindMobile) {
        this.bindMobile = bindMobile;
    }
}
