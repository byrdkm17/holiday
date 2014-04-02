package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;

import java.util.Date;

/**
 * Created by Jing on 14-1-28.
 */
@Entity
public class Comment extends SimpleEntity {

    // 所属商品
    @Ref
    private Goods goods;

    // 评价用户
    @Ref(cascade = "R")
    private Member member;

    private String phone;

    // 评价内容
    private String content;

    // 回复
    @Ref(cascade = "R")
    private Comment reply;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment getReply() {
        return reply;
    }

    public void setReply(Comment reply) {
        this.reply = reply;
    }

    public Date getTime() {
        return new Date(getTimestamp());
    }
}
