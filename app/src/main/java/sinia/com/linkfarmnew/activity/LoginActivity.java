package sinia.com.linkfarmnew.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.umeng.message.ALIAS_TYPE;
import com.umeng.message.proguard.L;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.LoginBean;
import sinia.com.linkfarmnew.myinterface.LoginApi;
import sinia.com.linkfarmnew.myinterface.OnLoginListener;
import sinia.com.linkfarmnew.myinterface.UserInfo;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ValidationUtils;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class LoginActivity extends BaseActivity {

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

    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    private String platform;
    private Context context;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, "登录");
        ButterKnife.bind(this);
        getBackView().setVisibility(View.GONE);
        getDoingView().setVisibility(View.GONE);
        validator = new Validator(this);
        initView();
        ShareSDK.initSDK(this);
    }

    private void initView() {
        flag = getIntent().getStringExtra("flag");
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                login(etPhone.getEditableText().toString().trim(), etPassword.getEditableText().toString().trim(),
                        "-1", "-1");
            }
        });
    }

    private void login(String tel, String pwd, String thirdId, String thirdType) {
        showLoad("登录中...");
        RequestParams params = new RequestParams();
        params.put("telephone", tel);
        params.put("password", pwd);
        params.put("content", thirdId);
        params.put("choose", thirdType);
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
                            startActivityForNoIntent(MainActivity.class);
                            ActivityManager.getInstance()
                                    .finishCurrentActivity();
                            setAlias(bean.getId(), "ALIAS_TYPE.SINA_WEIBO");
                        } else if ("2".equals(bean.getCheakStatus())) {
                            startActivityForNoIntent(CheckingActivity.class);
                        } else if ("3".equals(bean.getCheakStatus())) {
                            showToast("审核失败，请重新注册并提交审核");
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("第一次登陆，请先第三方通过后，进行手机号和密码登录");
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
                Intent intent = new Intent();
                intent.putExtra("isThridRegister", false);
                startActivityForIntent(RegisterActivity.class, intent);
                break;
            case R.id.tv_find_pwd:
                startActivityForNoIntent(ForgetPasswordActivity.class);
                ActivityManager.getInstance()
                        .finishCurrentActivity();
                break;
            case R.id.tv_qq:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                if (!qq.isAuthValid()) {
//                    thirdLogin(qq);
                    login(qq.getName());
                } else {
                    login("-1", "-1", qq.getDb().getUserId(), "1");
//                    qq.removeAccount();
                }
                break;
            case R.id.tv_wechat:
                Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
                if (!weChat.isAuthValid()) {
//                    thirdLogin(weChat);
                    login(weChat.getName());
                } else {
                    login("-1", "-1", weChat.getDb().getUserId(), "2");
//                    weChat.removeAccount();
                }
                break;
            case R.id.tv_weibo:
                break;
        }
    }

    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                return true;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(this);
    }

    private void thirdLogin(Platform plat) {
        plat.SSOSetting(false);//设置false表示使用SSO授权方式
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
                showToast("授权成功");
                Log.i("tag", "授权成功------授权成功");
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_COMPLETE;
                    msg.arg2 = action;
//                    msg.obj =  new Object[] {plat.getName(), res};
                    msg.obj = plat;
                    handler.sendMessage(msg);

//                    String openId = plat.getDb().getUserId();
//                    String userIcon = plat.getDb().getUserIcon();
//                    Intent intent = new Intent();
//                    intent.putExtra("thirdId", openId);
//                    intent.putExtra("userIcon", userIcon);
//                    intent.putExtra("isThirdRegister", true);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivityForIntent(RegisterActivity.class, intent);
                }
            }

            public void onError(Platform plat, int action, Throwable t) {
                showToast("授权失败");
                Log.i("tag", "授权失败------" + t);
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_ERROR;
                    msg.arg2 = action;
                    msg.obj = t;
                    handler.sendMessage(msg);
                }
                t.printStackTrace();
            }

            public void onCancel(Platform plat, int action) {
                showToast("授权取消");
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_CANCEL;
                    msg.arg2 = action;
                    msg.obj = plat;
                    handler.sendMessage(msg);
                }
            }
        });
        plat.showUser(null);//授权并获取用户信息
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ("1".equals(flag)) {
            ActivityManager.getInstance().finishAllActivity();
        }
    }
}
