package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class ShopDetailBean extends JsonBean {

    private String collStauts;//1.已收藏 2.未收藏
    private int collSize;//收藏数量
    private int buyNum;//购买数量
    private String merId;
    private String image;
    private String name;
    private List<ShopGoodsBean> items;

    public String getCollStauts() {
        return collStauts;
    }

    public void setCollStauts(String collStauts) {
        this.collStauts = collStauts;
    }

    public int getCollSize() {
        return collSize;
    }

    public void setCollSize(int collSize) {
        this.collSize = collSize;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShopGoodsBean> getItems() {
        return items;
    }

    public void setItems(List<ShopGoodsBean> items) {
        this.items = items;
    }

    public static class ShopGoodsBean {
        private String goodId;
        private String goodName;
        private String goodImage;
        private int buyNum;
        private String minKilograme;

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

        public int getBuyNum() {
            return buyNum;
        }

        public void setBuyNum(int buyNum) {
            this.buyNum = buyNum;
        }

        public String getMinKilograme() {
            return minKilograme;
        }

        public void setMinKilograme(String minKilograme) {
            this.minKilograme = minKilograme;
        }
    }
}
