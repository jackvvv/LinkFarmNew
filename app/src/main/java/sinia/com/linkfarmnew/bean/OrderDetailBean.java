package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/22.
 */
public class OrderDetailBean extends JsonBean {


    /**
     * createTime : 2016-08-22 17:26:05
     * comStatus : 2
     * truePrice : 20
     * orderNum : 20160822172426051
     * goodItems : [{"id":"402880f456b1908d0156b1923db0000b","price":20,"norm":"呆梨1","goodName":"西奈呆梨",
     * "goodImage":"http://bmob-cdn-5621.b0.upaiyun.com/2016/08/17/8ffc2e3bbec841509a677732e150836d.jpg"}]
     * orderStatus : 2
     * deType : 1
     * price : 20
     * address : 北京路步行街
     * merName : boring
     * name : 奥铃
     * freight : 0
     * payType : 1
     * telephone : 15651788424
     */

    private String createTime;
    private int comStatus;
    private double truePrice;
    private String orderNum;
    private int orderStatus;
    private int deType;
    private double price;
    private String address;
    private String merName;
    private String content;
    private String name;
    private int freight;
    private int payType;
    private String telephone;
    /**
     * id : 402880f456b1908d0156b1923db0000b
     * price : 20
     * norm : 呆梨1
     * goodName : 西奈呆梨
     * goodImage : http://bmob-cdn-5621.b0.upaiyun.com/2016/08/17/8ffc2e3bbec841509a677732e150836d.jpg
     */

    private List<GoodItemsBean> goodItems;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getComStatus() {
        return comStatus;
    }

    public void setComStatus(int comStatus) {
        this.comStatus = comStatus;
    }

    public double getTruePrice() {
        return truePrice;
    }

    public void setTruePrice(double truePrice) {
        this.truePrice = truePrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getDeType() {
        return deType;
    }

    public void setDeType(int deType) {
        this.deType = deType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<GoodItemsBean> getGoodItems() {
        return goodItems;
    }

    public void setGoodItems(List<GoodItemsBean> goodItems) {
        this.goodItems = goodItems;
    }

    public static class GoodItemsBean {
        private String id;
        private double price;
        private int buyNum;
        private String num;
        private String unit;
        private String norm;
        private String goodName;
        private String goodImage;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getBuyNum() {
            return buyNum;
        }

        public void setBuyNum(int buyNum) {
            this.buyNum = buyNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getNorm() {
            return norm;
        }

        public void setNorm(String norm) {
            this.norm = norm;
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
