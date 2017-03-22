package anjuyi.cc.edeco.mathro;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adorkable.iosdialog.AlertDialog;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.base.Const;
import anjuyi.cc.edeco.ui.activity.login.LoginActivity;
import anjuyi.cc.edeco.util.SPUtils;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by li on 2016/6/29.
 */
public class MaFragment extends BaseFragment {

    public static final String TAG = "MaFragment";
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_pause)
    TextView tvPause;
    @BindView(R.id.tv_stop)
    TextView tvStop;



    private Handler stepTimeHandler=new Handler();
    private Runnable mTicker ;
    long startTime = 0;
    long stateTime=0;
    private boolean isRun;


    public static MaFragment newInstance() {
        return new MaFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.ma_fragment_ma;
    }

    @Override
    public void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        llBack.setVisibility(View.GONE);
        mainCartTitle.setText("运动主页");
        rlTitle.setBackgroundColor(getResources().getColor(R.color.money_color));

       // 跑步热量（kcal）＝体重（kg）×距离（公里）×1.036，换算一下

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTicker = new Runnable() {
            public void run() {
                String content = TimerCount.showTimeCount(System.currentTimeMillis() - startTime+stateTime);
                tv_date.setText(content);
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                stepTimeHandler.postAtTime(mTicker, next);
            }
        };


    }


    @OnClick({R.id.tv_start, R.id.tv_pause, R.id.tv_stop})
    public void onClick(View view) {


        if(!SPUtils.loadBoolean(context, Const.LOGIN_STATE,false)){
            new AlertDialog(context).builder().setTitle("提示").setMsg("清先进行登录").setNegativeButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.anim_show, R.anim.anim_dismiss);
                }
            }).setPositiveButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
            return;
        }

        switch (view.getId()) {
            case R.id.tv_start:
                if(!isRun){
                    startTime=System.currentTimeMillis();
                    mTicker.run();
                    isRun=true;
                }else{
                    ToastUtils.showShort(getContext(),"已经开始计时");
                }
                break;
            case R.id.tv_pause:
                stateTime=System.currentTimeMillis() - startTime+stateTime;
                stepTimeHandler.removeCallbacks(mTicker);
                isRun=false;
                break;
            case R.id.tv_stop:

                new AlertDialog(context).builder().setTitle("提示").setMsg("是否结束行程").setNegativeButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stepTimeHandler.removeCallbacks(mTicker);
                        stateTime=0;
                        tv_date.setText("00:00:00");
                        isRun=false;

                        new AlertDialog(context).builder().setTitle("结果").setMsg("本次训练时长："+tv_date.getText()+"//n" +"本次训练距离：0.00Km"+"//n" +"本次训练速度：0.00Km/h" ).setNegativeButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        }).show();

                    }
                }).setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();


                break;
        }
    }
}
