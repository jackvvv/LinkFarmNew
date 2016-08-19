package sinia.com.linkfarmnew.bean;

/**
 * Created by 忧郁的眼神 on 2016/8/12.
 */
public class GroupBean {

    private String merName;
    private String merchantId;
    private boolean isChecked = false;

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
