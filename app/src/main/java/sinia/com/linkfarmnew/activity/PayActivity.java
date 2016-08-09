package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class PayActivity extends BaseActivity {

    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.ic_wx)
    ImageView icWx;
    @Bind(R.id.imageView3)
    ImageView imageView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.ic_alipay)
    ImageView icAlipay;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay, "支付");
        getDoingView().setVisibility(View.GONE);
    }

    @OnClick({R.id.ic_wx, R.id.ic_alipay, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_wx:
                break;
            case R.id.ic_alipay:
                break;
            case R.id.tv_ok:
                break;
        }
    }
}
