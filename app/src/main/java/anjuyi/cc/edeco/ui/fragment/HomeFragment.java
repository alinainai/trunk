package anjuyi.cc.edeco.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.MyPagerAdapter;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.bean.message.BannerBean;
import anjuyi.cc.edeco.util.DensityUtils;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import anjuyi.cc.edeco.view.ObservableScrollView;
import anjuyi.cc.edeco.view.ScrollTopView;
import anjuyi.cc.edeco.view.ptruiview.MyCustomHeaderView;
import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by ly on 2016/5/30 10:59.
 * 首页的fragment
 */
@SuppressLint("InflateParams")
public class HomeFragment extends BaseFragment {
    public static final String TAG = HomeFragment.class.getName();
    @BindView(R.id.home_viewPager)
    RollPagerView homeViewPager;
    @BindView(R.id.toutiao)
    ScrollTopView toutiao;
    @BindView(R.id.home_tv_location)
    TextView homeTvLocation;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.home_top_scBtn)
    TextView homeTopScBtn;
    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.rl_center)
    RelativeLayout rlCenter;
    @BindView(R.id.ll_right)
    ImageView llRight;
    @BindView(R.id.home_title)
    RelativeLayout homeTitle;
    @BindView(R.id.rl_home)
    RelativeLayout rl_home;
    @BindView(R.id.home_scv)
    ObservableScrollView mScrollView;
    @BindView(R.id.rotate_header_view_frame)
    PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.img_top)
    ImageView img_top;
    //是否显示向上箭头
    private Boolean isShow=false;
    private View view;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    List<BannerBean> articleList = new ArrayList<>();
    //测试数据
    private ArrayList<String> urls = new ArrayList<>();
    private String banner_1="http://10.100.24.108:8080/mvc/statics/image/banner_1.jpg";
    private String banner_2="http://10.100.24.108:8080/mvc/statics/image/banner_2.jpg";
    private String banner_3="http://10.100.24.108:8080/mvc/statics/image/banner_3.jpg";
    private String banner_4="http://10.100.24.108:8080/mvc/statics/image/banner_4.jpg";
   private  Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==1){

                isShowToast=true;
            }
            return true;
        }
    });



    private boolean isShowToast=true;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

        mScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

                if (y <= homeViewPager.getHeight() && y >=0) {
                    homeTitle.setBackgroundColor(Color.argb(
                            (int) ((float) y
                                    / (float) homeViewPager.getHeight() * 125),
                            0, 0, 0));
                } else if (y > homeViewPager.getHeight() && y >=0) {
                    homeTitle.setBackgroundColor(Color.argb(125, 0, 0, 0));
                }
                if (y>= ScreenUtils.getScreenHeight(context)*3/2) {

                    if(!isShow){
                        img_top.setVisibility(View.VISIBLE);
                        isShow=true;
                    }

                } else if (y< ScreenUtils.getScreenHeight(context)*3/2){
                    if(isShow){
                        img_top.setVisibility(View.GONE);
                        isShow=false;
                    }
                }

            }
        });
        mScrollView.setOnScrollToBottomLintener(new ObservableScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(int sy,boolean isBottom) {

                if(sy>0&&isBottom && isShowToast){
               ToastUtils.showShort(getActivity(),"滑动到底部了");
                    isShowToast=false;
                    mHandler.sendEmptyMessageDelayed(1,2000);
                }
            }
        });



        // header
//        final RentalsSunHeaderView header = new RentalsSunHeaderView(getContext());
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(0, DensityUtils.dp2px(context, 15), 0, DensityUtils.dp2px(context, 10));
//        header.setUp(mPtrFrame);

        final MyCustomHeaderView header = new MyCustomHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DensityUtils.dp2px(context, 15), 0, DensityUtils.dp2px(context, 10));
        header.setUp(mPtrFrame);

        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setDurationToCloseHeader(1500);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {


                if (homeViewPager.isPlaying()) {
                    homeViewPager.pause();
                    toutiao.cancelAuto();
                }
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (homeViewPager.isPlaying()) {
                            homeViewPager.pause();
                        }
                        mPtrFrame.refreshComplete();
                        homeViewPager.resume();
                        toutiao.setData(articleList);
                    }
                }, 1000);
            }

        });
        //模拟数据
        urls.add(banner_1);
        urls.add(banner_2);
        urls.add(banner_3);
        urls.add(banner_4);
        homeViewPager.setAdapter(new MyPagerAdapter(homeViewPager, urls, getContext()));

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        articleList.add(new BannerBean("title", "11111111111111111111111111111", null, null));
        articleList.add(new BannerBean("title", "22222222222222222222222222222", null, null));
        articleList.add(new BannerBean("title", "33333333333333333333333333333", null, null));
        articleList.add(new BannerBean("title", "44444444444444444444444444444", null, null));
        articleList.add(new BannerBean("title", "55555555555555555555555555555", null, null));
        articleList.add(new BannerBean("title", "66666666666666666666666666666", null, null));


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
//        mPtrFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPtrFrame.autoRefresh();
//            }
//        }, 1000);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (homeViewPager.isPlaying())
                homeViewPager.pause();
        } else {
            homeViewPager.resume();
        }
    }

    @OnClick({R.id.ll_location, R.id.img_search, R.id.home_top_scBtn, R.id.img_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_location:
                break;
            case R.id.img_search:
                break;
            case R.id.home_top_scBtn:
                break;
            case R.id.img_top:
             mScrollView.smoothScrollTo(0,0);
                Log.e("TAG","img_top被点击了");
                break;
        }
    }
}
