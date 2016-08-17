package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.WebViewActivity;
import sinia.com.linkfarmnew.bean.HomePageBean;
import sinia.com.linkfarmnew.utils.*;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class HomeFloorAdapter extends BaseAdapter {

    private Context context;

    private List<HomePageBean.UpActItemsBean> list;

    public HomeFloorAdapter(Context context, List<HomePageBean.UpActItemsBean> list) {
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
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case 0:
                    view = LayoutInflater.from(context).inflate(R.layout.item_floor_up, null);
                    break;
                case 1:
                    view = LayoutInflater.from(context).inflate(R.layout.item_floor_down, null);
                    break;
            }
        }
        int itemWidth = AppInfoUtil.getScreenWidth(context) / 2 - AppInfoUtil.dip2px(context, 15);
        int itemHeight = AppInfoUtil.getScreenHeight(context) / 3;
        // 大图片LayoutParams
        LinearLayout.LayoutParams lpB = new LinearLayout.LayoutParams(itemWidth, itemHeight);
        // 小图片LayoutParams
        LinearLayout.LayoutParams lpS = new LinearLayout.LayoutParams(itemWidth, itemHeight / 2 - AppInfoUtil.dip2px
                (context, 6));
        LinearLayout.LayoutParams lpS2 = new LinearLayout.LayoutParams(itemWidth, itemHeight / 2 - AppInfoUtil.dip2px
                (context, 6));
        int position = list.get(i).getLocationType();
        String imgUrl = list.get(i).getImage();
        String id = list.get(i).getUpactId();
        final String link = list.get(i).getLink();
        final Intent intent = new Intent(context, WebViewActivity.class);
        switch (type) {
            case 0:
                ImageView img_left_big = sinia.com.linkfarmnew.utils.ViewHolder.get(view, R.id.img_left_big);
                ImageView img_right_top = sinia.com.linkfarmnew.utils.ViewHolder.get(view, R.id.img_right_top);
                ImageView img_right_bottom = sinia.com.linkfarmnew.utils.ViewHolder.get(view, R.id.img_right_bottom);
                img_left_big.setLayoutParams(lpB);
                img_right_top.setLayoutParams(lpS);
                img_right_bottom.setLayoutParams(lpS);
                if (position == 1) {
                    Glide.with(context).load(imgUrl).placeholder(R.drawable.load_failed_left).into(img_left_big);
                    img_left_big.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent.putExtra("link", link);
                            context.startActivity(intent);
                        }
                    });
                } else if (position == 2) {
                    Glide.with(context).load(imgUrl).placeholder(R.drawable.load_failed_right).into(img_right_top);
                    img_right_top.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent.putExtra("link", link);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    Glide.with(context).load(imgUrl).placeholder(R.drawable.load_failed_right).into(img_right_bottom);
                    img_right_bottom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent.putExtra("link", link);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case 1:
                ImageView img_left_top = sinia.com.linkfarmnew.utils.ViewHolder.get(view, R.id.img_left_top);
                ImageView img_left_bottom = sinia.com.linkfarmnew.utils.ViewHolder.get(view, R.id.img_left_bottom);
                ImageView img_right_big = sinia.com.linkfarmnew.utils.ViewHolder.get(view, R.id.img_right_big);
                img_right_big.setLayoutParams(lpB);
                img_left_top.setLayoutParams(lpS);
                lpS2.topMargin = 15;
                img_left_bottom.setLayoutParams(lpS2);
                if (position == 1) {
                    Glide.with(context).load(imgUrl).placeholder(R.drawable.load_failed_right).into(img_left_top);
                    img_left_top.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent.putExtra("link", link);
                            context.startActivity(intent);
                        }
                    });
                } else if (position == 2) {
                    Glide.with(context).load(imgUrl).placeholder(R.drawable.load_failed_right).into(img_left_bottom);
                    img_left_bottom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent.putExtra("link", link);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    Glide.with(context).load(imgUrl).placeholder(R.drawable.load_failed_left).into(img_right_big);
                    img_right_big.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent.putExtra("link", link);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (1 == list.get(position).getType()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
