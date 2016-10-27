package anjuyi.cc.edeco.ui.activity.extra;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.bean.goods.CollectionModel;
import anjuyi.cc.edeco.https.Const;
import anjuyi.cc.edeco.https.VolleyRequest;
import anjuyi.cc.edeco.util.DensityUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 显示收藏
 * Created by li on 2016/6/23.
 */
public class CollectionActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.ll_back)
    LinearLayout llBack;//返回键
    @BindView(R.id.btn_service)
    Button btnService;//服务
    @BindView(R.id.btn_commodity)
    Button btnCommodity;//商品
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.recyclerview)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton top;

    private int collectionType;//0.服务  1.商品
    private RecyclerArrayAdapter adapter;

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, DensityUtils.dp2px(this, 6.0f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);

    }


    @Override
    public void setListener() {

        adapter = new RecyclerArrayAdapter<CollectionModel>(CollectionActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CollectionViewHolder(parent);
            }
        };
        mRecyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.footerview, this);
        adapter.setNoMore(R.layout.view_nom);
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemClick(int position) {
                adapter.remove(position);
                return true;
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter != null && adapter.getAllData().size() > 0) {
                    mRecyclerView.scrollToPosition(0);
                }
            }
        });
        mRecyclerView.setRefreshListener(this);
    }

    @Override
    public void onLoadMore() {
        loadData(false,false);
    }

    @Override
    public void onRefresh() {
        loadData(true,false);
    }

    @Override
    public void initData() {
        loadData(true,true);
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
                loadData(true,true);
                break;
            case R.id.btn_commodity:
                btnService.setSelected(false);
                btnCommodity.setSelected(true);
                collectionType = 1;
                loadData(true,true);
                break;
        }
    }

    /**
     * 取消收藏
     */
    public void collect(String goodsId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("favid", goodsId);
        VolleyRequest.RequestPost(context, Const.COLLECTION_DEL_URL, params, new VolleyRequest.MyStringCallback(context,false,null) {

            @Override
            public void onSuccess(JSONObject json) {
                if (json == null) {
                    ToastUtils.showShort(context, "移除失败");
                    return;
                }
                String message = json.optString("message");
                ToastUtils.showShort(context, message);
            }

            @Override
            public void onFailure() {

            }
        },true,true,1000);
    }

    /**
     * 加载数据的工具类
     *
     * @param isRefresh 是否是刷新
     */
    private void loadData(final boolean isRefresh,boolean isShowLoading) {

        if (isRefresh) {
            isMore = true;
            p = 1;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("attr", String.valueOf(collectionType));
        params.put("p", String.valueOf(p));
        VolleyRequest.RequestPost(context, Const.COLLECT_URL,

                params, new VolleyRequest.MyStringCallback(context,false,null) {

                    @Override
                    public void onSuccess(JSONObject json) {
                        List<CollectionModel> result = null;
                        if (json != null) {
                            String data = json.optString("message");
                            if ("".equals(data)) {// 暂无数据
                                isMore = false;
                            } else {
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<CollectionModel>>() {
                                }.getType();
                                result = gson.fromJson(data,
                                        type);
                                if (isRefresh) {
                                    adapter.clear();
                                }
                                if (result != null && !result.isEmpty()) {
                                    if (result.size() < 6) {
                                        isMore = false;
                                    } else {
                                        isMore = true;
                                        p++;
                                    }
                                } else {
                                    isMore = false;
                                }
                            }
                        }
                        adapter.addAll(result);
                    }
                    @Override
                    public void onFailure() {
                        adapter.addAll(new ArrayList());

                    }
                },isShowLoading,true,1000);
    }

    public class CollectionViewHolder extends BaseViewHolder<CollectionModel> {

        ImageView userIcon;
        TextView title;
        TextView price;
        TextView delete;

        public CollectionViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_collection);
            userIcon = $(R.id.user_icon);
            title = $(R.id.title);
            price = $(R.id.price);
            delete = $(R.id.delete);
        }

        @Override
        public void setData(final CollectionModel collection) {

            title.setText(collection.getGoodsname());
            price.setText(collection.getGoodsprice());
            Glide.with(context).load(collection.getPicurl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(userIcon);
        }

    }
}
