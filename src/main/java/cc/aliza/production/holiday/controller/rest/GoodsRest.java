package cc.aliza.production.holiday.controller.rest;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.MemberDao;
import cc.aliza.production.holiday.entity.Goods;
import cc.aliza.production.holiday.entity.Member;
import com.jfinal.core.Controller;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 14-2-1.
 */

public class GoodsRest extends Controller {

    public void collect() {
        String id = getPara(0);
        Integer collect = getParaToInt("collect");


        String memberID = getSessionAttr("member");

        if (StringUtils.isBlank(memberID)) {
            GoodsDao.dao.inc(id, "like", collect);
        } else {
            Member member = MemberDao.dao.findOne(memberID);
            Goods goods = GoodsDao.dao.findOne(id);
            List<Goods> collects = member.getCollect();
            if (collects == null) {
                MemberDao.dao.push(member, "collect", goods);
                GoodsDao.dao.inc(id, "collect", collect);
                GoodsDao.dao.inc(id, "like", collect);
            } else {
                if (member.getCollect().indexOf(goods) == -1) {
                    MemberDao.dao.push(member, "collect", goods);
                    GoodsDao.dao.inc(id, "collect", collect);
                    GoodsDao.dao.inc(id, "like", collect);
                }
            }
        }

        renderJson(Result.exec());
    }

    public void unCollect() {
        String id = getPara(0);

        String memberID = getSessionAttr("member");

        if (StringUtils.isNotBlank(memberID)) {
            Member member = MemberDao.dao.findOne(memberID);
            Goods goods = GoodsDao.dao.findOne(id);
            List<Goods> collects = member.getCollect();
            if (collects != null) {
                MemberDao.dao.pull(member, "collect", goods);
                GoodsDao.dao.inc(id, "collect", -1);
            }
        }

        redirect("/user/collect");
    }

    public void like() {
        String id = getPara(0);
        Integer like = getParaToInt("like");
        GoodsDao.dao.inc(id, "like", like);
        renderJson(Result.exec());
    }

    public void cookie() {
        String list = getPara("list");
        String[] ids = StringUtils.split(list, ",");
        List<Goods> goodsList = new ArrayList<Goods>();
        for (String id : ids) {
            if (goodsList.size() < 4) {
                goodsList.add(GoodsDao.dao.findOne(id));
            } else {
                break;
            }
        }
        renderJson(Result.exec(goodsList));
    }
}
