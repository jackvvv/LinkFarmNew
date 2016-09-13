package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.MyFootBean;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class GoodsFootAdapter extends BaseAdapter {

    private Context context;
    private List<MyFootBean.DetailBean> list;

    public GoodsFootAdapter(Context context, List<MyFootBean.DetailBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_goods_foot, null);
        }
        TextView tv_title = ViewHolder.get(view, R.id.tv_title);
        TextView tv_comment = ViewHolder.get(view, R.id.tv_comment);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        ImageView img = ViewHolder.get(view, R.id.img);

        Glide.with(context).load(list.get(i).getGoodImage()).placeholder(R.drawable.ic_launcher).into(img);
        tv_title.setText(list.get(i).getGoodName());
        tv_comment.setText(list.get(i).getComNum() + "人评论");
        tv_price.setText(StringUtil.formatePrice(Double.parseDouble(list.get(i).getPrice())) + "元/kg");
        final String shopId = list.get(i).getGoodId();
        return view;
    }
}
