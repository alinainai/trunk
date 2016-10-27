package anjuyi.cc.edeco.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.bean.message.CensusPhone;
import anjuyi.cc.edeco.ui.fragment.CartFragment;
import anjuyi.cc.edeco.ui.fragment.ClassifyFragment;
import anjuyi.cc.edeco.ui.fragment.HomeFragment;
import anjuyi.cc.edeco.ui.fragment.MineFragment;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;

/**
 * Created by ly on 2016/5/27 13:36.
 * 框架搭建
 */
@SuppressLint("UseSparseArrays")
public class MainActivity extends BaseActivity  {
    @BindView(R.id.home_btn)
    RadioButton homeBtn;//首页
    @BindView(R.id.classify_btn)
    RadioButton classifyBtn;//分类
    @BindView(R.id.shopping_btn)
    RadioButton shoppingBtn;//购物车
    @BindView(R.id.mine_btn)
    RadioButton mineBtn;//我的
    @BindView(R.id.rg_radio)
    RadioGroup rg_radio;


    //container
    private FragmentManager mFragManager;//fragment管理器
    private HomeFragment mHomeFragment;//首页的fragment
    private ClassifyFragment mClassifyFragment;//分类的fragment
    private CartFragment mShoppingFragment;//购物车的fragment
    private MineFragment mMineFragment;//我的fragment

    public static boolean content = false;
    private int index;
    // 当前fragment的index
    public int currentTabIndex;


    //初始化intent
   // private Intent init_intent=new Intent(this, InitService.class);

    private static final Map<Integer, String> FRAGMENS = new HashMap<Integer, String>() {
        {
            put(0, HomeFragment.TAG);
            put(1, ClassifyFragment.TAG);
            put(2, CartFragment.TAG);
            put(3, MineFragment.TAG);
        }
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        //可以将一下代码加到你的MainActivity中，或者在任意一个需要调用分享功能的activity当中
        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS};
        ActivityCompat.requestPermissions(MainActivity.this,mPermissionList, 100);
        mFragManager = getSupportFragmentManager();
    }

    @Override
    public void setListener() {

        rg_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.home_btn:
                        index = 0;
                        break;
                    case R.id.classify_btn:
                        index = 1;
                        break;
                    case R.id.shopping_btn:
                        index = 2;
                        break;
                    case R.id.mine_btn:
                        index = 3;
                        break;
                }
                changeFragment(currentTabIndex, index);
                // 把当前tab设为选中状态
                currentTabIndex = index;
            }
        });

    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }



    @Override
    public void initData() {
        resetButton();
    }


    public void changeFragment(int from, int to) {
        FragmentTransaction transaction = mFragManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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
        if (HomeFragment.TAG.equals(tag)) {
            if (mHomeFragment == null) {
                mHomeFragment = HomeFragment.newInstance();
            }
            return mHomeFragment;
        }
        if (ClassifyFragment.TAG.equals(tag)) {
            if (mClassifyFragment == null) {
                mClassifyFragment = ClassifyFragment.newInstance();

            }
            return mClassifyFragment;
        }
        if (CartFragment.TAG.equals(tag)) {
            if (mShoppingFragment == null) {
                mShoppingFragment = CartFragment.newInstance();
            }
            return mShoppingFragment;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {
        File file_log=new File(BaseApplication.AJYFILE,BaseApplication.AJYFILE_LOG_TXT);
        if(file_log.exists()){
            FileOutputStream fos = null;
            CensusPhone censusPhone=new CensusPhone();
            censusPhone.setActivityName("Main");
            censusPhone.setCensusDate("2015-8-7");
            censusPhone.setDuration("5000");
            censusPhone.setMethodName("123456");
            censusPhone.setTimeStamp(System.currentTimeMillis());
            try {
                fos = new FileOutputStream(file_log,true);
                fos.write(censusPhone.toString().getBytes());
                fos.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

            }
        }
        super.onPause();
    }
}
