package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/9.
 */
public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.tv_info)
    TextView tvInfo;
    @Bind(R.id.rl_shengming)
    RelativeLayout rlShengming;
    @Bind(R.id.tv_feedback)
    TextView tvFeedback;
    @Bind(R.id.rl_help)
    RelativeLayout rlHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus, "关于我们");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
    }

    @OnClick({R.id.rl_shengming, R.id.rl_help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_shengming:
                break;
            case R.id.rl_help:
                break;
        }
    }
}
