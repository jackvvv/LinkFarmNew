package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.PreViewImageActivity;
import sinia.com.linkfarmnew.bean.GoodsCommentBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class CommentImageAdapter extends BaseAdapter {

    private Context context;

    private List<GoodsCommentBean.CommentBean.CommentImage> imgList;

    public CommentImageAdapter(Context context, List<GoodsCommentBean.CommentBean.CommentImage> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_goodimg, null);
        }
        ImageView img_goods = ViewHolder.get(view, R.id.img_goods);
        Glide.with(context).load(imgList.get(i).getComImage()).placeholder(R.drawable.ic_launcher).into(img_goods);

        final String url = imgList.get(i).getComImage();
        img_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,
                        PreViewImageActivity.class);
                intent.putExtra("picUrl", url);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
