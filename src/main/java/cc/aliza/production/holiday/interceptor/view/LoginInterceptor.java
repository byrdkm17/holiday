package cc.aliza.production.holiday.interceptor.view;


import cc.aliza.production.holiday.commons.BusinessException;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Jing on 14-1-27.
 */
public class LoginInterceptor implements Interceptor {
    @Override
    public void intercept(ActionInvocation ai) {
        Controller controller = ai.getController();
        String id = controller.getSessionAttr("member");

        try {
            // 首次访问
            if (StringUtils.isBlank(id)) {
                throw new BusinessException();
            }
            ai.invoke();
        } catch (BusinessException e) {
            if (StringUtils.isNotBlank(ai.getController().getPara())) {
                controller.redirect(String.format("/login?redirect=%s/%s", ai.getActionKey(), ai.getController().getPara()));
            } else {
                controller.redirect(String.format("/login?redirect=%s", ai.getActionKey()));
            }
        }
    }
}
