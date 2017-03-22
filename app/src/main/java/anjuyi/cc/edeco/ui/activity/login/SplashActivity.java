package anjuyi.cc.edeco.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.ViewPaperAdapter;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.Const;
import anjuyi.cc.edeco.mathro.BlankActivity;
import anjuyi.cc.edeco.ui.activity.utils.DepthPageTransformer;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.SPUtils;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ly on 2016/5/25 16:10.
 * 欢迎页面
 */
public class SplashActivity extends BaseActivity {
    private  int TIME_COUNT = 4;
    @BindView(R.id.vp_guide)
    ViewPager mViewPager;
    @BindView(R.id.btn_go)
    Button mBtnGo;
    @BindView(R.id.img_src)
    ImageView img_src;
    @BindView(R.id.rl_skip)
    RelativeLayout rl_skip;
    @BindView(R.id.tv_skip)
    TextView tv_skip;

    //是否是第一次登录
    private boolean isFirstLogin;
    //版本号
    private String appVersion;
    //图片地址
    private String imgUrl;
    //加载图片完成的时间戳
    private long time_s;

    private Handler handler= new Handler();

    private ViewPaperAdapter vpAdapter;
    private String[] titles={"精彩首页","发现定位","欢乐互动"};
    private String[] titlesColors={"#FF5000","#49ca65","#16c5c6"};
    private String[] infos={"优惠第一时间为你奉上","给你最好的做你最想要的", "精彩由你"};


    private ArrayList<View> views=new ArrayList<>();





    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        appVersion= SPUtils.loadString(context, Const.APP_VERSION);
        isFirstLogin= SPUtils.loadBoolean(context, Const.FIRST_LOGIN,true);
        time_s = System.currentTimeMillis();
        if(isFirstLogin || !appVersion.equals(AppUtils.getVersionName(context)) ){

            rl_skip.setVisibility(View.GONE);
            for (int i=0;i<titles.length;i++){
                views.add(setView(titles[i],titlesColors[i],infos[i]));
            }
            vpAdapter = new ViewPaperAdapter(views);
            mViewPager.setOffscreenPageLimit(views.size());
            mViewPager.setAdapter(vpAdapter);

            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    if (position + 1 == mViewPager.getAdapter().getCount()) {
                        if (mBtnGo.getVisibility() != View.VISIBLE) {
                            mBtnGo.setVisibility(View.VISIBLE);
                            mBtnGo.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
                        }
                    } else {
                        if (mBtnGo.getVisibility() != View.GONE) {
                            mBtnGo.setVisibility(View.GONE);
                            mBtnGo.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
                        }
                    }
                }
            });
            mViewPager.setPageTransformer(false, new DepthPageTransformer());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewPager.setVisibility(View.VISIBLE);
                }
            },2000);
        }else{
            mViewPager.setVisibility(View.GONE);
            rl_skip.setVisibility(View.VISIBLE);
            tv_skip.setText(String.valueOf(TIME_COUNT)+"s");
            timer.schedule(task,1000, 1000);
            handler.postDelayed(r,2000);


 //           Glide.with(getApplicationContext()).load(Const.AD_IMG_URL).centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(new SimpleTarget<GlideDrawable>() {
//                @Override
//                public void onResourceReady(final GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                    long time_dif = System.currentTimeMillis() - time_s;
//                    if (time_dif < 2000) {
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if(null!=resource &&null!= img_src)
//                                        img_src.setImageDrawable(resource);
//                                    }
//                                });
//                            }
//                        }, 2000 - time_dif);
//                    } else {
//                        img_src.setImageDrawable(resource);
//                    }
//                }
//            });
        }


    }



    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    TIME_COUNT--;
                    if(TIME_COUNT <1){
                        timer.cancel();
                        Intent intent = new Intent(context, BlankActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return;
                    }
                    tv_skip.setText(String.valueOf(TIME_COUNT)+"s");
                }
            });
        }
    };

    private Runnable r=new Runnable() {
        @Override
        public void run() {
            Glide.with(getApplicationContext()).load(R.mipmap.adbacjground).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(img_src);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(r);
    }

    @Override
    protected void setListener() {



    }

    @Override
    protected void initData() {



    }

    private View setView(String title,String info,String color){
         View  view = LayoutInflater.from(context).inflate( R.layout.guide_view, null);
        ((ImageView)  view.findViewById(R.id.tv_pic)).setImageResource(R.mipmap.android_guide_step_1);
        ((TextView)   view.findViewById(R.id.tv_title)).setText(title);
        ((TextView)   view.findViewById(R.id.tv_title)).setTextColor(Color.parseColor(info));
        ((TextView)   view.findViewById(R.id.tv_desc)).setText(color);
        return view;
    }


    @OnClick({R.id.btn_go, R.id.rl_skip})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_go:
                SPUtils.saveString(context, Const.APP_VERSION, AppUtils.getVersionName(context));
                SPUtils.saveBoolean(context, Const.FIRST_LOGIN, false);
                Intent intent_main = new Intent(this, BlankActivity.class);
                startActivity(intent_main);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
                break;
            case R.id.rl_skip:
                timer.cancel();
                Intent intent = new Intent(context, BlankActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

        }
    }

}
