package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.ShopCollectAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.MyCollectBean;
import sinia.com.linkfarmnew.bean.RefreshBean;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class ShopCollectFragment extends BaseFragment {
    private ListView listView;
    private View rootView;
    private ShopCollectAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<MyCollectBean.CollectBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods_collect, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCollectData();
    }

    private void getCollectData() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", "2");
        Log.i("tag", Constants.BASE_URL + "myColltion&" + params);
        client.post(Constants.BASE_URL + "myColltion", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    MyCollectBean bean = gson.fromJson(s, MyCollectBean.class);
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

    private void initData() {
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new ShopCollectAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }
}
