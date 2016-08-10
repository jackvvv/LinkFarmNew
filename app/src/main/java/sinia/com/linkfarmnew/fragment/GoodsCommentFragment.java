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
import sinia.com.linkfarmnew.adapter.CommentAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods_comment, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        tvAll.setSelected(true);
        tvGood.setSelected(false);
        tvZhong.setSelected(false);
        tvBad.setSelected(false);
        adapter = new CommentAdapter(getActivity());
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
                break;
            case R.id.tv_good:
                tvGood.setSelected(true);
                tvAll.setSelected(false);
                tvZhong.setSelected(false);
                tvBad.setSelected(false);
                break;
            case R.id.tv_zhong:
                tvZhong.setSelected(true);
                tvGood.setSelected(false);
                tvAll.setSelected(false);
                tvBad.setSelected(false);
                break;
            case R.id.tv_bad:
                tvBad.setSelected(true);
                tvGood.setSelected(false);
                tvZhong.setSelected(false);
                tvAll.setSelected(false);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
