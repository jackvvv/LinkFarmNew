package sinia.com.linkfarmnew.bean;

/**
 * Created by 忧郁的眼神 on 2016/8/22.
 */
public class AddOrderBean extends JsonBean {


    /**
     * payMoney : 0
     * orderId : 402880f456b014760156b01b48490011
     */

    private double payMoney;
    private String orderId;

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
