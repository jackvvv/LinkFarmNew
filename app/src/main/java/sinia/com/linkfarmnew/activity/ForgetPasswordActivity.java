package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.ValidateCodeBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.StringUtils;
import sinia.com.linkfarmnew.utils.ValidationUtils;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class ForgetPasswordActivity extends BaseActivity {

    @Bind(R.id.tv_get_code)
    TextView tvGetCode;
    @Pattern(regex = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", message =
            "请输入正确的手机号码")
    @Order(1)
    @Bind(R.id.et_phone)
    EditText etPhone;
    @NotEmpty(message = "请输入验证码")
    @Order(2)
    @Bind(R.id.et_code)
    EditText etCode;
    @Password(sequence = 1, message = "请输入密码")
    @Order(3)
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Order(4)
    @ConfirmPassword(message = "两次输入的密码不一致，请重新输入")
    @Bind(R.id.et_confirm)
    EditText etConfirm;

    private Validator validator;
    private int i = 60;
    private String code;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd, "忘记密码");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        validator = new Validator(this);
        initView();
    }

    private void initView() {
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                if (!etCode.getEditableText().toString().trim().equals(code)) {
                    showToast("验证码不正确");
                    return;
                }
                findPassword();
            }
        });
    }

    private void findPassword() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("telephone", etPhone.getEditableText().toString().trim());
        params.put("password", etPassword.getEditableText().toString().trim());
        params.put("type", "1");
        Log.i("tag", Constants.BASE_URL + "updatePassword&" + params);
        client.post(Constants.BASE_URL + "updatePassword", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("密码重置成功");
                        startActivityForNoIntent(LoginActivity.class);
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    } else {
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_get_code, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (!StringUtils.isMobileNumber(etPhone.getEditableText().toString().trim())) {
                    showToast("请输入正确的手机号码");
                } else {
                    tvGetCode.setClickable(false);
                    tvGetCode.setText("重新发送(" + i + ")");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; i > 0; i--) {
                                handler.sendEmptyMessage(-9);
                                if (i <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(-8);
                        }
                    }).start();
                    getCode();
                }
                break;
            case R.id.tv_login:
                validator.validate();
                break;
        }
    }

    private void getCode() {
        showLoad("正在发送短信...");
        RequestParams params = new RequestParams();
        params.put("telephone", etPhone.getEditableText().toString().trim());
        params.put("type", "1");
        params.put("choose", "2");
        Log.i("tag", Constants.BASE_URL + "gainValidateCode&" + params);
        client.post(Constants.BASE_URL + "gainValidateCode", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    ValidateCodeBean bean = gson.fromJson(s, ValidateCodeBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("验证码已发送");
                        code = bean.getValidateCode();
                        showToast(code);
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("该手机号已经被注册");
                    } else {
                        showToast("验证码发送失败");
                    }
                }
            }
        });
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -9) {
                tvGetCode.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                tvGetCode.setText("获取验证码");
                tvGetCode.setClickable(true);
                i = 60;
            }
        }
    };
}
