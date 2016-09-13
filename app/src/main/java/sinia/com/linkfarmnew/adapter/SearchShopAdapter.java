package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.LoginActivity;
import sinia.com.linkfarmnew.activity.ShopDetailActivity;
import sinia.com.linkfarmnew.bean.SearchBean;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class SearchShopAdapter extends BaseAdapter {

    private Context context;
    private List<SearchBean.ItemsBean> list;

    public SearchShopAdapter(Context context, List<SearchBean.ItemsBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_shop_collect, null);
        }
        TextView tv_shopname = ViewHolder.get(view, R.id.tv_title);
        TextView tv_in = ViewHolder.get(view, R.id.tv_in);
        ImageView img = ViewHolder.get(view, R.id.img);

        Glide.with(context).load(list.get(i).getImage()).placeholder(R.drawable.ic_launcher).into(img);
        tv_shopname.setText(list.get(i).getName());
        final String shopId = list.get(i).getMerId();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    Intent intent = new Intent(context, ShopDetailActivity.class);
                    intent.putExtra("shopId", shopId);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        return view;
    }
}
