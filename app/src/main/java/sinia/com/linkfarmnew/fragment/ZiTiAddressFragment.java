package sinia.com.linkfarmnew.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
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
import sinia.com.linkfarmnew.adapter.AddressAdapter;
import sinia.com.linkfarmnew.adapter.ZiTiAddressAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.AddressListBean;
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
public class ZiTiAddressFragment extends BaseFragment {
    private View rootView;
    @Bind(R.id.listview)
    ListView listview;

    private ZiTiAddressAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<AddressListBean.AddressBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ziti_address, null);
        ButterKnife.bind(this, rootView);
        initData();
        getAddressList();
        return rootView;
    }

    private void initData() {
        adapter = new ZiTiAddressAdapter(getActivity(), list);
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
    }

    private void getAddressList() {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", "2");
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


}
