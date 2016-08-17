package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import sinia.com.linkfarmnew.adapter.CommentAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsCommentBean;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.utils.Constants;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class GoodsCommentFragment extends BaseFragment {

    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.tv_good)
    TextView tvGood;
    @Bind(R.id.tv_zhong)
    TextView tvZhong;
    @Bind(R.id.tv_bad)
    TextView tvBad;
    @Bind(R.id.listView)
    ListView listView;
    private View rootView;
    private CommentAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<GoodsCommentBean.CommentBean> list = new ArrayList<>();
    private GoodsDetailBean goodsBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods_comment, null);
        ButterKnife.bind(this, rootView);
        goodsBean = (GoodsDetailBean) getArguments().get("goodsBean");
        initData();
        getCommentData("1");
        return rootView;
    }

    public static GoodsCommentFragment newInstance() {
        GoodsCommentFragment fragment = new GoodsCommentFragment();
        return fragment;
    }

    private void getCommentData(String commentType) {
        RequestParams params = new RequestParams();
        params.put("type", commentType);
        params.put("otherId", goodsBean.getId());
        Log.i("tag", Constants.BASE_URL + "myComment&" + params);
        client.post(Constants.BASE_URL + "myComment", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    GoodsCommentBean bean = gson.fromJson(s, GoodsCommentBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful && null != bean) {
                        tvAll.setText("全部评价\n" + bean.getAllComNum());
                        tvGood.setText("好评\n" + bean.getGoodComNum());
                        tvZhong.setText("中评\n" + bean.getMidComNum());
                        tvBad.setText("差评\n" + bean.getLowComNum());
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
        tvAll.setSelected(true);
        tvGood.setSelected(false);
        tvZhong.setSelected(false);
        tvBad.setSelected(false);
        adapter = new CommentAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }

    @OnClick({R.id.tv_all, R.id.tv_good, R.id.tv_zhong, R.id.tv_bad})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                tvAll.setSelected(true);
                tvGood.setSelected(false);
                tvZhong.setSelected(false);
                tvBad.setSelected(false);
                getCommentData("1");
                break;
            case R.id.tv_good:
                tvGood.setSelected(true);
                tvAll.setSelected(false);
                tvZhong.setSelected(false);
                tvBad.setSelected(false);
                getCommentData("2");
                break;
            case R.id.tv_zhong:
                tvZhong.setSelected(true);
                tvGood.setSelected(false);
                tvAll.setSelected(false);
                tvBad.setSelected(false);
                getCommentData("3");
                break;
            case R.id.tv_bad:
                tvBad.setSelected(true);
                tvGood.setSelected(false);
                tvZhong.setSelected(false);
                tvAll.setSelected(false);
                getCommentData("4");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
