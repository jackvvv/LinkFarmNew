package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.SendAddressTypeActivity;
import sinia.com.linkfarmnew.activity.StandardDialogActivity;
import sinia.com.linkfarmnew.adapter.GoodsCommentAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsCommentBean;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.Utility;
import sinia.com.linkfarmnew.view.CustScrollView;
import sinia.com.linkfarmnew.view.NetworkImageHolderView;
import sinia.com.linkfarmnew.view.slideview.SlideShowView;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class GoodsFragment extends BaseFragment {

    @Bind(R.id.tv_goodsname)
    TextView tvGoodsname;
    @Bind(R.id.tv_buynum)
    TextView tvBuynum;
    @Bind(R.id.tv_select)
    TextView tvSelect;
    @Bind(R.id.tv_send_address)
    TextView tvSendAddress;
    @Bind(R.id.lv_comment)
    ListView lvComment;
    @Bind(R.id.custScrollView)
    CustScrollView custScrollView;
    @Bind(R.id.rl_standard)
    LinearLayout rlStandard;
    @Bind(R.id.tv_commentNum)
    TextView tvCommentNum;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    private View rootView;
    private List<String> picList = new ArrayList<String>();
    private GoodsCommentAdapter adapter;
    private GoodsDetailBean goodsBean;

    private AsyncHttpClient client = new AsyncHttpClient();
    private List<GoodsCommentBean.CommentBean> list = new ArrayList<>();
    private String price;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods, null);
        ButterKnife.bind(this, rootView);
        goodsBean = (GoodsDetailBean) getArguments().get("goodsBean");
        initData();
        getCommentData();
        return rootView;
    }

    private void getCommentData() {
        RequestParams params = new RequestParams();
        params.put("type", "1");
        params.put("otherId", goodsBean.getId());
        Log.i("tag", Constants.BASE_URL + "myComment&" + params);
        client.post(Constants.BASE_URL + "myComment", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    GoodsCommentBean bean = gson.fromJson(s, GoodsCommentBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        list.clear();
                        list.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                        if (null != lvComment && null != tvCommentNum) {
                            Utility.setListViewHeightBasedOnChildren(lvComment);
                            tvCommentNum.setText("评价(" + list.size() + ")");
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void initData() {
        tvGoodsname.setText(goodsBean.getGoodName());
        tvBuynum.setText("已" + goodsBean.getBuyNum() + "人购买");
        int h = AppInfoUtil.getScreenWidth(getActivity()) * 560 / 750;
        convenientBanner.getLayoutParams().height = h;
        String transforemerName = "ZoomOutTranformer";
        ABaseTransformer transforemer = null;
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.setPageIndicator(new int[]{R.drawable.carousel_point, R.drawable.carousel_point_select})
                    .getViewPager().setPageTransformer(true, transforemer);
            //部分3D特效需要调整滑动速度
            if (transforemerName.equals("StackTransformer")) {
                convenientBanner.setScrollDuration(1200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != goodsBean && null != goodsBean.getImageitems()) {
            picList = new ArrayList<>();
            for (int i = 0; i < goodsBean.getImageitems().size(); i++) {
                picList.add(goodsBean.getImageitems().get(i).getImage());
            }
            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, picList).startTurning(4000);
        }
        adapter = new GoodsCommentAdapter(getActivity(), list);
        lvComment.setAdapter(adapter);
//        Utility.setListViewHeightBasedOnChildren(lvComment);
    }

    @OnClick({R.id.rl_standard, R.id.ll_sendAddress})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_standard:
                intent = new Intent(getActivity(), StandardDialogActivity.class);
                intent.putExtra("goodsBean", goodsBean);
                startActivityForResult(intent, 99);
                break;
            case R.id.ll_sendAddress:
                intent = new Intent(getActivity(), SendAddressTypeActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 99) {
                String type = data.getStringExtra("type");
                String weight = data.getStringExtra("weight");
                String normId = data.getStringExtra("normId");
                price = data.getStringExtra("price");
                tvSelect.setText(type + "  " + weight + "kg");
                MyApplication.getInstance().setStringValue("buy_type", type);
                MyApplication.getInstance().setStringValue("buy_normId", normId);
                MyApplication.getInstance().setStringValue("buy_weight", weight);
                MyApplication.getInstance().setStringValue("buy_price", price);
            }
            if (requestCode == 100) {
                tvSendAddress.setText(data.getStringExtra("address"));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
