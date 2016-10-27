package anjuyi.cc.edeco.view.ptruiview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import anjuyi.cc.edeco.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import in.srain.cube.views.ptr.indicator.PtrTensionIndicator;

/**
 * 作者：Mr.Lee on 2016-8-17 17:26
 * 邮箱：569932357@qq.com
 */
public class MyCustomHeaderView extends FrameLayout implements PtrUIHandler {


    private Context context;
    private PtrFrameLayout mPtrFrameLayout;
    private PtrTensionIndicator mPtrTensionIndicator;

    private ImageView ivWheel1,ivWheel2;    //轮组图片组件
    private ImageView ivRider;  //骑手图片组件
    private ImageView ivSun,ivBack1,ivBack2;    //太阳、背景图片1、背景图片2
    private Animation wheelAnimation,sunAnimation;  //轮子、太阳动画
    private Animation backAnimation1,backAnimation2;    //两张背景图动画



    public MyCustomHeaderView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public MyCustomHeaderView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context=context;
        init();
    }

    public MyCustomHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.context=context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyCustomHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        init();
    }


    public void setUp(PtrFrameLayout ptrFrameLayout) {
        mPtrFrameLayout = ptrFrameLayout;
        mPtrTensionIndicator = new PtrTensionIndicator();
        mPtrFrameLayout.setPtrIndicator(mPtrTensionIndicator);
    }


    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.custom_ptr_header, this);

        //获取头布局图片组件
        ivRider = (ImageView) view.findViewById(R.id.iv_rider);
        ivSun = (ImageView) view.findViewById(R.id.ivsun);
        ivWheel1 = (ImageView) view.findViewById(R.id.wheel1);
        ivWheel2 = (ImageView) view.findViewById(R.id.wheel2);
        ivBack1 = (ImageView) view.findViewById(R.id.iv_back1);
        ivBack2 = (ImageView) view.findViewById(R.id.iv_back2);
        //获取动画
        wheelAnimation = AnimationUtils.loadAnimation(context, R.anim.tip);
        wheelAnimation.setFillAfter(true);
        sunAnimation = AnimationUtils.loadAnimation(context, R.anim.tip1);
        sunAnimation.setFillAfter(true);
        backAnimation1 = AnimationUtils.loadAnimation(context, R.anim.a);
        backAnimation1.setFillAfter(true);
        backAnimation2 = AnimationUtils.loadAnimation(context, R.anim.b);
        backAnimation2.setFillAfter(true);

    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        stopAnim();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        startAnim();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }

    /**
     * 开启动画
     */
    public void startAnim(){
        ivBack1.startAnimation(backAnimation1);
        ivBack2.startAnimation(backAnimation2);
        ivSun.startAnimation(sunAnimation);
        ivWheel1.startAnimation(wheelAnimation);
        ivWheel2.startAnimation(wheelAnimation);
    }

    /**
     * 关闭动画
     */
    public void stopAnim(){
        ivBack1.clearAnimation();
        ivBack2.clearAnimation();
        ivSun.clearAnimation();
        ivWheel1.clearAnimation();
        ivWheel2.clearAnimation();
    }


}
