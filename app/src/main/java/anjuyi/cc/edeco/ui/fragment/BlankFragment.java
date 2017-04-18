package anjuyi.cc.edeco.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonSyntaxException;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.commonadapter.CommonAdapter;
import anjuyi.cc.edeco.adapter.commonadapter.CommonViewHolder;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.bean.user.Beauty;
import anjuyi.cc.edeco.bean.user.BeautyResult;
import anjuyi.cc.edeco.bean.user.BeautyService;
import anjuyi.cc.edeco.https.net.NetManager;
import anjuyi.cc.edeco.https.rx.RxManager;
import anjuyi.cc.edeco.ui.activity.utils.ImageZoomActivity;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import anjuyi.cc.edeco.view.GridViewForScrollView;
import anjuyi.cc.edeco.view.JDAdverView;
import anjuyi.cc.edeco.view.JDViewAdapter;
import anjuyi.cc.edeco.view.ObservableScrollView;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.HttpException;
import rx.Subscriber;


public class BlankFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    public static final String TAG = "BlankFragment";
    @BindView(R.id.status_bar)
    View statusBar;
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
    @BindView(R.id.toutiao)
    JDAdverView toutiao;
    @BindView(R.id.main_nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.img_cart_right)
    ImageView img_cart_right;


    private int PAGE_COUNT = 1;//页数
    private boolean isRefresh = true;//是否在刷新中
    private Boolean isLoadMore = true;//是否有更多

    private List<Beauty> beaties = new ArrayList<>();
    private CommonAdapter<Beauty> adapter;

    //测试数据
    private ArrayList<String> urls = new ArrayList<>();
    private String banner_1 = "http://pic65.nipic.com/file/20150419/8684504_205612692746_2.jpg";
    private String banner_2 = "http://img5.imgtn.bdimg.com/it/u=1002453751,940470370&fm=21&gp=0.jpg";
    private String banner_3 = "http://img3.imgtn.bdimg.com/it/u=1530315978,2251709607&fm=21&gp=0.jpg";
    private String banner_4 = "http://f.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=185a9dbd3f01213fcf6646d861d71ae7/3c6d55fbb2fb4316f868d31e26a4462309f7d35b.jpg";


    private ArrayList<String> strs = new ArrayList();
    private JDViewAdapter<String> jdapter;


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
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        strs.add("《侠客行》 " + "\n" + "             --李白");
        strs.add("赵客缦胡缨， 吴钩霜雪明。银鞍照白马， 飒沓如流星。");
        strs.add("十步杀一人， 千里不留行。事了拂衣去， 深藏身与名。");
        strs.add("闲过信陵饮， 脱剑膝前横。将炙啖朱亥， 持觞劝侯嬴。");
        strs.add("三杯吐然诺， 五岳倒为轻。 眼花耳热后， 意气素霓生。");
        strs.add("赵客缦胡缨， 吴钩霜雪明。银鞍照白马， 飒沓如流星。");
        strs.add("救赵挥金槌， 邯郸先震惊。千秋二壮士， 烜赫大梁城。");
        strs.add("纵死侠骨香， 不惭世上英。谁能书阁下， 白首太玄经。");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBar.setVisibility(View.VISIBLE);
            statusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            statusBar.setLayoutParams(statusBar.getLayoutParams());
        } else {
            statusBar.setVisibility(View.GONE);
        }

        mSwipeRefreshWidget.setColorSchemeResources(R.color.black, R.color.red,
                R.color.blue, R.color.yellow);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        tv_loadmore.setVisibility(View.GONE);
        ll_back.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        img_cart_right.setVisibility(View.VISIBLE);
        img_cart_right.setImageResource(R.mipmap.setting);

        jdapter = new JDViewAdapter<String>(strs, context, R.layout.view_nom_t) {
            @Override
            public void setItem(View view, String data) {
                ((TextView) view.findViewById(R.id.tv_tips)).setText(data);
            }
        };
        toutiao.setAdapter(jdapter);
        toutiao.start();
        //设置NavigationView对应menu item的点击事情
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_set:
                        break;
                    case R.id.nav_about:
                        break;
                }
                //隐藏NavigationView
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        mainCartTitle.setText("Mr.Lee");

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {


        adapter = new CommonAdapter<Beauty>(context, beaties, R.layout.item_goods) {
            @Override
            public void convert(CommonViewHolder holder, int position, Beauty item) {

                ImageView image = holder.getView(R.id.img_goods);
                Glide.with(context)
                        .load(item.getUrl())
                        .dontAnimate()
                        .error(R.mipmap.image_reload_placeholder)
                        .into(image);
            }
        };
        scroll.setOnScrollToBottomLintener(new ObservableScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(int scrollY, boolean isBottom) {

                Log.e("TAG", isBottom + "" + isRefresh + isLoadMore);
                if (isBottom && !isRefresh && isLoadMore) {
                    if (PAGE_COUNT == 5) {
                        isLoadMore = false;
                        tv_loadmore.setVisibility(View.VISIBLE);
                        return;
                    }
                    loadGoods(false);
                } else if (!isLoadMore) {


                }
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (TextUtils.isEmpty(beaties.get(position).getUrl())) {
                    ToastUtils.showShort(getContext(), "地址不存在");
                    return;
                }
                Intent intent = new Intent(context, ImageZoomActivity.class);
                ArrayList<String> urls = new ArrayList<>();
                urls.add(beaties.get(position).getUrl());
                intent.putStringArrayListExtra("imgpath", urls);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
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


    private void loadGoods(final boolean refresh) {

        if (refresh) {
            tv_loadmore.setVisibility(View.GONE);
            beaties.clear();
            PAGE_COUNT = 1;
            isLoadMore = true;
        }
        //正在加载中
        isRefresh = true;
        RxManager.getInstance().doSubscribe(NetManager.getInstance().create(BeautyService.class).getGankBeautyData("data/" + "福利" + "/12/" + PAGE_COUNT), new Subscriber<BeautyResult<List<Beauty>>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

                adapter.notifyDataSetChanged();
                mSwipeRefreshWidget.setRefreshing(false);
                isRefresh = false;
                if (throwable instanceof SocketTimeoutException) {
                    ToastUtils.showShort(context, "连接超时");
                } else if (throwable instanceof JsonSyntaxException) {
                    ToastUtils.showShort(context, "请求数据错误");
                } else if (throwable instanceof HttpException) {
                    ToastUtils.showShort(context, "连接异常");
                } else {
                    ToastUtils.showShort(context, "连接异常");
                }
            }

            @Override
            public void onNext(BeautyResult<List<Beauty>> gankItemDatas) {

                mSwipeRefreshWidget.setRefreshing(false);
                isRefresh = false;
                if (isLoadMore) {
                    if (null != gankItemDatas && gankItemDatas.getResults() != null && gankItemDatas.getResults().size() != 0) {
                        beaties.addAll(gankItemDatas.getResults());
                        adapter.notifyDataSetChanged();
                        PAGE_COUNT++;
                    } else {

                    }
                } else {
                    beaties.addAll(gankItemDatas.getResults());
                    adapter.notifyDataSetChanged();
                    isLoadMore = true;
                }

            }
        });


    }


    @Override
    public void onRefresh() {
        loadGoods(true);
    }



    @OnClick({ R.id.tv_a_b, R.id.img_a_a, R.id.img_a_b,R.id.ll_cart_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_cart_right:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return;
                }
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_a_b:
                break;
            case R.id.img_a_a:
                break;
            case R.id.img_a_b:
                break;
        }
    }

    public class MyPagerAdapter extends LoopPagerAdapter {


        private RollPagerView viewPager;
        private ArrayList<String> imgs;
        private Context context;

        public MyPagerAdapter(RollPagerView viewPager, ArrayList<String> imgs,Context context) {
            super(viewPager);

            this.viewPager=viewPager;
            this.imgs=imgs;
            this.context=context;

        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView img = new ImageView(context);
            Glide.with(context).load(imgs.get(position)).centerCrop().placeholder(R.mipmap.loading_image).error(R.mipmap.loading_image).diskCacheStrategy(DiskCacheStrategy.NONE).into(img);
            ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
            lp.width=ViewPager.LayoutParams.MATCH_PARENT;
            lp.height=ViewPager.LayoutParams.MATCH_PARENT;
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setLayoutParams(lp);
            return img;
        }

        @Override
        public int getRealCount() {
            return imgs.size();
        }

    }


}
