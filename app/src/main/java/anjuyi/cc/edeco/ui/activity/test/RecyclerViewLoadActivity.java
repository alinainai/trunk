package anjuyi.cc.edeco.ui.activity.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class RecyclerViewLoadActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;
//    @BindView(R.id.fab)
//    ImageView fab;
    @BindView(R.id.rl_num)
    LinearLayout rl_num;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_num_count)
    TextView tv_num_count;

    private Boolean isShow=false;
    private Boolean isMore=true;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
               //     mSwipeRefreshWidget.setRefreshing(false);
                    adapter.getList().clear();
                    p=0;

                    for (int i = 0; i < 20; i++) {
                        list.add(i + 1);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    mSwipeRefreshWidget.setRefreshing(false);

                    if(p==6){
                        isMore=false;
                        Snackbar.make(mRecyclerView, "数据加载完成", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }
                        for (int i = 0; i < 20; i++) {
                            list.add(i+1);
                        }
                        adapter.notifyDataSetChanged();
                        p++;
                    break;
                default:
                    break;
            }
        }

    };

    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    private SampleAdapter adapter;
    private List<Integer> list=new ArrayList<>();
    private int p=0;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_recycler_view_load;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {


        mSwipeRefreshWidget.setColorSchemeResources(R.color.black, R.color.red,
                R.color.blue, R.color.yellow);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    mSwipeRefreshWidget.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    handler.sendEmptyMessageDelayed(1, 3000);
                }
//                else{
//                    if(isShow){
//                        fab.setBackground(new ColorDrawable(Color.TRANSPARENT));
//                    }
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isShow){
                    tv_num.setText(String.valueOf(mLayoutManager.findLastVisibleItemPosition()));
                }
                if(mLayoutManager.findLastVisibleItemPosition()>=25){

                    if(!isShow){
                    //    fab.setVisibility(View.VISIBLE);
                        rl_num.setVisibility(View.VISIBLE);
                        isShow=true;
                    }

                }else{
                    if(isShow){
                      //  fab.setVisibility(View.GONE);
                        rl_num.setVisibility(View.GONE);
                        isShow=false;
                    }
                }
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new SampleAdapter(list);
        mRecyclerView.setAdapter(adapter);


        mSwipeRefreshWidget.post(new Runnable() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                try {
                    mSwipeRefreshWidget.setRefreshing(true);
                    handler.sendEmptyMessageDelayed(0, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @OnClick({R.id.rl_num})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_num:
                if(adapter.getList()!=null && adapter.getList().size()>0){
                    mRecyclerView.smoothScrollToPosition(0);
            }
                break;
        }
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0, 3000);
    }

//     class SampleAdapter extends RecyclerView.Adapter<ViewHolder> {
//        private List<Integer> list;
//
//        private static final int TYPE_ITEM = 0;
//        private static final int TYPE_FOOTER = 1;
//
//        public List<Integer> getList() {
//            return list;
//        }
//
//        public SampleAdapter(List<Integer> list) {
//           this. list =list;
//        }
//
//        // RecyclerView的count设置为数据总条数+ 1（footerView）
//        @Override
//        public int getItemCount() {
//            return list.size() + 1;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            // 最后一个item设置为footerView
//            if (position + 1 == getItemCount()) {
//                return TYPE_FOOTER;
//            } else {
//                return TYPE_ITEM;
//            }
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, final int position) {
//            if (holder instanceof ItemViewHolder) {
//                ((ItemViewHolder) holder).textView.setText(String.valueOf(list
//                        .get(position)));
//            }
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            if (viewType == TYPE_ITEM) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(
//                        R.layout.list_item_text, null);
//                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
//                        RecyclerView.LayoutParams.WRAP_CONTENT));
//                return new ItemViewHolder(view);
//            }
//            // type == TYPE_FOOTER 返回footerView
//            else if (viewType == TYPE_FOOTER) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(
//                        R.layout.footerview, null);
//                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
//                        RecyclerView.LayoutParams.WRAP_CONTENT));
//                return new FooterViewHolder(view);
//            }
//
//            return null;
//        }
//
//        class FooterViewHolder extends ViewHolder {
//
//            public FooterViewHolder(View view) {
//                super(view);
//            }
//
//        }
//
//        class ItemViewHolder extends ViewHolder {
//            TextView textView;
//
//            public ItemViewHolder(View view) {
//                super(view);
//                textView = (TextView) view.findViewById(R.id.text);
//            }
//        }
//    }
    class SampleAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Integer> list;


        public List<Integer> getList() {
            return list;
        }

        public SampleAdapter(List<Integer> list) {
            this. list =list;
        }

        // RecyclerView的count设置为数据总条数+ 1（footerView）
        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder) holder).textView.setText(String.valueOf(list
                        .get(position)));
                ((ItemViewHolder) holder).textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item_text, null);
                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT));
                return new ItemViewHolder(view);
        }

        class ItemViewHolder extends ViewHolder {
            TextView textView;

            public ItemViewHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.text);
            }
        }
    }


}
