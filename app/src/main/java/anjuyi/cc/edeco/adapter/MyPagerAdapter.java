package anjuyi.cc.edeco.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.ArrayList;

import anjuyi.cc.edeco.R;

/**
 * 广告轮播图的适配器
 * loopviewpager
 */
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
