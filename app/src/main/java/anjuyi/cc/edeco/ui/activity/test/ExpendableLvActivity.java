package anjuyi.cc.edeco.ui.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;

public class ExpendableLvActivity extends BaseActivity {


    int[] logos = new int[]{R.mipmap.ic_share_moments, R.mipmap.ic_share_qq, R.mipmap.ic_share_weibo};
    @BindView(R.id.list)
    ExpandableListView list;
    //设置组视图的显示文字
    private String[] generalsTypes = new String[]{"魏", "蜀", "吴"};
    //子视图显示文字
    private String[][] generals = new String[][]{
            {"夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修"},
            {"马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云"},
            {"吕蒙", "陆逊", "孙权", "周瑜", "孙尚香"}};


    private  MyExpandableListAdapter adapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_expendable_lv;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
         adapter=new MyExpandableListAdapter(context,generalsTypes,generals){

             @Override
             public void onTitleClick(int position) {

               if( !list.isGroupExpanded(position)) {
                   list.expandGroup(position);
                   for (int i = 0;i< generalsTypes.length; i++) {
                       if (position != i) {// 关闭其他分组
                           list.collapseGroup(i);
                       }
                   }
               }else {
                   list.collapseGroup(position);
               }

             }
         };
        list.setAdapter(adapter);
        list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });

    }
}
