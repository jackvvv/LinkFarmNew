package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.GoodsDetailActivity;
import sinia.com.linkfarmnew.activity.LocationActivity;
import sinia.com.linkfarmnew.activity.MessageActivity;
import sinia.com.linkfarmnew.activity.SearchActivity;
import sinia.com.linkfarmnew.adapter.HomeRecommendAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.view.MyGridView;
import sinia.com.linkfarmnew.view.slideview.SlideShowView;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.tv_locate)
    TextView tvLocate;
    @Bind(R.id.tv_search_type)
    TextView tvSearchType;
    @Bind(R.id.et_content)
    TextView etContent;
    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.tv_scan)
    TextView tvScan;
    @Bind(R.id.tv_msg)
    TextView tvMsg;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.img_up)
    ImageView imgUp;
    @Bind(R.id.img_down)
    ImageView imgDown;
    @Bind(R.id.llup)
    LinearLayout llup;
    @Bind(R.id.img_left_big)
    ImageView imgLeftBig;
    @Bind(R.id.img_right_top)
    ImageView imgRightTop;
    @Bind(R.id.img_right_bottom)
    ImageView imgRightBottom;
    @Bind(R.id.img_left_top)
    ImageView imgLeftTop;
    @Bind(R.id.img_left_bottom)
    ImageView imgLeftBottom;
    @Bind(R.id.img_right_big)
    ImageView imgRightBig;
    @Bind(R.id.rl_search)
    RelativeLayout rl_search;
    private MyGridView gridView;
    @Bind(R.id.mySlideShowView)
    SlideShowView mySlideShowView;
    private View rootView;
    private List<String> picList = new ArrayList<String>();
    private HomeRecommendAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        int h = AppInfoUtil.getScreenWidth(getActivity()) * 320 / 750;
//        mySlideShowView = (SlideShowView) rootView.findViewById(R.id.mySlideShowView);
        gridView = (MyGridView) rootView.findViewById(R.id.gridView);
        mySlideShowView.getLayoutParams().height = h;
        picList.add("http://img2.imgtn.bdimg.com/it/u=436515947,1326912009&fm=21&gp=0.jpg");
        picList.add("http://img5.imgtn.bdimg.com/it/u=1394043143,3012833488&fm=21&gp=0.jpg");
        picList.add("http://img3.imgtn.bdimg.com/it/u=3555494465,3598698242&fm=21&gp=0.jpg");
        picList.add("http://img2.imgtn.bdimg.com/it/u=1913986186,2860582952&fm=21&gp=0.jpg");
        picList.add("http://img2.imgtn.bdimg.com/it/u=3927119590,239617978&fm=21&gp=0.jpg");
        picList.add("http://img3.imgtn.bdimg.com/it/u=1190498942,1807679665&fm=21&gp=0.jpg");
        mySlideShowView.setImagePath(picList);
        mySlideShowView.startPlay();
        adapter = new HomeRecommendAdapter(getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.tv_locate, R.id.tv_scan, R.id.tv_msg, R.id.rl_search})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_locate:
                intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_scan:
                break;
            case R.id.tv_msg:
                intent = new Intent(getActivity(), MessageActivity.class);
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
