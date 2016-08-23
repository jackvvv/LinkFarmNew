package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    WebView webView;
    private View rootView;
    private GoodsDetailBean goodsBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video, null);
        ButterKnife.bind(this, rootView);
        goodsBean = (GoodsDetailBean) getArguments().get("goodsBean");
        webView = (WebView) rootView.findViewById(R.id.webView);
//        initData();
        return rootView;
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    public void initData() {
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        webView.loadUrl(goodsBean.getMoiveLink());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String url) { //
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
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
