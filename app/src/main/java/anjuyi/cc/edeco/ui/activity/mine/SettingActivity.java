package anjuyi.cc.edeco.ui.activity.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adorkable.iosdialog.AlertDialog;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.util.CacheUtils;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ly on 2016/6/22.
 * 设置
 */
public class SettingActivity extends BaseActivity  {

    @BindView(R.id.setting_feedback_tv)
    TextView settingFeedbackTv;//意见反馈
    @BindView(R.id.setting_recommend_friends_tv)
    TextView settingRecommendFriendsTv;//推荐好友
    @BindView(R.id.setting_service_terms_tv)
    TextView settingServiceTermsTv;//服务条款
    @BindView(R.id.setting_about_us_tv)
    TextView settingAboutUsTv;//关于我们
    @BindView(R.id.setting_cache_size_tv)
    TextView settingCacheSizeTv;//缓存大小的显示
    @BindView(R.id.setting_clear_cache_rl)
    RelativeLayout settingClearCacheRl;//整体的缓存布局
    @BindView(R.id.update_version_num_tv)
    TextView updateVersionNumTv;//当前版本号
    @BindView(R.id.setting_check_update_rl)
    RelativeLayout settingCheckUpdateRl;//检查更新布局
    @BindView(R.id.setting_exit_login_btn)
    Button settingExitLoginBtn;//退出登录
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.ll_back)
    LinearLayout llBack;

    // 本地缓存大小
    private String size = "";

    @Override
    protected int initLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade mFade = new Fade();
            mFade.setDuration(500);
            getWindow().setExitTransition(mFade);
            getWindow().setEnterTransition(mFade);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.trans));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        rlTitle.setBackgroundResource(R.color.cff3e19);
        mainCartTitle.setText("设置");


    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

        try{
            settingCacheSizeTv.setText(CacheUtils.getTotalCacheSize(SettingActivity.this));
        }catch (Exception e){

        }

    }

    @OnClick({R.id.setting_feedback_tv,
              R.id.setting_recommend_friends_tv,
              R.id.setting_service_terms_tv,
              R.id.setting_about_us_tv,
              R.id.setting_clear_cache_rl,
              R.id.setting_exit_login_btn,
              R.id.setting_check_update_rl,
              R.id.ll_back})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.setting_feedback_tv://意见反馈
                intent=new Intent(SettingActivity.this,FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_recommend_friends_tv://推荐好友
                ToastUtils.showShort(this,"推荐好友,第三方分享");
                break;
            case R.id.setting_service_terms_tv://服务条款
                ToastUtils.showShort(this,"服务条款,内容等待接口");
                break;
            case R.id.setting_about_us_tv://关于我们


                break;
            case R.id.setting_exit_login_btn://退出登录


                new AlertDialog(context).builder().setTitle("退出登录")
                        .setMsg("是否退出登录")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();




                break;
            case R.id.setting_check_update_rl://检测更新

                new AlertDialog(context).builder().setTitle("检测更新")
                        .setMsg("发现现版本是否更新")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

                break;
            case R.id.ll_back://返回
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.setting_clear_cache_rl://清除缓存

                new AlertDialog(context).builder().setTitle("清除缓存")
                        .setMsg("是否要清除缓存")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CacheUtils.clearAllCache(getApplicationContext());
                                try {
                                    size = CacheUtils.getTotalCacheSize(getApplicationContext());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                settingCacheSizeTv.setText(size);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();


                break;
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }


}
