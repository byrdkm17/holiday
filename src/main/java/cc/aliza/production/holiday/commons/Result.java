package cc.aliza.production.holiday.commons;

import com.bugull.mongo.BuguEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by byrdkm17@gmail.com on 14-1-22.
 * Thanks
 */
public class Result {

    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static String exec() {
        return exec(true);
    }

    public static String exec(boolean success) {
        return exec(success, null, null, null);
    }

    public static String exec(Exception e) {
        if (e.getCause() == null) {
            return exec(false, e.getMessage(), null, null);
        } else {
            return exec(false, e.getCause().getMessage(), null, null);
        }
    }

    public static String exec(String message) {
        return exec(false, message, null, null);
    }

    public static String exec(List<?> list) {
        return exec(true, null, null, list);
    }

    public static String exec(BuguEntity entity) {
        return exec(true, null, entity, null);
    }

    public static String exec(boolean success, String message, BuguEntity entity, List<?> list) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("success", success);
        res.put("message", message);
        if (entity != null) {
            String name = entity.getClass().getSimpleName();
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            res.put(name, entity);
        }
        if (list != null) {
            res.put("list", list);
        }

        return gson.toJson(res);
    }
}
