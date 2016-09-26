package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class GoodsCommentBean extends JsonBean {

    private int goodComNum;
    private int midComNum;
    private int lowComNum;
    private int allComNum;
    private List<CommentBean> items;

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

    public List<CommentBean> getItems() {
        return items;
    }

    public void setItems(List<CommentBean> items) {
        this.items = items;
    }

    public static class CommentBean {
        private String comId;
        private String content;
        private String userImage;
        private String userName;
        private String buyTime;
        private String num;
        private String unit;
        private String norm;
        private String createTime;
        private List<CommentImage> comimageitems;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getNorm() {
            return norm;
        }

        public void setNorm(String norm) {
            this.norm = norm;
        }

        public String getComId() {
            return comId;
        }

        public void setComId(String comId) {
            this.comId = comId;
        }

        public List<CommentImage> getComimageitems() {
            return comimageitems;
        }

        public void setComimageitems(List<CommentImage> comimageitems) {
            this.comimageitems = comimageitems;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBuyTime() {
            return buyTime;
        }

        public void setBuyTime(String buyTime) {
            this.buyTime = buyTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public static class CommentImage {
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
