package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;

    public CommentAdapter(Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_goods_comment, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_commenttime = ViewHolder.get(view, R.id.tv_commenttime);
        TextView tv_buytime = ViewHolder.get(view, R.id.tv_buytime);
        TextView tv_content = ViewHolder.get(view, R.id.tv_content);
        ImageView img_head = ViewHolder.get(view, R.id.img_head);
        return view;
    }
}
