package sinia.com.linkfarmnew.myinterface;

/**
 * Created by 忧郁的眼神 on 2016/8/22.
 */
public interface OrderOperatorInterface {
    /**
     * 确认送达
     *
     * @param orderId
     */
    public void confirmOrderSend(String orderId, int position);

    /**
     * 取消订单
     *
     * @param orderId
     */
    public void cancelOrder(String orderId, int position);

}
