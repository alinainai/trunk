package anjuyi.cc.edeco.ui.activity.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.ui.activity.test.uber.FormView;
import anjuyi.cc.edeco.ui.activity.test.uber.MyVideoView;
import butterknife.BindView;
import butterknife.OnClick;

public class UberActivity extends BaseActivity {



    @BindView(R.id.formView)
    FormView formView;
    @BindView(R.id.buttonLeft)
    Button buttonLeft;
    @BindView(R.id.buttonRight)
    Button buttonRight;
    @BindView(R.id.appName)
    TextView appName;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.videoView)
    MyVideoView mVideoView;

    private InputType inputType = InputType.NONE;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_uber;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        formView.post(new Runnable() {
            @Override
            public void run() {
                int delta = formView.getTop() + formView.getHeight();
                formView.setTranslationY(-1 * delta);
            }
        });
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        File videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists()) {
            videoFile = copyVideoFile();
        }
        playVideo(videoFile);
        playAnim();
    }


    public static final String VIDEO_NAME = "welcome_video.mp4";


    private void playVideo(File videoFile) {
        mVideoView.setVideoPath(videoFile.getPath());
        mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
    }

    private void playAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(appName, "alpha", 0, 1);
        anim.setDuration(4000);
        anim.setRepeatCount(1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(appName !=null)
                appName.setVisibility(View.INVISIBLE);
            }
        });
    }

    @NonNull
    private File copyVideoFile() {
        File videoFile;
        try {
            FileOutputStream fos = openFileOutput(VIDEO_NAME, MODE_PRIVATE);
            InputStream in = getResources().openRawResource(R.raw.welcome_video);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");
        return videoFile;
    }

    @Override
    protected void onDestroy() {
        mVideoView.stopPlayback();
        super.onDestroy();
    }

    @OnClick({R.id.buttonLeft, R.id.buttonRight})
    public void onClick(View view) {
        int delta = formView.getTop()+formView.getHeight();
        switch (inputType) {
            case NONE:

                formView.animate().translationY(0).alpha(1).setDuration(500).start();
                if (view == buttonLeft) {
                    inputType = InputType.LOGIN;
                    buttonLeft.setText(R.string.button_confirm_login);
                    buttonRight.setText(R.string.button_cancel_login);
                } else if (view == buttonRight) {
                    inputType = InputType.SIGN_UP;
                    buttonLeft.setText(R.string.button_confirm_signup);
                    buttonRight.setText(R.string.button_cancel_signup);
                }

                break;
            case LOGIN:

                formView.animate().translationY(-1 * delta).alpha(0).setDuration(500).start();
                if (view == buttonLeft) {

                } else if (view == buttonRight) {

                }
                inputType = InputType.NONE;
                buttonLeft.setText(R.string.button_login);
                buttonRight.setText(R.string.button_signup);
                break;
            case SIGN_UP:

                formView.animate().translationY(-1 * delta).alpha(0).setDuration(500).start();
                if (view == buttonLeft) {

                } else if (view == buttonRight) {

                }
                inputType = InputType.NONE;
                buttonLeft.setText(R.string.button_login);
                buttonRight.setText(R.string.button_signup);
                break;
        }
    }

//    @Override
//    public void onClick(View view) {
//        int delta = formView.getTop()+formView.getHeight();
//        switch (inputType) {
//            case NONE:
//
//                formView.animate().translationY(0).alpha(1).setDuration(500).start();
//                if (view == buttonLeft) {
//                    inputType = InputType.LOGIN;
//                    buttonLeft.setText(R.string.button_confirm_login);
//                    buttonRight.setText(R.string.button_cancel_login);
//                } else if (view == buttonRight) {
//                    inputType = InputType.SIGN_UP;
//                    buttonLeft.setText(R.string.button_confirm_signup);
//                    buttonRight.setText(R.string.button_cancel_signup);
//                }
//
//                break;
//            case LOGIN:
//
//                formView.animate().translationY(-1 * delta).alpha(0).setDuration(500).start();
//                if (view == buttonLeft) {
//
//                } else if (view == buttonRight) {
//
//                }
//                inputType = InputType.NONE;
//                buttonLeft.setText(R.string.button_login);
//                buttonRight.setText(R.string.button_signup);
//                break;
//            case SIGN_UP:
//
//                formView.animate().translationY(-1 * delta).alpha(0).setDuration(500).start();
//                if (view == buttonLeft) {
//
//                } else if (view == buttonRight) {
//
//                }
//                inputType = InputType.NONE;
//                buttonLeft.setText(R.string.button_login);
//                buttonRight.setText(R.string.button_signup);
//                break;
//        }
//    }

    enum InputType {
        NONE, LOGIN, SIGN_UP;
    }


}
