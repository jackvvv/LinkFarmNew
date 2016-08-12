package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.GoodImageAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/12.
 */
public class FillOrderActivity extends BaseActivity {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.gv_goods)
    GridView gvGoods;
    @Bind(R.id.tv_good_count)
    TextView tvGoodCount;
    @Bind(R.id.rl_goodslist)
    RelativeLayout rlGoodslist;
    @Bind(R.id.rl_yhq)
    RelativeLayout rlYhq;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.tv_oldcost)
    TextView tvOldcost;
    @Bind(R.id.yunfei)
    TextView yunfei;
    @Bind(R.id.tv_realmoney)
    TextView tvRealmoney;
    @Bind(R.id.btn_delete)
    TextView btnSubmit;

    private GoodImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_order, "填写订单");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        adapter = new GoodImageAdapter(this);
        gvGoods.setAdapter(adapter);
    }

    @OnClick({R.id.rl_address, R.id.rl_yhq, R.id.btn_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                startActivityForNoIntent(SendtoAddressActivity.class);
                break;
            case R.id.rl_yhq:
                break;
            case R.id.btn_delete:
                break;
        }
    }
}
