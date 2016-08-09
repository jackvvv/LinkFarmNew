package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.OpenCityAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.ActivityManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location, "定位");
        ButterKnife.bind(this);
        getDoingView().setText("保存");
        initData();
    }

    private void initData() {
        adapter = new OpenCityAdapter(this);
        gridView.setAdapter(adapter);
    }

    @Override
    public void doing() {
        super.doing();
        ActivityManager.getInstance().finishCurrentActivity();
    }
}
