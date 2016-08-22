package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/22.
 */
public class MyOrderListBean extends JsonBean {

    /**
     * orderImageitem : [{"image":"http://bmob-cdn-5621.b0.upaiyun.com/2016/08/17/f3ad0043c713405ea4a4cdb8a6fe907b
     * .jpg"},{"image":"http://bmob-cdn-5621.b0.upaiyun.com/2016/08/17/f3ad0043c713405ea4a4cdb8a6fe907b.jpg"}]
     * comStatus : 2
     * price : 21000
     * merName : boring
     * goodNum : 2
     * orderStatus : 1
     * orderId : 402880f456b12be90156b138270b0032
     */

    private List<OrderBean> items;

    public List<OrderBean> getItems() {
        return items;
    }

    public void setItems(List<OrderBean> items) {
        this.items = items;
    }

    public static class OrderBean {
        private int comStatus;
        private double price;
        private String merName;
        private int goodNum;
        private int orderStatus;
        private String orderId;
        /**
         * image : http://bmob-cdn-5621.b0.upaiyun.com/2016/08/17/f3ad0043c713405ea4a4cdb8a6fe907b.jpg
         */

        private List<OrderImageitemBean> orderImageitem;

        public int getComStatus() {
            return comStatus;
        }

        public void setComStatus(int comStatus) {
            this.comStatus = comStatus;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public int getGoodNum() {
            return goodNum;
        }

        public void setGoodNum(int goodNum) {
            this.goodNum = goodNum;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public List<OrderImageitemBean> getOrderImageitem() {
            return orderImageitem;
        }

        public void setOrderImageitem(List<OrderImageitemBean> orderImageitem) {
            this.orderImageitem = orderImageitem;
        }

        public static class OrderImageitemBean {
            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
