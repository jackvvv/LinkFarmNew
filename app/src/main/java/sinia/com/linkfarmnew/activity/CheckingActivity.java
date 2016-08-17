package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class CheckingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking, "审核中");
        getDoingView().setVisibility(View.GONE);
    }
}
