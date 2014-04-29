package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.EmbedList;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;
import com.bugull.mongo.annotations.RefList;

import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-2-18.
 */
@Entity
public class Cart extends SimpleEntity {

    @RefList
    private List<Order> orders;

    // 状态：1 待支付，2 已支付，3 删除
    private Integer status;

    // 下单用户
    @Ref
    private Member member;

    // 旅客信息
    @EmbedList
    private List<Traveler> travelers;

    // 联系人姓名
    private String contactName;

    // 联系人电话
    private String contactPhone;

    // 留言
    private String message;

    // 支付方式
    private Integer payMethod;

    // 原价
    private Integer price;

    // 折扣部分
    private Integer discount;

    // 需支付
    private Integer payPrice;

    // 交易记录
    @EmbedList
    private List<Map<String, String>> trades;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Traveler> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<Traveler> travelers) {
        this.travelers = travelers;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Integer payPrice) {
        this.payPrice = payPrice;
    }

    public List<Map<String, String>> getTrades() {
        return trades;
    }

    public void setTrades(List<Map<String, String>> trades) {
        this.trades = trades;
    }
}
