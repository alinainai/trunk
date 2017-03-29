package anjuyi.cc.edeco.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.base.Const;
import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.SPUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/20.
 */
public class RegisterActivity extends BaseActivity {


    private static final String TAG = "RegisterActivity";

    @BindView(R.id.close)
    ImageView close;//关闭
    @BindView(R.id.ed_user_phone)
    EditText edUserPhone;//手机号
    @BindView(R.id.et_user_password)
    EditText etUserPassword;//密码
    @BindView(R.id.ed_identifying_code)
    EditText edIdentifyingCode;//验证码
    @BindView(R.id.verification_btn)
    TextView verificationBtn;//获取验证码
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.btn_register)
    AppCompatButton btnRegister;//注册
    @BindView(R.id.tv_toregisiter)
    TextView tvToregisiter;//登录已有账号
    @BindView(R.id.sw_pwd)
    SwitchCompat sw_pwd;//登录已有账号

    @Override
    protected int initLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {


        sw_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
    public void initData() {
        //如果正在运行就显示时间
        if (TimeCount.getInstence().isRun()) {
            TimeCount.getInstence().startTimer(verificationBtn);
        }
    }


    @OnClick({R.id.close, R.id.btn_register, R.id.verification_btn})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_toregisiter://登录已有账号
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                //第一个activity进入时的动画，另外一个参数则是第二个activity退出时的动画
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.close://返回
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.verification_btn://发送验证码
                //点击按钮后开始计时
                TimeCount.getInstence().startTimer(verificationBtn);
                //获取收集统一编码（删了就行）
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = tm.getDeviceId();
                edIdentifyingCode.setText("123456");
//                Map<String, String> params_re = new HashMap<>();
//                params_re.put("phone", edUserPhone.getText().toString().trim());
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

                User user = new User();
                user.setPhone(edUserPhone.getEditableText().toString());
                user.setPassword(etUserPassword.getEditableText().toString());
                user.setUsername(edUserPhone.getEditableText().toString());
                BaseApplication.instance.setUser(user);
                SPUtils.saveString(context, Const.USER_INFO,new Gson().toJson(user),toString());
                SPUtils.saveBoolean(context, Const.LOGIN_STATE,true);
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


//                Loading.show();
//                RxManager.getInstance().doSubscribe(NetManager.getInstance().create(AppHttpApi.UserApi.class).
//                                getRegister(etUserPassword.getEditableText().toString().trim(),
//                                        edUserPhone.getEditableText().toString().trim(),
//                                        edIdentifyingCode.getEditableText().toString().trim()),
//                        new Subscriber<HttpResult<User>>() {
//                            @Override
//                            public void onCompleted() {
//
//
//                            }
//                            @Override
//                            public void onError(Throwable throwable) {
//                                Loading.destroy();
//                                if (throwable instanceof SocketTimeoutException) {
//                                    ToastUtils.showShort(context,"连接超时");
//                                } else if(throwable instanceof JsonSyntaxException){
//                                    ToastUtils.showShort(context,"请求数据错误");
//                                }else if(throwable instanceof HttpException){
//                                    ToastUtils.showShort(context,"连接异常");
//                                }else{
//                                    ToastUtils.showShort(context,"连接异常");
//                                }
//                                //  throwable.printStackTrace();
//                            }
//                            @Override
//                            public void onNext(HttpResult<User> loginUserServiceResult) {
//                                Log.e("TAG",loginUserServiceResult.toString());
//                                Loading.destroy();
//                            }
//                        });


                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
