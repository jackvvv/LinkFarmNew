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
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtils;
import sinia.com.linkfarmnew.utils.ValidationUtils;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class ModifyPasswordActivity extends BaseActivity {

    @NotEmpty(message = "请输入旧密码")
    @Order(3)
    @Bind(R.id.et_oldpassword)
    EditText et_oldpassword;
    @Password(sequence = 1, message = "请输入新密码")
    @Order(4)
    @Bind(R.id.et_newpwd)
    EditText et_newpwd;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Order(5)
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
        setContentView(R.layout.activity_modifypwd, "修改密码");
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
                findPassword();
            }
        });
    }

    private void findPassword() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("password", et_oldpassword.getEditableText().toString().trim());
        params.put("content", et_newpwd.getEditableText().toString().trim());
        params.put("type", "1");
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("choose", "2");
        Log.i("tag", Constants.BASE_URL + "changePassOrTel&" + params);
        client.post(Constants.BASE_URL + "changePassOrTel", params, new AsyncHttpResponseHandler() {
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
                        showToast("密码修改成功");
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    } else {
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                validator.validate();
                break;
        }
    }

}
