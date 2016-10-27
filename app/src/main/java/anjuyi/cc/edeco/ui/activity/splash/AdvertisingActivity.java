package anjuyi.cc.edeco.ui.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.https.Const;
import anjuyi.cc.edeco.ui.MainActivity;
import butterknife.BindView;

/**
 * Created by ly on 2016/6/1 15:33.
 * 广告页
 */
public class AdvertisingActivity extends BaseActivity {

    @BindView(R.id.advertising_img)
    ImageView advertisingImg;


    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 延迟三秒

    private Handler handler= new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    @Override
    protected int initLayoutId() {
        return R.layout.activity_advertising;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

        Glide.with(context).load(Const.AD_IMG_URL).crossFade().into(advertisingImg);
        handler.postDelayed(runnable,SPLASH_DISPLAY_LENGTH);

    }


}
