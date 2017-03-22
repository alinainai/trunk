package anjuyi.cc.edeco.mathro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.ui.fragment.AccountFragment;
import anjuyi.cc.edeco.ui.fragment.MineFragment;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;

/**
 * Created by ly on 2016/5/27 13:36.
 * 框架搭建
 */
@SuppressLint("UseSparseArrays")
public class BlankActivity extends BaseActivity  {

    @BindView(R.id.home_btn)
    RadioButton homeBtn;//首页
    @BindView(R.id.mine_btn)
    RadioButton mineBtn;//我的
    @BindView(R.id.rg_radio)
    RadioGroup rg_radio;
    @BindView(R.id.account_btn)
    Button account_btn;
    //container
    private FragmentManager mFragManager;//fragment管理器
    private AccountFragment mAccountFragment;//我的账户
    private MaFragment mHomeFragment;//首页的fragment
    private MineFragment mMineFragment;//我的fragment

    public static boolean content = false;
    private int index;
    // 当前fragment的index
    public int currentTabIndex;

    private void setStatusColor(int color){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
         //   getWindow().setStatusBarColor(Color.rgb(0x8f,0xc4,0x50));
           getWindow().setStatusBarColor(getResources().getColor(color));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    private static final Map<Integer, String> FRAGMENS = new HashMap<Integer, String>() {
        {
            put(0, MaFragment.TAG);
            put(1, MineFragment.TAG);
        }
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        //可以将一下代码加到你的MainActivity中，或者在任意一个需要调用分享功能的activity当中
        String[] mPermissionList = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS};
        ActivityCompat.requestPermissions(BlankActivity.this,mPermissionList, 100);
        mFragManager = getSupportFragmentManager();
        setStatusColor(R.color.money_color);

    }

    @Override
    public void setListener() {

        rg_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.home_btn:
                        index = 0;
                        setStatusColor(R.color.money_color);
                        break;
                    case R.id.mine_btn:
                        index = 1;
                        setStatusColor(R.color.trans);
                        break;
                }
                changeFragment(currentTabIndex, index);
                // 把当前tab设为选中状态
                currentTabIndex = index;
            }
        });
        account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(context, NaviActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_show, R.anim.anim_dismiss);
            }
        });

    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_blank;
    }


    @Override
    public void initData() {
        resetButton();
    }


    public void changeFragment(int from, int to) {
        FragmentTransaction transaction = mFragManager.beginTransaction();
        //Fragment切换时的动画
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        Fragment tofragment = mFragManager.findFragmentByTag(FRAGMENS.get(to));

        if (tofragment == null) {
            tofragment = getFragmentFromFactory(FRAGMENS.get(to));
        }
        Fragment fromfragment = mFragManager.findFragmentByTag(FRAGMENS.get(from));
        if (fromfragment != null) {
            transaction.hide(fromfragment);
        }
        if (!tofragment.isAdded()) {
            transaction.add(R.id.container, tofragment, FRAGMENS.get(to));
        }
        transaction.show(tofragment).commitAllowingStateLoss();
    }

    private Fragment getFragmentFromFactory(String tag) {
        if (MaFragment.TAG.equals(tag)) {
            if (mHomeFragment == null) {
                mHomeFragment = MaFragment.newInstance();
            }
            return mHomeFragment;
        }
        if (MineFragment.TAG.equals(tag)) {
            if (mMineFragment == null) {
                mMineFragment = MineFragment.newInstance();
            }
            return mMineFragment;
        }

        return null;
    }

    public void resetButton() {
        index = 0;
        changeFragment(currentTabIndex, index);
        // 把第一个tab设为选中状态
        currentTabIndex = 0;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            ToastUtils.showShort(context, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
           // ActivityController.closeAllActivity();
            finish();
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        }
    }


}
