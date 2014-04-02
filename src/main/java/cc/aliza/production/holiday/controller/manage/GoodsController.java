package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.*;
import cc.aliza.production.holiday.entity.*;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class GoodsController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("production", getPara("production"));
        params.put("pageNumber", getParaToInt("pageNumber"));
        params.put("status", 1);
        setAttr("goodsPage", GoodsDao.dao.findBy(params));

        setAttr("production", getPara("production"));
        render("/manage/goods/list/index.html");
    }

    public void add() {
        setAttr("production", getPara(0));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageSize", 999);
        setAttr("supplierPage", SupplierDao.dao.findBy(params));

        params.clear();
        params.put("pageSize", 999);
        params.put("production", getPara(0));
        setAttr("servicePage", ServiceDao.dao.findBy(params));

        params.clear();
        params.put("production", getPara(0));
        setAttr("argPage", ArgDao.dao.findBy(params));
        setAttr("labelPage", LabelDao.dao.findBy(params));

        render("/manage/goods/add/index.html");
    }

    public void edit() {
        String id = getPara(0);

        Goods goods = GoodsDao.dao.findOne(id);
        BuguMapper.fetchCascade(goods, "args", "supplier", "labels", "services");

        setAttr("goods", goods);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageSize", 999);
        setAttr("supplierPage", SupplierDao.dao.findBy(params));

        params.clear();
        params.put("pageSize", 999);
        params.put("production", goods.getProduction());
        setAttr("servicePage", ServiceDao.dao.findBy(params));

        params.clear();
        params.put("production", goods.getProduction());
        setAttr("argPage", ArgDao.dao.findBy(params));
        setAttr("labelPage", LabelDao.dao.findBy(params));

        params.clear();
        setAttr("targetPage", TargetDao.dao.findBy(params));

        render("/manage/goods/add/index.html");
    }

    public void store() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("pageNumber", getParaToInt("pageNumber"));
        params.put("status", 0);
        setAttr("goodsPage", GoodsDao.dao.findBy(params));

        render("/manage/goods/store/index.html");
    }


    @Before(POST.class)
    public void hot() {
        String id = getPara("id");
        Integer hot = getParaToInt("hot");
        GoodsDao.dao.set(id, "hot", hot);
        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void status() {
        String id = getPara("id");
        Integer status = getParaToInt("status");
        GoodsDao.dao.set(id, "status", status);
        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void save() {
        Goods goods = new Goods();
        String goodsID = getPara("id");
        if (StringUtils.isNotBlank(goodsID)) {
            goods.setId(goodsID);
        }

        goods.setHot(getParaToInt("hot"));

        Integer receipt = getParaToInt("receipt");
        goods.setReceipt(receipt);

        String endTime = getPara("endTime");
        goods.setEndTime(endTime);

        String production = getPara("production");
        goods.setProduction(production);

        String startTime = getPara("startTime");
        goods.setStartTime(startTime);

        String arg = getPara("arg");
        String[] args = StringUtils.split(arg, ",");
        List<Arg> argList = new ArrayList<Arg>();
        for (String id : args) {
            Arg a = ArgDao.dao.findOne(id);
            argList.add(a);
        }
        goods.setArgs(argList);

        Integer saleType = getParaToInt("saleType");
        goods.setSaleType(saleType);

        String name = getPara("name");
        goods.setName(name);

        String feature = getPara("feature");
        goods.setFeature(feature);

        Integer coupon = getParaToInt("coupon");
        goods.setCoupon(coupon);

        String supplier = getPara("supplier");
        Supplier s = SupplierDao.dao.findOne(supplier);
        goods.setSupplier(s);

        String note = getPara("note");
        goods.setNote(note);

        Integer recommend = getParaToInt("recommend");
        goods.setRecommend(recommend);

        String priceGroup = getPara("priceGroup");
        goods.setPriceGroup(priceGroup);

        Integer[] payMethod = getParaValuesToInt("payMethod");
        goods.setPayMethod(payMethod);

        Integer[] paySupper = getParaValuesToInt("paySupper");
        goods.setPaySupper(paySupper);

        Integer status = getParaToInt("status");
        goods.setStatus(status);

        Integer priceType = getParaToInt("priceType");
        goods.setPriceType(priceType);

        String caption = getPara("caption");
        goods.setCaption(caption);

        String label = getPara("label");
        String[] labels = StringUtils.split(label, ",");
        List<Label> labelList = new ArrayList<Label>();
        for (String id : labels) {
            Label a = LabelDao.dao.findOne(id);
            labelList.add(a);
        }
        goods.setLabels(labelList);

        String target = getPara("target");
        String[] targets = StringUtils.split(target, ",");
        List<Target> targetList = new ArrayList<Target>();
        for (String id : targets) {
            Target a = TargetDao.dao.findOne(id);
            targetList.add(a);
        }
        goods.setTarget(targetList);

        String code = getPara("code");
        goods.setCode(code);

        String[] pic = getParaValues("pic");
        List<Image> picList = new ArrayList<Image>();
        Image master = null;
        for (String id : pic) {
            Image a = ImageDao.dao.findOne(id);
            if (master == null) {
                master = a;
            }
            picList.add(a);
        }
        goods.setPics(picList);
        goods.setMaster(master);

        String service = getPara("service");
        String[] services = StringUtils.split(service, ",");
        List<Service> serviceList = new ArrayList<Service>();
        for (String id : services) {
            Service a = ServiceDao.dao.findOne(id);
            serviceList.add(a);
        }
        goods.setServices(serviceList);

        Integer payType = getParaToInt("payType");
        goods.setPayType(payType);

        String priceSet = getPara("priceSet");
        goods.setPriceSet(priceSet);

        Integer preSale = getParaToInt("preSale");
        goods.setPreSale(preSale);

        Integer minPrice = getParaToInt("minPrice");
        goods.setMinPrice(minPrice);

        String source = getPara("source");
        goods.setSource(source);

        Integer discount = getParaToInt("discount");
        goods.setDiscount(discount);

        GoodsDao.dao.save(goods);

        renderJson(Result.exec());
    }
}
