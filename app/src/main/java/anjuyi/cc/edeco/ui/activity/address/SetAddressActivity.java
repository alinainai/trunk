package anjuyi.cc.edeco.ui.activity.address;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.bean.address.AddressDb;
import butterknife.BindView;
import butterknife.OnClick;


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
