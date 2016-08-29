package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.EditAddressActivity;
import sinia.com.linkfarmnew.bean.AddressListBean;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.myinterface.CalculatePriceInterface;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class StandardPriceAdapter extends BaseAdapter {

    private Context context;

    private List<GoodsDetailBean.NormListBean.NormTypeListBean> list;

    public StandardPriceAdapter(Context context, List<GoodsDetailBean.NormListBean.NormTypeListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_price, null);
        }
        TextView kg_s = ViewHolder.get(view, R.id.kg_s);
        TextView kg_e = ViewHolder.get(view, R.id.kg_e);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);

        kg_s.setText(list.get(i).getStKg() + "kg");
        kg_e.setText(list.get(i).getEnKg() + "kg");
        tv_price.setText(StringUtil.formatePrice(list.get(i).getPrice()));

        return view;
    }
}
