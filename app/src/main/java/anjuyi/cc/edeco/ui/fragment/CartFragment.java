package anjuyi.cc.edeco.ui.fragment;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superplayer.library.SuperPlayer;
import com.superplayer.library.SuperPlayerManage;
import com.superplayer.library.mediaplayer.IjkVideoView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.ui.fragment.cart.VideoAdapter;
import anjuyi.cc.edeco.ui.fragment.cart.VideoListBean;
import anjuyi.cc.edeco.util.DensityUtils;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.view.ptruiview.MyCustomHeaderView;
import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by li on 2016/6/29.
 */
public class CartFragment extends BaseFragment {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;  //状态栏
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.rotate_header_view_frame)
    PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.rl_gank)
    RecyclerView superRecyclerView;
    @BindView(R.id.full_screen)
    RelativeLayout  fullScreen;;

    public static final String TAG = "CartFragment";
    public LinearLayoutManager mLayoutManager;
    private SuperPlayer player;
    private int postion = -1;
    private int lastPostion = -1;

    private VideoAdapter mAdapter;
    private List<VideoListBean> dataList = new ArrayList<>();


    public static CartFragment newInstance() {
        return new CartFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        rl_title.setBackgroundColor(getResources().getColor(R.color.action_sheet_ed));
        mainCartTitle.setText("视频界面");
        imgBack.setVisibility(View.GONE);

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superRecyclerView.setLayoutManager(mLayoutManager);


        final MyCustomHeaderView header = new MyCustomHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DensityUtils.dp2px(context, 15), 0, DensityUtils.dp2px(context, 10));
        header.setUp(mPtrFrame);

        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setDurationToCloseHeader(1500);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);


        player = SuperPlayerManage.getSuperManage().initialize(context);
        player.setShowTopControl(false).setSupportGesture(false);

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {


        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, superRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //do something
                        mPtrFrame.refreshComplete();
                    }
                }, 1000);
            }
        });

        mAdapter = new VideoAdapter(context,dataList,false);
        superRecyclerView.setAdapter(mAdapter);
        mAdapter.setPlayClick(new VideoAdapter.onPlayClick() {
            @Override
            public void onPlayclick(int pos, RelativeLayout image) {
                image.setVisibility(View.GONE);
                if (player.isPlaying() && lastPostion == pos){
                    return;
                }

                postion = pos;
                if (player.getVideoStatus() == IjkVideoView.STATE_PAUSED) {
                    if (pos != lastPostion) {
                        player.stopPlayVideo();
                        player.release();
                    }
                }
                if (lastPostion != -1) {
                    player.showView(R.id.adapter_player_control);
                }
                View view = superRecyclerView.findViewHolderForAdapterPosition(pos).itemView;
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.adapter_super_video);
                frameLayout.removeAllViews();
                player.showView(R.id.adapter_player_control);
                frameLayout.addView(player);
                player.play(dataList.get(pos).getVideoUrl());
                lastPostion = pos;
            }
        });
        /**
         * 播放完设置还原播放界面
         */
        player.onComplete(new Runnable() {
            @Override
            public void run() {
                ViewGroup last = (ViewGroup) player.getParent();//找到videoitemview的父类，然后remove
                if (last != null && last.getChildCount() > 0) {
                    last.removeAllViews();
                    View itemView = (View) last.getParent();
                    if (itemView != null) {
                        itemView.findViewById(R.id.adapter_player_control).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        /***
         * 监听列表的下拉滑动
         */
        superRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                int index = superRecyclerView.getChildAdapterPosition(view);
                View controlview = view.findViewById(R.id.adapter_player_control);
                if (controlview == null) {
                    return;
                }
                view.findViewById(R.id.adapter_player_control).setVisibility(View.VISIBLE);
                if (index == postion) {
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.adapter_super_video);
                    frameLayout.removeAllViews();
                    if (player != null &&
                            ((player.isPlaying()) || player.getVideoStatus() == IjkVideoView.STATE_PAUSED)) {
                        view.findViewById(R.id.adapter_player_control).setVisibility(View.GONE);
                    }
                    if (player.getVideoStatus() == IjkVideoView.STATE_PAUSED) {
                        if (player.getParent() != null)
                            ((ViewGroup) player.getParent()).removeAllViews();
                        frameLayout.addView(player);
                        return;
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                int index = superRecyclerView.getChildAdapterPosition(view);
                if ((index) == postion) {
                    if (true) {
                        if (player != null) {
                            player.stop();
                            player.release();
                            player.showView(R.id.adapter_player_control);
                        }
                    }
                }
            }
        });


    }
    @Override
    public void initData(Bundle savedInstanceState) {

        dataList.clear();
        VideoListBean bean1 = new VideoListBean();
        bean1.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=9502&editionType=normal");
        dataList.add(bean1);
        VideoListBean bean2 = new VideoListBean();
        bean2.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=9508&editionType=normal");
        dataList.add(bean2);
        VideoListBean bean3 = new VideoListBean();
        bean3.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=8438&editionType=normal");
        dataList.add(bean3);
        VideoListBean bean4 = new VideoListBean();
        bean4.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=8340&editionType=normal");
        dataList.add(bean4);
        VideoListBean bean5 = new VideoListBean();
        bean5.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=9392&editionType=normal");
        dataList.add(bean5);
        VideoListBean bean6 = new VideoListBean();
        bean6.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=7524&editionType=normal");
        dataList.add(bean6);
        VideoListBean bean7 = new VideoListBean();
        bean7.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=9444&editionType=normal");
        dataList.add(bean7);
        VideoListBean bean8 = new VideoListBean();
        bean8.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=9442&editionType=normal");
        dataList.add(bean8);
        VideoListBean bean9 = new VideoListBean();
        bean9.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=8530&editionType=normal");
        dataList.add(bean9);
        VideoListBean bean10 = new VideoListBean();
        bean10.setVideoUrl("http://baobab.wandoujia.com/api/v1/playUrl?vid=9418&editionType=normal");
        dataList.add(bean10);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fullScreen.setVisibility(View.GONE);
                fullScreen.removeAllViews();
                superRecyclerView.setVisibility(View.VISIBLE);
                if (postion <= mLayoutManager.findLastVisibleItemPosition()
                        && postion >= mLayoutManager.findFirstVisibleItemPosition()) {
                    View view = superRecyclerView.findViewHolderForAdapterPosition(postion).itemView;
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.adapter_super_video);
                    frameLayout.removeAllViews();
                    ViewGroup last = (ViewGroup) player.getParent();//找到videoitemview的父类，然后remove
                    if (last != null) {
                        last.removeAllViews();
                    }
                    frameLayout.addView(player);
                }
                int mShowFlags =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                fullScreen.setSystemUiVisibility(mShowFlags);
            } else {
                ViewGroup viewGroup = (ViewGroup) player.getParent();
                if (viewGroup == null)
                    return;
                viewGroup.removeAllViews();
                fullScreen.addView(player);
                fullScreen.setVisibility(View.VISIBLE);
                int mHideFlags =
                        View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        ;
                fullScreen.setSystemUiVisibility(mHideFlags);
            }
        } else {
            fullScreen.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:





                break;
        }
    }


    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (player != null && player.onBackPressed()) {
//            return;
//        }
//        super.onBackPressed();
//    }

}
