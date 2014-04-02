package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.CustomDao;
import cc.aliza.production.holiday.dao.RecordDao;
import cc.aliza.production.holiday.entity.Record;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by byrdkm17@gmail.com on 2014-03-16.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
@Before(DataInterceptor.class)
public class CustomController extends Controller {

    public void index() {
        render("/view/custom.html");
    }

    public void meeting() {
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("type", "商务会议");
        setAttr("customPage", CustomDao.dao.findBy(filter));
        render("/view/include/wh.html");
        //render("/view/custom/meeting.html");
    }

    public void travel() {
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("type", "公司旅游");
        setAttr("customPage", CustomDao.dao.findBy(filter));
        render("/view/include/wh.html");
        //render("/view/custom/travel.html");
    }

    public void info() {
        setAttr("custom", CustomDao.dao.findOne(getPara()));
        render("/view/custom/info.html");
    }

    public void record() {
        Record record = new Record();
        record.setType(getPara("type"));
        record.setMobile(getPara("mobile"));
        RecordDao.dao.save(record);
        renderJson(Result.exec());
    }
}
