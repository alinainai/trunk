package anjuyi.cc.edeco.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.util.GlideUtils;

/**
 * 作者：Mr.Lee on 2016-7-26 16:52
 * 邮箱：569932357@qq.com
 */
public abstract class ImgSelectorAdapter extends RecyclerView.Adapter<ImgSelectorAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private List<String> mDatas;
    private int maxSize;
    private Context context;


    public abstract void onAddImg();
    public abstract void onDeleteImg(int position);
    public abstract void onShowImg(int position,View view);


    public ImgSelectorAdapter(Context context, List<String> datats, int maxSize) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datats;
        this.maxSize = maxSize;
        this.context = context;
    }

    @Override
    public ImgSelectorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_img_selector,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.selet_img);
        viewHolder.mDelete = (ImageView) view
                .findViewById(R.id.delete);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ImgSelectorAdapter.ViewHolder holder,final int position) {
        if (mDatas.size() < maxSize) {
            if (position == getItemCount()-1) {
                holder.mImg.setImageResource(R.mipmap.addimg_icon);
                holder.mDelete.setVisibility(View.GONE);

                holder.mImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onAddImg();
                    }
                });
            }else{
                holder.mDelete.setVisibility(View.VISIBLE);
                GlideUtils.getInstance().displayImage((Activity) context,mDatas.get(position), holder.mImg);
                holder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDeleteImg(position);
                    }
                });
                holder.mImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onShowImg(position,view);
                    }
                });

            }

        } else {
            holder.mDelete.setVisibility(View.VISIBLE);
            GlideUtils.getInstance().displayImage((Activity) context,mDatas.get(position), holder.mImg);
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteImg(position);
                }
            });
            holder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onShowImg(position,view);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        if (mDatas.size() == maxSize) {
            return maxSize;
        } else {
            return mDatas.size() + 1;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        ImageView mDelete;
        public ViewHolder(View arg0) {
            super(arg0);
        }
    }

}
