package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class CouponListBean extends JsonBean {

    private List<CouponBean> items;

    public List<CouponBean> getItems() {
        return items;
    }

    public void setItems(List<CouponBean> items) {
        this.items = items;
    }

    public static class CouponBean {
        private String couId;
        private String price;

        public String getCouId() {
            return couId;
        }

        public void setCouId(String couId) {
            this.couId = couId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
