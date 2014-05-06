package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.commons.BusinessException;
import cc.aliza.production.holiday.commons.Encrypt;
import cc.aliza.production.holiday.dao.GroupDao;
import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.entity.Order;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.ext.render.CaptchaRender;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Jing on 14-1-27.
 */
@Before(DataInterceptor.class)
public class LoginController extends Controller {

    private static final String RANDOM_CODE_KEY = "login";

    public void index() {
        setAttr("error", getSessionAttr("error"));
        setAttr("username", getSessionAttr("username"));
        setAttr("redirect", getPara("redirect"));
        removeSessionAttr("error");
        removeSessionAttr("username");

        render("/view/user/login.html");
    }

    @Before(POST.class)
    public void doLogin() {
        String username = getPara("username");
        String password = getPara("password");
        try {

            if (!CaptchaRender.validate(this, StringUtils.upperCase(getPara("captcha")), RANDOM_CODE_KEY)) {
                throw new BusinessException("验证码错误");
            }

            if (StringUtils.isBlank(username)) {
                throw new BusinessException("用户名不能为空");
            }

            if (StringUtils.isBlank(password)) {
                throw new BusinessException("密码不能为空");
            }

            Member member = MemberDao.dao.query().is("username", username).is("password", Encrypt.sha1(password)).is("status", 1).is("group", GroupDao.dao.findOne("code", "member")).result();

            if (member == null) {
                throw new BusinessException("用户名或密码错误");
            }

            member.setLastLoginTime(new Date());

            List<Order> orders = getSessionAttr("cart");
            if (orders != null) {
                for (Order order : orders) {
                    MemberDao.dao.push(member, "cart", order);
                }
            }
            removeSessionAttr("cart");

            setSessionAttr("member", member.getId());


            redirect(getPara("redirect", "/"));
        } catch (BusinessException e) {
            setSessionAttr("username", username);
            setSessionAttr("error", e.getMessage());
            redirect("/login?redirect=" + getPara("redirect"));
        }
    }
}
