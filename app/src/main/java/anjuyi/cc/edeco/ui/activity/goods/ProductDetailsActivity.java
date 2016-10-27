package anjuyi.cc.edeco.ui.activity.goods;

import android.os.Bundle;

import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.MyPagerAdapter;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;

/**
 * Created by li on 2016/6/20.
 */
public class ProductDetailsActivity extends BaseActivity {


    @BindView(R.id.mViewPager)
    RollPagerView mViewPager;//广告轮播图

    //测试数据
    private String path1="https://www.anjuyi.com.cn/./Uploads/shangpin/2016-05-05/572afdfb17f9a.png";
    private ArrayList<String> urls=new ArrayList<>();
    private String path2="https://www.anjuyi.com.cn/./Uploads/shangpin/2016-05-05/572abd1161f1a.png";


    @Override
    protected int initLayoutId() {
        return R.layout.activity_product_info;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_info);

        for (int i=0;i<5;i++){
            if(i%2==0)
                 urls.add(path1);
            else
                urls.add(path2);
        }

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        mViewPager.setAdapter(new MyPagerAdapter(mViewPager,urls,context));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
