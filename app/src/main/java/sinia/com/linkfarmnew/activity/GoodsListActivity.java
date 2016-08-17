package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.SearchGoodsAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.SearchBean;
import sinia.com.linkfarmnew.utils.ActivityManager;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class GoodsListActivity extends BaseActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.tv_zonghe)
    TextView tvZonghe;
    @Bind(R.id.iv_zonghe)
    ImageView ivZonghe;
    @Bind(R.id.ll_zonghe)
    LinearLayout llZonghe;
    @Bind(R.id.tv_xiaoliang)
    TextView tvXiaoliang;
    @Bind(R.id.img_sellup)
    ImageView imgSellup;
    @Bind(R.id.img_selldown)
    ImageView imgSelldown;
    @Bind(R.id.ll_xiaoliang)
    LinearLayout llXiaoliang;
    @Bind(R.id.tv_jiage)
    TextView tvJiage;
    @Bind(R.id.img_priceup)
    ImageView imgPriceup;
    @Bind(R.id.img_pricedown)
    ImageView imgPricedown;
    @Bind(R.id.ll_price)
    LinearLayout llPrice;
    @Bind(R.id.ll_shaixuan)
    LinearLayout llShaixuan;
    @Bind(R.id.listView)
    ListView listView;
    public static DrawerLayout drawerLayout;
    private SearchGoodsAdapter goodsAdapter;
    private List<SearchBean.ItemsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvZonghe.setSelected(true);
        ivZonghe.setImageDrawable(getResources().getDrawable(R.drawable.sj_select_xia));
        goodsAdapter = new SearchGoodsAdapter(this, list);
        listView.setAdapter(goodsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivityForNoIntent(GoodsDetailActivity.class);
            }
        });
    }

    @OnClick({R.id.back, R.id.ll_zonghe, R.id.ll_xiaoliang, R.id.ll_price, R.id.ll_shaixuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.ll_zonghe:
                tvZonghe.setSelected(true);
                ivZonghe.setImageDrawable(getResources().getDrawable(R.drawable.sj_select_xia));
                break;
            case R.id.ll_xiaoliang:
                tvZonghe.setSelected(false);
                ivZonghe.setImageDrawable(getResources().getDrawable(R.drawable.sj_xia));
                break;
            case R.id.ll_price:
                tvZonghe.setSelected(false);
                ivZonghe.setImageDrawable(getResources().getDrawable(R.drawable.sj_xia));
                break;
            case R.id.ll_shaixuan:
                tvZonghe.setSelected(false);
                ivZonghe.setImageDrawable(getResources().getDrawable(R.drawable.sj_xia));
                openRightMenu();
                break;
        }
    }

    private void openRightMenu() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }
}
