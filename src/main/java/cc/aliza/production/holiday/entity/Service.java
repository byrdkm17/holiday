package cc.aliza.production.holiday.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Ref;

/**
 * Created by Jing on 14-2-15.
 */
@Entity
public class Service extends SimpleEntity {

    private String production;

    private String name;

    @Ref
    private Image pic;

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getPic() {
        return pic;
    }

    public void setPic(Image pic) {
        this.pic = pic;
    }
}
