package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.EditAddressActivity;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class ZiTiAddressAdapter extends BaseAdapter {

    private Context context;

    public ZiTiAddressAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_ziti_address, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_tel = ViewHolder.get(view, R.id.tv_tel);
        TextView tv_address = ViewHolder.get(view, R.id.tv_address);
        ImageView img_default = ViewHolder.get(view, R.id.img_default);
        return view;
    }
}
