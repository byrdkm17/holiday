package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class OrderController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("pageNumber", getParaToInt("pageNumber"));
        params.put("status", getParaToInt("status"));

        String id = getPara("member");
        if (StringUtils.isNotBlank(id)) {
            Member member = MemberDao.dao.findOne(id);
            params.put("member", member);
            setAttr("member", member);
        }
        setAttr("status", getParaToInt("status"));
        setAttr("orderPage", OrderDao.dao.findBy(params));
        render("/manage/order/index.html");
    }

    public void info() {
        setAttr("order", OrderDao.dao.findOne(getPara(0)));
        render("/manage/order/info.html");
    }

}
