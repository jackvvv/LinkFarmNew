package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class VouchersAdapter extends BaseAdapter {

    private Context context;

    public int type;

    public VouchersAdapter(Context context) {
        this.context = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        return 4;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_integral_used, null);
        }
        TextView tv_money = ViewHolder.get(view, R.id.tv_money);
        TextView tv_jifen = ViewHolder.get(view, R.id.tv_jifen);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_exchangeCode = ViewHolder.get(view, R.id.tv_exchangeCode);
        TextView tv_type = ViewHolder.get(view, R.id.tv_type);
        LinearLayout ll_jifen = ViewHolder.get(view, R.id.ll_jifen);
        if (type == 1) {
            //已使用
            tv_type.setText("已使用");
        } else {
            //未使用
            tv_type.setText("未使用");
        }

        return view;
    }
}
