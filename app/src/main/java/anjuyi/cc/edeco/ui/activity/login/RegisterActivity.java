package anjuyi.cc.edeco.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.https.Const;
import anjuyi.cc.edeco.https.CustomOkHttpUtils;
import anjuyi.cc.edeco.ui.MainActivity;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.SPUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/20.
 */
public class RegisterActivity extends BaseActivity {



    private static final  String TAG="RegisterActivity" ;

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
    @BindView(R.id.protocol_choice)
    ImageView protocolChoice;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.btn_register)
    AppCompatButton btnRegister;//注册
    @BindView(R.id.tv_toregisiter)
    TextView tvToregisiter;//登录已有账号

    @Override
    protected int initLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @Override
    public void setListener() {

        //密码
        etUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().matches(AppUtils.ISPWD)&&edIdentifyingCode.getText().toString().trim().matches("^\\d{4}$")&&edUserPhone.getText().toString().matches(AppUtils.ISPHONENUM))
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

                if(s.toString().matches(AppUtils.ISPHONENUM)){

                    verificationBtn.setEnabled(true);
                    if(edIdentifyingCode.getText().toString().trim().matches("^\\d{4}$")&&etUserPassword.getText().toString().matches(AppUtils.ISPWD)){
                        btnRegister.setEnabled(true);
                    }

                }else{
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

                if(s.toString().trim().matches("^\\d{4}$")&&edUserPhone.getText().toString().matches(AppUtils.ISPHONENUM)&&etUserPassword.getText().toString().matches(AppUtils.ISPWD))
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
       if(TimeCount.getInstence().isRun()){
           TimeCount.getInstence().startTimer(verificationBtn);
       }



    }


    @OnClick({R.id.close, R.id.btn_register,R.id.verification_btn})
    public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.tv_toregisiter://登录已有账号
                        startActivity(new Intent(context,LoginActivity.class));
                        finish();
                        break;
                    case R.id.close://返回
                finish();
                break;
                    case R.id.verification_btn://发送验证码

                        //点击按钮后开始计时
                        TimeCount.getInstence().startTimer(verificationBtn);

                        //获取收集统一编码（删了就行）
                        TelephonyManager tm = (TelephonyManager) context
                                .getSystemService(Context.TELEPHONY_SERVICE);
                        String deviceId = tm.getDeviceId();
                        Map<String ,String> params_re = new HashMap<>();
                        params_re.put("phone",edUserPhone.getText().toString().trim() );

                        CustomOkHttpUtils.postForJsonString("https://www.ljnchn.com/v1/user/register", new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                                ToastUtils.showShort(context,e.toString()+"短信发送失败");
                                Log.e(TAG,"Exception="+e.toString());
                                //注销倒计时
                                TimeCount.getInstence().end();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e(TAG,"response="+response);
                                ToastUtils.showShort(context,"验证码发送成功，模拟数据“1234”");
                                edIdentifyingCode.setText("1234");
                            }
                        },params_re);

                        break;
            case R.id.btn_register:

                Map<String ,String> params = new HashMap<>();
                params.put("phone",edUserPhone.getText().toString().trim() );
                params.put("pass", etUserPassword.getText().toString().trim());
                params.put("verify","1234");

                CustomOkHttpUtils.postForJsonString("https://www.ljnchn.com/v1/user/verify", new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        ToastUtils.showShort(context,e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        ToastUtils.showShort(context,response);

                        Log.e(TAG,response);

//                        EventBus.getDefault().post(
//                                new FirstEvent("FirstEvent btn clicked"));
                        startActivity(new Intent(context, MainActivity.class));
                        SPUtils.saveBoolean(context,Const.LOGIN_STATE,true);
                        SPUtils.saveString(context,Const.USER_PHONE, "18519702340");
                        SPUtils.saveString(context,Const.USER_PWD, "123456");
                        finish();
                    }
                },params);



                break;
        }
    }
}
