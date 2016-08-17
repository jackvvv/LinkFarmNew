package sinia.com.linkfarmnew.bean;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class LoginBean extends JsonBean {

    private String id;
    private String image;
    private String name;
    private String cheakStatus;//审核状态 1.通过 2.待审核 3.审核失败
    private String point;
    private String leave;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheakStatus() {
        return cheakStatus;
    }

    public void setCheakStatus(String cheakStatus) {
        this.cheakStatus = cheakStatus;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }
}
