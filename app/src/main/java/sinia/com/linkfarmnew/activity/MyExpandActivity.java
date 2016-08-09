package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyTeamAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class MyExpandActivity extends BaseActivity {

    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.listView)
    ListView listView;

    private MyTeamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_expand, "我的推广");
        ButterKnife.bind(this);
        getDoingView().setText("积分记录");
        initData();
    }

    private void initData() {
        adapter = new MyTeamAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void doing() {
        super.doing();
        startActivityForNoIntent(PointRecordActivity.class);
    }
}
