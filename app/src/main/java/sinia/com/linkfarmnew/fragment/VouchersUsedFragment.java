package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.VouchersAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class VouchersUsedFragment extends BaseFragment {
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tv_clearAll)
    TextView tvClearAll;
    private View rootView;
    private VouchersAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_vouchers_used, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        adapter = new VouchersAdapter(getActivity());
        adapter.type = 1;
        listView.setAdapter(adapter);
    }

    private void getCollectData() {
//        showLoad("");
//        RequestParams params = new RequestParams();
//        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
//        params.put("type", "1");
//        Log.i("tag", Constants.BASE_URL + "myColltion&" + params);
//        client.post(Constants.BASE_URL + "myColltion", params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, String s) {
//                super.onSuccess(i, s);
//                dismiss();
//                Log.i("tag", s);
//                Gson gson = new Gson();
//                if (s.contains("isSuccessful")
//                        && s.contains("state")) {
//                    MyCollectBean bean = gson.fromJson(s, MyCollectBean.class);
//                    int state = bean.getState();
//                    int isSuccessful = bean.getIsSuccessful();
//                    if (0 == state && 0 == isSuccessful) {
//                        list.clear();
//                        list.addAll(bean.getItems());
//                        adapter.notifyDataSetChanged();
//                    } else if (0 == state && 1 == isSuccessful) {
//                        showToast("请求失败");
//                    }
//                }
//            }
//        });
    }

    @OnClick(R.id.tv_clearAll)
    public void onClick() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
