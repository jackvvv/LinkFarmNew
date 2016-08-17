package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class PointRecordBean extends JsonBean {

    private List<PointBean> items;

    public List<PointBean> getItems() {
        return items;
    }

    public void setItems(List<PointBean> items) {
        this.items = items;
    }

    public static class PointBean {
        private String userName;
        private String point;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }
    }
}
