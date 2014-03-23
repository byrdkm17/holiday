package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.CustomDao;
import cc.aliza.production.holiday.dao.ImageDao;
import cc.aliza.production.holiday.dao.RecordDao;
import cc.aliza.production.holiday.entity.Custom;
import cc.aliza.production.holiday.entity.Image;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class CustomController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNumber", getParaToInt("pageNumber"));
        setAttr("customPage", CustomDao.dao.findBy(params));
        render("/manage/custom/index.html");
    }

    public void record() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNumber", getParaToInt("pageNumber"));
        setAttr("recordPage", RecordDao.dao.findBy(params));
        render("/manage/custom/record.html");
    }

    public void add() {
        render("/manage/custom/add.html");
    }

    public void edit() {
        setAttr("custom", CustomDao.dao.findOne(getPara()));
        render("/manage/custom/add.html");
    }

    public void remove() {
        CustomDao.dao.remove(getPara());
        redirect("/manage/custom");
    }

    @Before(POST.class)
    public void note() {
        if (StringUtils.isNotBlank(getPara("id"))) {
            RecordDao.dao.set(getPara("id"), "note", getPara("note"));
        }
        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void save() {

        Custom custom = new Custom();
        if (StringUtils.isNotBlank(getPara("id"))) {
            custom.setId(getPara("id"));
        }
        custom.setType(getPara("type"));
        custom.setTitle(getPara("title"));
        custom.setInfo(getPara("info"));
        if (StringUtils.isNotBlank(getPara("image"))) {
            Image image = ImageDao.dao.findOne(getPara("image"));
            custom.setImage(image);
        }
        custom.setTime(getPara("time"));
        custom.setPeople(getPara("people"));
        custom.setAddress(getPara("address"));
        custom.setPrice(getPara("price"));
        custom.setService(getPara("service"));
        custom.setContent(getPara("content"));
        custom.setLike(getPara("like"));

        CustomDao.dao.save(custom);

        renderJson(Result.exec());
    }

}
