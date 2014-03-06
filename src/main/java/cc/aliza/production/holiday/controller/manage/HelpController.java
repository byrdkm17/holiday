package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.HelpDao;
import cc.aliza.production.holiday.entity.Help;
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
public class HelpController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        setAttr("helpPage", HelpDao.dao.findBy(params));

        render("/manage/system/help/index.html");
    }

    public void add() {
        render("/manage/system/help/edit.html");
    }

    public void edit() {
        setAttr("help", HelpDao.dao.findOne(getPara()));
        render("/manage/system/help/edit.html");
    }

    public void delete() {
        HelpDao.dao.remove(getPara());
        redirect("/manage/system/help");
    }

    @Before(POST.class)
    public void save() {
        String id = getPara("id");
        String tag = getPara("tag");
        String title = getPara("title");
        String content = getPara("content");
        Help help;
        if (StringUtils.isNotBlank(id)) {
            help = HelpDao.dao.findOne(id);
            help.setTag(tag);
            help.setTitle(title);
            help.setContent(content);
        } else {
            help = new Help();
        }
        help.setTag(tag);
        help.setTitle(title);
        help.setContent(content);
        HelpDao.dao.save(help);

        renderJson(Result.exec());
    }

}
