package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.PlayCategoryDao;
import cc.aliza.production.holiday.dao.PlayCommentDao;
import cc.aliza.production.holiday.dao.PlayDao;
import cc.aliza.production.holiday.dao.PlayOrderDao;
import cc.aliza.production.holiday.entity.Member;
import cc.aliza.production.holiday.entity.Play;
import cc.aliza.production.holiday.entity.PlayComment;
import cc.aliza.production.holiday.entity.PlayOrder;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by byrdkm17@gmail.com on 2014-03-16.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
@Before(DataInterceptor.class)
public class PlayController extends Controller {

    public void eat() {
        String type = "eat";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("category", getPara(0));
        Page<Play> playPage = PlayDao.dao.findBy(params);
        BuguMapper.fetchCascade(playPage.getList(), "labels", "category");

        params.clear();
        params.put("type", type);
        params.put("pageSize", 999);
        setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));

        setAttr("type", type);
        try {
            setAttr("category", URLDecoder.decode(getPara(0), "UTF-8"));
        } catch (Exception e) {
            System.out.print(e);
        }
        setAttr("playPage", playPage);
        render("/view/play/rimCate.html");
    }

    public void go() {
        String type = "go";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("category", getPara(0));
        Page<Play> playPage = PlayDao.dao.findBy(params);
        BuguMapper.fetchCascade(playPage.getList(), "labels", "category");

        params.clear();
        params.put("type", type);
        params.put("pageSize", 999);
        setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));

        setAttr("type", type);
        try {
            setAttr("category", URLDecoder.decode(getPara(0), "UTF-8"));
        } catch (Exception e) {
            System.out.print(e);
        }
        setAttr("playPage", playPage);
        render("/view/play/rimCate.html");
    }

    public void buy() {
        String type = "buy";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("category", getPara(0));
        Page<Play> playPage = PlayDao.dao.findBy(params);
        BuguMapper.fetchCascade(playPage.getList(), "labels", "category");

        params.clear();
        params.put("type", type);
        params.put("pageSize", 999);
        setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));

        setAttr("type", type);
        try {
            setAttr("category", URLDecoder.decode(getPara(0), "UTF-8"));
        } catch (Exception e) {
            System.out.print(e);
        }
        setAttr("playPage", playPage);
        render("/view/play/rimCate.html");
    }

    public void like() {
        String type = "like";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("category", getPara(0));
        Page<Play> playPage = PlayDao.dao.findBy(params);
        BuguMapper.fetchCascade(playPage.getList(), "labels", "category");

        params.clear();
        params.put("type", type);
        params.put("pageSize", 999);
        setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));

        setAttr("type", type);
        try {
            setAttr("category", URLDecoder.decode(getPara(0), "UTF-8"));
        } catch (Exception e) {
            System.out.print(e);
        }
        setAttr("playPage", playPage);
        render("/view/play/rimCate.html");
    }

    public void info() {
        String id = getPara(0);
        PlayDao.dao.inc(id, "view", 1);

        Play play = PlayDao.dao.findOne(id);
        BuguMapper.fetchCascade(play, "category", "labels");
        setAttr("play", play);
        setAttr("type", play.getType());
        render("/view/play/rimContent.html");
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

    @Before(POST.class)
    public void order() {
        String name = getPara("name");
        String tel = getPara("tel");
        String idss = getPara("ids");

        String[] ids = StringUtils.split(idss, ",");
        List<Play> plays = new ArrayList<Play>();
        for (String id : ids) {
            Play play = PlayDao.dao.findOne(id);
            PlayDao.dao.inc(play, "want", 1);
            plays.add(play);
        }

        PlayOrder order = new PlayOrder();
        order.setName(name);
        order.setTel(tel);
        order.setPlays(plays);
        PlayOrderDao.dao.save(order);

        renderJson(Result.exec());
    }
}
