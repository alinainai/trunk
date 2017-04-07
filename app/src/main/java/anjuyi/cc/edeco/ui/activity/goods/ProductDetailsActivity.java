package anjuyi.cc.edeco.ui.activity.goods;

import android.os.Bundle;

import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;

/**
 * 产品详情页
 * Created by li on 2016/6/20.
 */
public class ProductDetailsActivity extends BaseActivity {


    @BindView(R.id.mViewPager)
    RollPagerView mViewPager;//广告轮播图

    private ArrayList<String> urls=new ArrayList<>();


    @Override
    protected int initLayoutId() {
        return R.layout.activity_product_info;
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


}
