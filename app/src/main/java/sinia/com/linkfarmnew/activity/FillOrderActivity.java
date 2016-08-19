package sinia.com.linkfarmnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Double2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import sinia.com.linkfarmnew.adapter.GoodImageAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.CartBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MoneyCalculate;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;

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
    @Bind(R.id.btn_submit)
    TextView btnSubmit;
    @Bind(R.id.tv_selectAddress)
    TextView tv_selectAddress;
    @Bind(R.id.tv_couponMoney)
    TextView tvCouponMoney;

    private GoodImageAdapter adapter;
    private String norm, num, price, goodId, otherId, choose, type, addressId, coupleId, connectPrices;
    private double realMoney;
    private List<String> selectGoodsImage = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_order, "填写订单");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        norm = getIntent().getStringExtra("norm");
        num = getIntent().getStringExtra("num");
        price = getIntent().getStringExtra("price");
        connectPrices = getIntent().getStringExtra("connectPrices");
        goodId = getIntent().getStringExtra("goodId");
        otherId = getIntent().getStringExtra("otherId");
        choose = getIntent().getStringExtra("choose");
        type = getIntent().getStringExtra("type");
        selectGoodsImage = (List<String>) getIntent().getSerializableExtra("selectGoodsImage");
        tvGoodCount.setText(selectGoodsImage.size() + "件");
        Log.i("tag", connectPrices);

        adapter = new GoodImageAdapter(this, selectGoodsImage);
        gvGoods.setAdapter(adapter);
        realMoney = Double.parseDouble(price);
        tvOldcost.setText("¥ " + price);
        tvRealmoney.setText("¥ " + realMoney);
    }

    @OnClick({R.id.rl_address, R.id.rl_yhq, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                Intent intent = new Intent(FillOrderActivity.this, SendtoAddressActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.rl_yhq:
                Intent intent2 = new Intent(FillOrderActivity.this, UseCouponsActivity.class);
                startActivityForResult(intent2, 101);
                break;
            case R.id.btn_submit:
                if (StringUtil.isEmpty(addressId)) {
                    showToast("请选择配送地址");
                } else {
                    submitOrder();
                }
                break;
        }
    }

    private void submitOrder() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("goodId", goodId);
        params.put("otherId", otherId);
        if (StringUtil.isEmpty(etMessage.getEditableText().toString().trim())) {
            params.put("content", "-1");
        } else {
            params.put("content", etMessage.getEditableText().toString().trim());
        }
        params.put("choose", choose);
        params.put("norm", norm);
        params.put("num", num);
        params.put("type", type);
        if (type.equals("2")) {
            params.put("price", connectPrices);
        } else {
            params.put("price", realMoney + "");
        }
        params.put("addressId", addressId);
        if (StringUtil.isEmpty(coupleId)) {
            params.put("coupleId", "-1");
        } else {
            params.put("coupleId", coupleId);
        }
        Log.i("tag", Constants.BASE_URL + "addOrder&" + params);
        client.post(Constants.BASE_URL + "addOrder", params, new AsyncHttpResponseHandler() {
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
                        showToast("订单提交成功");
                        MyApplication.getInstance().setStringValue("buy_type", null);
                        MyApplication.getInstance().setStringValue("buy_weight", null);
                        MyApplication.getInstance().setStringValue("buy_price", null);
                        MyApplication.getInstance().setStringValue("buy_normId", null);
                        startActivityForNoIntent(PayActivity.class);
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 100) {
                addressId = data.getStringExtra("addressId");
                tvAddress.setText(data.getStringExtra("address"));
                tvName.setText(data.getStringExtra("username"));
                tvTel.setText(data.getStringExtra("tel"));
                tv_selectAddress.setVisibility(View.GONE);
                tvAddress.setVisibility(View.VISIBLE);
            }
            if (requestCode == 101) {
                coupleId = data.getStringExtra("coupons_id");
                String coupons_money = data.getStringExtra("coupons_money");
                tvCouponMoney.setText("¥ " + coupons_money);
                realMoney = MoneyCalculate.substract(Double.parseDouble(price), Double.parseDouble
                        (coupons_money));
                tvRealmoney.setText("¥ " + realMoney);
            }
        }
    }
}
