package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.GoodsListActivity;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ValidationUtils;

public class MenuRightFragment extends BaseFragment {
    @Bind(R.id.et_place_s)
    EditText etPlaceS;
    @Bind(R.id.et_place_e)
    EditText etPlaceE;
    @Bind(R.id.tv_reset)
    TextView tvReset;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private View rootView;
    private LayoutInflater inflater;
    private GoodsListActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_menu_right, null);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        activity = (GoodsListActivity) getActivity();
    }

    @OnClick({R.id.tv_reset, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reset:
                etPlaceS.setText("");
                etPlaceE.setText("");
                break;
            case R.id.tv_ok:
                if (StringUtil.isEmpty(etPlaceS.getEditableText().toString()) && StringUtil.isEmpty(etPlaceE
                        .getEditableText().toString())) {
                    showToast("请输入出产地或发货地");
                } else {
                    GoodsListActivity.startPlace = etPlaceS.getEditableText().toString().trim();
                    GoodsListActivity.endPlace = etPlaceE.getEditableText().toString().trim();
                    Message msg = new Message();
                    msg.what = 0;
                    activity.handler.sendMessage(msg);
                    GoodsListActivity.drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
