package anjuyi.cc.edeco.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ly on 2016/6/23.
 * 意见反馈详情页
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.feed_back_input_ed)
    EditText feedBackInputEd;//意见反馈输入
    @BindView(R.id.feed_back_phone_input_ed)
    EditText feedBackPhoneInputEd;//手机号输入
    @BindView(R.id.feed_back_submit_btn)
    Button feedBackSubmitBtn;//提交按钮

    @Override
    protected int initLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.feed_back_input_ed, R.id.feed_back_phone_input_ed, R.id.feed_back_submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feed_back_input_ed://意见反馈
                break;
            case R.id.feed_back_phone_input_ed://手机号
                break;
            case R.id.feed_back_submit_btn://提交
                break;
        }
    }
}
