package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;

/**
 * Created by Jing on 14-2-15.
 */
@Entity
public class Image extends SimpleEntity {

    // 存放路径（相对）
    private String path;

    // 文件原始名称（带后缀）
    private String original;

    // 本地文件名称（带后缀）
    private String name;

    // 文件类型
    private String contentType;

    // 文件后缀
    private String ext;

    // 文件大小
    private long size;

    // 访问次数
    private int count;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
