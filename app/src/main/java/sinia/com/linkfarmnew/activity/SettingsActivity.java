package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcw.togglebutton.ToggleButton;

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
    @Bind(R.id.toggleBtn)
    ToggleButton toggleBtn;
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
            toggleBtn.setToggleOn();
        } else {
            toggleBtn.setToggleOff();
        }
        tvVersion.setText("v" + AppInfoUtil.getVersionCode(this) + ".0");
        toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    MyApplication.mPushAgent.enable();
                    toggleBtn.setToggleOn();
                } else {
                    MyApplication.mPushAgent.disable();
                    toggleBtn.setToggleOff();
                }
            }
        });
    }

    @OnClick({R.id.rl_clear, R.id.rl_aboutus, R.id.rl_feedback, R.id.rl_logout})
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
        }
    }

    private void clearCache() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("提示").setMessage("缓存数据清理后将无法恢复，您确定要清理吗?")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("清理了缓存" + DataCleanManager
                                .getTotalCacheSize(SettingsActivity.this));
                        DataCleanManager.clearAllCache(SettingsActivity.this);
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
