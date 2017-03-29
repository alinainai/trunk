package anjuyi.cc.edeco.ui.activity.login;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.https.AppHttpApi;
import anjuyi.cc.edeco.https.HttpResult;
import anjuyi.cc.edeco.https.net.NetManager;
import anjuyi.cc.edeco.https.rx.RxManager;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.Loading;
import anjuyi.cc.edeco.util.ScreenUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static anjuyi.cc.edeco.R.id.rl_title;
import static anjuyi.cc.edeco.R.id.sw_pwd;


/**
 * 修改密码界面
 */

public class ResetPwdActivity extends BaseActivity {

    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.ed_user_phone)
    EditText edUserPhone;
    @BindView(R.id.et_user_password)
    EditText etUserPassword;
    @BindView(sw_pwd)
    SwitchCompat swPwd;

    @BindView(R.id.ed_identifying_code)
    EditText edIdentifyingCode;
    @BindView(R.id.verification_btn)
    TextView verificationBtn;
    @BindView(R.id.btn_register)
    AppCompatButton btnRegister;
    @BindView(rl_title)
    RelativeLayout rlTitle;



    @Override
    protected int initLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


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
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(context);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        rlTitle.setBackgroundResource(R.color.camera_color);
        mainCartTitle.setText("重置密码");

    }

    @Override
    protected void setListener() {

        swPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    etUserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    etUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        //密码
        etUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().matches(AppUtils.ISPWD) && edIdentifyingCode.getText().toString().trim().matches(AppUtils.ISCODE) && edUserPhone.getText().toString().matches(AppUtils.ISPHONENUM))
                    btnRegister.setEnabled(true);
                else
                    btnRegister.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //手机
        edUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().matches(AppUtils.ISPHONENUM)) {

                    verificationBtn.setEnabled(true);
                    if (edIdentifyingCode.getText().toString().trim().matches(AppUtils.ISCODE) && etUserPassword.getText().toString().matches(AppUtils.ISPWD)) {
                        btnRegister.setEnabled(true);
                    }

                } else {
                    btnRegister.setEnabled(false);
                    verificationBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //验证码
        edIdentifyingCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().matches(AppUtils.ISCODE) && edUserPhone.getText().toString().matches(AppUtils.ISPHONENUM) && etUserPassword.getText().toString().matches(AppUtils.ISPWD))
                    btnRegister.setEnabled(true);
                else
                    btnRegister.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @OnClick({R.id.ll_back, R.id.verification_btn, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.verification_btn:
                //点击按钮后开始计时
                TimeCount.getInstence().startTimer(verificationBtn);
                //获取收集统一编码（删了就行）
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = tm.getDeviceId();

                break;
            case R.id.btn_register:


                if(TextUtils.isEmpty(edUserPhone.getEditableText().toString().trim())){
                    ToastUtils.showShort(context,"请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(etUserPassword.getEditableText().toString().trim())){
                    ToastUtils.showShort(context,"请输入密码");
                    return;
                }
                if(TextUtils.isEmpty(edIdentifyingCode.getEditableText().toString().trim())){
                    ToastUtils.showShort(context,"请输验证码");
                    return;
                }

                Loading.show();
                RxManager.getInstance().doSubscribe(NetManager.getInstance().create(AppHttpApi.UserApi.class).
                                getRegister(etUserPassword.getEditableText().toString().trim(),
                                        edUserPhone.getEditableText().toString().trim(),
                                        edIdentifyingCode.getEditableText().toString().trim()),
                        new Subscriber<HttpResult<User>>() {
                            @Override
                            public void onCompleted() {


                            }
                            @Override
                            public void onError(Throwable throwable) {
                                Loading.destroy();
                                if (throwable instanceof SocketTimeoutException) {
                                    ToastUtils.showShort(context,"连接超时");
                                } else if(throwable instanceof JsonSyntaxException){
                                    ToastUtils.showShort(context,"请求数据错误");
                                }else if(throwable instanceof HttpException){
                                    ToastUtils.showShort(context,"连接异常");
                                }else{
                                    ToastUtils.showShort(context,"连接异常");
                                }
                                //  throwable.printStackTrace();
                            }
                            @Override
                            public void onNext(HttpResult<User> loginUserServiceResult) {
                                Log.e("TAG",loginUserServiceResult.toString());
                                Loading.destroy();
                            }
                        });





                break;
        }
    }


}
