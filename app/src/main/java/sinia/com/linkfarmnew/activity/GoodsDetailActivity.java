package sinia.com.linkfarmnew.activity;

import android.app.Dialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.WeiboAppManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyFragmentPagerAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.bean.ShopDetailBean;
import sinia.com.linkfarmnew.fragment.GoodsCommentFragment;
import sinia.com.linkfarmnew.fragment.GoodsDetailFragment;
import sinia.com.linkfarmnew.fragment.SourceFragment;
import sinia.com.linkfarmnew.fragment.VideoFragment;
import sinia.com.linkfarmnew.myinterface.GoodsDetailInterface;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;

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
    private String goodId;
    private AsyncHttpClient client = new AsyncHttpClient();
    private GoodsDetailBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        //网页中的视频，上屏幕的时候，可能出现闪烁的情况
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getGoodsDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getGoodsDetail() {
        goodId = getIntent().getStringExtra("goodId");
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("goodId", goodId);
        Log.i("tag", Constants.BASE_URL + "goodDetail&" + params);
        client.post(Constants.BASE_URL + "goodDetail", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    goodsBean = gson.fromJson(s, GoodsDetailBean.class);
                    int state = goodsBean.getState();
                    int isSuccessful = goodsBean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        if (null != goodsBean) {
                            initViews();
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                } else {
                    showToast("该商品不存在");
                }
            }
        });
    }

    private void initViews() {
        titleList = new ArrayList<>();
        titleList.add("商品");
        titleList.add("溯源");
        titleList.add("评论");
        titleList.add("视频");
        fragmentList = new ArrayList<>();

        Bundle args = new Bundle();
        goodsFragment = GoodsDetailFragment.newInstance();
        sourceFragment = SourceFragment.newInstance();
        commentFragment = GoodsCommentFragment.newInstance();
        videoFragment = VideoFragment.newInstance();

        args.putSerializable("goodsBean", goodsBean);

        goodsFragment.setArguments(args);
        sourceFragment.setArguments(args);
        commentFragment.setArguments(args);
        videoFragment.setArguments(args);

        fragmentList.add(goodsFragment);
        fragmentList.add(sourceFragment);
        fragmentList.add(commentFragment);
        fragmentList.add(videoFragment);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabTitle.setTabMode(TabLayout.MODE_FIXED);
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(0)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(1)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(2)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(3)));
        tabTitle.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 3) {
//                    videoFragment.initData();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        final UMImage image = new UMImage(this, "http://7xnmrr.com1.z0.glb.clouddn.com/111jianhse.jpg");
        img_qq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                new ShareAction(GoodsDetailActivity.this).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
//                        .withTitle("点击网络分享").withText("分享测试").withTargetUrl("https://www.baidu.com").withMedia(image)
//                        .share();
                dialog.dismiss();
            }
        });
        img_weibo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                if (WeiboAppManager.getInstance(GoodsDetailActivity.this).getWeiboInfo() == null) {
//                    showToast("请安装新浪微博客户端");
//                }
//                new ShareAction(GoodsDetailActivity.this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
//                        .withTitle
//                                ("点击网络分享").withText("分享测试").withTargetUrl("https://www.baidu.com").withMedia(image)
//                        .share();
                dialog.dismiss();
            }
        });
        img_wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                new ShareAction(GoodsDetailActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
//                        .withTitle("点击网络分享").withText("分享测试").withTargetUrl("https://www.baidu.com").withMedia(image)
//                        .share();
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

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            showToast("分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            showToast("分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showToast("分享取消了");
        }
    };

}
