package sinia.com.linkfarmnew.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class GoodsDetailBean extends JsonBean implements Serializable {

    private int collStatus;
    private int buyNum;
    private int goodComNum;
    private int midComNum;
    private int lowComNum;
    private int allComNum;
    private String id;
    private String goodName;
    private String goodImage;
    private String moiveLink;
    private String leastKiloGram;//剩余量
    private List<CommentBean> commentitems;//评价集合
    private List<GoodsImageBean> imageitems;//商品图片集合
    private List<NormListBean> normitems;//规格集合
    private List<SendAddressBean> peiitems;//配送地址集合
    private List<SelfGetBean> ziitems;//自提地址集合
    private List<SourceBean> orginitems;//溯源集合

    public String getLeastKiloGram() {
        return leastKiloGram;
    }

    public void setLeastKiloGram(String leastKiloGram) {
        this.leastKiloGram = leastKiloGram;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getCollStatus() {
        return collStatus;
    }

    public void setCollStatus(int collStatus) {
        this.collStatus = collStatus;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getGoodComNum() {
        return goodComNum;
    }

    public void setGoodComNum(int goodComNum) {
        this.goodComNum = goodComNum;
    }

    public int getMidComNum() {
        return midComNum;
    }

    public void setMidComNum(int midComNum) {
        this.midComNum = midComNum;
    }

    public int getLowComNum() {
        return lowComNum;
    }

    public void setLowComNum(int lowComNum) {
        this.lowComNum = lowComNum;
    }

    public int getAllComNum() {
        return allComNum;
    }

    public void setAllComNum(int allComNum) {
        this.allComNum = allComNum;
    }

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    public String getMoiveLink() {
        return moiveLink;
    }

    public void setMoiveLink(String moiveLink) {
        this.moiveLink = moiveLink;
    }

    public List<CommentBean> getCommentitems() {
        return commentitems;
    }

    public void setCommentitems(List<CommentBean> commentitems) {
        this.commentitems = commentitems;
    }

    public List<GoodsImageBean> getImageitems() {
        return imageitems;
    }

    public void setImageitems(List<GoodsImageBean> imageitems) {
        this.imageitems = imageitems;
    }

    public List<NormListBean> getNormitems() {
        return normitems;
    }

    public void setNormitems(List<NormListBean> normitems) {
        this.normitems = normitems;
    }

    public List<SendAddressBean> getPeiitems() {
        return peiitems;
    }

    public void setPeiitems(List<SendAddressBean> peiitems) {
        this.peiitems = peiitems;
    }

    public List<SelfGetBean> getZiitems() {
        return ziitems;
    }

    public void setZiitems(List<SelfGetBean> ziitems) {
        this.ziitems = ziitems;
    }

    public List<SourceBean> getOrginitems() {
        return orginitems;
    }

    public void setOrginitems(List<SourceBean> orginitems) {
        this.orginitems = orginitems;
    }

    public static class SourceBean implements Serializable {
        private String orginId;
        private String orginContent;
        private String orginName;

        public String getOrginId() {
            return orginId;
        }

        public void setOrginId(String orginId) {
            this.orginId = orginId;
        }

        public String getOrginContent() {
            return orginContent;
        }

        public void setOrginContent(String orginContent) {
            this.orginContent = orginContent;
        }

        public String getOrginName() {
            return orginName;
        }

        public void setOrginName(String orginName) {
            this.orginName = orginName;
        }
    }

    public static class SelfGetBean implements Serializable {
        private String addId;
        private String addName;
        private String addTelephone;
        private String addAddress;

        public String getAddId() {
            return addId;
        }

        public void setAddId(String addId) {
            this.addId = addId;
        }

        public String getAddName() {
            return addName;
        }

        public void setAddName(String addName) {
            this.addName = addName;
        }

        public String getAddTelephone() {
            return addTelephone;
        }

        public void setAddTelephone(String addTelephone) {
            this.addTelephone = addTelephone;
        }

        public String getAddAddress() {
            return addAddress;
        }

        public void setAddAddress(String addAddress) {
            this.addAddress = addAddress;
        }
    }

    public static class SendAddressBean implements Serializable {
        private String addId;
        private String addName;
        private String addTelephone;
        private String addAddress;

        public String getAddId() {
            return addId;
        }

        public void setAddId(String addId) {
            this.addId = addId;
        }

        public String getAddName() {
            return addName;
        }

        public void setAddName(String addName) {
            this.addName = addName;
        }

        public String getAddTelephone() {
            return addTelephone;
        }

        public void setAddTelephone(String addTelephone) {
            this.addTelephone = addTelephone;
        }

        public String getAddAddress() {
            return addAddress;
        }

        public void setAddAddress(String addAddress) {
            this.addAddress = addAddress;
        }
    }

    public static class NormListBean implements Serializable {
        private String normName;
        private String normId;
        private List<NormTypeListBean> typeItems;

        public String getNormId() {
            return normId;
        }

        public void setNormId(String normId) {
            this.normId = normId;
        }

        public String getNormName() {
            return normName;
        }

        public void setNormName(String normName) {
            this.normName = normName;
        }

        public List<NormTypeListBean> getTypeItems() {
            return typeItems;
        }

        public void setTypeItems(List<NormTypeListBean> typeItems) {
            this.typeItems = typeItems;
        }

        public static class NormTypeListBean implements Serializable {
            private double stKg;
            private double enKg;
            private double price;

            public double getStKg() {
                return stKg;
            }

            public void setStKg(double stKg) {
                this.stKg = stKg;
            }

            public double getEnKg() {
                return enKg;
            }

            public void setEnKg(double enKg) {
                this.enKg = enKg;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }

    public static class GoodsImageBean implements Serializable {
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class CommentBean implements Serializable {
        private String content;
        private String image;
        private String merName;
        private List<CommentImage> commentimageitems;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public List<CommentImage> getCommentimageitems() {
            return commentimageitems;
        }

        public void setCommentimageitems(List<CommentImage> commentimageitems) {
            this.commentimageitems = commentimageitems;
        }

        public static class CommentImage implements Serializable {
            private String comImage;

            public String getComImage() {
                return comImage;
            }

            public void setComImage(String comImage) {
                this.comImage = comImage;
            }
        }
    }

}
