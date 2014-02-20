package cc.aliza.production.holiday.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.render.CaptchaRender;

/**
 * Created by Jing on 14-1-28.
 */
public class CaptchaController extends Controller {

    public void index() {
        render(new CaptchaRender(getPara("randomCodeKey")));
    }
}
