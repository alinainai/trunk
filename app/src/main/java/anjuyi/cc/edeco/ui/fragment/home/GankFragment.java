package anjuyi.cc.edeco.ui.fragment.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.rvadapter.OnItemClickListeners;
import anjuyi.cc.edeco.adapter.rvadapter.OnLoadMoreListener;
import anjuyi.cc.edeco.adapter.rvadapter.ViewHolder;
import anjuyi.cc.edeco.base.mvp.BaseMvpFragment;
import anjuyi.cc.edeco.ui.activity.utils.ImageZoomActivity;
import anjuyi.cc.edeco.util.DensityUtils;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.view.ptruiview.MyCustomHeaderView;
import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static anjuyi.cc.edeco.R.id.status_bar;


public class GankFragment extends BaseMvpFragment<GankDataView, GankDataPresenter> implements GankDataView {


    @BindView(R.id.rl_title)
    RelativeLayout rl_title;  //状态栏
    @BindView(status_bar)
    View statusBar;  //状态栏
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView main_cart_title;
    @BindView(R.id.rl_gank)
    RecyclerView mRecyclerView;
    @BindView(R.id.rotate_header_view_frame)
    PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.img_refresh)
    ImageView img_refresh;
    private int PAGE_COUNT = 1;//页数 请求
    private int mTempPageCount = 2;//页数
    private boolean isLoadMore;//是否还有
    private static final String mSubtype = "福利";
    private   MyCustomHeaderView  header;

    private BeautyAdapter mGankItemAdapter;

    public GankFragment() {
    }

    public static GankFragment newInstance() {
        GankFragment fragment = new GankFragment();
        return fragment;
    }


    @Override
    protected GankDataPresenter initPresenter() {
        return new GankDataPresenter();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //do something
                        mPtrFrame.autoRefresh();
                    }
                }, 800);
            }
        });
    }

    @Override
    protected void fetchData() {
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    public void initView() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBar.setVisibility(View.VISIBLE);
            statusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            statusBar.setLayoutParams(statusBar.getLayoutParams());
        } else {
            statusBar.setVisibility(View.GONE);
        }
        main_cart_title.setText("主页");
        llBack.setVisibility(View.GONE);
        rl_title.setBackgroundColor(getResources().getColor(R.color.money_color));

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置item之间的间隔

        mGankItemAdapter = new BeautyAdapter(context, new ArrayList<Beauty>(), true);
        mGankItemAdapter.setLoadingView(R.layout.view_loading_layout);

        header = new MyCustomHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DensityUtils.dp2px(context, 15), 0, DensityUtils.dp2px(context, 10));
        header.setUp(mPtrFrame);

        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setDurationToCloseHeader(1500);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isLoadMore = false;
                PAGE_COUNT = 1;
               mPresenter.getBeautyData("data/" + mSubtype + "/16/" + PAGE_COUNT);
            }

        });

        mGankItemAdapter.setOnItemClickListener(new OnItemClickListeners<Beauty>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, Beauty beauty, int position) {
                Intent intent = new Intent(context, ImageZoomActivity.class);
                ArrayList<String> urls = new ArrayList<>();
                urls.add(beauty.getUrl());
                intent.putStringArrayListExtra("imgpath", urls);
                startActivity(intent);
            }
        });

        mGankItemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (PAGE_COUNT == mTempPageCount && !isReload) {
                    return;
                }
                isLoadMore = true;
                PAGE_COUNT = mTempPageCount;
                mPresenter.getBeautyData("data/" + mSubtype + "/16/" + PAGE_COUNT);
            }
        });

        mRecyclerView.setAdapter(mGankItemAdapter);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void onSuccess(List<Beauty> data) {


        if (isLoadMore) {
            if (data.size() == 0) {
                mGankItemAdapter.setLoadEndView(R.layout.view_nom);
            } else {
                mGankItemAdapter.setLoadMoreData(data);
                mTempPageCount++;
            }
        } else {
            mGankItemAdapter.setNewData(data);
            mPtrFrame.refreshComplete();
        }


    }

    @Override
    public void onError() {

        if (isLoadMore) {
            mGankItemAdapter.setLoadFailedView(R.layout.view_error);
        } else {
            mPtrFrame.refreshComplete();
        }

    }


    @OnClick({R.id.img_refresh})
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.img_refresh:
                mPtrFrame.autoRefresh();
                mRecyclerView.scrollToPosition(0);
                break;

        }
    }



}
