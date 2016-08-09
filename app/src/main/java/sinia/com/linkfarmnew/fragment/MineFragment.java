package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.AddressManagerActivity;
import sinia.com.linkfarmnew.activity.MyCollectActivity;
import sinia.com.linkfarmnew.activity.MyCouponsActivity;
import sinia.com.linkfarmnew.activity.MyExpandActivity;
import sinia.com.linkfarmnew.activity.MyFootPrintActivity;
import sinia.com.linkfarmnew.activity.MyOrderActivity;
import sinia.com.linkfarmnew.activity.PersonalCenterActivty;
import sinia.com.linkfarmnew.activity.ServiceActivity;
import sinia.com.linkfarmnew.activity.SettingsActivity;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.view.CircleImageView;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class MineFragment extends BaseFragment {

    @Bind(R.id.img_kefu)
    ImageView imgKefu;
    @Bind(R.id.img_settings)
    ImageView imgSettings;
    @Bind(R.id.iv_head)
    CircleImageView ivHead;
    @Bind(R.id.tv_jifen)
    TextView tvJifen;
    @Bind(R.id.img_mall)
    ImageView imgMall;
    @Bind(R.id.img_into)
    ImageView imgInto;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.rl_person_info)
    RelativeLayout rlPersonInfo;
    @Bind(R.id.tv_daizhifucount)
    TextView tvDaizhifucount;
    @Bind(R.id.rl_notpay)
    RelativeLayout rlNotpay;
    @Bind(R.id.tv_daishouhuocount)
    TextView tvDaishouhuocount;
    @Bind(R.id.rl_delivery)
    RelativeLayout rlDelivery;
    @Bind(R.id.tv_daipingjiacount)
    TextView tvDaipingjiacount;
    @Bind(R.id.rl_notcomment)
    RelativeLayout rlNotcomment;
    @Bind(R.id.tv_myorder)
    TextView tvMyorder;
    @Bind(R.id.iv_dingdan)
    ImageView ivDingdan;
    @Bind(R.id.rl_yhq)
    RelativeLayout rlYhq;
    @Bind(R.id.iv_dingdan1)
    ImageView ivDingdan1;
    @Bind(R.id.rl_tuihuo)
    RelativeLayout rlTuihuo;
    @Bind(R.id.aa)
    ImageView aa;
    @Bind(R.id.rl_my_collect)
    RelativeLayout rlMyCollect;
    @Bind(R.id.iv_dingdan5)
    ImageView ivDingdan5;
    @Bind(R.id.rl_foot)
    RelativeLayout rlFoot;
    @Bind(R.id.iv_dingdan2)
    ImageView ivDingdan2;
    @Bind(R.id.rl_address_manager)
    RelativeLayout rlAddressManager;
    @Bind(R.id.iv_dingdan6)
    ImageView ivDingdan6;
    @Bind(R.id.rl_tuiguang)
    RelativeLayout rlTuiguang;
    private View rootView;
    private MaterialDialog materialDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.img_kefu, R.id.img_settings, R.id.iv_head, R.id.img_mall, R.id.rl_person_info, R.id.rl_notpay, R.id.rl_delivery, R.id.rl_notcomment, R.id.rl_yhq, R.id.rl_tuihuo, R.id.rl_my_collect, R.id.rl_foot, R.id.rl_address_manager, R.id.rl_tuiguang})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_kefu:
                callService();
                break;
            case R.id.img_settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_head:
                break;
            case R.id.img_mall:
                break;
            case R.id.rl_person_info:
                intent = new Intent(getActivity(), PersonalCenterActivty.class);
                startActivity(intent);
                break;
            case R.id.rl_notpay:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("title", "待付款");
                startActivity(intent);
                break;
            case R.id.rl_delivery:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("title", "待收货");
                startActivity(intent);
                break;
            case R.id.rl_notcomment:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("title", "待评价");
                startActivity(intent);
                break;
            case R.id.rl_yhq:
                intent = new Intent(getActivity(), MyCouponsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_tuihuo:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("title", "退货订单");
                startActivity(intent);
                break;
            case R.id.rl_my_collect:
                intent = new Intent(getActivity(), MyCollectActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_foot:
                intent = new Intent(getActivity(), MyFootPrintActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_address_manager:
                intent = new Intent(getActivity(), AddressManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_tuiguang:
                intent = new Intent(getActivity(), MyExpandActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void callService() {
        materialDialog = new MaterialDialog(getActivity());
        materialDialog.setTitle("联系客服").setMessage("400-888-666")
                .setPositiveButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400-888-666"));
                        startActivity(intent);
                        materialDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        }).show();
    }
}
