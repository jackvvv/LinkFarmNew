package sinia.com.linkfarmnew.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.message.PushAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.DataCleanManager;
import sinia.com.linkfarmnew.utils.MyApplication;

/**
 * Created by 忧郁的眼神 on 2016/8/9.
 */
public class SettingsActivity extends BaseActivity {

    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.rl_notice)
    RelativeLayout rlNotice;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.rl_clear)
    RelativeLayout rlClear;
    @Bind(R.id.tv_info)
    TextView tvInfo;
    @Bind(R.id.rl_aboutus)
    RelativeLayout rlAboutus;
    @Bind(R.id.tv_feedback)
    TextView tvFeedback;
    @Bind(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.rl_versin)
    RelativeLayout rlVersin;
    @Bind(R.id.rl_pwd)
    RelativeLayout rlPwd;
    @Bind(R.id.rl_logout)
    RelativeLayout rlLogout;
    @Bind(R.id.img_switch)
    ImageView imgSwitch;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings, "我的设置");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        if (MyApplication.mPushAgent.isEnabled()) {
            imgSwitch.setImageResource(R.drawable.switch_open);
        } else {
            imgSwitch.setImageResource(R.drawable.switch_close);
        }
        tvVersion.setText("v" + AppInfoUtil.getVersionCode(this) + ".0");
    }

    @OnClick({R.id.rl_clear, R.id.rl_aboutus, R.id.rl_feedback, R.id.rl_logout, R.id.img_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clear:
                clearCache();
                break;
            case R.id.rl_aboutus:
                startActivityForNoIntent(AboutUsActivity.class);
                break;
            case R.id.rl_feedback:
                startActivityForNoIntent(FeedBackActivity.class);
                break;
            case R.id.rl_logout:
                logout();
                break;
            case R.id.img_switch:
                if (MyApplication.mPushAgent.isEnabled()) {
                    MyApplication.mPushAgent.disable();
                    imgSwitch.setImageResource(R.drawable.switch_close);
                } else {
                    MyApplication.mPushAgent.enable();
                    imgSwitch.setImageResource(R.drawable.switch_open);
                }
                break;
        }
    }

    private void clearCache() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("提示").setMessage("缓存数据清理后将无法恢复，您确定要清理吗?")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataCleanManager.clearAllCache(SettingsActivity.this);
                        showToast("清理了缓存" + DataCleanManager
                                .getTotalCacheSize(SettingsActivity.this));
                        materialDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        }).show();
    }

    private void logout() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("提示").setMessage("确定退出此账号?")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().setBooleanValue("is_login",
                                false);
                        MyApplication.getInstance().setLoginBean(null);
                        startActivityForNoIntent(LoginActivity.class);
                        ActivityManager.getInstance().finishCurrentActivity();
                        materialDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        }).show();
    }

}
