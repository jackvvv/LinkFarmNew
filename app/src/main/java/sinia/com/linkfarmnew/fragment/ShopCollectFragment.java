package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.ShopCollectAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class ShopCollectFragment extends BaseFragment {
    private ListView listView;
    private View rootView;
    private ShopCollectAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods_collect, null);
        ButterKnife.bind(this.rootView);
        initData();
        return rootView;
    }

    private void initData() {
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new ShopCollectAdapter(getActivity());
        listView.setAdapter(adapter);
    }
}