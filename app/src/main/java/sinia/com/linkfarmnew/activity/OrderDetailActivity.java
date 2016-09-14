package sinia.com.linkfarmnew.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
import sinia.com.linkfarmnew.adapter.OrderGoodsAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.OrderDetailBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MoneyCalculate;
import sinia.com.linkfarmnew.utils.StringUtil;
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
    @Bind(R.id.tv_message)
    TextView tv_message;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.tv_couponMoney)
    TextView tvCouponMoney;
    @Bind(R.id.tv_self_tip)
    TextView tvSelfTip;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;

    private OrderGoodsAdapter adapter;
    private String orderId, merImage, coupleId = "-1";
    private int orderStatus;
    private double realMoney, price;
    private OrderDetailBean bean;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<OrderDetailBean.GoodItemsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail, "订单详情");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
        getOrderDetail();
    }

    private void initData() {
        orderId = getIntent().getStringExtra("orderId");
        merImage = getIntent().getStringExtra("merImage");
        adapter = new OrderGoodsAdapter(this, list);
        lvGoods.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lvGoods);
    }

    private void getOrderDetail() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", orderId);
        Log.i("tag", Constants.BASE_URL + "orderDetail&" + params);
        client.post(Constants.BASE_URL + "orderDetail", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    bean = gson.fromJson(s, OrderDetailBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        setOrderData(bean);
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void setOrderData(OrderDetailBean bean) {
        tvOrderNum.setText("订单号：" + bean.getOrderNum());
        tvPerson.setText(bean.getName());
        tvShopName.setText(bean.getMerName());
        tvTel.setText(bean.getTelephone());
        tvAddress.setText(bean.getAddress());
        tv_message.setText(bean.getContent());
        orderTime.setText("下单时间：" + bean.getCreateTime());
        tvOldcost.setText("¥ " + StringUtil.formatePrice(bean.getPrice()));
        tvRealmoney.setText("¥ " + StringUtil.formatePrice(bean.getTruePrice()));
        price = bean.getPrice();
        if (1 == bean.getPayType()) {
            tvPayType.setText("在线支付");
        } else {
            tvPayType.setText("在线支付");
        }
        if (1 == bean.getDeType()) {
            tvSendType.setText("配送");
            tvSelfTip.setVisibility(View.GONE);
        } else {
            tvSendType.setText("自提");
            tvSelfTip.setText("自提运费和商家线下结算");
        }
        orderStatus = bean.getOrderStatus();
        if (1 == orderStatus) {
            //待支付
            btn1.setText("取消订单");
            btn2.setText("去支付");
        }
        if (2 == orderStatus) {
            //待发货
            v1.setVisibility(View.GONE);
            rlYhq.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setText("申请退货");
            btn2.setBackgroundResource(R.drawable.green_round_edit);
        }
        if (3 == orderStatus) {
            //待收货
            btn1.setText("申请退货");
            btn1.setTextColor(Color.parseColor("#962722"));
            btn1.setBackgroundResource(R.drawable.red_round_edit);
            btn2.setText("确认送达");
            v1.setVisibility(View.GONE);
            rlYhq.setVisibility(View.GONE);
        }
        if (5 == orderStatus) {
            //灰色的申请退货
            btn1.setVisibility(View.GONE);
            btn2.setText("申请退货");
            btn2.setBackgroundResource(R.drawable.gray_round_edit);
            btn2.setClickable(false);
            v1.setVisibility(View.GONE);
            rlYhq.setVisibility(View.GONE);
        }
        if (6 == orderStatus) {
            //删除订单
            btn2.setText("删除订单");
            btn2.setBackgroundResource(R.drawable.green_round_edit);
            btn1.setVisibility(View.GONE);
            btn2.setClickable(true);
            v1.setVisibility(View.GONE);
            rlYhq.setVisibility(View.GONE);
        }
        if (7 == orderStatus) {
            //申请退货
            btn1.setVisibility(View.GONE);
            btn2.setText("再次申请");
            btn2.setBackgroundResource(R.drawable.green_round_edit);
            btn2.setClickable(true);
            v1.setVisibility(View.GONE);
            rlYhq.setVisibility(View.GONE);
        }
        int commentStatus = bean.getComStatus();
        if (4 == orderStatus) {
            //已完成
            if (1 == commentStatus) {
                //已评价
                btn2.setBackgroundResource(R.drawable.gray_round_edit);
                btn2.setClickable(false);
            } else {
                btn2.setBackgroundResource(R.drawable.green_round_edit);
            }
            btn1.setVisibility(View.GONE);
            btn2.setText("服务评价");
            v1.setVisibility(View.GONE);
            rlYhq.setVisibility(View.GONE);
        }
        list.clear();
        list.addAll(bean.getGoodItems());
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.rl_yhq, R.id.btn2, R.id.btn1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_yhq:
                Intent intent2 = new Intent(OrderDetailActivity.this, UseCouponsActivity.class);
                startActivityForResult(intent2, 101);
                break;
            case R.id.btn2:
                if (orderStatus == 1) {
                    //支付
                    Intent intent = new Intent(OrderDetailActivity.this, PayActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("payMoney", realMoney + "");
                    intent.putExtra("coupleId", coupleId);
                    intent.putExtra("norm", "2");
                    startActivity(intent);
                }
                if (orderStatus == 2) {
                    //申请退货
                    Intent intent = new Intent(OrderDetailActivity.this, ApplyReturnActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                }
                if (orderStatus == 3) {
                    //确认送达
                    confirmOrderSend(orderId);
                }
                if (orderStatus == 4) {
                    //评价
                    Intent intent = new Intent(OrderDetailActivity.this, ServiceCommentActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("merImage", merImage);
                    startActivity(intent);
                }
                if (orderStatus == 6) {
                    //删除订单
                    cancelOrder(orderId);
                }
                if (orderStatus == 7) {
                    //再次申请退货
                    Intent intent = new Intent(OrderDetailActivity.this, ApplyReturnActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                }
                break;
            case R.id.btn1:
                if (orderStatus == 1) {
                    //取消订单
                    cancelOrder(orderId);
                }
                if (orderStatus == 3) {
                    //申请退货
                    Intent intent = new Intent(OrderDetailActivity.this, ApplyReturnActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 101) {
                coupleId = data.getStringExtra("coupons_id");
                if (StringUtil.isEmpty(coupleId)) {
                    coupleId = "-1";
                }
                String coupons_money = data.getStringExtra("coupons_money");
                tvCouponMoney.setText("¥ " + coupons_money);
                realMoney = MoneyCalculate.substract(price, Double.parseDouble(coupons_money));
                tvRealmoney.setText("¥ " + realMoney);
            }
        }
    }

    public void confirmOrderSend(String orderId) {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", orderId);
        Log.i("tag", Constants.BASE_URL + "comGetOrder&" + params);
        client.post(Constants.BASE_URL + "comGetOrder", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("订单确认送达成功");
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    public void cancelOrder(String orderId) {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", orderId);
        Log.i("tag", Constants.BASE_URL + "delayOrder&" + params);
        client.post(Constants.BASE_URL + "delayOrder", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("取消订单成功");
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }
}
