package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyCouponsAdapter;
import sinia.com.linkfarmnew.adapter.UseCouponsAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_coupons, "优惠券");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        adapter = new UseCouponsAdapter(this);
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
                break;
        }
    }
}
