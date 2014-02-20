package cc.aliza.production.holiday.controller;

import cc.aliza.production.holiday.commons.Encrypt;
import cc.aliza.production.holiday.dao.*;
import cc.aliza.production.holiday.entity.Arg;
import cc.aliza.production.holiday.entity.Group;
import cc.aliza.production.holiday.entity.Member;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;

/**
 * Created by Jing on 14-2-15.
 */
public class SystemController extends Controller {

    private static Logger logger = Logger.getLogger(SystemController.class);

    public void init() {

        String key = getPara("key");

        if ("aliza1226".equals(key)) {
            logger.info("系统初始化 ...[开始]");

            ImageDao.dao.drop();
            LabelDao.dao.drop();
            ServiceDao.dao.drop();
            SupplierDao.dao.drop();
            GoodsDao.dao.drop();

            logger.info("创建初始用户组 ... [开始]");
            GroupDao.dao.drop();

            Group group = new Group();
            group.setName("超级管理组");
            group.setCode("superAdmin");
            group.setType(0);
            GroupDao.dao.save(group);
            Group superGroup = group;

            group = new Group();
            group.setName("管理组");
            group.setCode("admin");
            group.setType(0);
            GroupDao.dao.save(group);

            group = new Group();
            group.setName("普通用户组");
            group.setCode("member");
            group.setType(1);
            GroupDao.dao.save(group);

            logger.info("创建初始用户组 ... [完成]");

            logger.info("创建初始用户 ... [开始]");
            MemberDao.dao.drop();

            Member member = new Member();
            member.setUsername("admin");
            member.setPassword(Encrypt.sha1("admin"));
            member.setGroup(superGroup);
            member.setStatus(1);
            MemberDao.dao.save(member);

            logger.info("创建初始用户 ... [完成]");

            logger.info("创建初始属性 ... [开始]");
            ArgDao.dao.drop();

            Arg arg = new Arg();
            arg.setName("出发地");
            arg.setProduction("line");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("旅游日程");
            arg.setProduction("line");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("交通");
            arg.setProduction("line");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("住宿");
            arg.setProduction("line");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("用餐");
            arg.setProduction("line");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("导游");
            arg.setProduction("line");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("保险");
            arg.setProduction("line");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("其他");
            arg.setProduction("line");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("早餐");
            arg.setProduction("hotel");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("宽带");
            arg.setProduction("hotel");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("床型");
            arg.setProduction("hotel");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("服务设施");
            arg.setProduction("hotel");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("其他");
            arg.setProduction("hotel");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("车型");
            arg.setProduction("car");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("汽车品牌");
            arg.setProduction("car");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("其他");
            arg.setProduction("car");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("所在地");
            arg.setProduction("ticket");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("主题分类");
            arg.setProduction("ticket");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("景区评级");
            arg.setProduction("ticket");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("入园凭证");
            arg.setProduction("ticket");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("景点地址");
            arg.setProduction("ticket");
            ArgDao.dao.save(arg);

            arg = new Arg();
            arg.setName("其他");
            arg.setProduction("ticket");
            ArgDao.dao.save(arg);

            logger.info("创建初始属性 ... [完成]");

            logger.info("系统初始化 ...[完成]");
        }
        redirect("/");
    }
}
