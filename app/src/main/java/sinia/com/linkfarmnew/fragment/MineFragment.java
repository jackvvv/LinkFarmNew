package sinia.com.linkfarmnew.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.AddressManagerActivity;
import sinia.com.linkfarmnew.activity.IntegralMallActivity;
import sinia.com.linkfarmnew.activity.LoginActivity;
import sinia.com.linkfarmnew.activity.MyCollectActivity;
import sinia.com.linkfarmnew.activity.MyCouponsActivity;
import sinia.com.linkfarmnew.activity.MyExpandActivity;
import sinia.com.linkfarmnew.activity.MyFootPrintActivity;
import sinia.com.linkfarmnew.activity.MyOrderActivity;
import sinia.com.linkfarmnew.activity.PersonalCenterActivty;
import sinia.com.linkfarmnew.activity.SettingsActivity;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.LoginBean;
import sinia.com.linkfarmnew.bean.RefreshBean;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
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
    @Bind(R.id.rl_login)
    RelativeLayout rl_login;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    private View rootView;
    private MaterialDialog materialDialog;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String userName, headImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getInstance().getBoolValue("is_login")) {
            setUserData();
            rl_login.setVisibility(View.GONE);
            rlPersonInfo.setVisibility(View.VISIBLE);
        } else {
            rl_login.setVisibility(View.VISIBLE);
            rlPersonInfo.setVisibility(View.GONE);
            tvDaizhifucount.setVisibility(View.INVISIBLE);
            tvDaishouhuocount.setVisibility(View.INVISIBLE);
            tvDaipingjiacount.setVisibility(View.INVISIBLE);
        }
    }

    private void setUserData() {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", "1");
        Log.i("tag", Constants.BASE_URL + "refresh&" + params);
        client.post(Constants.BASE_URL + "refresh", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    RefreshBean bean = gson.fromJson(s, RefreshBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        BitmapUtilsHelp.getImage(getActivity(), R.drawable
                                .ic_launcher).display(ivHead, bean.getImageUrl());
                        tvUsername.setText(bean.getNickName());
                        userName = bean.getNickName();
                        headImage = bean.getImageUrl();
                        if (0 != bean.getWaitPayNum()) {
                            tvDaizhifucount.setVisibility(View.VISIBLE);
                            tvDaizhifucount.setText(bean.getWaitPayNum() + "");
                        } else {
                            tvDaizhifucount.setVisibility(View.INVISIBLE);
                        }
                        if (0 != bean.getWaitShouNum()) {
                            tvDaishouhuocount.setVisibility(View.VISIBLE);
                            tvDaishouhuocount.setText(bean.getWaitShouNum() + "");
                        } else {
                            tvDaishouhuocount.setVisibility(View.INVISIBLE);
                        }
                        if (0 != bean.getComNum()) {
                            tvDaipingjiacount.setVisibility(View.VISIBLE);
                            tvDaipingjiacount.setText(bean.getComNum() + "");
                        } else {
                            tvDaipingjiacount.setVisibility(View.INVISIBLE);
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    } else {
                        showToast("请求失败");
                    }
                }
            }
        });
        LoginBean userBean = MyApplication.getInstance().getLoginBean();
        if (null != userBean) {
            tvJifen.setText(userBean.getPoint() + "\n 积分分数");
            tvLevel.setText("Lv" + userBean.getLeave());
            if ("1".equals(userBean.getCheakStatus())) {
                tvStatus.setText("企业已认证");
            } else if ("2".equals(userBean.getCheakStatus())) {
                tvStatus.setText("企业待认证");
            } else if ("3".equals(userBean.getCheakStatus())) {
                tvStatus.setText("认证失败");
            }
        }

    }

    @OnClick({R.id.img_kefu, R.id.img_settings, R.id.iv_head, R.id.img_mall, R.id.rl_person_info, R.id.rl_login, R.id
            .rl_notpay, R
            .id.rl_delivery, R.id.rl_notcomment, R.id.rl_myorder, R.id.rl_yhq, R.id.rl_tuihuo, R.id.rl_my_collect, R
            .id.rl_foot, R.id
            .rl_address_manager, R.id.rl_tuiguang})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_kefu:
                callService();
                break;
            case R.id.img_settings:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_head:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), PersonalCenterActivty.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.img_mall:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), IntegralMallActivity.class);
                    intent.putExtra("userName",userName);
                    intent.putExtra("headImage",headImage);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_person_info:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), PersonalCenterActivty.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_login:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_notpay:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyOrderActivity.class);
                    intent.putExtra("title", "待付款");
                    intent.putExtra("type", "1");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_delivery:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyOrderActivity.class);
                    intent.putExtra("title", "待收货");
                    intent.putExtra("type", "2");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_notcomment:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyOrderActivity.class);
                    intent.putExtra("title", "待评价");
                    intent.putExtra("type", "3");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_myorder:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyOrderActivity.class);
                    intent.putExtra("title", "我的订单");
                    intent.putExtra("type", "4");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_yhq:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyCouponsActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_tuihuo:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyOrderActivity.class);
                    intent.putExtra("title", "退货订单");
                    intent.putExtra("type", "5");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_my_collect:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyCollectActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_foot:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyFootPrintActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_address_manager:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), AddressManagerActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_tuiguang:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyExpandActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
