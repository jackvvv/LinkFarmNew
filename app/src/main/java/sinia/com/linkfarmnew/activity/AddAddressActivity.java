package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.DialogUtils;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class AddAddressActivity extends BaseActivity {

    @NotEmpty(message = "请输入收货人姓名")
    @Order(1)
    @Bind(R.id.et_name)
    EditText etName;
    @Pattern(regex = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(2)
    @Bind(R.id.et_tel)
    EditText etTel;
    @NotEmpty(message = "请选择所在地区")
    @Order(3)
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @NotEmpty(message = "请输入详细地址")
    @Order(4)
    @Bind(R.id.et_detail)
    EditText etDetail;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address, "编辑收货地址");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        validator = new Validator(this);
    }

    @OnClick({R.id.tv_address, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                DialogUtils.createAddressDialog(AddAddressActivity.this, tvAddress);
                break;
            case R.id.tv_ok:
//                validator.validate();
                break;
        }
    }
}
