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
import sinia.com.linkfarmnew.bean.ClassfyListBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class ClassfyAdapter extends BaseAdapter {

    private Context context;
    public int selectPosition;
    private List<ClassfyListBean.BigClassBean> list;

    public ClassfyAdapter(Context context, List<ClassfyListBean.BigClassBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_classfy_left, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        if (selectPosition == i) {
            tv_name.setTextColor(Color.WHITE);
            view.setBackgroundColor(context.getResources().getColor(R.color.themeColor));
        } else {
            tv_name.setTextColor(context.getResources().getColor(R.color.textblackColor));
            view.setBackgroundColor(Color.WHITE);
        }
        tv_name.setText(list.get(i).getBigTypeName());
        return view;
    }
}
