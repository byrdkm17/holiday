package cc.aliza.production.holiday.controller.rest;

import cc.aliza.production.holiday.commons.HolidayConstants;
import cc.aliza.production.holiday.commons.Result;
import cc.aliza.production.holiday.commons.ext.ImageRender;
import cc.aliza.production.holiday.dao.ImageDao;
import cc.aliza.production.holiday.entity.Image;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;
import com.jfinal.upload.UploadFile;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jing on 14-2-15.
 */
public class ImageRest extends Controller {
    private static final Logger logger = Logger.getLogger(ImageRest.class);

    private static final String separator = File.separator;

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    public void index() {
        String id = getPara(0);
        if (StringUtils.isBlank(id)) {
            render(new ImageRender(PathKit.getWebRootPath().concat("/resource/default.jpg")));
        } else {
            Image image = ImageDao.dao.findOne(id);
            if (image == null) {
                render(new ImageRender(PathKit.getWebRootPath().concat("/resource/default.jpg")));
            } else {
                render(new ImageRender(HolidayConstants.getProperty("image.path") + image.getPath()));
            }
        }
    }

    @Before(POST.class)
    public void save() {
        try {

            String base = HolidayConstants.getProperty("image.path");
            String path = separator + format.format(new Date()) + separator;

            List<UploadFile> files = getFiles(base + path);

            List<Image> images = new ArrayList<Image>();

            Map<String, String> result = new HashMap<String, String>();

            for (UploadFile file : files) {
                String ext = StringUtils.lowerCase(StringUtils.substring(file.getFileName(), StringUtils.lastIndexOf(file.getFileName(), ".") + 1));

                Image image = new Image();
                image.setContentType(file.getContentType());
                image.setOriginal(file.getFileName());
                image.setExt(ext);
                image.setSize(file.getFile().length());
                ImageDao.dao.save(image);

                image.setName(image.getId() + "." + ext);
                image.setPath(path + image.getName());
                ImageDao.dao.save(image);

                String newFile = base + image.getPath();
                if (file.getFile().renameTo(new File(newFile))) {
                    images.add(image);
                }

                result.put("original", image.getOriginal());
                result.put("title", image.getName());
                result.put("url", "/rest/image/" + image.getId());
                result.put("state", "SUCCESS");
            }

            if ("ueditor".equals(getPara("from"))) {
                renderJson(result);
            } else {
                renderJson(Result.exec(images));
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            renderJson(Result.exec(e));
        }
    }
}
