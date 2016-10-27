package anjuyi.cc.edeco.ui.activity.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.ViewPaperAdapter;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.https.Const;
import anjuyi.cc.edeco.ui.MainActivity;
import anjuyi.cc.edeco.ui.activity.utils.ZoomOutPageTransformer;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.SPUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class IndicatorActivity extends BaseActivity {

    @BindView(R.id.vp_guide)
    ViewPager mViewPager;
    @BindView(R.id.btn_go)
    Button mBtnGo;


    private ViewPaperAdapter vpAdapter;


    private String[] titles={"精彩首页","发现定位","欢乐互动"};
    private String[] titlesColors={"#FF5000","#49ca65","#16c5c6"};
    private String[] infos={"优惠第一时间为你奉上","给你最好的做你最想要的", "精彩由你"};

    private View view1,view2,view3;
    private ArrayList<View> views=new ArrayList<>();

    @Override
    protected int initLayoutId() {
        return R.layout.activity_indicator;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        view1 = LayoutInflater.from(context).inflate( R.layout.guide_view, null);
        view2 = LayoutInflater.from(context).inflate( R.layout.guide_view, null);
        view3 = LayoutInflater.from(context).inflate( R.layout.guide_view, null);
    }

    @Override
    public void setListener() {
            ((ImageView)  view1.findViewById(R.id.tv_pic)).setImageResource(R.mipmap.android_guide_step_1);
            ((TextView)   view1.findViewById(R.id.tv_title)).setText(titles[0]);
            ((TextView)   view1.findViewById(R.id.tv_title)).setTextColor(Color.parseColor(titlesColors[0]));
            ((TextView)   view1.findViewById(R.id.tv_desc)).setText(infos[0]);
            views.add(view1);
        ((ImageView)  view2.findViewById(R.id.tv_pic)).setImageResource(R.mipmap.android_guide_step_2);
        ((TextView)   view2.findViewById(R.id.tv_title)).setText(titles[1]);
        ((TextView)   view2.findViewById(R.id.tv_title)).setTextColor(Color.parseColor(titlesColors[1]));
        ((TextView)   view2.findViewById(R.id.tv_desc)).setText(infos[1]);
        views.add(view2);
        ((ImageView)  view3.findViewById(R.id.tv_pic)).setImageResource(R.mipmap.android_guide_step_3);
        ((TextView)   view3.findViewById(R.id.tv_title)).setText(titles[2]);
        ((TextView)   view3.findViewById(R.id.tv_title)).setTextColor(Color.parseColor(titlesColors[2]));
        ((TextView)   view3.findViewById(R.id.tv_desc)).setText(infos[2]);
        views.add(view3);
    }

    @Override
    public void initData() {

        vpAdapter = new ViewPaperAdapter(views);

        mViewPager.setOffscreenPageLimit(views.size());
        mViewPager.setPageMargin(-dip2px(135));
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

        mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
    }
    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @OnClick(R.id.btn_go)
    public void onClick() {

        SPUtils.saveString(context, Const.APP_VERSION, AppUtils.getVersionName(context));
        SPUtils.saveBoolean(context, Const.FIRST_LOGIN, false);
        Intent intent_main = new Intent(this, MainActivity.class);
        startActivity(intent_main);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();

    }
}
