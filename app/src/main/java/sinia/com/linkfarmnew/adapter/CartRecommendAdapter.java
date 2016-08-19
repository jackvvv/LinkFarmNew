package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.CartBean;
import sinia.com.linkfarmnew.bean.HomePageBean;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class CartRecommendAdapter extends BaseAdapter {

    private Context context;

    private List<CartBean.HisitemsBean> list;

    public CartRecommendAdapter(Context context, List<CartBean.HisitemsBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_cart_recommend, null);
        }
        TextView tv_title = ViewHolder.get(view, R.id.tv_title);
        ImageView img = ViewHolder.get(view, R.id.img);
        tv_title.setText(list.get(i).getGoodName());
        Glide.with(context).load(list.get(i).getGoodImage()).placeholder(R.drawable.load_failed_left).into(img);

        int itemWidth = AppInfoUtil.getScreenWidth(context) / 2 - AppInfoUtil.dip2px(context, 15);
        int itemHeight = AppInfoUtil.getScreenHeight(context) / 3;
        // 大图片LayoutParams
        LinearLayout.LayoutParams lpB = new LinearLayout.LayoutParams(itemWidth, itemHeight);
        img.setLayoutParams(lpB);
        return view;
    }
}
