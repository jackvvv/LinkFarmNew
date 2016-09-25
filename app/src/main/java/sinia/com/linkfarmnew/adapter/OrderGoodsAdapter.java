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
import sinia.com.linkfarmnew.bean.OrderDetailBean;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class OrderGoodsAdapter extends BaseAdapter {

    private Context context;
    private List<OrderDetailBean.GoodItemsBean> list;

    public OrderGoodsAdapter(Context context, List<OrderDetailBean.GoodItemsBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_order_goods, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_num = ViewHolder.get(view, R.id.tv_num);
        TextView tv_weight = ViewHolder.get(view, R.id.tv_weight);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        ImageView img = ViewHolder.get(view, R.id.img);

        Glide.with(context).load(list.get(i).getGoodImage()).placeholder(R.drawable.load_failed_left).into(img);
        tv_name.setText(list.get(i).getGoodName());
        tv_num.setText("× " + list.get(i).getBuyNum());
        tv_weight.setText(list.get(i).getNorm() + " " + list.get(i).getNum() + list.get(i).getUnit());
        tv_price.setText("¥ " + StringUtil.formatePrice(list.get(i).getPrice()));
        return view;
    }
}
