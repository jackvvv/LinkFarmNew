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
import com.umeng.message.proguard.B;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.GoodsDetailActivity;
import sinia.com.linkfarmnew.activity.LoginActivity;
import sinia.com.linkfarmnew.bean.ClassfyGoodsBean;
import sinia.com.linkfarmnew.bean.SearchBean;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class ClassfyGoodsAdapter extends BaseAdapter {

    private Context context;

    private List<ClassfyGoodsBean.ItemsBean> list;

    public ClassfyGoodsAdapter(Context context, List<ClassfyGoodsBean.ItemsBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_goods_search, null);
        }
        TextView tv_title = ViewHolder.get(view, R.id.tv_title);
        TextView tv_buynum = ViewHolder.get(view, R.id.tv_buynum);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        ImageView img = ViewHolder.get(view, R.id.img);

        BitmapUtilsHelp.getImage(context).display(img, list.get(i).getGoodImage());
        tv_title.setText(list.get(i).getGoodName());
        tv_buynum.setText(list.get(i).getComNum() + "人评论");
        tv_price.setText(StringUtil.formatePrice(list.get(i).getMinKilograme()) + "元/" + list.get(i).getUnit());

        final String goodId = list.get(i).getGoodId();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra("goodId", goodId);
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
