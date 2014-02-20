package cc.aliza.production.holiday.controller.manage;

import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.dao.SupplierDao;
import cc.aliza.production.holiday.entity.Supplier;
import cc.aliza.production.holiday.interceptor.manage.AuthInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-11.
 */
@Before(AuthInterceptor.class)
public class SupplierController extends Controller {

    public void index() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("pageNumber", getParaToInt("pageNumber"));

        setAttr("supplierPage", SupplierDao.dao.findBy(params));
        render("/manage/base/supplier/index.html");
    }

    @Before(POST.class)
    public void save() {
        String name = getPara("name");
        String id = getPara("id");

        if (StringUtils.isNotBlank(id)) {
            SupplierDao.dao.set(id, "name", name);
        } else {
            Supplier supplier = new Supplier();
            supplier.setName(name);
            SupplierDao.dao.save(supplier);
        }

        renderJson(Result.exec());
    }

    @Before(POST.class)
    public void remove() {
        SupplierDao.dao.remove(getPara(0));
        renderJson(Result.exec());
    }
}
