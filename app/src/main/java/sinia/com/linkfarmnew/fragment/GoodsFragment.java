package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.SendAddressTypeActivity;
import sinia.com.linkfarmnew.activity.StandardDialogActivity;
import sinia.com.linkfarmnew.adapter.CommentAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.Utility;
import sinia.com.linkfarmnew.view.CustScrollView;
import sinia.com.linkfarmnew.view.slideview.SlideShowView;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class GoodsFragment extends BaseFragment {

    @Bind(R.id.mySlideShowView)
    SlideShowView mySlideShowView;
    @Bind(R.id.tv_goodsname)
    TextView tvGoodsname;
    @Bind(R.id.tv_buynum)
    TextView tvBuynum;
    @Bind(R.id.tv_select)
    TextView tvSelect;
    @Bind(R.id.tv_send_address)
    TextView tvSendAddress;
    @Bind(R.id.lv_comment)
    ListView lvComment;
    @Bind(R.id.custScrollView)
    CustScrollView custScrollView;
    @Bind(R.id.rl_standard)
    LinearLayout rlStandard;
    private View rootView;
    private List<String> picList = new ArrayList<String>();
    private CommentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        int h = AppInfoUtil.getScreenWidth(getActivity()) * 560 / 750;
        mySlideShowView.getLayoutParams().height = h;
        picList.add("http://img2.imgtn.bdimg.com/it/u=436515947,1326912009&fm=21&gp=0.jpg");
        picList.add("http://img5.imgtn.bdimg.com/it/u=1394043143,3012833488&fm=21&gp=0.jpg");
        picList.add("http://img3.imgtn.bdimg.com/it/u=3555494465,3598698242&fm=21&gp=0.jpg");
        picList.add("http://img2.imgtn.bdimg.com/it/u=1913986186,2860582952&fm=21&gp=0.jpg");
        picList.add("http://img2.imgtn.bdimg.com/it/u=3927119590,239617978&fm=21&gp=0.jpg");
        picList.add("http://img3.imgtn.bdimg.com/it/u=1190498942,1807679665&fm=21&gp=0.jpg");
        mySlideShowView.setImagePath(picList);
        mySlideShowView.startPlay();
        adapter = new CommentAdapter(getActivity());
        lvComment.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lvComment);
    }

    @OnClick({R.id.rl_standard, R.id.tv_send_address})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_standard:
                intent = new Intent(getActivity(), StandardDialogActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_send_address:
                intent = new Intent(getActivity(), SendAddressTypeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
