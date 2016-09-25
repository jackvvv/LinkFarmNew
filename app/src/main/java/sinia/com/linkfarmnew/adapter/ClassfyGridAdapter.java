package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.CartBean;
import sinia.com.linkfarmnew.bean.ClassfyListBean;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class ClassfyGridAdapter extends BaseAdapter {

    private Context context;
    private List<ClassfyListBean.BigClassBean.SmallitemsBean> list;

    public ClassfyGridAdapter(Context context, List<ClassfyListBean.BigClassBean.SmallitemsBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_classfy_grid, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        ImageView img = ViewHolder.get(view, R.id.img);
        Glide.with(context).load(list.get(i).getSmallImage()).crossFade().into(img);
        tv_name.setText(list.get(i).getSmallTypeName());
        return view;
    }
}
