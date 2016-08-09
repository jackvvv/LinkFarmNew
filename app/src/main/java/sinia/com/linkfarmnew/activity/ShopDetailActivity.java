package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.GoodsRecommendAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
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
    @Bind(R.id.img_collect)
    ImageView imgCollect;
    @Bind(R.id.ll_collect)
    LinearLayout llCollect;
    @Bind(R.id.gridView)
    MyGridView gridView;

    private GoodsRecommendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail, "店铺");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        adapter = new GoodsRecommendAdapter(this);
        gridView.setAdapter(adapter);
    }

    @OnClick(R.id.ll_collect)
    public void onClick() {

    }
}
