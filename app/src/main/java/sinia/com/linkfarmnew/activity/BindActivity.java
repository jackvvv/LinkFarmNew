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
import cn.sharesdk.framework.ShareSDK;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.LoginBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ValidationUtils;

import static sinia.com.linkfarmnew.R.id.city;

/**
 * Created by 忧郁的眼神 on 2016/10/10.
 */

public class BindActivity extends BaseActivity {

    @Pattern(regex = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(1)
    @Bind(R.id.et_phone)
    EditText etPhone;
    @NotEmpty(message = "请输入密码")
    @Order(2)
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_login)
    TextView tvLogin;

    private Validator validator;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String platType, thirdId;//1.QQ,2.WEXIN,3.WEIBO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind, "绑定");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        validator = new Validator(this);
        initView();
    }

    private void initView() {
        thirdId = getIntent().getStringExtra("thirdId");
        platType = getIntent().getStringExtra("platType");
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                bindAndLogin();
            }

        });
    }

    private void bindAndLogin() {
        showLoad("绑定中...");
        RequestParams params = new RequestParams();
        params.put("telephone", etPhone.getEditableText().toString().trim());
        params.put("password", etPassword.getEditableText().toString().trim());
        params.put("content", thirdId);
        params.put("choose", platType);//第三方平台类型
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
                            showToast("绑定成功");
                            MyApplication.getInstance().setBooleanValue(
                                    "is_login", true);
                            MyApplication.getInstance().setStringValue(
                                    "userId", bean.getId());
                            MyApplication.getInstance().setLoginBean(bean);
                            startActivityForNoIntent(MainActivity.class);
                            ActivityManager.getInstance()
                                    .finishCurrentActivity();
                            setAlias(bean.getId(), "ALIAS_TYPE.SINA_WEIBO");
                        } else if ("2".equals(bean.getCheakStatus())) {
                            startActivityForNoIntent(CheckingActivity.class);
                        } else if ("3".equals(bean.getCheakStatus())) {
                            showToast(bean.getReturnResult());
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast(bean.getReturnResult());
                    } else {
//                        showToast("手机号或密码输入有误");
                        showToast(bean.getReturnResult());
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

    @OnClick({R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                validator.validate();
                break;
        }
    }
}
