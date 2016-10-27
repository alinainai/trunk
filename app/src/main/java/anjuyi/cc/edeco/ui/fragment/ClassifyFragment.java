package anjuyi.cc.edeco.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.base.mvp.BaseMvpFragment;
import anjuyi.cc.edeco.ui.fragment.classify.GankItemFragment;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.view.CircleImageView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ly on 2016/5/30 11:05.
 * 分类的fragment
 */
public class ClassifyFragment extends BaseFragment {
    public static final String TAG = "ClassifyFragment";
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
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.main_nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private View view;

    private List<BaseMvpFragment> mFragments;
    private List<String> mTitles= new ArrayList<>();
    private TypePageAdapter mTypeAdapter;


    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView() {
        mainCartTitle.setText("分类");
        imgBack.setImageResource(R.mipmap.menue);
        mTitles= AppUtils.stringArrayToList(context, R.array.gank);
        mFragments = new ArrayList<>();
        for (String subtype : mTitles) {
            mFragments.add(GankItemFragment.newInstance(subtype));
        }
        initNavigationView();
        mTypeAdapter = new TypePageAdapter(getChildFragmentManager());
        mTypeAdapter.setData(mFragments, mTitles);
        mViewPager.setAdapter(mTypeAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size() - 1);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }

    private void initNavigationView() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //将侧边栏顶部延伸至status bar
            mDrawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            mDrawerLayout.setClipToPadding(false);
        }
        CircleImageView icon = (CircleImageView) mNavView.getHeaderView(0).findViewById(R.id.mine_user_icon_img);
        TextView name = (TextView) mNavView.getHeaderView(0).findViewById(R.id.nav_head_name);
        name.setText(R.string.app_name);
        mNavView.setCheckedItem(R.id.nav_gank);//设置默认选中
        //设置NavigationView对应menu item的点击事情
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_gank:
                        break;
                    case R.id.nav_girl:
                        break;
                    case R.id.nav_set:
                        break;
                    case R.id.nav_about:
                        break;
                }
                //隐藏NavigationView
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.img_cart_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.img_cart_right:


                break;
        }
    }
    public class TypePageAdapter extends FragmentPagerAdapter {
        private List<BaseMvpFragment> fragments;
        private List<String> titles;

        public TypePageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(List<BaseMvpFragment> fragments, List<String> titles) {
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
