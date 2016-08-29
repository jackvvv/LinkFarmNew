package sinia.com.linkfarmnew.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.AddAddressActivity;
import sinia.com.linkfarmnew.activity.AddressManagerActivity;
import sinia.com.linkfarmnew.adapter.AddressAdapter;
import sinia.com.linkfarmnew.adapter.ShopCollectAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.AddressListBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
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
    private List<AddressListBean.AddressBean> list = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_address_manager, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAddressList();
    }

    private void initData() {
        adapter = new AddressAdapter(getActivity(), list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("address", list.get(i).getAddArea() + list.get(i).getAddAddress());
                intent.putExtra("username", list.get(i).getAddName());
                intent.putExtra("tel", list.get(i).getAddTelephone());
                intent.putExtra("addressId", list.get(i).getAddId());
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });
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
                        String id = list.get(position).getAddId();
                        deleteAddress(id, position);
                }
                return false;
            }
        });
        listview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    listview.getParent().requestDisallowInterceptTouchEvent(false);
                else
                    listview.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void getAddressList() {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", "1");
        Log.i("tag", Constants.BASE_URL + "addressList&" + params);
        client.post(Constants.BASE_URL + "addressList", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    AddressListBean bean = gson.fromJson(s, AddressListBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        list.clear();
                        list.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void deleteAddress(String addressId, final int p) {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", addressId);
        Log.i("tag", Constants.BASE_URL + "delAddress&" + params);
        client.post(Constants.BASE_URL + "delAddress", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("删除成功");
                        list.remove(p);
                        adapter.notifyDataSetChanged();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    @OnClick(R.id.l)
    public void onClick() {
        Intent intent = new Intent(getActivity(), AddAddressActivity.class);
        startActivity(intent);
    }
}
