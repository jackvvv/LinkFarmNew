package sinia.com.linkfarmnew.bean;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class ValidateCodeBean extends JsonBean {


    /**
     * camTime : 2016-08-15 10:55:50
     * codeId : 402880f0568c15b601568c206ea10016
     * validateCode : 6382
     */

    private String camTime;
    private String codeId;
    private String validateCode;

    public String getCamTime() {
        return camTime;
    }

    public void setCamTime(String camTime) {
        this.camTime = camTime;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
