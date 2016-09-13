package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class SearchBean extends JsonBean {


    /**
     * minKilograme : 26
     * buyNum :
     * goodId : 402880ee566e872f01566e8bc1b20002
     * goodName : 大西瓜
     * goodImage : http://localhost:8080/farmer/images/goodImage/kf1.jpg
     */

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * 商品
         */
        private String minKilograme;
        private String buyNum;
        private String goodId;
        private String goodName;
        private String goodImage;
        /**
         * 商铺
         */
        private String name;
        private String image;
        private String merId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMerId() {
            return merId;
        }

        public void setMerId(String merId) {
            this.merId = merId;
        }

        public String getMinKilograme() {
            return minKilograme;
        }

        public void setMinKilograme(String minKilograme) {
            this.minKilograme = minKilograme;
        }

        public String getBuyNum() {
            return buyNum;
        }

        public void setBuyNum(String buyNum) {
            this.buyNum = buyNum;
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
