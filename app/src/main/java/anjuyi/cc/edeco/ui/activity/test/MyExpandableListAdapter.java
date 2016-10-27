package anjuyi.cc.edeco.ui.activity.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.util.ToastUtils;

public abstract class MyExpandableListAdapter extends BaseExpandableListAdapter {




    private Context context;
    private String[] generalsTypes;
    private String[][] generals;
    public MyExpandableListAdapter(Context context,String[] generalsTypes,String[][] generals){
        this.context=context;
        this.generalsTypes=generalsTypes;
        this.generals=generals;

    }

    public abstract void onTitleClick(int position);

    @Override
    public int getGroupCount() {
        return generalsTypes.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return generals[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder mHolder;
        if (convertView ==null) {
            mHolder = new GroupViewHolder();
            convertView = View.inflate(context, R.layout.home_classfication, null);
            mHolder.img1 = (ImageView) convertView.findViewById(R.id.home_btn_sever);
            mHolder.img2 = (ImageView)convertView.findViewById(R.id.home_btn_activ);
            mHolder.img3 = (ImageView) convertView.findViewById(R.id.home_btn_ency);
            mHolder.img4 = (ImageView) convertView.findViewById(R.id.home_btn_phy);
            mHolder.img5 = (ImageView) convertView.findViewById(R.id.home_btn_quan);
            mHolder.img6 = (ImageView)convertView.findViewById(R.id.home_btn_help);
            mHolder.img7 = (ImageView)convertView.findViewById(R.id.home_btn_all);
            mHolder.img8 = (ImageView)convertView.findViewById(R.id.home_btn_bas);
            mHolder.title = (TextView)convertView.findViewById(R.id.tv_title);
            convertView.setTag(mHolder);
        } else {
            mHolder = (GroupViewHolder)convertView.getTag();
        }

        mHolder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"111");
            }
        });
        mHolder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"111");
            }
        });
        mHolder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"111");
            }
        });
        mHolder.img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"111");
            }
        });
        mHolder.img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"111");
            }
        });
        mHolder.img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"111");
            }
        });
        mHolder.img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"111");
            }
        });
        mHolder.img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"111");
            }
        });
        mHolder.title.setText(generalsTypes[groupPosition]+"-----第几个条目");
        mHolder. title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onTitleClick(groupPosition);


            }
        });
        return convertView;
    }

    class GroupViewHolder {

        ImageView img1;
        ImageView img2;
        ImageView img3;
        ImageView img4;
        ImageView img5;
        ImageView img6;
        ImageView img7;
        ImageView img8;
        TextView title;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder mHolder;
        if (convertView ==null) {
            mHolder = new ChildViewHolder();
            convertView = View.inflate(context, R.layout.view_banner_item, null);

            mHolder.title = (TextView)convertView.findViewById(R.id.title);
            mHolder.info = (TextView)convertView.findViewById(R.id.info);
            mHolder.img = (ImageView)convertView.findViewById(R.id.icon);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ChildViewHolder)convertView.getTag();
        }
        mHolder.title.setText(generals[groupPosition][childPosition]+"- - - - - 第几个条目");
        return convertView;
    }

    class ChildViewHolder {

        TextView title;
        TextView info;
        ImageView img;

    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
