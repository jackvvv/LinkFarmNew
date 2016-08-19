package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class VideoFragment extends BaseFragment {

    @Bind(R.id.webView)
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
        initData();
        return rootView;
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    private void initData() {
//        webView.loadUrl(goodsBean.getMoiveLink());
        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
