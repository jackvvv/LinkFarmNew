package sinia.com.linkfarmnew.myinterface;

import java.util.List;

import sinia.com.linkfarmnew.bean.GoodsDetailBean;

/**
 * Created by 忧郁的眼神 on 2016/8/18.
 */
public interface SetPriceDataInterface {

    /**
     * 设置价格区间adapter list数据
     *
     * @param priceList
     */
    public void setPriceList(List<GoodsDetailBean.NormListBean.NormTypeListBean> priceList,String unit);
}
