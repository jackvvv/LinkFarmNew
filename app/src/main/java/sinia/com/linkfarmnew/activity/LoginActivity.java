package sinia.com.linkfarmnew.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.umeng.message.ALIAS_TYPE;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.LoginBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ValidationUtils;

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
    private AsyncHttpClient client = new AsyncHttpClient();
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, "登录");
        ButterKnife.bind(this);
        getBackView().setVisibility(View.GONE);
        getDoingView().setVisibility(View.GONE);
        validator = new Validator(this);
        initView();
    }

    private void initView() {
        flag = getIntent().getStringExtra("flag");
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                login();
            }
        });
    }

    private void login() {
        showLoad("登录中...");
        RequestParams params = new RequestParams();
        params.put("telephone", etPhone.getEditableText().toString().trim());
        params.put("password", etPassword.getEditableText().toString().trim());
        params.put("content", "-1");
        params.put("type", "1");
        Log.i("tag", Constants.BASE_URL + "login&" + params);
        client.post(Constants.BASE_URL + "login", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    LoginBean bean = gson.fromJson(s, LoginBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        if ("1".equals(bean.getCheakStatus())) {
                            showToast("登录成功");
                            MyApplication.getInstance().setBooleanValue(
                                    "is_login", true);
                            MyApplication.getInstance().setStringValue(
                                    "userId", bean.getId());
                            MyApplication.getInstance().setLoginBean(bean);
                            ActivityManager.getInstance()
                                    .finishCurrentActivity();
                            setAlias(bean.getId(), "ALIAS_TYPE.SINA_WEIBO");
                        } else if ("2".equals(bean.getCheakStatus())) {
                            startActivityForNoIntent(CheckingActivity.class);
                        } else if ("3".equals(bean.getCheakStatus())) {
                            showToast("审核失败，请重新注册并提交审核");
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("第一次登陆，请先第三方通过后，进行手机号号和密码登录");
                    } else {
                        showToast("手机号或密码输入有误");
                    }
                }
            }
        });
    }

    private void setAlias(String alias, String aliasType) {
        new AddAliasTask(alias, aliasType).execute();
    }

    class AddAliasTask extends AsyncTask<Void, Void, Boolean> {

        String alias;
        String aliasType;

        public AddAliasTask(String aliasString, String aliasTypeString) {
            this.alias = aliasString;
            this.aliasType = aliasTypeString;
        }

        protected Boolean doInBackground(Void... params) {
            try {
                return MyApplication.mPushAgent.addAlias(alias, ALIAS_TYPE.SINA_WEIBO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
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
                ActivityManager.getInstance()
                        .finishCurrentActivity();
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_wechat:
                break;
            case R.id.tv_weibo:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ("1".equals(flag)) {
            ActivityManager.getInstance().finishAllActivity();
        }
    }
}
