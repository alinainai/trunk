package anjuyi.cc.edeco.ui.fragment.classify;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.rvadapter.BaseAdapter;
import anjuyi.cc.edeco.adapter.rvadapter.ViewHolder;
import anjuyi.cc.edeco.base.BaseApplication;


/**
 * Author: Othershe
 * Time: 2016/8/18 16:53
 */
public class GankItemAdapter extends BaseAdapter<GankItemData> {


    public GankItemAdapter(Context context, List<GankItemData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, GankItemData gankItemData) {
        holder.setText(R.id.gank_item_desc, gankItemData.getDesc());

        String who = TextUtils.isEmpty(gankItemData.getWho()) ? "null" : gankItemData.getWho();
        holder.setText(R.id.gank_item_who, who);

        holder.setText(R.id.gank_item_publishedat, gankItemData.getPublishedAt().substring(0, 10));
        ImageView image = holder.getView(R.id.gank_item_icon);
        Glide.with(BaseApplication.instance)
                .load(R.mipmap.user_icon)
                .crossFade()
                .into(image);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_gank_layout;
    }

}
