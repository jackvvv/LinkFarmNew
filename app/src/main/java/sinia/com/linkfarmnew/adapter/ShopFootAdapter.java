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
import sinia.com.linkfarmnew.bean.MyFootBean;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class ShopFootAdapter extends BaseAdapter {

    private Context context;

    private List<MyFootBean.DetailBean> list;

    public ShopFootAdapter(Context context, List<MyFootBean.DetailBean> list) {
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

        Glide.with(context).load(list.get(i).getMerImage()).placeholder(R.drawable.ic_launcher).into(img);
        tv_shopname.setText(list.get(i).getMerName());

        final String shopId = list.get(i).getMerchantId();
        tv_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra("shopId", shopId);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
