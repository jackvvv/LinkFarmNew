package sinia.com.linkfarmnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
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
import sinia.com.linkfarmnew.adapter.MyCouponsAdapter;
import sinia.com.linkfarmnew.adapter.UseCouponsAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.CouponListBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class UseCouponsActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.ck_use)
    CheckBox ckUse;
    @Bind(R.id.btn_ok)
    TextView btnOk;
    @Bind(R.id.b)
    RelativeLayout b;

    private UseCouponsAdapter adapter;
    private List<CouponListBean.CouponBean> list = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_coupons, "优惠券");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
        getCouponsData();
    }

    private void getCouponsData() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        Log.i("tag", Constants.BASE_URL + "coupleList&" + params);
        client.post(Constants.BASE_URL + "coupleList", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    CouponListBean bean = gson.fromJson(s, CouponListBean.class);
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
        adapter = new UseCouponsAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.selectPosition = 0;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.selectPosition = i;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.ck_use, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ck_use:
                break;
            case R.id.btn_ok:
                if (ckUse.isChecked()) {
                    //不使用优惠券
                    ActivityManager.getInstance().finishCurrentActivity();
                } else {
                    if (0 != list.size()) {
                        Intent intent = new Intent();
                        intent.putExtra("coupons_money", list.get(adapter.selectPosition).getPrice());
                        intent.putExtra("coupons_id", list.get(adapter.selectPosition).getCouId());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        ActivityManager.getInstance().finishCurrentActivity();
                    }
                }
                break;
        }
    }
}
