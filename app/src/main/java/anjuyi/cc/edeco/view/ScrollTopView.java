package anjuyi.cc.edeco.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.bean.message.BannerBean;
import anjuyi.cc.edeco.util.DensityUtils;


/*
 * 类似淘宝 、头条
 *
 */
public class ScrollTopView extends LinearLayout {

    private Scroller mScroller;  //滚动实例

    private List<BannerBean> articleList;  //存放数据集合
    private final int DURING_TIME = 2000;  //滚动延迟
    private OnAdapterClickListener<BannerBean> click;
    private Context context;
    private final int distance=80;

    public ScrollTopView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public ScrollTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    /**
     * 设置数据
     * @param articleList
     */
    public void setData(List<BannerBean> articleList) {
        this.articleList = articleList;
        if (articleList != null) {
            removeAllViews();
            int size = articleList.size(); //> 1 ? 4 : articleList.size();
            for (int i = 0; i < size; i++) {
                addContentView(i);
            }
            if (articleList.size() > 1) {
                getLayoutParams().height = DensityUtils.dp2px(context, distance);  //调节滚动数据的高度
                // 滚动
                cancelAuto();
                mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
                smoothScrollBy(0, distance);
            }
        }
    }

    /**
     * 设置列表点击事件
     *
     * @param click
     */
    public void setClickListener(OnAdapterClickListener<BannerBean> click) {
        this.click = click;
    }

    /**
     * 重置数据
     */
    private void resetView() {
        BannerBean article = articleList.get(0);
        articleList.remove(0);
        articleList.add(article);

        for (int i = 0; i < articleList.size(); i++) {
            addContentView(i);
        }
    }

    /**
     * 取消滚动
     */
    public void cancelAuto() {
        mHandler.removeMessages(0);
    }

    private void addContentView(int position) {
        ViewHolder mHolder;
        if (position >= getChildCount()) {
            mHolder = new ViewHolder();
            View v = View.inflate(getContext(), R.layout.activity_address, null);
            mHolder.title = (TextView) v.findViewById(R.id.title);
            mHolder.info = (TextView) v.findViewById(R.id.info);
            mHolder.img = (ImageView) v.findViewById(R.id.icon);
            v.setTag(mHolder);
            addView(v, LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, distance));
        } else {
            mHolder = (ViewHolder) getChildAt(position).getTag();
        }
        final BannerBean article = articleList.get(position);
        mHolder.title.setText(article.getTitle());
        mHolder.info.setText(article.getInfo());
        mHolder.info.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (click != null) {
                    click.onAdapterClick(null, article);
                }
            }
        });
    }

    private class ViewHolder {
        TextView title;
        TextView info;
        ImageView img;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
            smoothScrollBy(0, distance);
            resetView();
        };
    };

    // 调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        // 设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), 0, dx, dy, DURING_TIME);
        invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }
    public interface OnAdapterClickListener <T> {
        public void onAdapterClick(View v,T t);
    }

}
