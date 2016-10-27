package anjuyi.cc.edeco.ui.activity.test.jiemiandonghua;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anjuyi.cc.edeco.R;


/**
 * Created by 晓勇 on 2015/8/26 0026.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private List<String> datas;
    public RecyclerViewAdapter(List<String> datas) {
        this.datas = datas;
    }
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder recyclerViewHolder, final int position) {
        recyclerViewHolder.mTextView.setText(datas.get(position));
        if(onItemClickListener!=null){
            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(recyclerViewHolder.itemView,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        return datas.size();

    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
