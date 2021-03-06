package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.CouponListBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class UseCouponsAdapter extends BaseAdapter {

    private Context context;

    public int selectPosition;

    private List<CouponListBean.CouponBean> list;

    public UseCouponsAdapter(Context context, List<CouponListBean.CouponBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_use_coupons, null);
        }
        TextView tv_money = ViewHolder.get(view, R.id.tv_money);
        ImageView img_check = ViewHolder.get(view, R.id.img_check);
        if (selectPosition == i) {
            img_check.setVisibility(View.VISIBLE);
        } else {
            img_check.setVisibility(View.INVISIBLE);
        }
        tv_money.setText(list.get(i).getPrice());
        return view;
    }
}
