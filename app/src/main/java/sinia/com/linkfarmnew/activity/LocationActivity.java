package sinia.com.linkfarmnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.OpenCityAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.HomePageBean;
import sinia.com.linkfarmnew.bean.OpenCityBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.view.MyGridView;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class LocationActivity extends BaseActivity {

    @Bind(R.id.tv_nowcity)
    TextView tvNowcity;
    @Bind(R.id.gridView)
    MyGridView gridView;
    private OpenCityAdapter adapter;
    private String city, selectCity;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<OpenCityBean.City> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location, "定位");
        ButterKnife.bind(this);
        getDoingView().setText("保存");
        initData();
        getOpenCityList();
    }

    private void getOpenCityList() {
        client.post(Constants.BASE_URL + "openCity", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    OpenCityBean bean = gson.fromJson(s, OpenCityBean.class);
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
        city = getIntent().getStringExtra("city");
        tvNowcity.setText(city);
        adapter = new OpenCityAdapter(this, list);
        gridView.setAdapter(adapter);
        adapter.selectPosition = 0;
        if (0 != list.size()) {
            selectCity = list.get(0).getCityName();
        } else {
            selectCity = "南京";
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.selectPosition = i;
                selectCity = list.get(i).getCityName();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void doing() {
        super.doing();
        Intent intent = new Intent();
        intent.putExtra("selectCity", selectCity);
        setResult(RESULT_OK, intent);
        finish();
    }
}
