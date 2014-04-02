package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.dao.SmsDao;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class MemberController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("groupType", 1);

        setAttr("memberPage", MemberDao.dao.findBy(params));

        render("/manage/member/list/index.html");
    }

    public void sms() {
        Map<String, Object> params = new HashMap<String, Object>();

        setAttr("smsPage", SmsDao.dao.findBy(params));

        render("/manage/member/sms/index.html");
    }
}
