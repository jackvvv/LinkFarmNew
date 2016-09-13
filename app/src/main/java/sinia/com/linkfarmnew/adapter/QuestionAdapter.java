package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.ServiceBean;
import sinia.com.linkfarmnew.utils.*;

/**
 * Created by 忧郁的眼神 on 2016/9/13.
 */
public class QuestionAdapter extends BaseAdapter {

    private Context context;
    private List<ServiceBean.ItemsBean> questionList;

    public QuestionAdapter(Context context, List<ServiceBean.ItemsBean> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_question, null);
        }
        TextView tv_title = sinia.com.linkfarmnew.utils.ViewHolder.get(view, R.id.tv_title);
        tv_title.setText(questionList.get(i).getTitle());
        return view;
    }
}
