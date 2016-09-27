package sinia.com.linkfarmnew.fragment;

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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.AddressManagerActivity;
import sinia.com.linkfarmnew.activity.GoodsDetailActivity;
import sinia.com.linkfarmnew.adapter.GoodsFootAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.MyFootBean;
import sinia.com.linkfarmnew.bean.RefreshBean;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenu;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuCreator;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuItem;
import sinia.com.linkfarmnew.view.swipmenulistview.SwipeMenuListView;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static sinia.com.linkfarmnew.R.id.listview;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class GoodsFootFragment extends BaseFragment {
    private SwipeMenuListView listView;
    private View rootView;
    private GoodsFootAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<MyFootBean.DetailBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods_collect, null);
        ButterKnife.bind(this, rootView);
        initData();
        getFootPrintData();
        return rootView;
    }

    private void getFootPrintData() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", "1");
        Log.i("tag", Constants.BASE_URL + "myHis&" + params);
        client.post(Constants.BASE_URL + "myHis", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    MyFootBean bean = gson.fromJson(s, MyFootBean.class);
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
        listView = (SwipeMenuListView) rootView.findViewById(R.id.listView);
        adapter = new GoodsFootAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String goodId = list.get(i).getGoodId();
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goodId", goodId);
                getActivity().startActivity(intent);
            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0x42, 0x41)));
                deleteItem.setWidth(AppInfoUtil.dip2px(getActivity(), 90));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {
                switch (index) {
                    case 0:
                        String id = list.get(position).getHisId();
                        deleteAddress(id, position);
                }
                return false;
            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    listView.getParent().requestDisallowInterceptTouchEvent(false);
                else
                    listView.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void deleteAddress(String addressId, final int p) {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", addressId);
        Log.i("tag", Constants.BASE_URL + "delHis&" + params);
        client.post(Constants.BASE_URL + "delHis", params, new AsyncHttpResponseHandler() {
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
                        showToast("删除失败");
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
