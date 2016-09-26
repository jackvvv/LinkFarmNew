package sinia.com.linkfarmnew.bean;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class RefreshBean extends JsonBean {

    private String imageUrl;
    private String nickName;
    private String password;
    private String sex;
    private int comNum;
    private int waitShouNum;
    private int waitPayNum;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getComNum() {
        return comNum;
    }

    public void setComNum(int comNum) {
        this.comNum = comNum;
    }

    public int getWaitShouNum() {
        return waitShouNum;
    }

    public void setWaitShouNum(int waitShouNum) {
        this.waitShouNum = waitShouNum;
    }

    public int getWaitPayNum() {
        return waitPayNum;
    }

    public void setWaitPayNum(int waitPayNum) {
        this.waitPayNum = waitPayNum;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
