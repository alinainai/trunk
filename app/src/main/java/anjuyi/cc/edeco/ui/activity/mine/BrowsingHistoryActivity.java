package anjuyi.cc.edeco.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.bean.goods.HistoryResultModel;
import anjuyi.cc.edeco.https.Const;
import anjuyi.cc.edeco.ui.activity.goods.ProductDetailsActivity;
import anjuyi.cc.edeco.util.DensityUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 浏览历史
 *
 * @author W
 */
public class BrowsingHistoryActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {



    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.recyclerview)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private int p = 1;
    private boolean isMore = true;
    private RecyclerArrayAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected int initLayoutId() {
        return R.layout.activity_browsing_history;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mainCartTitle.setText("浏览历史");
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpaceDecoration itemDecoration = new SpaceDecoration((int) DensityUtils.dp2px(this, 4.0f));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        adapter = new RecyclerArrayAdapter<HistoryResultModel>(BrowsingHistoryActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                BaseViewHolder holder = new BaseViewHolder<HistoryResultModel>(parent, R.layout.grid_product_item) {

                    ImageView item_icon = $(R.id.item_icon);
                    TextView item_title = $(R.id.item_title);
                    TextView item_price = $(R.id.item_price);
                    TextView item_sale = $(R.id.item_sale);

                    @Override
                    public void setData(final HistoryResultModel
                                                history) {
                        item_title.setText(history.getGoodsname());
                        item_price.setText(history.getGoodsprice());
                        item_sale.setText(history.getSale_count());
                        Glide.with(context).load(history.getPicurl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(item_icon);
                    }
                };
                return holder;
            }
        };
        //条目点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(int position) {

                                               Intent intent=new Intent(context, ProductDetailsActivity.class);
                                               startActivity(intent);

                                           }
                                       }

        );
        adapter.setMore(R.layout.footerview, this);
        adapter.setNoMore(R.layout.view_nom);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        mRecyclerView.setRefreshListener(this);
    }

    @Override
    public void initData() {
        LoadHistoryResultMore(true, true);
    }

    @Override
    public void onLoadMore() {
        LoadHistoryResultMore(false, false);
    }

    @Override
    public void onRefresh() {
        LoadHistoryResultMore(true, false);
    }

    /**
     * 加载更多浏览历史
     */
    public void LoadHistoryResultMore(final boolean isRefresh, boolean isShowLoad) {


        //测试用数据
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                if (isRefresh) {
                    isMore = true;
                    p = 1;
                    adapter.clear();
                }
                List<HistoryResultModel> result = null;
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<HistoryResultModel>>() {
                }.getType();
                result = gson.fromJson(Const.DATA,
                        type);
                if (p == 3) {
                    adapter.addAll(new ArrayList<HistoryResultModel>());
                } else {
                    adapter.addAll(result);
                }
                p++;
            }
        }, 1000);


//        Map<String, String> params = new HashMap<String, String>();
//        if (isRefresh) {
//            isMore = true;
//            p = 1;
//        }
//        params.put("p", String.valueOf(p));
//        VolleyRequest.RequestPost(context, Const.TOKEN
//                , params, new VolleyRequest.MyStringCallback(context) {
//
//                    @Override
//                    public void onSuccess(JSONObject json) {
//
//                    }
//
//                    @Override
//                    public void onFailure() {
//
//                    }
//                }, true, true);
    }


    @OnClick({R.id.ll_back, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.fab:
                if (adapter != null && adapter.getAllData().size() > 0) {
                    mRecyclerView.scrollToPosition(0);
                }
                break;
        }
    }


}
