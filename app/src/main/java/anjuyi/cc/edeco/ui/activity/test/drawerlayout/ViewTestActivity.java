package anjuyi.cc.edeco.ui.activity.test.drawerlayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class ViewTestActivity extends BaseActivity {


    @BindView(R.id.bz)
    FavorLayout bz;
    @BindView(R.id.btn1)
    Button btn1;
    private boolean isRun=false;
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            bz.addFavor();
            handler.postDelayed(this,200);
        }
    };

    @Override
    protected int initLayoutId() {
        return R.layout.activity_view_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


    }

    @Override
    protected void setListener() {


    }

    @Override
    protected void initData() {


    }

    @OnClick({R.id.btn1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                if(isRun){
                    handler.removeCallbacks(runnable);
                    isRun=false;

                }else{
                    handler.post(runnable);
                    isRun=true;
                }
                break;

        }
    }

    @Override
    protected void onPause() {
        if(isRun){
            handler.removeCallbacks(runnable);
            isRun=false;
        }
        super.onPause();
    }
}
