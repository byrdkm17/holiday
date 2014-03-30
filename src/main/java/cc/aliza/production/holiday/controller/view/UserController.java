package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.commons.Encrypt;
import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.*;
import cc.aliza.production.holiday.entity.Goods;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.entity.Sms;
import cc.aliza.production.holiday.entity.Yzm;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import cc.aliza.production.holiday.interceptor.view.LoginInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by Jing on 14-2-8.
 */
@Before({LoginInterceptor.class, DataInterceptor.class})
public class UserController extends Controller {

    public void order() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("member", getAttr("member"));
        params.put("pageNumber", getAttr("pageNumber"));
        params.put("pageSize", 5);
        params.put("orderKey", getPara("orderKey"));
        params.put("member", getAttr("member"));
        setAttr("orderPage", OrderDao.dao.findBy(params));
        params.clear();
        params.put("hot", 1);
        params.put("status", 1);
        setAttr("goodsPage", GoodsDao.dao.findBy(params));
        render("/view/user/order.html");
    }

    public void collect() {
        Member member = getAttr("member");
        BuguMapper.fetchCascade(member, "collect");
        List<Goods> collect = member.getCollect();
        List<Goods> nc = new ArrayList<Goods>();
        if (getPara("search") != null) {
            for (Goods goods : collect) {
                if (StringUtils.indexOf(goods.getName(), getPara("search")) > -1) {
                    nc.add(goods);
                    continue;
                }
                if (StringUtils.indexOf(goods.getCaption(), getPara("search")) > -1) {
                    nc.add(goods);
                }
            }
        } else {
            nc = collect;
        }

        setAttr("collect", nc);
        render("/view/user/collect.html");
    }

    public void info() {
        render("/view/user/info.html");
    }

    public void safe() {
        render("/view/user/safe.html");
    }

    public void edit() {
        setAttr("error", getSessionAttr("error"));
        removeSessionAttr("error");
        setAttr("type", getPara(0));
        render("/view/user/edit.html");
    }

    public void message() {
        render("/view/user/message.html");
    }

    public void address() {
        render("/view/user/address.html");
    }

    @Before(POST.class)
    public void saveInfo() {

        Member member = getAttr("member");

        String id = getPara("headerID");
        if (StringUtils.isNotBlank(id)) {
            member.setHeader(ImageDao.dao.findOne(id));
        }

        member.setRealName(getPara("realName"));
        member.setSex(getParaToInt("sex"));
        member.setEmail(getPara("email"));
        member.setMobile(getPara("mobile"));
        member.setCardType(getParaToInt("cardType"));
        member.setCardNo(getPara("cardNo"));
        member.setBirthday(getPara("birthday"));

        MemberDao.dao.save(member);

        redirect("/user/info");
    }

    @Before(POST.class)
    public void upPassword() {
        Member member = getAttr("member");

        String password = getPara("password");
        if (Encrypt.sha1(password).equals(member.getPassword())) {
            String newPassword = getPara("newPassword");
            MemberDao.dao.set(member, "password", Encrypt.sha1(newPassword));
            redirect("/user/safe");
        } else {
            setSessionAttr("error", "原密码错误");
            redirect("/user/edit/password");
        }
    }

    @Before(POST.class)
    public void sendYzm() {
        String mobile = getPara("mobile");

        String yzm = getYZM();
        Yzm y = new Yzm();
        y.setCode(yzm);
        y.setMobile(mobile);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 3);
        y.setExpire(calendar.getTime());
        YzmDao.dao.save(y);

        Sms sms = new Sms();
        sms.setMobile(mobile);
        sms.setContent(String.format(Sms.template_yzm, yzm));
        sms.setStatus(0);
        SmsDao.dao.save(sms);

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void yz() {
        String mobile = getPara("mobile");
        String yzm = getPara("yzm");
        String bind = getPara("bind", "0");

        Yzm y = YzmDao.dao.query().is("mobile", mobile).is("code", yzm).greaterThan("expire", new Date()).result();
        if (y != null) {
            Member member = getAttr("member");
            MemberDao.dao.set(member, "mobile", mobile);
            MemberDao.dao.set(member, "bindMobile", 1);
            renderJson(Result.exec());
        } else {
            renderJson(Result.exec("验证码错误或已过期"));
        }
    }

    private String getYZM() {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < 6; i++)
            result = result * 10 + array[i];
        return String.valueOf(result);
    }
}
