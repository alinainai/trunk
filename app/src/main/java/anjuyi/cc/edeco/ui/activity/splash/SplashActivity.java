package anjuyi.cc.edeco.ui.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.https.Const;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.SPUtils;

/**
 * Created by ly on 2016/5/25 16:10.
 * 欢迎页面
 */
public class SplashActivity extends BaseActivity {
    private static final int TIME = 1000;
    private boolean isFirstLogin;
    private String appVersion;

    private Handler handler= new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Intent intent_main=null;
            if(isFirstLogin || !appVersion.equals(AppUtils.getVersionName(context)) ){
                intent_main = new Intent(SplashActivity.this, IndicatorActivity.class);
            }else{
                intent_main= new Intent(SplashActivity.this, AdvertisingActivity.class);
            }
            startActivity(intent_main);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
        }
    };

    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        appVersion= SPUtils.loadString(context, Const.APP_VERSION);
        isFirstLogin= SPUtils.loadBoolean(context, Const.FIRST_LOGIN,true);
        handler.postDelayed(runnable,TIME);

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void initData() {



    }



    protected void onDestroy() {

        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler=null;
        }
        super.onDestroy();
    }

}
