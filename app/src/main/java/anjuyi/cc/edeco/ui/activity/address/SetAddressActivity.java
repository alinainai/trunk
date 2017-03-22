package anjuyi.cc.edeco.ui.activity.address;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.bean.address.AddressDb;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;
import kale.adapter.CommonRcvAdapter;
import kale.adapter.item.AdapterItem;


/**
 * 选取地址的界面
 */

public class SetAddressActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.rv_set_address)
    RecyclerView mRecyclerView;

    private List<AddressDb> addressDbs = new ArrayList<>();
    private CommonRcvAdapter<AddressDb> adapter;
    private LinearLayoutManager mLayoutManager;
    private int choice_type=0; //选择类型
    private int cityId=1; //用来存储第二个


    @Override
    protected int initLayoutId() {
        return R.layout.activity_setaddress;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

        adapter = new CommonRcvAdapter<AddressDb>(addressDbs) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new AdapterItem<AddressDb>() {

                    private static final String TAG = "ButtonItem";
                    private TextView info;
                    private RelativeLayout rl;
                    private int position;

                    @Override
                    public int getLayoutResId() {
                        return R.layout.item_address;
                    }

                    @Override
                    public void bindViews(View root) {
                        rl = (RelativeLayout) root;
                        info = (TextView) root.findViewById(R.id.pro_choice_tv);
                    }

                    @Override
                    public void setViews() {
                        rl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(choice_type<2){
                                    int proId = Integer.valueOf(addressDbs.get(position).getCityid());
                                    addressDbs.clear();
                                    queryAddress(proId);
                                    setData(addressDbs);
                                    notifyDataSetChanged();
                                    if(choice_type==0){
                                        cityId=proId;
                                    }
                                    choice_type++;
                                }else{
                                    ToastUtils.show(context,addressDbs.get(position).getCityname(),0);
                                }
                            }
                        });
                    }
                    @Override
                    public void handleData(AddressDb type, final int position) {
                        // 在每次适配器getView的时候就会触发，这里避免做耗时的操作
                        this.position = position;
                        info.setText(type.getCityname());
                    }
                };
            }
        };
        queryAddress(1);
        mRecyclerView.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        if (choice_type == 0) {
            finish();
        } else if (choice_type == 1) {
            choice_type = 0;
            addressDbs.clear();
            queryAddress(1);
            adapter.setData(addressDbs);
            adapter. notifyDataSetChanged();
            cityId=0;

        } else if (choice_type == 2) {
            choice_type = 1;
            addressDbs.clear();
            queryAddress(cityId);
            adapter.setData(addressDbs);
            adapter.notifyDataSetChanged();

        }
    }

    public void queryAddress(int id) {
        try {
            final String databaseFilename = BaseApplication.AJYFILE + "/" + "address.sqlite";
            SQLiteDatabase mSQLiteDatabase = openOrCreateDatabase(databaseFilename, Activity.MODE_PRIVATE, null);//有则打开，没有创建
            Cursor cur = mSQLiteDatabase.rawQuery("select cityname,cityid,pcityid from t_loan_city_info where pcityid=" + id, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    AddressDb addressDb = null;
                    do {
                        String name = cur.getString(cur
                                .getColumnIndex("cityname"));
                        String cityid = cur.getString(cur
                                .getColumnIndex("cityid"));
                        String pcityid = cur.getString(cur
                                .getColumnIndex("pcityid"));
                        addressDb = new AddressDb();
                        addressDb.setPcityid(pcityid);
                        addressDb.setCityname(name);
                        addressDb.setCityid(cityid);
                        addressDbs.add(addressDb);
                    } while (cur.moveToNext());
                    cur.close();
                }
            }
            mSQLiteDatabase.close();//关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_back, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.ll_back:
                finish();
                break;
        }
    }


}
