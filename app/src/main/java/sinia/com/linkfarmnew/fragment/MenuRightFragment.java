package sinia.com.linkfarmnew.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.GoodsListActivity;
import sinia.com.linkfarmnew.base.BaseFragment;

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

    private void initView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_menu_right, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_reset, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reset:
                GoodsListActivity.drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.tv_ok:
                GoodsListActivity.drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
