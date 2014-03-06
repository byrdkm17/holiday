package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;
import com.bugull.mongo.annotations.RefList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-2-15.
 */
@Entity
public class Goods extends SimpleEntity {

    // 发票
    private Integer receipt;

    // 结束时间
    private String endTime;

    // 产品分类
    private String production;

    // 开始时间
    private String startTime;

    // 属性
    @RefList
    private List<Arg> args;

    // 消费类型
    private Integer saleType;

    // 商品名称
    private String name;

    // 特色
    private String feature;

    // 优惠券
    private Integer coupon;

    // 供应商
    @Ref
    private Supplier supplier;

    // 产品描述
    private String note;

    // 价格分组
    private String priceGroup;

    // 支付方式
    private Integer[] payMethod;

    // 支付支持
    private Integer[] paySupper;

    // 状态
    private Integer status;

    // 设价类型
    private Integer priceType;

    // 标题下描述
    private String caption;

    // 标签
    @RefList
    private List<Label> labels;

    // 目的地
    @RefList
    private List<Target> target;

    // 产品编码
    private String code;

    // 积分
    private String source;

    // 图片
    @RefList
    private List<Image> pics;

    // 主图
    @Ref
    private Image master;

    // 服务
    @RefList
    private List<Service> services;

    // 设价类型
    private Integer payType;

    // 票价集合
    private String priceSet;

    // 预售天
    private Integer preSale;

    // 最低价格
    private Integer minPrice;

    // 最低折扣价
    private Integer minRealPrice;

    // 热门线路
    private Integer hot;

    // 折扣
    private Integer discount;

    // 销售数
    private Integer sales;

    // 收藏
    private Integer collect;

    // 喜欢
    private Integer like;

    // 推荐指数
    private Integer recommend;

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public List<Target> getTarget() {
        return target;
    }

    public void setTarget(List<Target> target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getReceipt() {
        return receipt;
    }

    public void setReceipt(Integer receipt) {
        this.receipt = receipt;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<Arg> getArgs() {
        return args;
    }

    public void setArgs(List<Arg> args) {
        this.args = args;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public Integer[] getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer[] payMethod) {
        this.payMethod = payMethod;
    }

    public Integer[] getPaySupper() {
        return paySupper;
    }

    public void setPaySupper(Integer[] paySupper) {
        this.paySupper = paySupper;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Image> getPics() {
        return pics;
    }

    public void setPics(List<Image> pics) {
        this.pics = pics;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(String priceSet) {
        this.priceSet = priceSet;
    }

    public Integer getPreSale() {
        return preSale;
    }

    public void setPreSale(Integer preSale) {
        this.preSale = preSale;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Image getMaster() {
        return master;
    }

    public void setMaster(Image master) {
        this.master = master;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }


    public List<Map> getPriceGroupObject() {
        Type listMap = new TypeToken<List<Map>>() {
        }.getType();
        return new Gson().fromJson(this.priceGroup, listMap);
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getMinRealPrice() {
        return (this.minPrice - this.discount) < 0 ? this.minPrice : (this.minPrice - this.discount);
    }
}
