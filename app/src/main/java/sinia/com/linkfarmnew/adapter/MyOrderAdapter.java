package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.PayActivity;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class MyOrderAdapter extends BaseAdapter {

    private Context context;

    private GoodImageAdapter adapter;

    public MyOrderAdapter(Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_my_order, null);
        }
        TextView tv_shopname = ViewHolder.get(view, R.id.tv_shopname);
        TextView tv_orderstatus = ViewHolder.get(view, R.id.tv_orderstatus);
        TextView tv_num = ViewHolder.get(view, R.id.tv_num);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        TextView btn1 = ViewHolder.get(view, R.id.btn1);
        TextView btn2 = ViewHolder.get(view, R.id.btn2);
        LinearLayout ll_detail = ViewHolder.get(view, R.id.ll_detail);
        GridView gv_goods = ViewHolder.get(view, R.id.gv_goods);
        adapter = new GoodImageAdapter(context);
        gv_goods.setAdapter(adapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PayActivity.class);
                context.startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PayActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
