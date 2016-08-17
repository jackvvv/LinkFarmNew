package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.MessageListBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class AnnouncementAdapter extends BaseAdapter {

    private Context context;

    private List<MessageListBean.MessageBean> list;

    private int type;

    public AnnouncementAdapter(Context context, List<MessageListBean.MessageBean> list) {
        this.context = context;
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_message, null);
        }
        TextView tv_type = ViewHolder.get(view, R.id.tv_type);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_content = ViewHolder.get(view, R.id.tv_content);
        if (getType() == 1) {
            tv_type.setText("系统公告");
        } else {
            tv_type.setText("订单通知");
        }
        tv_time.setText(list.get(i).getCreateTime());
        tv_content.setText(list.get(i).getContent());
        return view;
    }
}
