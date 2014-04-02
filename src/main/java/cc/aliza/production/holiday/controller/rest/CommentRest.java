package cc.aliza.production.holiday.controller.rest;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.CommentDao;
import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.entity.Comment;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;

/**
 * Created by Jing on 14-2-2.
 */
@Before(DataInterceptor.class)
public class CommentRest extends Controller {

    @Before(POST.class)
    public void save() {
        Comment comment = new Comment();
        comment.setContent(getPara("message"));
        comment.setGoods(GoodsDao.dao.findOne(getPara("goods")));
        comment.setMember((Member) getAttr("member"));
        comment.setPhone(getPara("phone"));
        CommentDao.dao.save(comment);
        renderJson(Result.exec(comment));
    }

}
