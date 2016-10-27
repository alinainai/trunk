package anjuyi.cc.edeco.ui.activity.test.mpchart;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class MpchartActivity extends BaseActivity {


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
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private List<BaseFragment> mFragments = new ArrayList<>();// 全部
    private List<String> list = new ArrayList<>();


    @Override
    protected int initLayoutId() {
        return R.layout.activity_mpchart;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mFragments.add(BarCharFragment.newInstance("BarCharFragment"));
        mFragments.add(LineCharFragment.newInstance("LineCharFragment"));
        mFragments.add(PieCharFragment.newInstance("PieCharFragment"));
        mFragments.add(RadarCharFragment.newInstance("RadarCharFragment"));
        mFragments.add(ScatterChartFragment.newInstance("ScatterChartFragment"));


        //设置TabLayout的模式
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //为TabLayout添加tab名称
        list.add("柱形图");
        list.add("折线图");
        list.add("饼状图");
        list.add("网状图");
        list.add("点状图");

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(5);
        tablayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.img_back, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_back:
                finish();
                break;
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
