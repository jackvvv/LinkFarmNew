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
public class StandardTypeAdapter extends BaseAdapter {

    private Context context;

    private List<GoodsDetailBean.NormListBean> list;

    public int selectPosition;

    public StandardTypeAdapter(Context context, List<GoodsDetailBean.NormListBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_standard_type, null);
        }
        TextView tv_type = ViewHolder.get(view, R.id.tv_type);
        tv_type.setText(list.get(i).getNormName());

        if (i == selectPosition) {
            tv_type.setTextColor(Color.WHITE);
            tv_type.setBackgroundResource(R.drawable.green_btn);
        } else {
            tv_type.setTextColor(context.getResources().getColor(R.color.textblackColor));
            tv_type.setBackgroundResource(R.drawable.gray_btn);
        }
        return view;
    }
}
