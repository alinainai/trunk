package anjuyi.cc.edeco.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import anjuyi.cc.edeco.util.ScreenUtils;
import butterknife.BindView;

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
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.status_bar)
    View mStatusBar;

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
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainCartTitle.setText("分类");
        llBack.setVisibility(View.GONE);
        mTitles= AppUtils.stringArrayToList(context, R.array.gank);
        mFragments = new ArrayList<>();
        for (String subtype : mTitles) {
            mFragments.add(GankItemFragment.newInstance(subtype));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
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
