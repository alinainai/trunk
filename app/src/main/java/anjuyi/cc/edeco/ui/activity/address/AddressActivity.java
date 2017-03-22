package anjuyi.cc.edeco.ui.activity.address;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.img_cart_right)
    ImageView imgCartRight;
    @BindView(R.id.type_item_recyclerview)
    RecyclerView typeItemRecyclerview;
    @BindView(R.id.btn_add_address)
    Button add_address;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_address;
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


    @OnClick({R.id.ll_back, R.id.tvRight, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                break;
            case R.id.tvRight:
                break;
            case R.id.btn_login:
                break;
        }
    }
}
