package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.RecommendCodeBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class MyTeamAdapter extends BaseAdapter {

    private Context context;
    private List<RecommendCodeBean.RecommendName> list;

    public MyTeamAdapter(Context context, List<RecommendCodeBean.RecommendName> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_team, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_num = ViewHolder.get(view, R.id.tv_num);
        tv_num.setText(list.get(i).getUserName());
        return view;
    }
}
