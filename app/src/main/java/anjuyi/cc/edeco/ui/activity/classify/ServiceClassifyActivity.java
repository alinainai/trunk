package anjuyi.cc.edeco.ui.activity.classify;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.CommonAdapter;
import anjuyi.cc.edeco.adapter.CommonViewHolder;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.view.GridViewForScrollView;
import butterknife.BindView;

/**
 * Created by ly on 2016/6/20 15:39.
 * 配装服务,基础建材
 * 0==配装服务
 * 1==基础建材
 */
public class ServiceClassifyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.service_classify_brand_recommend_ll)
    LinearLayout serviceClassifyBrandRecommendLl;//整体的品牌推荐
    @BindView(R.id.service_classify_fixed_tv)
    TextView serviceClassifyFixedTv;//不动的字
    @BindView(R.id.service_classify_left_mListView)
    ListView serviceClassifyLeftMListView;//左侧ListView
    @BindView(R.id.service_classify_you_recommend_mGridView)
    GridViewForScrollView serviceClassifyYouRecommendMGridView;//右侧GridView
    @BindView(R.id.service_classify_brand_recommend_mGridViews)
    GridViewForScrollView serviceClassifyBrandRecommendMGridViews;//品牌推荐的GridView

    private String type;//判断字的改变

    private List<String> list = new ArrayList<String>();
    private CommonAdapter<String> listAdapter;
    private CommonAdapter<String> gridAdapter;
    private CommonAdapter<String> conAdapter;
    private int currentIndex = 0;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_service_classify;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //判断字
        type = getIntent().getStringExtra("type");
        if (type.equals("0")) {  //配装服务
            serviceClassifyBrandRecommendLl.setVisibility(View.GONE);
            serviceClassifyFixedTv.setText(R.string.install_service_tv);
        } else if (type.equals("1")) {  //基础建材
            serviceClassifyBrandRecommendLl.setVisibility(View.VISIBLE);
            serviceClassifyFixedTv.setText(R.string.basic_building_tv);
        }
    }

    @Override
    public void setListener() {
        serviceClassifyLeftMListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                currentIndex = position;
                listAdapter.notifyDataSetChanged();
                serviceClassifyLeftMListView.setSelection(currentIndex);
            }
        });

        serviceClassifyYouRecommendMGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            }
        });

        serviceClassifyBrandRecommendMGridViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void initData() {
        list.add("为您推荐");
        list.add("水工材料");
        list.add("电工材料");
        list.add("瓦工材料");
        list.add("木工材料");
        list.add("油工材料");
        list.add("其他材料");
        listAdapter = new CommonAdapter<String>(context, list, R.layout.list_service_classify_item) {

            @Override
            public void convert(CommonViewHolder holder, int position, String item) {
                View line = holder.getView(R.id.line);
                TextView tvItem = holder.getView(R.id.item_title);
                line.setVisibility(View.GONE);
                tvItem.setSelected(false);
                if (currentIndex == position) {
                    line.setVisibility(View.VISIBLE);
                    tvItem.setSelected(true);
                }
                tvItem.setText(item);
            }
        };
        gridAdapter = new CommonAdapter<String>(context, list, R.layout.grid_service_classify_item) {

            @Override
            public void convert(CommonViewHolder holder, int position, String item) {

            }
        };

        conAdapter=new CommonAdapter<String>(context,list,R.layout.grid_service_classify_brand_item) {
            @Override
            public void convert(CommonViewHolder holder, int position, String item) {

            }
        };

        serviceClassifyLeftMListView.setAdapter(listAdapter);
        serviceClassifyYouRecommendMGridView.setAdapter(gridAdapter);
        serviceClassifyBrandRecommendMGridViews.setAdapter(conAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
