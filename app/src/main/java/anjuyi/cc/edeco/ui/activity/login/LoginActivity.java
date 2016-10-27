package anjuyi.cc.edeco.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.https.AppHttpApi;
import anjuyi.cc.edeco.https.HttpResult;
import anjuyi.cc.edeco.https.net.NetManager;
import anjuyi.cc.edeco.https.rx.RxManager;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.Loading;
import anjuyi.cc.edeco.util.eventbusbean.FirstEvent;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by li on 2016/6/29.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.user_icon)
    ImageView userIcon;
    @BindView(R.id.back_to_login)
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


    @Override
    protected int initLayoutId() {
        return R.layout.activity_other_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

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


    }

    /**
     * event_bus的监听事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onUserEvent(FirstEvent event) {
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @OnClick({R.id.user_icon, R.id.back_to_login, R.id.logup_phone, R.id.forget_pwd, R.id.btn_login, R.id.qq, R.id.sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_icon:
                break;
            case R.id.back_to_login:
                break;
            case R.id.logup_phone:
                startActivity(new Intent(context,RegisterActivity.class));
                break;
            case R.id.forget_pwd:
                break;
            case R.id.btn_login:
                Log.e("log", "btn_login");

//                RetrofitManage.getInstance().getRetrofit().create(AppHttpApi.LoginApi.class).getLoginData("MTk4ODY1MDRsang=", "18519702340")
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HttpResult<User>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                        if (throwable instanceof HttpException) {
//                            HttpException error = (HttpException) throwable;
//                        } else if(throwable instanceof JsonSyntaxException){
//                            Log.e("log", "Json转换错误");
//                        }
//                        throwable.printStackTrace();
//
//                    }
//
//                    @Override
//                    public void onNext(HttpResult<User> loginUserServiceResult) {
//
//
//                    }
//                });

                Loading.show();

                RxManager.getInstance().doSubscribe(NetManager.getInstance().create(AppHttpApi.LoginData.class).
                        getLoginData(password.getEditableText().toString(),accountNum.getEditableText().toString()),
                        new Subscriber<HttpResult<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Loading.destroy();

                        if (throwable instanceof HttpException) {
                            HttpException error = (HttpException) throwable;
                        } else if(throwable instanceof JsonSyntaxException){
                            Log.e("log", "Json转换错误");
                        }
                        throwable.printStackTrace();

                    }

                    @Override
                    public void onNext(HttpResult<User> loginUserServiceResult) {
                        Loading.destroy();
                    }
                });


                break;
            case R.id.qq:
                break;
            case R.id.sina:
                break;
        }
    }
}
