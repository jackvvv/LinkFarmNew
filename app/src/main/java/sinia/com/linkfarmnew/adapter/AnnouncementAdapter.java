package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class AnnouncementAdapter extends BaseAdapter {

    private Context context;

    public AnnouncementAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_message, null);
        }
        TextView tv_type = ViewHolder.get(view, R.id.tv_type);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_content = ViewHolder.get(view, R.id.tv_content);
        return view;
    }
}
