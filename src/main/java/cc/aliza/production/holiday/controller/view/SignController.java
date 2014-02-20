package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.commons.BusinessException;
import cc.aliza.production.holiday.commons.Encrypt;
import cc.aliza.production.holiday.dao.GroupDao;
import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.entity.Group;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.ext.render.CaptchaRender;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by Jing on 14-1-27.
 */
@Before(DataInterceptor.class)
public class SignController extends Controller {

    private static final String RANDOM_CODE_KEY = "sign";


    public void index() {
        setAttr("error", getSessionAttr("error"));
        setAttr("username", getSessionAttr("username"));
        removeSessionAttr("error");
        removeSessionAttr("username");

        render("/view/user/sign.html");
    }

    @Before(POST.class)
    public void doSign() {
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

            Member member = MemberDao.dao.query().is("username", username).result();

            if (member == null) {
                member = new Member();
                member.setUsername(username);
                member.setPassword(Encrypt.sha1(password));
                member.setStatus(1);
                Group group = GroupDao.dao.findOne("code", "member");

                member.setGroup(group);

                member.setLastLoginTime(new Date());
                MemberDao.dao.save(member);

                getSession().setAttribute("member", member.getId());

                redirect("/");
            } else {
                throw new BusinessException("用户名已存在");
            }


        } catch (BusinessException e) {
            setSessionAttr("username", username);
            setSessionAttr("error", e.getMessage());
            redirect("/sign");
        }
    }
}
