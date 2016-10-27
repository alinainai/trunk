package anjuyi.cc.edeco.ui.activity.test.path;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.cmonadapter.CommonAdapter;
import anjuyi.cc.edeco.adapter.cmonadapter.item.AdapterItem;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.util.DensityUtils;
import butterknife.BindView;

public class IosListViewActivity extends BaseActivity {

    @BindView(R.id.list)
    iOSMessageList list;

    private CommonAdapter<String> adapter;
    private List messages = new ArrayList<String>();

    @Override
    protected int initLayoutId() {
        return R.layout.activity_ios_list_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @Override
    public void setListener() {

        adapter=new CommonAdapter<String>(messages,1) {
            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new AdapterItem() {

                    private TextView textView;
                    @Override
                    public int getLayoutResId() {
                        return R.layout.message_item;
                    }

                    @Override
                    public void bindViews(View root) {
                         textView = (TextView) root.findViewById(R.id.text);
                    }
                    @Override
                    public void setViews() {

                    }
                    @Override
                    public void handleData(Object o, int position) {

                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context,300), ViewGroup.LayoutParams.WRAP_CONTENT);
                        textView.setText(getItem(position)+"");
                        if (position % 2 == 0) {
                            textView.setBackgroundResource(R.drawable.others_message);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
                        } else {
                            textView.setBackgroundResource(R.drawable.my_message);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
                        }
                        textView.setLayoutParams(layoutParams);
                    }
                };
            }
        };
        list.setAdapter(adapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 30; i++) {
            messages.add("阿里上哪里卡萨诺代码使代码了；洒满了；代码里；啊思密达了麻烦了开始的；拉什么的；拉伸的拉什么的；马上；的马上； ");
        }
        adapter.setData(messages);
        adapter.notifyDataSetChanged();
    }
}
