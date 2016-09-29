package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.graphics.Color;
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
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.ViewHolder;

import static sinia.com.linkfarmnew.R.id.img;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class GoodImageAdapter extends BaseAdapter {

    private Context context;

    private List<String> list;

    public GoodImageAdapter(Context context, List<String> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_order_goodimg, null);
        }
        ImageView img_goods = ViewHolder.get(view, R.id.img_goods);
//        Glide.with(context).load(list.get(i)).cossFade().into(img_goods);
        BitmapUtilsHelp.getImage(context).display(img_goods,list.get(i));
        return view;
    }
}
