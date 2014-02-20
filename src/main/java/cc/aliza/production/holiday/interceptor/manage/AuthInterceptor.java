package cc.aliza.production.holiday.interceptor.manage;

import cc.aliza.production.holiday.dao.MemberDao;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Jing on 14-2-11.
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public void intercept(ActionInvocation ai) {
        Controller controller = ai.getController();

        String id = controller.getSessionAttr("manager");
        if (StringUtils.isBlank(id)) {
            if ("/manage".equals(ai.getActionKey())) {
                controller.redirect("/manage/auth");
            } else {
                controller.redirect(String.format("/manage/auth?redirect=%s/%s", ai.getActionKey(), StringUtils.defaultIfBlank(controller.getPara(), "")));
            }
        } else {
            controller.setAttr("auth", true);
            controller.setAttr("manager", MemberDao.dao.findOne(id));
            ai.invoke();
        }
    }
}
