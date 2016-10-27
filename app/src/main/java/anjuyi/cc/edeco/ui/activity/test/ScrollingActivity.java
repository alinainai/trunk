package anjuyi.cc.edeco.ui.activity.test;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;

public class ScrollingActivity extends BaseActivity {



    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_scrolling;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }


}
