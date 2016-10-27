package anjuyi.cc.edeco.ui.activity.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.https.AppHttpApi;
import anjuyi.cc.edeco.https.HttpResult;
import anjuyi.cc.edeco.https.RetrofitManage;
import anjuyi.cc.edeco.https.net.NetManager;
import anjuyi.cc.edeco.https.rx.RxManager;
import anjuyi.cc.edeco.util.Loading;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RXTestActivity extends BaseActivity {

    @BindView(R.id.tv_getinfo)
    TextView tvGetinfo;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_rxtest;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_getinfo,R.id.tv_getlogin})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_getinfo:

                Loading.show();

//                BoxOfficeConnection.getBoxOfficeApi()
//                        .getBoxOfficeData(Const.JUHE_KEY, "CN") //请求参数
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<HttpResult<List<BoxOffice>>>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable throwable) {
//                                if (throwable instanceof HttpException) {
//                                    HttpException error = (HttpException) throwable;
//                                } else if(throwable instanceof JsonSyntaxException){
//                                    Log.w("log", "Json转换错误");
//                                }
//                                throwable.printStackTrace();
//                            }
//
//                            @Override
//                            public void onNext(HttpResult<List<BoxOffice>> data) {
//
//                                tvInfo.setText(data.toString());
//
//                            }
//                        });

//                RxManager.getInstance().doSubscribe(NetManager.getInstance().create(AppHttpApi.getWchatMessage.class).
//                                getAccessToken("client_credential","wx073db7c5d9fa46bd","0411ec37b5ca9d1772a476abc7e96eed"),
//                        new Subscriber<ResponseBody>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable throwable) {
//
//                                Loading.dismiss();
//
//                                if (throwable instanceof HttpException) {
//                                    HttpException error = (HttpException) throwable;
//                                } else if(throwable instanceof JsonSyntaxException){
//                                    Log.e("log", "Json转换错误");
//                                }
//                                throwable.printStackTrace();
//
//                            }
//
//                            @Override
//                            public void onNext(ResponseBody result) {
//                                Loading.dismiss();
//                                try {
//                                    Log.e("TAG",result.string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });
                RxManager.getInstance().doSubscribe(NetManager.getInstance().create(AppHttpApi.getWchatMessage.class).
                                getMessageCount("JtzU2DzCnRGIpFmlE7Rj0M5Xmu8kJcb19vM4FF8mx1UPhwg45UuaIkhCkcqsleACvnan9b_OWxj3SLU_VJo1DQnF4LRISrAn-7K0k9IjUdLcybH2QS0gnrRmLWVrinwoRSBbADAZAJ"),
                        new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                                Loading.dismiss();

                                if (throwable instanceof HttpException) {
                                    HttpException error = (HttpException) throwable;
                                } else if(throwable instanceof JsonSyntaxException){
                                    Log.e("log", "Json转换错误");
                                }
                                throwable.printStackTrace();

                            }

                            @Override
                            public void onNext(ResponseBody result) {
                                Loading.dismiss();
                                try {
                                    Log.e("TAG",result.string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });





                break;
            case R.id.tv_getlogin:


                RetrofitManage.getInstance().getRetrofit().create(AppHttpApi.LoginApi.class).getLoginData("MTk4ODY1MDRsang=", "18519702340")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HttpResult<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        if (throwable instanceof HttpException) {
                            HttpException error = (HttpException) throwable;
                        } else if(throwable instanceof JsonSyntaxException){
                            Log.e("log", "Json转换错误");
                        }
                        throwable.printStackTrace();

                        tvInfo.setText(throwable.toString());
                    }

                    @Override
                    public void onNext(HttpResult<User> loginUserServiceResult) {
                        tvInfo.setText(((User)loginUserServiceResult.getResults()).toString());
                    }
                });
                break;
            case R.id.tv_uploadimg:

                Map<String, RequestBody> map = new HashMap<>();

                map.put("token", RetrofitManage.toRequestBody("123465"));
                map.put("uid", RetrofitManage.toRequestBody("123456"));
                File file = new File("file_name");
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                map.put("uploadimg\"; filename=\""+file.getName(), fileBody);

                RetrofitManage.getInstance().getRetrofit().create(AppHttpApi.InfoService.class).completeAddress(map)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HttpResult<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        if (throwable instanceof HttpException) {
                            HttpException error = (HttpException) throwable;
                        } else if(throwable instanceof JsonSyntaxException){
                            Log.e("log", "Json转换错误");
                        }
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(HttpResult<Boolean> loginUserServiceResult) {
                        tvInfo.setText(loginUserServiceResult.toString());
                    }
                });
                break;

        }

    }
}
