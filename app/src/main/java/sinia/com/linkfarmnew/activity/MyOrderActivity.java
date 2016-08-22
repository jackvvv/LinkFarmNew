package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyOrderAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.ClassfyGoodsBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.MyOrderListBean;
import sinia.com.linkfarmnew.myinterface.OrderOperatorInterface;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class MyOrderActivity extends BaseActivity implements OrderOperatorInterface {

    @Bind(R.id.listView)
    ListView listView;

    private String title, type;
    private MyOrderAdapter adapter;
    private List<MyOrderListBean.OrderBean> list = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        setContentView(R.layout.activity_my_order, title);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderData();
    }

    private void getOrderData() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", type);
        Log.i("tag", Constants.BASE_URL + "cusOrderManager&" + params);
        client.post(Constants.BASE_URL + "cusOrderManager", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    MyOrderListBean bean = gson.fromJson(s, MyOrderListBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        list.clear();
                        list.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void initData() {
        adapter = new MyOrderAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void confirmOrderSend(String orderId, final int pos) {
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
                        list.remove(pos);
                        adapter.notifyDataSetChanged();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    @Override
    public void cancelOrder(String orderId, final int pos) {
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
                        list.remove(pos);
                        adapter.notifyDataSetChanged();
                        showToast("取消订单成功");
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

}
