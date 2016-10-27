package anjuyi.cc.edeco.ui.activity.extra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 分享赚钱界面
 * Created by li on 2016/6/22.
 */
public class ShareForMoneyActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;//返回键
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;//标题
    @BindView(R.id.code_title)
    ImageView codeTitle; //图片
    @BindView(R.id.tv_gotoauth)
    TextView tvGotoauth; //去认证
    @BindView(R.id.tv_backmoney)
    TextView tvBackmoney; //返现系数
    @BindView(R.id.tv_forremord)
    TextView tvForremord;//推广总额
    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;//邀请码
    @BindView(R.id.tv_share)
    TextView tvShare;//分享
    @BindView(R.id.et_code_input)
    EditText etCodeInput;//请求码输入框
    @BindView(R.id.input_sure)
    Button inputSure;//确认件
    @BindView(R.id.zhanghu_money)
    TextView zhanghuMoney;//账户总金额
    @BindView(R.id.tixian_money)
    TextView tixianMoney;//提现总金额
    @BindView(R.id.tixian)
    Button tixian;//提现按钮
    @BindView(R.id.zancun_money)
    TextView zancunMoney;//暂存金额
    @BindView(R.id.tv_myfrenid)
    TextView tvMyfrenid;//我的好友
    @BindView(R.id.tv_promote)
    TextView tvPromote;//推广明细

    @Override
    protected int initLayoutId() {
        return R.layout.activity_share_for_money;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mainCartTitle.setText("分享赚钱");
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.img_back, R.id.tv_gotoauth, R.id.tv_share, R.id.input_sure, R.id.tixian, R.id.tv_myfrenid})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_gotoauth:



                break;
            case R.id.tv_share:
                break;
            case R.id.input_sure:
                break;
            case R.id.tixian:
                break;
            case R.id.tv_myfrenid:
                break;
        }
    }
}
