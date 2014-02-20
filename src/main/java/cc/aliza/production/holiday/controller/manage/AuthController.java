package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Encrypt;
import cc.aliza.production.holiday.dao.GroupDao;
import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.entity.Member;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Jing on 14-2-11.
 */
public class AuthController extends Controller {

    public void index() {
        setAttr("error", getSessionAttr("error"));
        setAttr("username", getSessionAttr("username"));
        setAttr("redirect", getPara("redirect"));
        removeSessionAttr("error");
        removeSessionAttr("username");

        render("/manage/login.html");
    }

    @Before(POST.class)
    public void login() {
        String username = StringUtils.lowerCase(getPara("username"));
        String password = getPara("password");
        try {
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                throw new Exception("用户名或密码不能为空");
            }
            Member member = MemberDao.dao.query().is("username", username).is("password", Encrypt.sha1(password)).in("group", GroupDao.dao.query().is("type", 0).results()).result();
            if (member == null) {
                throw new Exception("用户名或密码错误");
            }
            setSessionAttr("manager", member.getId());
            redirect(getPara("redirect", "/manage"));
        } catch (Exception e) {
            setSessionAttr("error", e.getMessage());
            setSessionAttr("username", username);
            if (StringUtils.isBlank(getPara("redirect"))) {
                redirect("/manage");
            } else {
                redirect(String.format("/manage/auth?redirect=%s", getPara("redirect")));
            }
        }
    }

    public void logout() {
        getSession().invalidate();
        redirect("/manage/auth");
    }
}