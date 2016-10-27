package anjuyi.cc.edeco.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.ArrayList;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.util.GlideUtils;

/**
 * Created by 葛晶晶 on 2016-7-25.
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
        GlideUtils.getInstance().LoadContextBitmap(context, imgs.get(position), img, R.mipmap.loading_image,R.mipmap.loading_image, null);
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
