package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyTeamAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.MyCollectBean;
import sinia.com.linkfarmnew.bean.RecommendCodeBean;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class MyExpandActivity extends BaseActivity {

    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.listView)
    ListView listView;

    private MyTeamAdapter adapter;
    private List<RecommendCodeBean.RecommendName> list = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_expand, "我的推广");
        ButterKnife.bind(this);
        getDoingView().setText("积分记录");
        initData();
        getRecommendData();
    }

    private void getRecommendData() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        Log.i("tag", Constants.BASE_URL + "myRec&" + params);
        client.post(Constants.BASE_URL + "myRec", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    RecommendCodeBean bean = gson.fromJson(s, RecommendCodeBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        tvCode.setText(bean.getRecCode());
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
        adapter = new MyTeamAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void doing() {
        super.doing();
        startActivityForNoIntent(PointRecordActivity.class);
    }
}
