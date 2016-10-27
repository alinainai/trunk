package anjuyi.cc.edeco.ui.activity.utils;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.util.GlideUtils;
import anjuyi.cc.edeco.view.ZoomImageView;
import anjuyi.cc.edeco.view.ZoomViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 显示图片的工具界面
 */

public class ImageZoomActivity extends BaseActivity {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView tv_title;
    @BindView(R.id.zoomViewPager)
    ZoomViewPager viewPager;
    @BindView(R.id.img_cart_right)
    ImageView img_cart_right;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tvRight)
    TextView tvRight;
    private int current;
    private List<String> urls;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_zoomimage;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        img_cart_right.setVisibility(View.VISIBLE);
        viewPager.setPageMargin((int) getResources().getDisplayMetrics().density * 10);
        urls = getIntent().getStringArrayListExtra("imgpath");
        current = getIntent().getIntExtra("current", 0);
        img_cart_right.setImageResource(R.drawable.share_press);

    }

    @Override
    public void setListener() {

        viewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                tv_title.setText(String.valueOf((arg0 + 1)) + "/" + String.valueOf(urls.size()));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });


    }

    @Override
    public void initData() {
        tv_title.setText(String.valueOf((current + 1)) + "/" + String.valueOf(urls.size()));
        viewPager.setAdapter(new SamplePagerAdapter(urls, ImageZoomActivity.this));
        viewPager.setCurrentItem(current);
    }

    @OnClick({R.id.ll_back, R.id.img_cart_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.img_cart_right:


                break;
        }
    }

    private class PhotoTapListener implements ZoomImageView.OnPhotoTapListener {
        @Override
        public void onPhotoTap(View view, float x, float y) {

        }
    }

    private class SamplePagerAdapter extends PagerAdapter {
        private List<String> urlString;
        private Context context;

        public SamplePagerAdapter(List<String> urlString, Context context) {
            // Create a background thread and a handler for it
            this.urlString = urlString;
            this.context = context;
        }

        @Override
        public int getCount() {
            return urlString.size();
        }

        @Override
        public View instantiateItem(final ViewGroup container,
                                    final int position) {

            final ZoomImageView zoomImageView = new ZoomImageView(
                    container.getContext());
            if (Build.VERSION.SDK_INT >= 21) {
                zoomImageView.setTransitionName("trans");
            }
            //此处没有缓存到本地
            GlideUtils.getInstance().LoadContextBitmap(context, urlString.get(position), zoomImageView, R.mipmap.loading_image, R.mipmap.loading_image, null);
            container.addView(zoomImageView, LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            return zoomImageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
