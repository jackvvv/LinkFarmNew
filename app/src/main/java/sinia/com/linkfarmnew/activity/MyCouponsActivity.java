package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyCouponsAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class MyCouponsActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;

    private MyCouponsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupons, "优惠券");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);

        initData();
    }

    private void initData() {
        adapter = new MyCouponsAdapter(this);
        listView.setAdapter(adapter);
    }
}
