package anjuyi.cc.edeco.ui.activity.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.cmonadapter.CommonRcvAdapter;
import anjuyi.cc.edeco.adapter.cmonadapter.item.AdapterItem;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.ui.activity.test.path.SlackLoadingView;
import butterknife.BindView;
import butterknife.OnClick;

public class HRecyclerViewActivity extends BaseActivity {

    private static final String TAG = "HRecyclerViewActivity";

    @BindView(R.id.id_recyclerview_horizontal)
    RecyclerView mRecyclerView;
    @BindView(R.id.adddata)
    TextView adddata;
    @BindView(R.id.startAnima)
    TextView startAnima;
    @BindView(R.id.resetAnima)
    TextView resetAnima;
    @BindView(R.id.img_show)
    SlackLoadingView mLoadingView;
    private CommonRcvAdapter<DemoModel> adapter;
    private List<DemoModel> data = new ArrayList<>();

    @Override
    protected int initLayoutId() {
        return R.layout.activity_hrecycler_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CommonRcvAdapter<DemoModel>(data) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new AdapterItem<DemoModel>() {
                    //优化小技巧：这个就等于一个viewHolder，用于复用，所以不会重复建立对象
                    private static final String TAG = "ButtonItem";
                    private TextView title;
                    private ImageView img;

                    @Override
                    public int getLayoutResId() {
                        return R.layout.item_view_hrv;
                    }

                    @Override
                    public void bindViews(View root) {
                        title = (TextView) root.findViewById(R.id.id_index_gallery_item_text);
                        img = (ImageView) root.findViewById(R.id.id_index_gallery_item_image);
                    }

                    @Override
                    public void setViews() {
                        // 这个方法仅仅在item构建时才会触发，所以在这里也仅仅建立一次监听器，不会重复建立
                    }

                    @Override
                    public void handleData(DemoModel type, final int position) {
                        // 在每次适配器getView的时候就会触发，这里避免做耗时的操作
                        img.setImageResource(R.mipmap.icon_logo);
                        title.setText(type.name);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(img.getContext(), "pos = " + position, Toast.LENGTH_SHORT).show();
                                data.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                };
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.adddata, R.id.startAnima,R.id.resetAnima})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.adddata:
                DemoModel model;
                for (int i = 0; i < 20; i++) {
                    model = new DemoModel();
                    model.name = "第" + i + "个条目";
                    data.add(model);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.startAnima:
                mLoadingView.start();
                break;
            case R.id.resetAnima:
                  mLoadingView.reset();
                break;
        }
    }

    public class DemoModel {
        public String name;
        public int resId;
        public String type;
    }
}
