package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/22.
 */
public class ClassfyListBean extends JsonBean {


    /**
     * bigTypeName : 水果
     * id : 402880e75691268301569127de120002
     * smallitems : [{"smallTypeName":"苹果","smallid":"402880e756912683015691292ab8000a"},{"smallTypeName":"西瓜",
     * "smallid":"402880e756912683015691293b3f000c"},{"smallTypeName":"菠萝",
     * "smallid":"402880e756912683015691296252000e"},{"smallTypeName":"葡萄",
     * "smallid":"402880e7569126830156912976590010"}]
     */

    private List<BigClassBean> items;

    public List<BigClassBean> getItems() {
        return items;
    }

    public void setItems(List<BigClassBean> items) {
        this.items = items;
    }

    public static class BigClassBean {
        private String bigTypeName;
        private String id;
        /**
         * smallTypeName : 苹果
         * smallid : 402880e756912683015691292ab8000a
         */

        private List<SmallitemsBean> smallitems;

        public String getBigTypeName() {
            return bigTypeName;
        }

        public void setBigTypeName(String bigTypeName) {
            this.bigTypeName = bigTypeName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<SmallitemsBean> getSmallitems() {
            return smallitems;
        }

        public void setSmallitems(List<SmallitemsBean> smallitems) {
            this.smallitems = smallitems;
        }

        public static class SmallitemsBean {
            private String smallTypeName;
            private String smallid;
            private String smallImage;

            public String getSmallImage() {
                return smallImage;
            }

            public void setSmallImage(String smallImage) {
                this.smallImage = smallImage;
            }

            public String getSmallTypeName() {
                return smallTypeName;
            }

            public void setSmallTypeName(String smallTypeName) {
                this.smallTypeName = smallTypeName;
            }

            public String getSmallid() {
                return smallid;
            }

            public void setSmallid(String smallid) {
                this.smallid = smallid;
            }
        }
    }
}
