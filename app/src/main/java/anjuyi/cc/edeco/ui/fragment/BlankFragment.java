package anjuyi.cc.edeco.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonSyntaxException;
import com.jude.rollviewpager.RollPagerView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.MyPagerAdapter;
import anjuyi.cc.edeco.adapter.commonadapter.CommonAdapter;
import anjuyi.cc.edeco.adapter.commonadapter.CommonViewHolder;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.https.net.NetManager;
import anjuyi.cc.edeco.https.rx.RxManager;
import anjuyi.cc.edeco.ui.fragment.home.Beauty;
import anjuyi.cc.edeco.ui.fragment.home.BeautyResult;
import anjuyi.cc.edeco.ui.fragment.home.BeautyService;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import anjuyi.cc.edeco.view.GridViewForScrollView;
import anjuyi.cc.edeco.view.ObservableScrollView;
import anjuyi.cc.edeco.view.ScrollTopView;
import butterknife.BindView;
import retrofit2.HttpException;
import rx.Subscriber;


public class BlankFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    public static final String TAG = "BlankFragment";
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.scroll)
    ObservableScrollView scroll;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshWidget;
    @BindView(R.id.list)
    GridViewForScrollView list;
    @BindView(R.id.tv_loadmore)
    TextView tv_loadmore;
    @BindView(R.id.home_viewPager)
    RollPagerView home_viewPager;

    private int PAGE_COUNT = 1;//页数
    private boolean isRefresh=true;//是否在刷新中
    private Boolean isLoadMore=true;//是否有更多

    private List<Beauty> beaties=new ArrayList<>();
    private CommonAdapter<Beauty> adapter;

    //测试数据
    private ArrayList<String> urls = new ArrayList<>();
    private String banner_1="http://pic65.nipic.com/file/20150419/8684504_205612692746_2.jpg";
    private String banner_2="http://img5.imgtn.bdimg.com/it/u=1002453751,940470370&fm=21&gp=0.jpg";
    private String banner_3="http://img3.imgtn.bdimg.com/it/u=1530315978,2251709607&fm=21&gp=0.jpg";
    private String banner_4="http://f.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=185a9dbd3f01213fcf6646d861d71ae7/3c6d55fbb2fb4316f868d31e26a4462309f7d35b.jpg";

    @BindView(R.id.toutiao)
    ScrollTopView toutiao;

    public BlankFragment() {
    }

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_blank;
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
        mainCartTitle.setText("主页");
        rlTitle.setBackgroundColor(getResources().getColor(R.color.money_color));

        mSwipeRefreshWidget.setColorSchemeResources(R.color.black, R.color.red,
                R.color.blue, R.color.yellow);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        tv_loadmore.setVisibility(View.GONE);

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {


        adapter = new CommonAdapter<Beauty>(context,beaties,R.layout.item_goods) {
            @Override
            public void convert(CommonViewHolder holder, int position, Beauty item) {

                ImageView image = holder.getView(R.id.img_goods);
                Glide. with( context )
                        .load(item.getUrl())
                        .dontAnimate()
                        .error(R.mipmap.image_reload_placeholder)
                        .into(image);
            }
        };
        scroll.setOnScrollToBottomLintener(new ObservableScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(int scrollY, boolean isBottom) {

                Log.e("TAG",isBottom +""+isRefresh+isLoadMore);
                if(isBottom && !isRefresh && isLoadMore){
                    if(PAGE_COUNT==5){
                        isLoadMore=false;
                        tv_loadmore.setVisibility(View.VISIBLE);
                        return;
                    }
                    loadGoods(false);
                }else if(!isLoadMore){


                }
            }
        });
        list.setAdapter(adapter);
        urls.add(banner_1);
        urls.add(banner_2);
        urls.add(banner_3);
        urls.add(banner_4);
        home_viewPager.setAdapter(new MyPagerAdapter(home_viewPager, urls, getContext()));
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mSwipeRefreshWidget.setRefreshing(true);
        loadGoods(true);
    }


    private void loadGoods(final boolean refresh){


        if(refresh){
            tv_loadmore.setVisibility(View.GONE);
            beaties.clear();
            PAGE_COUNT=1;
            isLoadMore=true;
        }
        //正在加载中
        isRefresh=true;
        RxManager.getInstance().doSubscribe(NetManager.getInstance().create(BeautyService.class).getGankBeautyData("data/" + "福利" + "/12/" + PAGE_COUNT), new Subscriber<BeautyResult<List<Beauty>>>() {


            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable throwable) {

                    adapter.notifyDataSetChanged();
                    mSwipeRefreshWidget.setRefreshing(false);
                    isRefresh=false;
                if (throwable instanceof SocketTimeoutException) {
                    ToastUtils.showShort(context,"连接超时");
                } else if(throwable instanceof JsonSyntaxException){
                    ToastUtils.showShort(context,"请求数据错误");
                }else if(throwable instanceof HttpException){
                    ToastUtils.showShort(context,"连接异常");
                }else{
                    ToastUtils.showShort(context,"连接异常");
                }
            }

            @Override
            public void onNext(BeautyResult<List<Beauty>> gankItemDatas) {

                    mSwipeRefreshWidget.setRefreshing(false);
                    isRefresh=false;
                if (isLoadMore) {
                    if (null!=gankItemDatas&&gankItemDatas.getResults()!=null&&gankItemDatas.getResults().size() != 0) {
                        beaties.addAll(gankItemDatas.getResults());
                        adapter.notifyDataSetChanged();
                        PAGE_COUNT++;
                    } else {

                    }
                } else {
                    beaties.addAll(gankItemDatas.getResults());
                    adapter.notifyDataSetChanged();
                    isLoadMore=true;
                }

            }
        });



    }




    @Override
    public void onRefresh() {
        loadGoods(true);
    }




}
