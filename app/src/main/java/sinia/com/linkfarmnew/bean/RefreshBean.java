package sinia.com.linkfarmnew.bean;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class RefreshBean extends JsonBean {

    private String imageUrl;
    private String nickName;
    private String password;
    private String sex;
    private String leave;//等级名
    private String point;
    private String leavelPoint;//下一个等级积分
    private String ence;
    private int comNum;
    private int waitShouNum;
    private int waitPayNum;

    public String getLeavel() {
        return leave;
    }

    public void setLeavel(String leavelName) {
        this.leave = leavelName;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getLeavelPoint() {
        return leavelPoint;
    }

    public void setLeavelPoint(String leavelPoint) {
        this.leavelPoint = leavelPoint;
    }

    public String getEnce() {
        return ence;
    }

    public void setEnce(String ence) {
        this.ence = ence;
    }

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
