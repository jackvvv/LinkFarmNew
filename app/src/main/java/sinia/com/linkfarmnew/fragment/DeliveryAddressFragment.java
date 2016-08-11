package sinia.com.linkfarmnew.fragment;

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
import sinia.com.linkfarmnew.adapter.DeliveryAddressAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class DeliveryAddressFragment extends BaseFragment {
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private View rootView;
    private DeliveryAddressAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_delivery_address, null);
        ButterKnife.bind(this.rootView);
        initData();
        return rootView;
    }

    private void initData() {
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new DeliveryAddressAdapter(getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_ok)
    public void onClick() {
    }
}
