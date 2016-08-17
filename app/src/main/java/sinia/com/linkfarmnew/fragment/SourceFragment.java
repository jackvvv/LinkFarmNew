package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.SourceAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class SourceFragment extends BaseFragment {

    @Bind(R.id.listView)
    ListView listView;
    private View rootView;
    private GoodsDetailBean goodsBean;
    private SourceAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_source, null);
        ButterKnife.bind(this, rootView);
        goodsBean = (GoodsDetailBean) getArguments().get("goodsBean");
        initData();
        return rootView;
    }

    public static SourceFragment newInstance() {
        SourceFragment fragment = new SourceFragment();
        return fragment;
    }

    private void initData() {
        adapter = new SourceAdapter(getActivity(), goodsBean.getOrginitems());
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
