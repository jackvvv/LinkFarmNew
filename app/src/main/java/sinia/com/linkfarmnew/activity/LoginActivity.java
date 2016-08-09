package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class LoginActivity extends BaseActivity {

    @Pattern(regex = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(1)
    @Bind(R.id.et_phone)
    EditText etPhone;
    @NotEmpty(message = "请输入密码")
    @Order(2)
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.tv_find_pwd)
    TextView tvFindPwd;
    @Bind(R.id.tv_qq)
    TextView tvQq;
    @Bind(R.id.tv_wechat)
    TextView tvWechat;
    @Bind(R.id.tv_weibo)
    TextView tvWeibo;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, "登录");
        ButterKnife.bind(this);
        getBackView().setVisibility(View.GONE);
        getDoingView().setVisibility(View.GONE);
        validator = new Validator(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_find_pwd, R.id.tv_qq, R.id.tv_wechat, R.id
            .tv_weibo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                validator.validate();
                break;
            case R.id.tv_register:
                startActivityForNoIntent(RegisterActivity.class);
                break;
            case R.id.tv_find_pwd:
                startActivityForNoIntent(ForgetPasswordActivity.class);
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_wechat:
                break;
            case R.id.tv_weibo:
                break;
        }
    }
}
