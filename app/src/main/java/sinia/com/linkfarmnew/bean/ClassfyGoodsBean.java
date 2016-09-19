package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/22.
 */
public class ClassfyGoodsBean extends JsonBean {


    /**
     * minKilograme : 0
     * comNum : 0
     * buyNum : 0
     * goodId : 402880eb569724820156972ed9c90050
     * goodName : 西奈呆梨
     * goodImage : http://bmob-cdn-5621.b0.upaiyun.com/2016/08/17/8ffc2e3bbec841509a677732e150836d.jpg
     */

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private double minKilograme;
        private int comNum;
        private int buyNum;
        private String goodId;
        private String goodName;
        private String goodImage;
        private String unit;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public double getMinKilograme() {
            return minKilograme;
        }

        public void setMinKilograme(double minKilograme) {
            this.minKilograme = minKilograme;
        }

        public int getComNum() {
            return comNum;
        }

        public void setComNum(int comNum) {
            this.comNum = comNum;
        }

        public int getBuyNum() {
            return buyNum;
        }

        public void setBuyNum(int buyNum) {
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
