package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.HelpDao;
import cc.aliza.production.holiday.entity.Help;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by byrdkm17@gmail.com on 2014-03-24.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
@Before(DataInterceptor.class)
public class HelpController extends Controller {

    public void index() {
        String id = getPara();
        Help help = HelpDao.dao.findOne(id);
        if (help == null) {
            redirect("/help");
        } else {
            setAttr("help", help);
            render("/view/help.html");
        }
    }

}
