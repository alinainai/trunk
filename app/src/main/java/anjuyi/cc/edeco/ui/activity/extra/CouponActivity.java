package anjuyi.cc.edeco.ui.activity.extra;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 优惠券界面
 * Created by li on 2016/6/22.
 */
public class CouponActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;// 返回键
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle; //标题
    @BindView(R.id.tvRight)
    TextView tvRight;  //标题右侧文本
    @BindView(R.id.rl_anju)
    RelativeLayout rlAnju; //安居券
    @BindView(R.id.rl_yi)
    RelativeLayout rlYi; //易券
    @BindView(R.id.rl_dui)
    RelativeLayout rlDui;//兑换优惠券

    @Override
    protected int initLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mainCartTitle.setText("优惠券");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("兑换优惠券");
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.img_back, R.id.tvRight, R.id.rl_anju, R.id.rl_yi, R.id.rl_dui})
    public void onClick(View view) {

      //  Intent intent = new Intent(CouponActivity.this,ShowCouponActivity.class);
        switch (view.getId()) {

            case R.id.img_back:
                finish();
                break;
            case R.id.rl_anju:
//                intent.putExtra("title", 1);//1.安居券 0.易券
//                startActivity(intent);

                break;
            case R.id.rl_yi:
//                intent.putExtra("title", 0);;//1.安居券 0.易券
//                startActivity(intent);
                break;
            case R.id.tvRight:
            case R.id.rl_dui:
               // startActivity(new Intent(CouponActivity.this,VoucherActivity.class));
                break;
            default:
                break;
        }
    }
}
