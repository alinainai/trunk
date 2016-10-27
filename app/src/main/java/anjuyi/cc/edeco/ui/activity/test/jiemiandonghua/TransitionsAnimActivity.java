package anjuyi.cc.edeco.ui.activity.test.jiemiandonghua;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;

public class TransitionsAnimActivity extends BaseActivity {


    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_transitions_anim;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);
        mButton6 = (Button) findViewById(R.id.button6);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MpchartActivity.this,ImageActivity.class));
                startActivity(new Intent(context,RecyclerViewActivity.class));
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator mAnimator = ViewAnimationUtils.createCircularReveal(mButton4, mButton4.getWidth()/2, mButton4.getHeight()/2, 0, mButton4.getHeight());
                mAnimator.setDuration(2000);
                mAnimator.setInterpolator(new AccelerateInterpolator());
                mAnimator.start();
            }
        });
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float endRadius = (float) Math.hypot(mButton5.getWidth(), mButton5.getHeight());//勾股定理得到斜边长度
                Animator mAnimator = ViewAnimationUtils.createCircularReveal(mButton5, mButton5.getWidth(), 0, 0, endRadius);
                mAnimator.setDuration(2000);
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.start();
            }
        });
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,ImageActivity.class));
            }
        });


    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }
}
