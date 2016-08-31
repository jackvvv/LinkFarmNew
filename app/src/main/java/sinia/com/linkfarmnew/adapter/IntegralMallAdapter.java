package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.EditAddressActivity;
import sinia.com.linkfarmnew.bean.AddressListBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class IntegralMallAdapter extends BaseAdapter {

    private Context context;

    public IntegralMallAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_integral, null);
        }
        TextView tv_money = ViewHolder.get(view, R.id.tv_money);
        TextView tv_jifen = ViewHolder.get(view, R.id.tv_jifen);

        return view;
    }
}
