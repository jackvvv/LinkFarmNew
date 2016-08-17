package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.OrderGoodsAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.Utility;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class OrderDetailActivity extends BaseActivity {

    @Bind(R.id.tv_orderNum)
    TextView tvOrderNum;
    @Bind(R.id.tv_name)
    TextView tvPerson;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.lv_goods)
    ListView lvGoods;
    @Bind(R.id.rl_goodslist)
    RelativeLayout rlGoodslist;
    @Bind(R.id.rl_yhq)
    RelativeLayout rlYhq;
    @Bind(R.id.tv_pay_type)
    TextView tvPayType;
    @Bind(R.id.tv_send_type)
    TextView tvSendType;
    @Bind(R.id.tv_oldcost)
    TextView tvOldcost;
    @Bind(R.id.yunfei)
    TextView yunfei;
    @Bind(R.id.tv_realmoney)
    TextView tvRealmoney;
    @Bind(R.id.orderTime)
    TextView orderTime;
    @Bind(R.id.btn2)
    TextView btn2;
    @Bind(R.id.btn1)
    TextView btn1;

    private OrderGoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail, "订单详情");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        adapter = new OrderGoodsAdapter(this);
        lvGoods.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lvGoods);
    }

    @OnClick({R.id.rl_yhq, R.id.btn2, R.id.btn1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_yhq:
                startActivityForNoIntent(UseCouponsActivity.class);
                break;
            case R.id.btn2:
                startActivityForNoIntent(PayActivity.class);
                break;
            case R.id.btn1:
                break;
        }
    }
}
