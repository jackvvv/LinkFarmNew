package sinia.com.linkfarmnew.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class ServiceActivity extends BaseActivity {

    @Bind(R.id.tv_online)
    TextView tvOnline;
    @Bind(R.id.tv_call)
    TextView tvCall;

    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service, "客服");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_online, R.id.tv_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_online:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("link","http://kefu.easemob.com/webim/im.html?tenantId=27590");
                startActivity(intent);
                break;
            case R.id.tv_call:
                callService();
                break;
        }
    }

    private void callService() {
        materialDialog = new MaterialDialog(ServiceActivity.this);
        materialDialog.setTitle("联系客服").setMessage("400-888-666")
                .setPositiveButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400-888-666"));
                        if (ActivityCompat.checkSelfPermission(ServiceActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
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
