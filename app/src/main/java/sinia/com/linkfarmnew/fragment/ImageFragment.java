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
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class ImageFragment extends BaseFragment {

    @Bind(R.id.webView)
    WebView webView;
    private View rootView;
    private boolean hasInited = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_image, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void initImg() {
        if (null != webView && !hasInited) {
            hasInited = true;
            webView.loadUrl("http://img.zcool.cn/community/0153fd55d6968132f875a13220fd84.jpg");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
