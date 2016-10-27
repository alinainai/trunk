package anjuyi.cc.edeco.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.view.GridViewForScrollView;
import anjuyi.cc.edeco.view.PinnedSectionListView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by li on 2016/6/29.
 */
public class CartFragment extends BaseFragment {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.list)
    PinnedSectionListView list;
    @BindView(R.id.cart_header_tv_null)
    TextView cartHeaderTvNull;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.cart_gv)
    GridViewForScrollView cartGv;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tv_man_jian)
    TextView tvManJian;
    @BindView(R.id.goToPay)
    TextView goToPay;
    @BindView(R.id.rl_normal)
    RelativeLayout rlNormal;
    @BindView(R.id.share_btn)
    TextView shareBtn;
    @BindView(R.id.addCollect)
    TextView addCollect;
    @BindView(R.id.del)
    TextView del;
    @BindView(R.id.ll_edit)
    LinearLayout llEdit;

    private View view;
    public static final String TAG = CartFragment.class.getName();

    public static CartFragment newInstance() {
        return new CartFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView() {
        mainCartTitle.setText("购物车");
        imgBack.setVisibility(View.GONE);
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {


    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @OnClick({R.id.ll_back, R.id.goToPay, R.id.share_btn, R.id.addCollect, R.id.del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                break;
            case R.id.goToPay:
                break;
            case R.id.share_btn:
                break;
            case R.id.addCollect:
                break;
            case R.id.del:
                break;
        }
    }
}
