package anjuyi.cc.edeco.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
public class AccountFragment extends BaseFragment {

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

    public static final String TAG = "AccountFragment";


    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    public void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }

        rl_title.setBackgroundColor(getResources().getColor(R.color.cff3e19));
        mainCartTitle.setText("导航界面");
        imgBack.setVisibility(View.GONE);

    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {



    }





}
