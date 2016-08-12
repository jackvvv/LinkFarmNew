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
import sinia.com.linkfarmnew.activity.AddressManagerActivity;
import sinia.com.linkfarmnew.adapter.AddressAdapter;
import sinia.com.linkfarmnew.adapter.ShopCollectAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenu;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuCreator;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuItem;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuListView;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class SendtoAddressFragment extends BaseFragment {
    private View rootView;
    @Bind(R.id.listview)
    SwipeMenuListView listview;
    @Bind(R.id.tv_add)
    TextView tvAdd;

    private AddressAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_address_manager, null);
        ButterKnife.bind(this,rootView);
        initData();
        return rootView;
    }

    private void initData() {
        adapter = new AddressAdapter(getActivity());
        listview.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0x42, 0x41)));
                deleteItem.setWidth(AppInfoUtil.dip2px(getActivity(), 90));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        listview.setMenuCreator(creator);
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {
                switch (index) {
                    case 0:
//                        String id = list.get(position).getCompanyId();
//                        deleteOrder(id, position);
                }
                return false;
            }
        });
    }


    @OnClick(R.id.tv_add)
    public void onClick() {
        Intent intent = new Intent(getActivity(), AddAddressActivity.class);
        startActivity(intent);
    }
}
