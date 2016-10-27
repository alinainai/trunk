package anjuyi.cc.edeco.ui.activity.test.jiemiandonghua;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import anjuyi.cc.edeco.R;

/**
 * Created by 晓勇 on 2015/8/27 0027.
 */
public class ImageDetailActivity extends AppCompatActivity {
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_iamge_detail);
        mImageView = (ImageView) findViewById(R.id.imageView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            Fade mFade = new Fade ();
            mFade.setInterpolator(new AccelerateInterpolator());
            mFade.setDuration(500);
            getWindow().setEnterTransition(mFade);
            getWindow().setExitTransition(mFade);
        }


    }
    public void click(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            finishAfterTransition();
        }else{
            finish();
        }
    }

}
