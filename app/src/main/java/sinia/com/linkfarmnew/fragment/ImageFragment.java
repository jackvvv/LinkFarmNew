package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class ImageFragment extends BaseFragment {

    @Bind(R.id.webView)
    WebView webView;
    private View rootView;
    private boolean hasInited = false;
    private GoodsDetailBean goodsBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_image, null);
        ButterKnife.bind(this, rootView);
        goodsBean = (GoodsDetailBean) getArguments().get("goodsBean");
        return rootView;
    }

    public void initImg(String imgUrl) {
        if (null != webView && !hasInited) {
            hasInited = true;
            webView.loadUrl(imgUrl);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
