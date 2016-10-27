package anjuyi.cc.edeco.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.util.CacheUtils;
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

    // 本地缓存大小
    private String size = "";

    @Override
    protected int initLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.setting_feedback_tv,
              R.id.setting_recommend_friends_tv,
              R.id.setting_service_terms_tv,
              R.id.setting_about_us_tv,
              R.id.setting_clear_cache_rl})
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
                ToastUtils.showShort(this,"关于我们,内容等待接口");
                break;
            case R.id.setting_clear_cache_rl://清除缓存
                CacheUtils.clearAllCache(getApplicationContext());
                try {
                    size = CacheUtils.getTotalCacheSize(getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                settingCacheSizeTv.setText(size);
                break;
        }
    }


}
