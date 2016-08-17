package sinia.com.linkfarmnew.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/16.
 */
public class AddressListBean extends JsonBean {

    private List<AddressBean> items;

    public List<AddressBean> getItems() {
        return items;
    }

    public void setItems(List<AddressBean> items) {
        this.items = items;
    }

    public static class AddressBean {
        private String addId;
        private String addName;
        private String addTelephone;
        private String addAddress;
        private String addArea;

        public String getAddArea() {
            return addArea;
        }

        public void setAddArea(String addArea) {
            this.addArea = addArea;
        }

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
}
