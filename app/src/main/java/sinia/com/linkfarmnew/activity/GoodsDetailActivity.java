package sinia.com.linkfarmnew.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyFragmentPagerAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.fragment.GoodsCollectFragment;
import sinia.com.linkfarmnew.fragment.GoodsCommentFragment;
import sinia.com.linkfarmnew.fragment.GoodsDetailFragment;
import sinia.com.linkfarmnew.fragment.ShopCollectFragment;
import sinia.com.linkfarmnew.fragment.SourceFragment;
import sinia.com.linkfarmnew.fragment.VideoFragment;
import sinia.com.linkfarmnew.utils.ActivityManager;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class GoodsDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.tab_title)
    TabLayout tabTitle;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private MyFragmentPagerAdapter pagerAdapter;
    private List<String> titleList;
    private List<Fragment> fragmentList;
    private GoodsDetailFragment goodsFragment;
    private SourceFragment sourceFragment;
    private GoodsCommentFragment commentFragment;
    private VideoFragment videoFragment;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titleList = new ArrayList<>();
        titleList.add("商品");
        titleList.add("溯源");
        titleList.add("评论");
        titleList.add("视频");
        fragmentList = new ArrayList<>();
        goodsFragment = new GoodsDetailFragment();
        sourceFragment = new SourceFragment();
        commentFragment = new GoodsCommentFragment();
        videoFragment = new VideoFragment();
        fragmentList.add(goodsFragment);
        fragmentList.add(sourceFragment);
        fragmentList.add(commentFragment);
        fragmentList.add(videoFragment);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(pagerAdapter);
        tabTitle.setTabMode(TabLayout.MODE_FIXED);
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(0)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(1)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(2)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(3)));
        tabTitle.setupWithViewPager(viewPager);
    }

    @OnClick({R.id.back, R.id.img_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.img_share:
                createShareDialog();
                break;
        }
    }

    private Dialog createShareDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_share, null);
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        dialog.show();
        dialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        ImageView img_wx = (ImageView) dialog.findViewById(R.id.img_wx);
        ImageView img_weibo = (ImageView) dialog.findViewById(R.id.img_weibo);
        ImageView img_qq = (ImageView) dialog.findViewById(R.id.img_qq);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        img_qq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        img_weibo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        img_wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
