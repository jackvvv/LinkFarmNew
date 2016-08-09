package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.tv_get_code)
    TextView tvGetCode;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_company_name)
    EditText etCompanyName;
    @Bind(R.id.et_company_address)
    EditText etCompanyAddress;
    @Bind(R.id.et_contact_name)
    EditText etContactName;
    @Bind(R.id.et_recommend_code)
    EditText etRecommendCode;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register, "注册");
        getDoingView().setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_get_code, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
