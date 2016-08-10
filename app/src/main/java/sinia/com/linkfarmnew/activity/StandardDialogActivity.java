package sinia.com.linkfarmnew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.AppInfoUtil;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class StandardDialogActivity extends Activity {

    @Bind(R.id.img_colse)
    ImageView imgColse;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.img_jian)
    ImageView imgJian;
    @Bind(R.id.et_weight)
    EditText etWeight;
    @Bind(R.id.img_jia)
    ImageView imgJia;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_cart)
    TextView tvCart;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.ll_root)
    RelativeLayout ll_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        int w = AppInfoUtil.getScreenWidth(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, FrameLayout.LayoutParams.WRAP_CONTENT);
        ll_root.setLayoutParams(lp);
    }

    @OnClick({R.id.img_colse, R.id.img_jian, R.id.img_jia, R.id.tv_cart, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_colse:
                finish();
                break;
            case R.id.img_jian:
                break;
            case R.id.img_jia:
                break;
            case R.id.tv_cart:
                finish();
                break;
            case R.id.tv_ok:
                finish();
                break;
        }
    }
}
