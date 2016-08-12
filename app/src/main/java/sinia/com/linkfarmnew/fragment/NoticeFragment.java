package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.AnnouncementAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class NoticeFragment extends BaseFragment {
    private ListView listView;
    private View rootView;
    private AnnouncementAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_my_coupons, null);
        ButterKnife.bind(this,rootView);
        initData();
        return rootView;
    }

    private void initData() {
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new AnnouncementAdapter(getActivity());
        listView.setAdapter(adapter);
    }
}
