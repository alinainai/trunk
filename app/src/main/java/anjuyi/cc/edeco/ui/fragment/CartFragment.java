package anjuyi.cc.edeco.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.util.ScreenUtils;
import butterknife.BindView;

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
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;  //状态栏
    @BindView(R.id.status_bar)
    View mStatusBar;

    public static final String TAG = "CartFragment";
    public LinearLayoutManager mLayoutManager;
    private int postion = -1;
    private int lastPostion = -1;



    public static CartFragment newInstance() {
        return new CartFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        rl_title.setBackgroundColor(getResources().getColor(R.color.cff3e19));
        mainCartTitle.setText("视频界面");
        imgBack.setVisibility(View.GONE);

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {}

    @Override
    public void initData(Bundle savedInstanceState) {

    }


}
