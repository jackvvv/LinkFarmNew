package sinia.com.linkfarmnew.bean;

/**
 * Created by 忧郁的眼神 on 2016/8/12.
 */
public class GroupBean {

    private String shopName;
    private String shopId;
    private boolean isChecked = false;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
