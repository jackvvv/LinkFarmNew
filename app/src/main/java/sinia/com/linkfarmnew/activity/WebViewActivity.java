package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.view.MyWebView;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class WebViewActivity extends BaseActivity {

    @Bind(R.id.webView)
    MyWebView webView;

    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview, "链接详情");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        link = getIntent().getStringExtra("link");
        webView.loadUrl(link);
    }
}
