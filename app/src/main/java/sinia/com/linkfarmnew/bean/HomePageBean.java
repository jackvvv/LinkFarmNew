package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class HomePageBean extends JsonBean {


    private List<BannerItemsBean> banitems;
    private List<RecarrayItemsBean> recarrayitems;
    private List<UpActItemsBean> upactitems;
    private List<AnnItemsBean> annitems;

    /**
     * link : sdf
     * locationType : 2
     * image : http://localhost:8080/farmer/images/actImage/kf1.jpg
     * downactId : 402880e75672401f0156724438aa0002
     */

    public List<BannerItemsBean> getBanitems() {
        return banitems;
    }

    public void setBanitems(List<BannerItemsBean> banitems) {
        this.banitems = banitems;
    }

    public List<RecarrayItemsBean> getRecarrayitems() {
        return recarrayitems;
    }

    public void setRecarrayitems(List<RecarrayItemsBean> recarrayitems) {
        this.recarrayitems = recarrayitems;
    }

    public List<UpActItemsBean> getUpactitems() {
        return upactitems;
    }

    public void setUpactitems(List<UpActItemsBean> upactitems) {
        this.upactitems = upactitems;
    }

    public List<AnnItemsBean> getAnnitems() {
        return annitems;
    }

    public void setAnnitems(List<AnnItemsBean> annitems) {
        this.annitems = annitems;
    }

    public static class BannerItemsBean {
        private String banId;
        private String image;
        private String link;

        public String getBanId() {
            return banId;
        }

        public void setBanId(String banId) {
            this.banId = banId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public static class AnnItemsBean {
        private String annId;
        private String content;

        public String getAnnId() {
            return annId;
        }

        public void setAnnId(String annId) {
            this.annId = annId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class UpActItemsBean {
        private String link;
        private int locationType;//1,2,3
        private int type;//1 上，2下
        private String image;
        private String upactId;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getLocationType() {
            return locationType;
        }

        public void setLocationType(int locationType) {
            this.locationType = locationType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUpactId() {
            return upactId;
        }

        public void setUpactId(String upactId) {
            this.upactId = upactId;
        }
    }

    public static class DownactitemsBean {
        private String link;
        private int locationType;
        private String image;
        private String downactId;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getLocationType() {
            return locationType;
        }

        public void setLocationType(int locationType) {
            this.locationType = locationType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDownactId() {
            return downactId;
        }

        public void setDownactId(String downactId) {
            this.downactId = downactId;
        }
    }

    public static class RecarrayItemsBean {
        private String goodId;
        private String recId;
        private String goodName;
        private String goodImage;
        private String comNum;
        private String price;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public String getRecId() {
            return recId;
        }

        public void setRecId(String recId) {
            this.recId = recId;
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

        public String getComNum() {
            return comNum;
        }

        public void setComNum(String comNum) {
            this.comNum = comNum;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
