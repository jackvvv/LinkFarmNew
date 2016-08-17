package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class RecommendCodeBean extends JsonBean {


    /**
     * recCode : xSPBwQ
     * items : []
     */

    private String recCode;
    private List<RecommendName> items;

    public String getRecCode() {
        return recCode;
    }

    public void setRecCode(String recCode) {
        this.recCode = recCode;
    }

    public List<RecommendName> getItems() {
        return items;
    }

    public void setItems(List<RecommendName> items) {
        this.items = items;
    }

    public static class RecommendName {
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
