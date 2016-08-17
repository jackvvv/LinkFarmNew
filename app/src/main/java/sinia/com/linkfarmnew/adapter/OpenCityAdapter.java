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
import sinia.com.linkfarmnew.bean.OpenCityBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class OpenCityAdapter extends BaseAdapter {

    private Context context;

    public int selectPosition;

    private List<OpenCityBean.City> list;

    public OpenCityAdapter(Context context, List<OpenCityBean.City> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_city, null);
        }
        TextView tv_city = ViewHolder.get(view, R.id.tv_city);
        tv_city.setText(list.get(i).getCityName());
        if (i == selectPosition) {
            tv_city.setTextColor(Color.WHITE);
            tv_city.setBackgroundResource(R.drawable.green_btn);
        } else {
            tv_city.setTextColor(context.getResources().getColor(R.color.textblackColor));
            tv_city.setBackgroundResource(R.drawable.gray_btn);
        }
        return view;
    }
}
