package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class SourceAdapter extends BaseAdapter {

    private Context context;

    private List<GoodsDetailBean.SourceBean> list;

    public SourceAdapter(Context context, List<GoodsDetailBean.SourceBean> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_source, null);
        }
        TextView content = ViewHolder.get(view, R.id.content);
        TextView name = ViewHolder.get(view, R.id.name);
        content.setText(list.get(i).getOrginContent());
        name.setText(list.get(i).getOrginName()+":");
        return view;
    }
}
