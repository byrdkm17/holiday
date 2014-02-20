package cc.aliza.production.holiday.dao;

import cc.aliza.production.holiday.entity.Image;
import com.bugull.mongo.BuguDao;

/**
 * Created by Jing on 14-1-27.
 */
public class ImageDao extends BuguDao<Image> {

    public static ImageDao dao = new ImageDao();

    public ImageDao() {
        super(Image.class);
    }
}
