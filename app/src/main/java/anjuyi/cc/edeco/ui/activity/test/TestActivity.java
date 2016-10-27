package anjuyi.cc.edeco.ui.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.adorkable.iosdialog.AlertDialog;
import com.umeng.socialize.UMShareAPI;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.https.Const;
import anjuyi.cc.edeco.ui.activity.login.GuesterSetActivity;
import anjuyi.cc.edeco.ui.activity.login.LoginActivity;
import anjuyi.cc.edeco.ui.activity.splash.IndicatorActivity;
import anjuyi.cc.edeco.ui.activity.test.collection.NodeActivity;
import anjuyi.cc.edeco.ui.activity.test.drawerlayout.ViewTestActivity;
import anjuyi.cc.edeco.ui.activity.test.jiemiandonghua.TransitionsAnimActivity;
import anjuyi.cc.edeco.ui.activity.test.mpchart.MpchartActivity;
import anjuyi.cc.edeco.ui.activity.test.path.IosListViewActivity;
import anjuyi.cc.edeco.ui.activity.test.path.PathActivity;
import anjuyi.cc.edeco.ui.activity.utils.ImageSelectorActivity;
import anjuyi.cc.edeco.ui.activity.utils.WebViewActivity;
import anjuyi.cc.edeco.ui.dialog.ShareDialog;
import anjuyi.cc.edeco.util.SPUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/18.
 */
public class TestActivity extends BaseActivity {

    private static final String TAG="TestActivity";

    private LocationManager locationManager;
    private String provider;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        //判断是否创建图标了
        if(!SPUtils.loadBoolean(context,Const.CREATE_WIN_ICON,false)){
            createShortcut();
        }
   }


    //设置快捷方式
    private void createShortcut() {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        shortcut.putExtra("duplicate", false);//设置是否重复创建
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(this, TestActivity.class);//设置第一个页面
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.mipmap.icon_logo);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        sendBroadcast(shortcut);
        SPUtils.saveBoolean(context,Const.CREATE_WIN_ICON,true);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10,
            R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14,R.id.btn15,R.id.btn16,R.id.btn17,R.id.btn18,R.id.btn19,R.id.btn20,R.id.btn21,R.id.btn22})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(context,PtrTestActivity.class));
                break;
            case R.id.btn2:

                new ShareDialog((Activity)context,0){
                    @Override
                    public void sure(String a) {
                        ToastUtils.showShort(context,"信息:"+a);
                    }

            }.show();

                break;
            case R.id.btn3:

                new AlertDialog(context).builder().setTitle("退出当前账号")
                        .setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                        .setPositiveButton("确认退出", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

                break;
            case R.id.btn4:

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setMessage("Android Material Design Dialog @ CSDN Zhang Phil")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", null)
                        .setTitle("退出当前账号")
                        .setMessage("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                        .show();
                break;
            case R.id.btn5:
                Intent intent = new Intent(context, GuesterSetActivity.class);
                intent.putExtra("from",context.getClass().getSimpleName());
                context.startActivity(intent);
                break;
            case R.id.btn6:
                startActivity(new Intent(this, ViewTestActivity.class));

                break;
            case R.id.btn7:
                 startActivity(new Intent(this, WebViewActivity.class).putExtra("url", "file:///android_asset/fuwuxieyi.html").putExtra("title", "《冠易贷服务协议》"));

                break;
            case R.id.btn8:

                startActivity(new Intent(context, ImageSelectorActivity.class));


                break;
            case R.id.btn9:

                startActivity(new Intent(context, ScrollingActivity.class));

                break;
            case R.id.btn10:

                startActivity(new Intent(context, RecyclerViewLoadActivity.class));

                break;
            case R.id.btn11://测试下载
                startActivity(new Intent(context, DownLoadActivity.class));
                break;
            case R.id.btn12:
                Intent intent_welcom = new Intent(context, IndicatorActivity.class);
                startActivity(intent_welcom);
                break;
            case R.id.btn13://Rxjava + Retrofit + Okhttp
                startActivity(new Intent(context, RXTestActivity.class));
                break;
            case R.id.btn14:// ExpendableListView的使用

                startActivity(new Intent(context, ExpendableLvActivity.class));

                break;
            case R.id.btn15:// RecycleView的使用
                startActivity(new Intent(context, HRecyclerViewActivity.class));
                break;
            case R.id.btn16://火花
                startActivity(new Intent(context, MpchartActivity.class));

                break;
            case R.id.btn17://仿Uber登录页
                startActivity(new Intent(context, UberActivity.class));
                break;
            case R.id.btn18://登录界面
                startActivity(new Intent(context, LoginActivity.class));
                break;
            case R.id.btn19:
                startActivity(new Intent(context, PathActivity.class));
                break;
            case R.id.btn20:
                startActivity(new Intent(context, IosListViewActivity.class));
                break;
            case R.id.btn21:

                startActivity(new Intent(context, TransitionsAnimActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                break;
            case R.id.btn22:

                startActivity(new Intent(context, NodeActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
    }

}
