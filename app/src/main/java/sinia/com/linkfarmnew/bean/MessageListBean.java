package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class MessageListBean extends JsonBean {

    private List<MessageBean> items;

    public List<MessageBean> getItems() {
        return items;
    }

    public void setItems(List<MessageBean> items) {
        this.items = items;
    }

    public static class MessageBean {
        private String messId;
        private String content;
        private String createTime;

        public String getMessId() {
            return messId;
        }

        public void setMessId(String messId) {
            this.messId = messId;
        }

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
    }
}
