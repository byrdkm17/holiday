package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.*;
import cc.aliza.production.holiday.entity.*;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class PlayController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("type", getPara("type"));
        params.put("pageNumber", getParaToInt("pageNumber"));
        setAttr("playPage", PlayDao.dao.findBy(params));

        setAttr("type", getPara("type"));
        render("/manage/play/list/index.html");
    }

    public void order() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("pageNumber", getParaToInt("pageNumber"));
        setAttr("orderPage", PlayOrderDao.dao.findBy(params));

        render("/manage/play/order/index.html");
    }

    public void orderInfo() {
        PlayOrder order = PlayOrderDao.dao.findOne(getPara(0));
        BuguMapper.fetchCascade(order, "plays");
        setAttr("order", order);
        render("/manage/play/info/index.html");
    }

    public void add() {
        setAttr("type", getPara(0));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageSize", 999);
        params.put("type", getPara(0));
        setAttr("labelPage", PlayLabelDao.dao.findBy(params));

        params.clear();
        params.put("pageSize", 999);
        params.put("type", getPara(0));
        setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));
        render("/manage/play/add/index.html");
    }

    public void edit() {
        String id = getPara(0);

        Play play = PlayDao.dao.findOne(id);
        BuguMapper.fetchCascade(play, "category", "labels");

        setAttr("play", play);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageSize", 999);
        params.put("type", play.getType());
        setAttr("categoryPage", PlayCategoryDao.dao.findBy(params));
        setAttr("labelPage", PlayLabelDao.dao.findBy(params));

        render("/manage/play/add/index.html");
    }

    public void category() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", "eat");
        params.put("pageSize", 999);
        setAttr("eatPage", PlayCategoryDao.dao.findBy(params));

        params.clear();
        params.put("type", "go");
        params.put("pageSize", 999);
        setAttr("goPage", PlayCategoryDao.dao.findBy(params));

        params.clear();
        params.put("type", "buy");
        params.put("pageSize", 999);
        setAttr("buyPage", PlayCategoryDao.dao.findBy(params));

        params.clear();
        params.put("type", "like");
        params.put("pageSize", 999);
        setAttr("likePage", PlayCategoryDao.dao.findBy(params));

        render("/manage/play/category/index.html");
    }

    public void label() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", "eat");
        params.put("pageSize", 999);
        setAttr("eatPage", PlayLabelDao.dao.findBy(params));

        params.clear();
        params.put("type", "go");
        params.put("pageSize", 999);
        setAttr("goPage", PlayLabelDao.dao.findBy(params));

        params.clear();
        params.put("type", "buy");
        params.put("pageSize", 999);
        setAttr("buyPage", PlayLabelDao.dao.findBy(params));

        params.clear();
        params.put("type", "like");
        params.put("pageSize", 999);
        setAttr("likePage", PlayLabelDao.dao.findBy(params));

        render("/manage/play/label/index.html");
    }

    public void comment() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNumber", getPara("pageNumber"));
        Page<PlayComment> commentPage = PlayCommentDao.dao.findBy(params);
        BuguMapper.fetchCascade(commentPage.getList(), "play");
        setAttr("commentPage", commentPage);
        render("/manage/play/comment/index.html");
    }

    public void delComment() {
        PlayCommentDao.dao.remove(getPara(0));
        redirect("/manage/play/comment");
    }

    @Before(POST.class)
    public void saveNote() {
        if (StringUtils.isNotBlank(getPara("id"))) {
            PlayOrderDao.dao.set(getPara("id"), "note", getPara("note"));
        }
        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void save() {
        Play play = new Play();
        String playID = getPara("id");
        if (StringUtils.isNotBlank(playID)) {
            play.setId(playID);
        }

        String type = getPara("type");
        play.setType(type);

        String label = getPara("label");
        String[] labels = StringUtils.split(label, ",");
        List<PlayLabel> labelList = new ArrayList<PlayLabel>();
        for (String id : labels) {
            PlayLabel a = PlayLabelDao.dao.findOne(id);
            labelList.add(a);
        }
        play.setLabels(labelList);

        String name = getPara("name");
        play.setName(name);

        Integer like = getParaToInt("like");
        play.setLike(like);

        String category = getPara("category");
        PlayCategory s = PlayCategoryDao.dao.findOne(category);
        play.setCategory(s);

        String info = getPara("info");
        play.setInfo(info);

        String note = getPara("note");
        play.setNote(note);

        Image image = ImageDao.dao.findOne(getPara("image"));
        play.setImage(image);

        PlayDao.dao.save(play);

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void categorySave() {
        PlayCategory category = new PlayCategory();
        String categoryID = getPara("id");
        if (StringUtils.isNotBlank(categoryID)) {
            category.setId(categoryID);
        }

        String type = getPara("type");
        category.setType(type);

        String name = getPara("name");
        category.setName(name);

        PlayCategoryDao.dao.save(category);
        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void categoryRemove() {
        String id = getPara("id");

        PlayCategoryDao.dao.remove(id);

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void labelSave() {
        PlayLabel label = new PlayLabel();
        String labelID = getPara("id");
        if (StringUtils.isNotBlank(labelID)) {
            label.setId(labelID);
        }

        String type = getPara("type");
        label.setType(type);

        String name = getPara("name");
        label.setName(name);

        PlayLabelDao.dao.save(label);
        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void labelRemove() {
        String id = getPara("id");

        PlayLabelDao.dao.remove(id);

        renderJson(Result.exec());
    }
}
