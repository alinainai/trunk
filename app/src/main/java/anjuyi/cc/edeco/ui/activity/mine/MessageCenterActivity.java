package anjuyi.cc.edeco.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static anjuyi.cc.edeco.R.id.rl_system;

/**
 * Created by li on 2016/6/27.
 */
public class MessageCenterActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.item_title_ajy)
    TextView itemTitleAjy;
    @BindView(R.id.time_ajy)
    TextView timeAjy;
    @BindView(R.id.content_ajy)
    TextView contentAjy;
    @BindView(rl_system)
    RelativeLayout rlSystem;
    @BindView(R.id.time_wchat_time)
    TextView timeWchatTime;
    @BindView(R.id.content_wchat_content)
    TextView contentWchatContent;
    @BindView(R.id.rl_wchat)
    RelativeLayout rlWchat;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        mainCartTitle.setText("消息中心");
    }

    @OnClick({R.id.img_back, R.id.ll_back, R.id.rl_system,R.id.rl_wchat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_back:
                break;
            case R.id.rl_system:
                break;
            case R.id.rl_wchat:
                break;
        }
    }

}
