package anjuyi.cc.edeco.ui.fragment.home;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.rvadapter.BaseAdapter;
import anjuyi.cc.edeco.adapter.rvadapter.ViewHolder;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.util.DensityUtils;
import anjuyi.cc.edeco.util.ScreenUtils;


/**
 * Author: Othershe
 * Time: 2016/8/18 16:53
 */
public class BeautyAdapter extends BaseAdapter<Beauty> {


    private Context context;
    private static   Random ra =new Random();
    private static  int a=(ScreenUtils.getScreenWidth(BaseApplication.instance)- DensityUtils.dp2px(BaseApplication.instance,5.0f)*3)/2;


    public BeautyAdapter(Context context, List<Beauty> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder holder, Beauty gankItemData) {

        holder.setText(R.id.tv_author, gankItemData.getWho());
        holder.setText(R.id.tv_date, gankItemData.getDesc());
        ImageView image = holder.getView(R.id.img_beauty);

        ViewGroup.LayoutParams ps = image.getLayoutParams();


        ps.height = ra.nextInt(5)*DensityUtils.dp2px(context,10)+a;
        image.setLayoutParams(ps);

        Glide. with( context )
                .load(gankItemData.getUrl())
                .crossFade()
                .error(R.mipmap.image_reload_placeholder)
                .into(image);
//        new BitmapImageViewTarget(image) {
//            @Override
//            public void onResourceReady (Bitmap resource , GlideAnimation<? super Bitmap> glideAnimation) {
//                super.onResourceReady(resource , glideAnimation);
//                //提取并设置颜色
//                Palette.from (resource).generate(new Palette.PaletteAsyncListener() {
//                    public void onGenerated(Palette p) {
//                        int vibrant = p.getLightVibrantColor( 0x000000) ;
//                        // mHolder .mTextView .setBackgroundColor(vibrant) ;
//                    }
//                }) ;
//            }
//        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_beauty;
    }

}
