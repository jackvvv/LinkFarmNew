package sinia.com.linkfarmnew.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/9/13.
 */
public class ServiceBean extends JsonBean {


    /**
     * items : [{"content":"新用户首次登陆 ＋20 完成帐户的激活 ","id":"402880ec56bf9f080156bfa3f8780002","title":"如何获得积分"}]
     * telephone : 15687954896
     */

    private String telephone;
    /**
     * content : 新用户首次登陆 ＋20 完成帐户的激活
     * id : 402880ec56bf9f080156bfa3f8780002
     * title : 如何获得积分
     */

    private List<ItemsBean> items;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Serializable{
        private String content;
        private String id;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
