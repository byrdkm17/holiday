package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.ADDao;
import cc.aliza.production.holiday.dao.ImageDao;
import cc.aliza.production.holiday.entity.AD;
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
public class ADController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();
        Integer pageNumber = getParaToInt(0);
        params.put("pageNumber", pageNumber);
        setAttr("adPage", ADDao.dao.findBy(params));
        render("/manage/ad/index.html");
    }

    @Before(POST.class)
    public void save() {
        String position = getPara("position");
        String image = getPara("image");
        String id = getPara("id");
        String url = getPara("url");
        String name = getPara("name");

        if (StringUtils.isNotBlank(id)) {
            AD ad = ADDao.dao.findOne(id);
            ADDao.dao.set(id, "position", position);
            ADDao.dao.set(id, "image", ImageDao.dao.findOne(image));
            ADDao.dao.set(id, "url", url);
            ADDao.dao.set(id, "name", name);
        } else {
            AD ad = new AD();
            ad.setImage(ImageDao.dao.findOne(image));
            ad.setUrl(url);
            ad.setPosition(position);
            ad.setName(name);
            ADDao.dao.save(ad);
        }

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void remove() {
        ADDao.dao.remove(getPara(0));
        renderJson(Result.exec());
    }

}
