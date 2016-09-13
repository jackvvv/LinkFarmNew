package sinia.com.linkfarmnew.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.GoodsRecommendAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.ShopDetailBean;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.view.MyGridView;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class ShopDetailActivity extends BaseActivity {

    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.tv_shopname)
    TextView tvShopname;
    @Bind(R.id.tv_buynum)
    TextView tvBuynum;
    @Bind(R.id.tv_collectnum)
    TextView tvCollectnum;
    @Bind(R.id.tv_content)
    TextView tv_content;
    //    @Bind(R.id.img_collect)
//    ImageView imgCollect;
    @Bind(R.id.gridView)
    MyGridView gridView;
    @Bind(R.id.tpv)
    ThumbUpView tpv;
    @Bind(R.id.ll_collect)
    LinearLayout llCollect;

    private GoodsRecommendAdapter adapter;
    private String shopId,merTelephone, isCollect;//1，收藏，2未收藏
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<ShopDetailBean.ShopGoodsBean> list = new ArrayList<>();
    private ShopDetailBean shopBean;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail, "店铺");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        getImg_pic().setVisibility(View.VISIBLE);
        getImg_pic().setImageResource(R.drawable.icon_call);
        shopId = getIntent().getStringExtra("shopId");
        getShopDetail();
        initData();
    }

    private void getShopDetail() {
        tpv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("userId", shopId);
        params.put("otherId", MyApplication.getInstance().getStringValue("userId"));
        Log.i("tag", Constants.BASE_URL + "goodListByMerId&" + params);
        client.post(Constants.BASE_URL + "goodListByMerId", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    shopBean = gson.fromJson(s, ShopDetailBean.class);
                    int state = shopBean.getState();
                    int isSuccessful = shopBean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        setShopData(shopBean);
                        list.clear();
                        list.addAll(shopBean.getItems());
                        adapter.notifyDataSetChanged();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void setShopData(ShopDetailBean bean) {
        merTelephone = bean.getMerTelephone();
        Glide.with(this).load(bean.getImage()).placeholder(R.drawable.ic_launcher).into(img);
        tvShopname.setText(bean.getName());
        tv_content.setText(bean.getContent());
        tvBuynum.setText("已有" + bean.getBuyNum() + "人购买");
        tvCollectnum.setText(bean.getCollSize() + "人\n 收藏人数");
        if ("1".equals(bean.getCollStauts())) {
//            imgCollect.setImageResource(R.drawable.ic_collected);
            isCollect = "1";
            tpv.Like();
        } else {
//            imgCollect.setImageResource(R.drawable.ic_not_collect);
            isCollect = "2";
            tpv.UnLike();
        }
    }

    private void initData() {
        adapter = new GoodsRecommendAdapter(this, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String goodId = list.get(i).getGoodId();
                Intent intent = new Intent();
                intent.putExtra("goodId", goodId);
                startActivityForIntent(GoodsDetailActivity.class, intent);
            }
        });
    }

    @OnClick(R.id.ll_collect)
    public void onClick() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("otherId", shopId);
        params.put("type", "2");
        if ("1".equals(isCollect)) {
            //取消收藏
            params.put("choose", "2");
        } else {
            //收藏
            params.put("choose", "1");
        }
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        Log.i("tag", Constants.BASE_URL + "collorDeColl&" + params);
        client.post(Constants.BASE_URL + "collorDeColl", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        if ("1".equals(isCollect)) {
                            //取消成功
                            isCollect = "2";
                            if (0 > shopBean.getCollSize()) {
                                tvCollectnum.setText(0 + "人\n 收藏人数");
                            } else {
                                tvCollectnum.setText(shopBean.getCollSize() - 1 + "人\n 收藏人数");
                            }
                            shopBean.setCollSize(Integer.parseInt(tvCollectnum.getText().toString().split("人")[0]));
//                            imgCollect.setImageResource(R.drawable.ic_not_collect);
                            tpv.UnLike();
                            tpv.setUnLikeType(ThumbUpView.LikeType.broken);
                        } else {
                            //收藏成功
                            isCollect = "1";
                            tvCollectnum.setText(shopBean.getCollSize() + 1 + "人\n 收藏人数");
                            shopBean.setCollSize(Integer.parseInt(tvCollectnum.getText().toString().split("人")[0]));
//                            imgCollect.setImageResource(R.drawable.ic_collected);
                            tpv.Like();
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    @Override
    public void doing() {
        super.doing();
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("联系商家").setMessage(merTelephone)
                .setPositiveButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + merTelephone));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
                                .CALL_PHONE) !=
                                PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                        materialDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        }).show();
    }
}
