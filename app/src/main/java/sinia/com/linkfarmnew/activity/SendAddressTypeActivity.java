package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MainFragmentAdapter;
import sinia.com.linkfarmnew.adapter.MyFragmentPagerAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.fragment.DeliveryAddressFragment;
import sinia.com.linkfarmnew.fragment.SelfGetFragment;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.MyApplication;

/**
 * Created by 忧郁的眼神 on 2016/8/11.
 */
public class SendAddressTypeActivity extends FragmentActivity {

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.img_colse)
    ImageView imgColse;
    @Bind(R.id.ll_root)
    RelativeLayout llRoot;
    @Bind(R.id.tv_send)
    TextView tvSend;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.rl_order)
    RelativeLayout rlOrder;
    @Bind(R.id.tv_self)
    TextView tvSelf;
    @Bind(R.id.v2)
    View v2;
    @Bind(R.id.rl_contract)
    RelativeLayout rlContract;
    @Bind(R.id.ll_a)
    LinearLayout llA;

    private List<String> titleList;
    private ArrayList<BaseFragment> fragmentList;
    private DeliveryAddressFragment deliveryFragment;
    private SelfGetFragment selfFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        int w = AppInfoUtil.getScreenWidth(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, FrameLayout.LayoutParams.WRAP_CONTENT);
        llRoot.setLayoutParams(lp);

        titleList = new ArrayList<>();
        titleList.add("配送地址");
        titleList.add("自提地址");
        fragmentList = new ArrayList<>();
        deliveryFragment = new DeliveryAddressFragment();
        selfFragment = new SelfGetFragment();
        fragmentList.add(deliveryFragment);
        fragmentList.add(selfFragment);

        MainFragmentAdapter adapter = new MainFragmentAdapter(
                getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                switch (arg0) {
                    case 0:
                        tvSend.setTextColor(getResources().getColor(R.color.themeColor));
                        tvSelf.setTextColor(getResources().getColor(R.color.textblackColor));
                        v1.setVisibility(View.VISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        tvSelf.setTextColor(getResources().getColor(R.color.themeColor));
                        tvSend.setTextColor(getResources().getColor(R.color.textblackColor));
                        v2.setVisibility(View.VISIBLE);
                        v1.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @OnClick({R.id.rl_order, R.id.rl_contract, R.id.img_colse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_order:
                viewPager.setCurrentItem(0);
                tvSend.setTextColor(getResources().getColor(R.color.themeColor));
                tvSelf.setTextColor(getResources().getColor(R.color.textblackColor));
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.INVISIBLE);

                break;
            case R.id.rl_contract:
                viewPager.setCurrentItem(1);
                tvSelf.setTextColor(getResources().getColor(R.color.themeColor));
                tvSend.setTextColor(getResources().getColor(R.color.textblackColor));
                v2.setVisibility(View.VISIBLE);
                v1.setVisibility(View.INVISIBLE);
                break;
            case R.id.img_colse:
                finish();
                break;
        }
    }
}
