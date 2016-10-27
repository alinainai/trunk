package anjuyi.cc.edeco.ui.activity.test;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.CommonAdapter;
import anjuyi.cc.edeco.adapter.CommonViewHolder;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.bean.goods.HistoryResultModel;
import anjuyi.cc.edeco.https.Const;
import anjuyi.cc.edeco.util.DensityUtils;
import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2016/7/18.
 */
public class PtrTestActivity extends BaseActivity {
    @BindView(R.id.rotate_header_list_view)
    ListView mListView;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout mPtrFrame;


   private  CommonAdapter adapter;
    private Handler handler = new Handler();
    private int p;
    private ArrayList<HistoryResultModel> list = new ArrayList<>();
    private boolean isMore=true;
    private View view_nomore;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_ptrrefresh;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        view_nomore= LayoutInflater.from(context).inflate(R.layout.view_nom,null);

        final StoreHouseHeader header = new StoreHouseHeader(context);
        header.setPadding(0, DensityUtils.dp2px(context,15), 0, 0);
        header.initWithPointList(getPointList());

        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LoadHistoryResultMore(true,false);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
//        mPtrFrame.setResistance(1.7f);
//        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.0f);
//        mPtrFrame.setDurationToClose(200);
//        mPtrFrame.setDurationToCloseHeader(1000);
//        // default is false
//        mPtrFrame.setPullToRefresh(false);
//        // default is true
//        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);




    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {


        adapter = new CommonAdapter<HistoryResultModel>(context, list,
                R.layout.grid_product_item) {

            @Override
            public void convert(CommonViewHolder holder, int position,
                                final HistoryResultModel item) {
                ImageView img = holder.getView(R.id.item_icon);
                Glide.with(context).load(item.getPicurl()).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(img);

                holder.setText(R.id.item_title, item.getGoodsname());
                holder.setText(R.id.item_price, "¥ " + item.getGoodsprice());

            }
        };
        mListView.setAdapter(adapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                        if(isMore)
                            LoadHistoryResultMore(false,false);
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


    }

    private ArrayList<float[]> getPointList() {
        // this point is taken from https://github.com/cloay/CRefreshLayout
        List<Point> startPoints = new ArrayList<Point>();
        startPoints.add(new Point(240, 80));
        startPoints.add(new Point(270, 80));
        startPoints.add(new Point(265, 103));
        startPoints.add(new Point(255, 65));
        startPoints.add(new Point(275, 80));
        startPoints.add(new Point(275, 80));
        startPoints.add(new Point(302, 80));
        startPoints.add(new Point(275, 107));

        startPoints.add(new Point(320, 70));
        startPoints.add(new Point(313, 80));
        startPoints.add(new Point(330, 63));
        startPoints.add(new Point(315, 87));
        startPoints.add(new Point(330, 80));
        startPoints.add(new Point(315, 100));
        startPoints.add(new Point(330, 90));
        startPoints.add(new Point(315, 110));
        startPoints.add(new Point(345, 65));
        startPoints.add(new Point(357, 67));
        startPoints.add(new Point(363, 103));

        startPoints.add(new Point(375, 80));
        startPoints.add(new Point(375, 80));
        startPoints.add(new Point(425, 80));
        startPoints.add(new Point(380, 95));
        startPoints.add(new Point(400, 63));

        List<Point> endPoints = new ArrayList<Point>();
        endPoints.add(new Point(270, 80));
        endPoints.add(new Point(270, 110));
        endPoints.add(new Point(270, 110));
        endPoints.add(new Point(250, 110));
        endPoints.add(new Point(275, 107));
        endPoints.add(new Point(302, 80));
        endPoints.add(new Point(302, 107));
        endPoints.add(new Point(302, 107));

        endPoints.add(new Point(340, 70));
        endPoints.add(new Point(360, 80));
        endPoints.add(new Point(330, 80));
        endPoints.add(new Point(340, 87));
        endPoints.add(new Point(315, 100));
        endPoints.add(new Point(345, 98));
        endPoints.add(new Point(330, 120));
        endPoints.add(new Point(345, 108));
        endPoints.add(new Point(360, 120));
        endPoints.add(new Point(363, 75));
        endPoints.add(new Point(345, 117));

        endPoints.add(new Point(380, 95));
        endPoints.add(new Point(425, 80));
        endPoints.add(new Point(420, 95));
        endPoints.add(new Point(420, 95));
        endPoints.add(new Point(400, 120));
        ArrayList<float[]> list = new ArrayList<float[]>();

        int offsetX = Integer.MAX_VALUE;
        int offsetY = Integer.MAX_VALUE;

        for (int i = 0; i < startPoints.size(); i++) {
            offsetX = Math.min(startPoints.get(i).x, offsetX);
            offsetY = Math.min(startPoints.get(i).y, offsetY);
        }
        for (int i = 0; i < endPoints.size(); i++) {
            float[] point = new float[4];
            point[0] = startPoints.get(i).x - offsetX;
            point[1] = startPoints.get(i).y - offsetY;
            point[2] = endPoints.get(i).x - offsetX;
            point[3] = endPoints.get(i).y - offsetY;
            list.add(point);
        }
        return list;
    }

    /**
     * 加载更多浏览历史
     */
    public void LoadHistoryResultMore(final boolean isRefresh, boolean isShowLoad) {


        //测试用数据

                if (isRefresh) {
                    isMore = true;
                    if(mListView.getFooterViewsCount()!=0){
                        mListView.removeFooterView(view_nomore);
                    }
                    p = 1;
                    list.clear();
                }
                ArrayList<HistoryResultModel> result = null;
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<HistoryResultModel>>() {
                }.getType();
                result = gson.fromJson(Const.DATA,
                        type);
                if (p == 3) {
                    isMore=false;
                    if(mListView.getFooterViewsCount()==0){
                        mListView.addFooterView(view_nomore, null, false);
                    }
                } else {
                    list.addAll(result);
                    adapter.notifyDataSetChanged();
                }
                p++;
                mPtrFrame.refreshComplete();


    }

}
