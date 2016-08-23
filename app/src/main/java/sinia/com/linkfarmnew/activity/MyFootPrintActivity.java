package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.fragment.GoodsCollectFragment;
import sinia.com.linkfarmnew.fragment.GoodsFootFragment;
import sinia.com.linkfarmnew.fragment.ShopCollectFragment;
import sinia.com.linkfarmnew.fragment.ShopFootFragment;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class MyFootPrintActivity extends BaseActivity {

    @Bind(R.id.tab_title)
    TabLayout tabTitle;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private MyFragmentPagerAdapter pagerAdapter;
    private List<String> titleList;
    private List<Fragment> fragmentList;
    private GoodsFootFragment goodsFragment;
    private ShopFootFragment shopFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_foot, "我的足迹");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initViews();
    }

    private void initViews() {
        titleList = new ArrayList<>();
        titleList.add("商品");
        titleList.add("商铺");
        fragmentList = new ArrayList<>();
        goodsFragment = new GoodsFootFragment();
        shopFragment = new ShopFootFragment();
        fragmentList.add(goodsFragment);
        fragmentList.add(shopFragment);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(pagerAdapter);
        tabTitle.setTabMode(TabLayout.MODE_FIXED);
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(0)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(1)));
        tabTitle.setupWithViewPager(viewPager);
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private List<String> titleList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {

            return titleList.get(position % titleList.size());
        }
    }
}
