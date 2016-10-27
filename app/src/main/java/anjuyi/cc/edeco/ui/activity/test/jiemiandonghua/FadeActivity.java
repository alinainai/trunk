package anjuyi.cc.edeco.ui.activity.test.jiemiandonghua;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;

/**
 * Created by 晓勇 on 2015/8/28 0028.
 */
public class FadeActivity extends BaseActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_fade;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade mFade = new Fade();
            mFade.setDuration(500);
            getWindow().setExitTransition(mFade);
            getWindow().setEnterTransition(mFade);
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

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }
}
