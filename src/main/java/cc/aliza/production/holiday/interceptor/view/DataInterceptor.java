package cc.aliza.production.holiday.interceptor.view;

import cc.aliza.production.holiday.dao.LabelDao;
import cc.aliza.production.holiday.dao.MemberDao;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
public class DataInterceptor implements Interceptor {
    @Override
    public void intercept(ActionInvocation ai) {
        Controller controller = ai.getController();

        String id = controller.getSessionAttr("member");

        if (StringUtils.isNotBlank(id)) {
            controller.setAttr("member", MemberDao.dao.findOne(id));
        }

        ai.invoke();

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("production", "line");
        controller.setAttr("lineLabelPage", LabelDao.dao.findBy(params));

        params.clear();
        params.put("other", true);
        controller.setAttr("otherLabelPage", LabelDao.dao.findBy(params));
    }
}
