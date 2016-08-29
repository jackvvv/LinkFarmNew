package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class MyCollectBean extends JsonBean {


    /**
     * price : 0
     * merName : 西瓜
     * collId : 402880eb569221b90156922249920011
     * merImage : http://bmob-cdn-5621.b0.upaiyun.com/2016/08/16/4f1270ea9d7c4f94832119c637ab5beb.jpg
     * comNum : 0
     * merchantId : 402880f0568d0cc901568d28d226000f
     * goodId :
     * goodName :
     * goodImage :
     */

    private List<CollectBean> items;

    public List<CollectBean> getItems() {
        return items;
    }

    public void setItems(List<CollectBean> items) {
        this.items = items;
    }

    public static class CollectBean {
        private String price;
        private String merName;
        private String collId;
        private String merImage;
        private String comNum;
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

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public String getCollId() {
            return collId;
        }

        public void setCollId(String collId) {
            this.collId = collId;
        }

        public String getMerImage() {
            return merImage;
        }

        public void setMerImage(String merImage) {
            this.merImage = merImage;
        }

        public String getComNum() {
            return comNum;
        }

        public void setComNum(String comNum) {
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
