package anjuyi.cc.edeco.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by ly on 2016/5/27 11:08.
 * Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected Unbinder mUnbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // System.gc();
        ActivityController.addActivity(this);
        //允许使用转换动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        initLayoutId();
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }*/
        mUnbinder = ButterKnife.bind(this);
        context = this;
        initView(savedInstanceState);
        setListener();
        initData();

    }

    protected abstract int initLayoutId();
    protected abstract void initView(Bundle savedInstanceState);//savedInstanceState获取照片时使用
    protected abstract void setListener();
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();



//        MobclickAgent.onResume(this); //友盟
        JPushInterface.onResume(this);//极光推送
//        Bugtags.onResume(this);//Bugta

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);

    }

}
