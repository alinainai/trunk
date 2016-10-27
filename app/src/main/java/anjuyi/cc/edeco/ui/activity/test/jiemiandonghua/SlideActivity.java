package anjuyi.cc.edeco.ui.activity.test.jiemiandonghua;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import anjuyi.cc.edeco.R;

/**
 * Created by 晓勇 on 2015/8/28 0028.
 */
public class SlideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide mSlide = new Slide();
            mSlide.setDuration(500);
            getWindow().setExitTransition(mSlide);
            getWindow().setEnterTransition(mSlide);
        }


        TextView mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                    return;
                }
                finish();
            }
        });
    }

}
