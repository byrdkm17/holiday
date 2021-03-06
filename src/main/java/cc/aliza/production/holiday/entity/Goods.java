package cc.aliza.production.holiday.entity;

import cc.aliza.production.holiday.dao.CommentDao;
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

    // 最低价格
    private Integer maxPrice;

    // 最低折扣价
    private Integer minRealPrice;

    // 市场价
    private Integer price;

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


    // 线路特色·描述
    private String lineTSContent1;

    // 线路特色·描述
    private String lineTSContent2;

    // 线路特色·图片
    @RefList
    private List<Image> lineTSPics;

    // 行程说明·描述
    private List<String> lineXCContent;

    // 行程说明·所在地
    private List<String> lineXCSite;

    // 行程说明·图片
    private Map<String, String> lineXCPics;

    // 行程说明·其他
    private List<Map<String, String>> lineXCOther;

    // 出发城市
    private String start;

    // 行程天数
    private Integer XC;

    // 温馨提示
    private String lineTip;

    // 预订流程
    private String lineFlow;

    // 费用说明
    private Map<String, Map<String, Object>> lineList;

    // 成人票价
    private Integer linePrice1;

    // 儿童票价
    private Integer linePrice2;

    // 市场价格
    private String SCPrice;

    public String getSCPrice() {
        return SCPrice;
    }

    public void setSCPrice(String SCPrice) {
        this.SCPrice = SCPrice;
    }

    public Integer getLinePrice1() {
        return linePrice1;
    }

    public void setLinePrice1(Integer linePrice1) {
        this.linePrice1 = linePrice1;
    }

    public Integer getLinePrice2() {
        return linePrice2;
    }

    public void setLinePrice2(Integer linePrice2) {
        this.linePrice2 = linePrice2;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

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
        if (this.maxPrice == null || this.maxPrice == 0) {
            return (this.minPrice - this.discount) < 0 ? this.minPrice : (this.minPrice - this.discount);
        } else {
            return this.maxPrice;
        }
    }

    public long getCommentsSize() {
        return CommentDao.dao.count("goods", this);
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinRealPrice(Integer minRealPrice) {
        this.minRealPrice = minRealPrice;
    }

    public String getLineTSContent1() {
        return lineTSContent1;
    }

    public void setLineTSContent1(String lineTSContent1) {
        this.lineTSContent1 = lineTSContent1;
    }

    public String getLineTSContent2() {
        return lineTSContent2;
    }

    public void setLineTSContent2(String lineTSContent2) {
        this.lineTSContent2 = lineTSContent2;
    }

    public List<Image> getLineTSPics() {
        return lineTSPics;
    }

    public void setLineTSPics(List<Image> lineTSPics) {
        this.lineTSPics = lineTSPics;
    }

    public List<String> getLineXCContent() {
        return lineXCContent;
    }

    public void setLineXCContent(List<String> lineXCContent) {
        this.lineXCContent = lineXCContent;
    }


    public Map<String, String> getLineXCPics() {
        return lineXCPics;
    }

    public void setLineXCPics(Map<String, String> lineXCPics) {
        this.lineXCPics = lineXCPics;
    }

    public List<Map<String, String>> getLineXCOther() {
        return lineXCOther;
    }

    public void setLineXCOther(List<Map<String, String>> lineXCOther) {
        this.lineXCOther = lineXCOther;
    }

    public String getLineTip() {
        return lineTip;
    }

    public void setLineTip(String lineTip) {
        this.lineTip = lineTip;
    }

    public String getLineFlow() {
        return lineFlow;
    }

    public void setLineFlow(String lineFlow) {
        this.lineFlow = lineFlow;
    }

    public Integer getXC() {
        return XC;
    }

    public void setXC(Integer XC) {
        this.XC = XC;
    }

    public Map<String, Map<String, Object>> getLineList() {
        return lineList;
    }

    public void setLineList(Map<String, Map<String, Object>> lineList) {
        this.lineList = lineList;
    }

    public List<String> getLineXCSite() {
        return lineXCSite;
    }

    public void setLineXCSite(List<String> lineXCSite) {
        this.lineXCSite = lineXCSite;
    }
}
