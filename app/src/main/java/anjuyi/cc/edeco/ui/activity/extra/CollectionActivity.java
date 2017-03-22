package anjuyi.cc.edeco.ui.activity.extra;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 显示收藏
 * Created by li on 2016/6/23.
 */
public class CollectionActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.ll_back)
    LinearLayout llBack;//返回键
    @BindView(R.id.btn_service)
    Button btnService;//服务
    @BindView(R.id.btn_commodity)
    Button btnCommodity;//商品
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton top;

    private int collectionType;//0.服务  1.商品


    private int p=1;
    private boolean isMore=true;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_collection;
    }

    @Override

    public void initView(Bundle savedInstanceState) {
        collectionType = getIntent().getIntExtra("collection_type", 0);

        if (collectionType == 0) {
            btnService.setSelected(true);
            btnCommodity.setSelected(false);
        } else {
            btnService.setSelected(false);
            btnCommodity.setSelected(true);
        }



    }


    @Override
    public void setListener() {

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.ll_back, R.id.btn_service, R.id.btn_commodity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_service:
                btnService.setSelected(true);
                btnCommodity.setSelected(false);
                collectionType = 0;
                break;
            case R.id.btn_commodity:
                btnService.setSelected(false);
                btnCommodity.setSelected(true);
                collectionType = 1;
                break;
        }
    }


}
