package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.ServiceBean;

/**
 * Created by 忧郁的眼神 on 2016/9/13.
 */
public class QuetionDetailActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    private ServiceBean.ItemsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail, "常见问题");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        setData();
    }

    private void setData() {
        bean = (ServiceBean.ItemsBean) getIntent().getSerializableExtra("bean");
        tvTitle.setText(bean.getTitle());
        tvContent.setText(bean.getContent());
    }
}
