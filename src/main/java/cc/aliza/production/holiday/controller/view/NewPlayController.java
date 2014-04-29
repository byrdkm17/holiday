package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.ADDao;
import cc.aliza.production.holiday.dao.PlayCategoryDao;
import cc.aliza.production.holiday.dao.PlayCommentDao;
import cc.aliza.production.holiday.dao.PlayDao;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.entity.Play;
import cc.aliza.production.holiday.entity.PlayCategory;
import cc.aliza.production.holiday.entity.PlayComment;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-4-19.
 */
@Before(DataInterceptor.class)
public class NewPlayController extends Controller {

    public void eat() {
        String type = "eat";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("pageSize", 999);
        Page<Play> playPage = PlayDao.dao.findBy(params);
        BuguMapper.fetchCascade(playPage.getList(), "labels", "category");

        params.clear();
        params.put("type", type);
        params.put("pageSize", 999);
        setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));

        setAttr("FOOD1", ADDao.dao.findOne("position", "FOOD11"));

        setAttr("type", type);

        setAttr("playPage", playPage);
        render("/view/food/index.html");
    }

    public void go() {
        try {
            String type = "go";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", type);
            params.put("pageSize", 999);
            Page<Play> playPage = PlayDao.dao.findBy(params);
            BuguMapper.fetchCascade(playPage.getList(), "labels", "category");

            params.clear();
            params.put("type", type);
            params.put("pageSize", 999);
            setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));

            setAttr("FOOD1", ADDao.dao.findOne("position", "FOOD21"));

            setAttr("type", type);

            setAttr("playPage", playPage);
            render("/view/food/index.html");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void buy() {
        try {
            String type = "buy";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", type);
            params.put("pageSize", 999);
            Page<Play> playPage = PlayDao.dao.findBy(params);
            BuguMapper.fetchCascade(playPage.getList(), "labels", "category");

            params.clear();
            params.put("type", type);
            params.put("pageSize", 999);
            setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));

            setAttr("FOOD1", ADDao.dao.findOne("position", "FOOD31"));

            setAttr("type", type);

            setAttr("playPage", playPage);
            render("/view/food/index.html");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void like() {
        try {
            String type = "like";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", type);
            params.put("pageSize", 999);
            Page<Play> playPage = PlayDao.dao.findBy(params);
            BuguMapper.fetchCascade(playPage.getList(), "labels", "category");

            params.clear();
            params.put("type", type);
            params.put("pageSize", 999);
            setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));

            setAttr("FOOD1", ADDao.dao.findOne("position", "FOOD41"));

            setAttr("type", type);

            setAttr("playPage", playPage);
            render("/view/food/index.html");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void info() {
        String id = getPara(0);
        PlayDao.dao.inc(id, "view", 1);

        Play play = PlayDao.dao.findOne(id);
        BuguMapper.fetchCascade(play, "category", "labels");
        setAttr("play", play);
        setAttr("type", play.getType());
        render("/view/food/contoct.html");
    }

    public void list() {
        String type = getPara(0);
        String id = getPara(1);
        Integer pageNumber = getParaToInt(2, 1);
        PlayCategory category = PlayCategoryDao.dao.findOne(id);
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("category", category);
        filter.put("type", type);
        filter.put("pageNumber", pageNumber);
        filter.put("pageSize", 4);
        setAttr("playPage", PlayDao.dao.findBy(filter));

        filter.clear();
        filter.put("type", type);
        filter.put("pageSize", 999);
        setAttr("categoryPage", PlayCategoryDao.dao.findBy(filter));

        if ("eat".equals(type)) {
            setAttr("FOOD1", ADDao.dao.findOne("position", "FOOD11"));
        }
        if ("go".equals(type)) {
            setAttr("FOOD1", ADDao.dao.findOne("position", "FOOD21"));
        }
        if ("buy".equals(type)) {
            setAttr("FOOD1", ADDao.dao.findOne("position", "FOOD31"));
        }
        if ("like".equals(type)) {
            setAttr("FOOD1", ADDao.dao.findOne("position", "FOOD41"));
        }


        setAttr("curCategory", category);

        setAttr("type", type);
        render("/view/food/list.html");
    }

    @Before(POST.class)
    public void commentSave() {
        String id = getPara("play");
        String mobile = getPara("mobile");
        String content = getPara("content");

        PlayComment comment = new PlayComment();
        comment.setContent(content);
        comment.setMobile(mobile);
        comment.setMember((Member) getAttr("member"));
        comment.setPlay(PlayDao.dao.findOne(id));

        PlayCommentDao.dao.save(comment);

        renderJson(Result.exec());
    }
}
