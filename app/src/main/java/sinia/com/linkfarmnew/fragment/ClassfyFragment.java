package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.GoodsListActivity;
import sinia.com.linkfarmnew.activity.PayActivity;
import sinia.com.linkfarmnew.adapter.ClassfyAdapter;
import sinia.com.linkfarmnew.adapter.ClassfyGridAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.AddOrderBean;
import sinia.com.linkfarmnew.bean.ClassfyListBean;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class ClassfyFragment extends BaseFragment {

    private ListView lvClassfy;
    private GridView gvRight;
    private View rootView;
    private ClassfyAdapter classfyAdapter;
    private ClassfyGridAdapter gridAdapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<ClassfyListBean.BigClassBean> leftList = new ArrayList<>();
    private List<ClassfyListBean.BigClassBean.SmallitemsBean> rightList = new ArrayList<>();
    private ClassfyListBean bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_classfy, null);
        ButterKnife.bind(this, rootView);
        initData();
        getClassfyData();
        return rootView;
    }

    private void getClassfyData() {
        showLoad("");
        RequestParams params = new RequestParams();
        Log.i("tag", Constants.BASE_URL + "goodList&" + params);
        client.post(Constants.BASE_URL + "goodList", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    bean = gson.fromJson(s, ClassfyListBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        setData(bean);
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void setData(ClassfyListBean bean) {
        leftList.clear();
        rightList.clear();
        leftList.addAll(bean.getItems());
        classfyAdapter.notifyDataSetChanged();
        if (0 != bean.getItems().size()) {
            rightList.addAll(bean.getItems().get(0).getSmallitems());
        }
        gridAdapter.notifyDataSetChanged();
    }

    private void initData() {
        lvClassfy = (ListView) rootView.findViewById(R.id.lv_classfy);
        gvRight = (GridView) rootView.findViewById(R.id.gv_right);
        classfyAdapter = new ClassfyAdapter(getActivity(), leftList);
        lvClassfy.setAdapter(classfyAdapter);
        gridAdapter = new ClassfyGridAdapter(getActivity(), rightList);
        classfyAdapter.selectPosition = 0;
        gvRight.setAdapter(gridAdapter);
        lvClassfy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classfyAdapter.selectPosition = i;
                classfyAdapter.notifyDataSetChanged();

                rightList.clear();
                rightList.addAll(bean.getItems().get(i).getSmallitems());
                gridAdapter.notifyDataSetChanged();
            }
        });
        gvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String secondId = rightList.get(i).getSmallid();
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                intent.putExtra("secondId", secondId);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
