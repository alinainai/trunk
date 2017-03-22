package anjuyi.cc.edeco.ui.fragment.cart;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.superplayer.library.utils.SuperPlayerUtils;

import java.util.List;
import java.util.Random;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.VideoBaseAdapter;
import anjuyi.cc.edeco.adapter.rvadapter.ViewHolder;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.util.DensityUtils;
import anjuyi.cc.edeco.util.ScreenUtils;


/**
 * Author: Othershe
 * Time: 2016/8/18 16:53
 */
public class VideoAdapter extends VideoBaseAdapter<VideoListBean> {


    private Context context;
    private static   Random ra =new Random();
    private static  int a=(ScreenUtils.getScreenWidth(BaseApplication.instance)- DensityUtils.dp2px(BaseApplication.instance,5.0f)*3)/2;


    public VideoAdapter(Context context, List<VideoListBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder holder, VideoListBean gankItemData,final int position) {


        final  RelativeLayout rlayPlayerControl = holder.getView(R.id.adapter_player_control);
        RelativeLayout rlayPlayer= holder.getView(R.id.adapter_super_video_layout);

        if (rlayPlayer!=null){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlayPlayer.getLayoutParams();
            layoutParams.height = (int) (SuperPlayerUtils.getScreenWidth((Activity) mContext) * 0.5652f);//这值是网上抄来的，我设置了这个之后就没有全屏回来拉伸的效果，具体为什么我也不太清楚
            rlayPlayer.setLayoutParams(layoutParams);
        }

        rlayPlayerControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playclick != null)
                    playclick.onPlayclick(position, rlayPlayerControl);
            }
        });

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_super_video;
    }


    private VideoAdapter.onPlayClick playclick;

    public void setPlayClick(VideoAdapter.onPlayClick playclick) {
        this.playclick = playclick;
    }

    public interface onPlayClick {
        void onPlayclick(int position, RelativeLayout image);
    }

}
