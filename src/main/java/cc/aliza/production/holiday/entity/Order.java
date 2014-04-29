package cc.aliza.production.holiday.entity;

import cc.aliza.production.holiday.commons.ext.HolidayEntity;
import com.bugull.mongo.annotations.EmbedList;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-2-18.
 */
@Entity
public class Order extends HolidayEntity {

    // 关联商品
    @Ref
    private Goods goods;

    // 商品快照
    private String goodsJson;

    // 状态：0 购物车，1 待支付，2 已支付，3 删除
    private Integer status;

    // 商品规格
    private String priceSet;

    // 数量
    private Integer number;

    // 单价
    private Integer amount;

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

    // 开始日期
    private String beginDate;

    // 结束日期
    private String endDate;

    // 交易记录
    @EmbedList
    private List<Map<String, String>> trades;

    public List<Map<String, String>> getTrades() {
        return trades;
    }

    public void setTrades(List<Map<String, String>> trades) {
        this.trades = trades;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
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

    public List<Traveler> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<Traveler> travelers) {
        this.travelers = travelers;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(String priceSet) {
        this.priceSet = priceSet;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsJson() {
        return goodsJson;
    }

    public void setGoodsJson(String goodsJson) {
        this.goodsJson = goodsJson;
    }

    public Goods getGoodsObject() {
        return new Gson().fromJson(this.goodsJson, Goods.class);
    }

    public Map getPriceSetObject() {
        return new Gson().fromJson(this.priceSet, Map.class);
    }

    public String getNo() {
        return String.valueOf(getTimestamp());
    }

    public Date getCreateTime() {
        return new Date(getTimestamp());
    }
}
