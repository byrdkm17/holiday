package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Encrypt;
import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.GroupDao;
import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.entity.Group;
import cc.aliza.production.holiday.entity.Member;
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
public class ManagerController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("groupType", 0);

        setAttr("memberPage", MemberDao.dao.findBy(params));

        render("/manage/system/manager/index.html");
    }

    @Before(POST.class)
    public void save() {
        String id = getPara("id");
        String username = getPara("username");
        String password = getPara("password");

        if (StringUtils.isNotBlank(id)) {
            Member member = MemberDao.dao.findOne(id);
            member.setUsername(username);
            member.setPassword(Encrypt.sha1(password));
            MemberDao.dao.save(member);
        } else {
            Member member = MemberDao.dao.findOne("username", username);
            if (member == null) {
                member = new Member();
                member.setStatus(1);
                member.setUsername(username);
                member.setPassword(Encrypt.sha1(password));
                Group group = GroupDao.dao.findOne("code", "superAdmin");

                member.setGroup(group);

                MemberDao.dao.save(member);
            }
        }

        renderJson(Result.exec());
    }

    public void find() {
        renderJson(Result.exec(MemberDao.dao.findOne(getPara())));
    }

}
