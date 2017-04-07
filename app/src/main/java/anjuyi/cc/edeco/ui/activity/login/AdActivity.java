package anjuyi.cc.edeco.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.Const;
import anjuyi.cc.edeco.ui.MainActivity;
import anjuyi.cc.edeco.ui.activity.utils.DepthPageTransformer;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.SPUtils;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ly on 2016/5/25 16:10.
 * 欢迎页面
 */
public class AdActivity extends BaseActivity {
    private  int TIME_COUNT = 3;
    @BindView(R.id.vp_guide)
    ViewPager mViewPager;
    @BindView(R.id.btn_go)
    Button mBtnGo;
    @BindView(R.id.img_src)
    ImageView img_src;
    @BindView(R.id.rl_skip)
    LinearLayout rl_skip;
    @BindView(R.id.tv_skip)
    TextView tv_skip;

    //是否是第一次登录
    private boolean isFirstLogin;
    //版本号
    private String appVersion;

    private ViewPaperAdapter vpAdapter;
    private String[] titles={"精彩首页","发现定位","欢乐互动"};
    private String[] titlesColors={"#FF5000","#49ca65","#16c5c6"};
    private String[] infos={"优惠第一时间为你奉上","给你最好的做你最想要的", "精彩由你"};
    private ArrayList<View> views=new ArrayList<>();

    @Override
    protected int initLayoutId() {
        return R.layout.activity_ad;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appVersion= SPUtils.loadString(context, Const.APP_VERSION);
        isFirstLogin= SPUtils.loadBoolean(context, Const.FIRST_LOGIN,true);

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
            mViewPager.setVisibility(View.VISIBLE);

        }else{
            mViewPager.setVisibility(View.GONE);
            rl_skip.setVisibility(View.VISIBLE);
            tv_skip.setText(String.valueOf(TIME_COUNT)+"s");
            timer.schedule(task,1000, 1000);
            Glide.with(getApplicationContext()).load(R.mipmap.adbacjground).centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_src);
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
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        return;
                    }
                    tv_skip.setText(String.valueOf(TIME_COUNT)+"s");
                }
            });
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                Intent intent_main = new Intent(this, MainActivity.class);
                startActivity(intent_main);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
                break;
            case R.id.rl_skip:
                timer.cancel();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;

        }
    }
    public class ViewPaperAdapter extends PagerAdapter {
        private ArrayList<View> views;

        public ViewPaperAdapter(ArrayList<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            } else
                return 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }
    }

}
