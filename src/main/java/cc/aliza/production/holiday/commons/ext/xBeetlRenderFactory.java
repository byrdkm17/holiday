package cc.aliza.production.holiday.commons.ext;


import com.jfinal.kit.PathKit;
import com.jfinal.render.IMainRenderFactory;
import com.jfinal.render.Render;
import org.bee.tl.core.GroupTemplate;
import org.bee.tl.ext.WebConfig;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jing
 * Date: 13-10-19
 * Time: 下午11:57
 */
public class xBeetlRenderFactory implements IMainRenderFactory {
    public static WebConfig config = null;
    public static String viewExtension = ".html";
    public static GroupTemplate groupTemplate = null;

    static {
        try {
            config = new WebConfig();
            config.updateRootPath(PathKit.getWebRootPath());
            groupTemplate = config.createGroupTemplate();
        } catch (IOException e) {
            throw new RuntimeException("Can notload properties for beetl");
        }
    }

    @Override
    public Render getRender(String view) {
        return new xBeetlRender(groupTemplate, view);
    }

    @Override
    public String getViewExtension() {
        return viewExtension;
    }
}
