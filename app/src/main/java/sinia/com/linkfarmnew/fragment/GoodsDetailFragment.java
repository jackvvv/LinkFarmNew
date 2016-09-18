package sinia.com.linkfarmnew.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.FillOrderActivity;
import sinia.com.linkfarmnew.activity.MainActivity;
import sinia.com.linkfarmnew.activity.ShopDetailActivity;
import sinia.com.linkfarmnew.activity.StandardDialogActivity;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.view.DragLayout;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class GoodsDetailFragment extends BaseFragment {

    @Bind(R.id.frame_goods)
    FrameLayout frameGoods;
    @Bind(R.id.frame_img)
    FrameLayout frameImg;
    @Bind(R.id.draglayout)
    DragLayout draglayout;
    @Bind(R.id.tv_cart)
    TextView tvCart;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.tpv)
    ThumbUpView tpv;
    @Bind(R.id.ll_collect)
    LinearLayout llCollect;
    @Bind(R.id.img_cart)
    ImageView imgCart;
    @Bind(R.id.img_reddot)
    ImageView imgReddot;
    @Bind(R.id.ll_call)
    LinearLayout llCall;
    @Bind(R.id.ll_shop)
    LinearLayout llShop;
    @Bind(R.id.ll_cart)
    LinearLayout llCart;
    private View rootView;
    private GoodsFragment goodsFragment;
    private ImageFragment imgFragment;
    private GoodsDetailBean goodsBean;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String goodsId, isCollect;//1，收藏，2未收藏
    private List<String> selectGoodsImage = new ArrayList<>();
    private String merTelephone, lastNum, goodsUnit;//剩余量s
    private MaterialDialog materialDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods_detail, null);
        ButterKnife.bind(this, rootView);
        goodsBean = (GoodsDetailBean) getArguments().get("goodsBean");
        lastNum = StandardDialogActivity.lastNum;
        goodsUnit = StandardDialogActivity.goodsUnit;
        merTelephone = goodsBean.getMerTelephone();
        initData();
        return rootView;
    }

    public static GoodsDetailFragment newInstance() {
        GoodsDetailFragment fragment = new GoodsDetailFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        goodsId = MyApplication.getInstance().getStringValue("addcart_goodid");
        if (goodsBean.getId().equals(goodsId)) {
            imgCart.setImageResource(R.drawable.youwe_select);
            imgReddot.setVisibility(View.VISIBLE);
        } else {
            imgCart.setImageResource(R.drawable.youwe);
            imgReddot.setVisibility(View.GONE);
        }
    }

    private void initData() {
        MyApplication.getInstance().setStringValue("buy_type", null);
        MyApplication.getInstance().setStringValue("buy_weight", null);
        MyApplication.getInstance().setStringValue("buy_price", null);
        MyApplication.getInstance().setStringValue("buy_normId", null);
        tpv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //return true 屏蔽触摸事件
                return true;
            }
        });

        if (1 == goodsBean.getCollStatus()) {
            isCollect = "1";
            tpv.Like();
        } else {
            isCollect = "2";
            tpv.UnLike();
        }
        goodsFragment = new GoodsFragment();
        imgFragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putSerializable("goodsBean", goodsBean);
        goodsFragment.setArguments(args);
        imgFragment.setArguments(args);
        getChildFragmentManager().beginTransaction()
                .add(R.id.frame_goods, goodsFragment).add(R.id.frame_img, imgFragment)
                .commit();
        DragLayout.ShowNextPageNotifier nextPageNotifier = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                imgFragment.initImg(goodsBean.getGoodImage());
            }
        };
        draglayout.setNextPageListener(nextPageNotifier);
    }

    @OnClick({R.id.ll_shop, R.id.ll_collect, R.id.tv_cart, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_shop:
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("shopId", goodsBean.getMerchantId());
                startActivity(intent);
                break;
            case R.id.ll_collect:
                collectGoods();
                break;
            case R.id.tv_cart:
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                intent2.putExtra("flag", "1");
                startActivity(intent2);
                break;
            case R.id.tv_ok:
                if (!StringUtil.isEmpty(MyApplication.getInstance().getStringValue("buy_weight")) && !StringUtil.isEmpty
                        (MyApplication.getInstance().getStringValue("buy_price"))) {
                    if (!StringUtil.isEmpty(lastNum) && Float.parseFloat(MyApplication.getInstance().getStringValue
                            ("buy_weight")
                    ) - Float
                            .parseFloat(lastNum) > 0) {
                        materialDialog = new MaterialDialog(getActivity());
                        materialDialog.setTitle("提示").setMessage("本商品库存剩余量" + lastNum + goodsUnit + ",如需大量购买，请联系商家")
                                .setPositiveButton("知道了", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        materialDialog.dismiss();
                                    }
                                }).show();
                    } else {
                        toPay();
                    }
                } else {
                    showToast("请选择产品规格");
                }
                break;
        }
    }

    private void toPay() {
        Intent intent = new Intent(getActivity(), FillOrderActivity.class);
        intent.putExtra("norm", MyApplication.getInstance().getStringValue("buy_type"));
        intent.putExtra("num", MyApplication.getInstance().getStringValue("buy_weight"));
        intent.putExtra("price", MyApplication.getInstance().getStringValue("buy_price"));
        intent.putExtra("name", MyApplication.getInstance().getStringValue("buy_normId"));
        intent.putExtra("goodId", goodsBean.getId());
        //填写订单显示的购买商品的图片集合
        selectGoodsImage = new ArrayList<>();
        if (goodsBean.getImageitems() != null && 0 != goodsBean.getImageitems().size()) {
            selectGoodsImage.add(goodsBean.getImageitems().get(0).getImage());
        }
        intent.putExtra("selectGoodsImage", (Serializable) selectGoodsImage);
        intent.putExtra("otherId", "-1");//购物车
        intent.putExtra("choose", "-1");//商户id
        intent.putExtra("type", "1");//1.直接购买 2.购物车
        startActivity(intent);
    }

    private void collectGoods() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("otherId", goodsBean.getId());
        params.put("type", "1");
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
                            tpv.UnLike();
                            tpv.setUnLikeType(ThumbUpView.LikeType.broken);
                        } else {
                            //收藏成功
                            isCollect = "1";
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        dismiss();
    }

    @OnClick(R.id.ll_call)
    public void onClick() {
        materialDialog = new MaterialDialog(getActivity());
        materialDialog.setTitle("联系商家").setMessage(merTelephone)
                .setPositiveButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + merTelephone));
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
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
