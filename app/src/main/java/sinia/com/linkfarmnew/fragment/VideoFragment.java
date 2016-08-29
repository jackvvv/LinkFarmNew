package sinia.com.linkfarmnew.fragment;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class VideoFragment extends BaseFragment {

    //    @Bind(R.id.webView)
    static com.tencent.smtt.sdk.WebView webView;
    private View rootView;
    private GoodsDetailBean goodsBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video, null);
        ButterKnife.bind(this, rootView);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        goodsBean = (GoodsDetailBean) getArguments().get("goodsBean");
        webView = (com.tencent.smtt.sdk.WebView) rootView.findViewById(R.id.webView);
        initData();
        return rootView;
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    public void initData() {
        webView.loadUrl(goodsBean.getMoiveLink());
        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        /**
         * 解决二级fragment嵌套的时候，fragment重新创建的时候，外链页面load失败
         */
        if (webView != null) {
            webView.stopLoading();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }

}
