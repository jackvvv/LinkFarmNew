package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;

import com.tencent.smtt.sdk.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;


/**
 * Created by 忧郁的眼神 on 2016/10/20.
 */

public class ProtecolActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protecol, "用户协议");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);

        webView.loadUrl("file:///android_asset/protecol.html");
    }
}
