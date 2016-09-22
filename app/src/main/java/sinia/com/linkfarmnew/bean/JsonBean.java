package sinia.com.linkfarmnew.bean;

/**
 * Created by 忧郁的眼神 on 2016/7/28.
 */
public class JsonBean {

    private int isSuccessful;
    private int state;
    private String returnResult;

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
