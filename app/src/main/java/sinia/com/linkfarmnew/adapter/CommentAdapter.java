package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.PreViewImageActivity;
import sinia.com.linkfarmnew.bean.GoodsCommentBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;

    private List<GoodsCommentBean.CommentBean> list;
    private List<GoodsCommentBean.CommentBean.CommentImage> imgList = new ArrayList<>();
    private CommentImageAdapter adapter;

    public CommentAdapter(Context context, List<GoodsCommentBean.CommentBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_goods_comment, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_commenttime = ViewHolder.get(view, R.id.tv_commenttime);
        TextView tv_buytime = ViewHolder.get(view, R.id.tv_buytime);
        TextView tv_content = ViewHolder.get(view, R.id.tv_content);
        ImageView img_head = ViewHolder.get(view, R.id.img_head);
        final GridView gv_img = ViewHolder.get(view, R.id.gv_img);

        tv_name.setText(list.get(i).getUserName());
        tv_commenttime.setText(list.get(i).getCreateTime());
        tv_buytime.setText(list.get(i).getBuyTime());
        tv_content.setText(list.get(i).getContent());
        Glide.with(context).load(list.get(i).getUserImage()).placeholder(R.drawable.ic_launcher).into(img_head);

        imgList.clear();
        imgList.addAll(list.get(i).getComimageitems());
        adapter = new CommentImageAdapter(context, imgList);
        gv_img.setAdapter(adapter);

        return view;
    }
}
