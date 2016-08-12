package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.AddAddressActivity;
import sinia.com.linkfarmnew.adapter.AddressAdapter;
import sinia.com.linkfarmnew.adapter.ZiTiAddressAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenu;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuCreator;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuItem;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuListView;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class ZiTiAddressFragment extends BaseFragment {
    private View rootView;
    @Bind(R.id.listview)
    ListView listview;

    private ZiTiAddressAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ziti_address, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        adapter = new ZiTiAddressAdapter(getActivity());
        listview.setAdapter(adapter);
    }


}
