package anjuyi.cc.edeco.ui.activity.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.ui.fragment.order.OrderFragment;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by li on 2016/6/27.
 */
public class OrderManagerActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private int currentIndex = 0;

    private List<BaseFragment> mFragments = new ArrayList<>();// 全部
    private List<String> list = new ArrayList<>();

    @Override
    protected int initLayoutId() {
        return R.layout.activity_order_manager;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mFragments.add(OrderFragment.newInstance("all"));
        mFragments.add(OrderFragment.newInstance("forpay"));
        mFragments.add(OrderFragment.newInstance("forservice"));
        mFragments.add(OrderFragment.newInstance("forcomment"));

        currentIndex = getIntent().getIntExtra("currentIndex", 0);
        //设置TabLayout的模式
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        list.add("全部");
        list.add("待付款");
        list.add("待服务");
        list.add("待评价");
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(currentIndex);
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {


    }

    @OnClick(R.id.ll_back)
    public void onClick() {

        finish();

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
