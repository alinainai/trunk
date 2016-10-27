package anjuyi.cc.edeco.ui.activity.test.path;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.util.Loading;

public class PathActivity extends BaseActivity {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            if(message.what==1){
                Loading.destroy();
            }
            return false;
        }
    });


    @Override
    protected int initLayoutId() {
        return R.layout.activity_path;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

        final AnimatedPathView view = (AnimatedPathView)findViewById(R.id.animated_path);
        findViewById(R.id.btn_showdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.show();
                mHandler.sendEmptyMessageDelayed(1,2000);
            }
        });

        ViewTreeObserver observer = view.getViewTreeObserver();
        if(observer != null){
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    float[][] points = new float[][]{
                            {view.getWidth()/2,  view.getHeight()/2},
                            {view.getWidth()/2+30, view.getHeight()/2+30 },
                            {view.getWidth()/2+150,  view.getHeight()/2+30},
                    };
                    view.setPath(points);
                }
            });
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator anim = ObjectAnimator.ofFloat(view, "percentage", 0.0f, 1.0f);
                anim.setDuration(2000);
                anim.setInterpolator(new AccelerateInterpolator());
                anim.start();
            }
        });

    }
    @Override
    public void initData() {

    }
}
