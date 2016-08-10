package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.view.DragLayout;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class GoodsDetailFragment extends BaseFragment {

    @Bind(R.id.frame_goods)
    FrameLayout frameGoods;
    @Bind(R.id.frame_img)
    FrameLayout frameImg;
    @Bind(R.id.draglayout)
    DragLayout draglayout;
    @Bind(R.id.tv_collect)
    TextView tvCollect;
    @Bind(R.id.tv_cart)
    TextView tvCart;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private View rootView;
    private GoodsFragment goodsFragment;
    private ImageFragment imgFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods_detail, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        goodsFragment = new GoodsFragment();
        imgFragment = new ImageFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.frame_goods, goodsFragment).add(R.id.frame_img, imgFragment)
                .commit();
        DragLayout.ShowNextPageNotifier nextPageNotifier = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                imgFragment.initImg();
            }
        };
        draglayout.setNextPageListener(nextPageNotifier);
    }

    @OnClick({R.id.tv_collect, R.id.tv_cart, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                break;
            case R.id.tv_cart:
                break;
            case R.id.tv_ok:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
