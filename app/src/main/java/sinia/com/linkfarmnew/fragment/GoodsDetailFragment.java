package sinia.com.linkfarmnew.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
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
    private View rootView;
    private GoodsFragment goodsFragment;
    private ImageFragment imgFragment;
    private GoodsDetailBean goodsBean;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String goodsId, isCollect;//1，收藏，2未收藏

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods_detail, null);
        ButterKnife.bind(this, rootView);
        goodsBean = (GoodsDetailBean) getArguments().get("goodsBean");
        initData();
        return rootView;
    }

    public static GoodsDetailFragment newInstance() {
        GoodsDetailFragment fragment = new GoodsDetailFragment();
        return fragment;
    }

    private void initData() {
        tpv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
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

    @OnClick({R.id.ll_collect, R.id.tv_cart, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_collect:
                collectGoods();
                break;
            case R.id.tv_cart:
                break;
            case R.id.tv_ok:
                break;
        }
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
    }
}
