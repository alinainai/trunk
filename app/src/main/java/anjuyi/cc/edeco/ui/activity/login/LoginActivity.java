package anjuyi.cc.edeco.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.base.Const;
import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.SPUtils;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.view.MyVideoView;
import butterknife.BindView;
import butterknife.OnClick;

import static anjuyi.cc.edeco.R.id.back_to_login;


/**
 * Created by li on 2016/6/29.
 */
public class LoginActivity extends BaseActivity {

    @BindView(back_to_login)
    ImageView backToLogin;
    @BindView(R.id.accountNum)
    EditText accountNum;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.logup_phone)
    TextView logupPhone;
    @BindView(R.id.forget_pwd)
    TextView forgetPwd;
    @BindView(R.id.btn_login)
    Button login;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.sina)
    TextView sina;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.videoView)
    MyVideoView mVideoView;
    @BindView(R.id.re_login)
    RelativeLayout re_login;


    private  File videoFile;

    public static final String VIDEO_NAME = "welcome_video.mp4";

    @Override
    protected int initLayoutId() {
        return R.layout.activity_other_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.trans));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBar.setVisibility(View.VISIBLE);
            statusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            statusBar.setLayoutParams(statusBar.getLayoutParams());
        } else {
            statusBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void setListener() {

        accountNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().matches(AppUtils.ISPHONENUM)){

                    if(password.getEditableText().toString().matches(AppUtils.ISPWD))
                        login.setEnabled(true);

                }else{
                    login.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().matches(AppUtils.ISPWD)){

                    if(accountNum.getEditableText().toString().matches(AppUtils.ISPHONENUM))
                        login.setEnabled(true);
                }else{
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void initData() {

        try{
             videoFile = getFileStreamPath(VIDEO_NAME);
            if (!videoFile.exists()) {
                videoFile = copyVideoFile();
            }
            playVideo(videoFile);

        }catch (Exception e){
            //如果有异常背景变为黑
            re_login.setBackgroundColor(Color.BLACK);
        }

    }

    /**
     * 播放背景动画
     * @param videoFile
     */
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

    /**
     * 复制文件到/data/data/目录下
     * @return
     */
    @NonNull
    private File copyVideoFile() {
        File videoFile=null;
        try {
            FileOutputStream fos = openFileOutput(VIDEO_NAME, MODE_PRIVATE);
            InputStream in = getResources().openRawResource(R.raw.welcome_video);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");
        return videoFile;

    }

    @OnClick({R.id.back_to_login,  R.id.logup_phone, R.id.forget_pwd, R.id.btn_login, R.id.qq, R.id.sina})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back_to_login:
                finish();
                overridePendingTransition(R.anim.anim_show,R.anim.anim_dismiss);
                break;
            case R.id.logup_phone:
                startActivity(new Intent(context,RegisterActivity.class));
                overridePendingTransition(R.anim.anim_show, R.anim.anim_dismiss);
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(context,ResetPwdActivity.class));
                overridePendingTransition(R.anim.anim_show, R.anim.anim_dismiss);
                finish();
                break;
            case R.id.btn_login:

                User user = new User();
                user.setPhone(accountNum.getEditableText().toString());
                user.setPassword(password.getEditableText().toString());
                user.setUsername(accountNum.getEditableText().toString());
                BaseApplication.instance.setUser(user);
                SPUtils.saveString(context, Const.USER_INFO,new Gson().toJson(user),toString());
                SPUtils.saveBoolean(context, Const.LOGIN_STATE,true);
                finish();
                overridePendingTransition(R.anim.anim_show,R.anim.anim_dismiss);

//                Loading.show();
//                RxManager.getInstance().doSubscribe(NetManager.getInstance().create(AppHttpApi.UserApi.class).
//                        getLogin(password.getEditableText().toString(),accountNum.getEditableText().toString()),
//                        new Subscriber<HttpResult<User>>() {
//                    @Override
//                    public void onCompleted() {
//
//
//                    }
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Loading.destroy();
//                        if (throwable instanceof SocketTimeoutException) {
//                            ToastUtils.showShort(context,"连接超时");
//                        } else if(throwable instanceof JsonSyntaxException){
//                            ToastUtils.showShort(context,"请求数据错误");
//                        }else if(throwable instanceof HttpException){
//                            ToastUtils.showShort(context,"连接异常");
//                        }else{
//                            ToastUtils.showShort(context,"连接异常");
//                        }
//                    }
//                    @Override
//                    public void onNext(HttpResult<User> loginUserServiceResult) {
//                        Log.e("TAG",loginUserServiceResult.toString());
//
//                        Loading.destroy();
//                    }
//                });
                break;
            case R.id.qq://起吊qq
                break;
            case R.id.sina://起吊微信
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_show,R.anim.anim_dismiss);
    }
}
