package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class MyFootBean extends JsonBean {

    /**
     * price : 0
     * hisId : 402880eb569221b901569222387d000d
     * merName : 大西瓜
     * merImage :
     * comNum : 0
     * merchantId : 402880f0568c15b601568c1eebfc000e
     * goodId : 402880eb569221b901569222387d000d
     * goodName :
     * goodImage :
     */

    private List<DetailBean> items;

    public List<DetailBean> getItems() {
        return items;
    }

    public void setItems(List<DetailBean> items) {
        this.items = items;
    }

    public static class DetailBean {
        private String price;
        private String hisId;
        private String merName;
        private String merImage;
        private int comNum;
        private String merchantId;
        private String goodId;
        private String goodName;
        private String goodImage;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getHisId() {
            return hisId;
        }

        public void setHisId(String hisId) {
            this.hisId = hisId;
        }

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public String getMerImage() {
            return merImage;
        }

        public void setMerImage(String merImage) {
            this.merImage = merImage;
        }

        public int getComNum() {
            return comNum;
        }

        public void setComNum(int comNum) {
            this.comNum = comNum;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getGoodImage() {
            return goodImage;
        }

        public void setGoodImage(String goodImage) {
            this.goodImage = goodImage;
        }
    }
}
