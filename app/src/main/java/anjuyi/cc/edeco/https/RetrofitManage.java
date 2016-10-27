package anjuyi.cc.edeco.https;


import java.io.File;
import java.util.concurrent.TimeUnit;

import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.util.AppUtils;
import anjuyi.cc.edeco.util.MD5;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * retrofit管理类
 * Created by Jing on 2016/5/24.
 */
public final class RetrofitManage {
    public static RetrofitManage retrofitManage;
    private Retrofit retrofit;


    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static RequestBody toRequestBody(File value) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), value);
        return body;
    }

//    设备唯一标识：device_id = 7a4391e28f309c21
//    业务唯一标识：uid = 2231001
//    平台类型：platform = android
//    客户端版本号：version_code = 6


    public RetrofitManage(){
        //OKHTTP拦截器，用于配置
        BasicParamsInterceptor basicParamsInterceptor =
                new BasicParamsInterceptor.Builder()
                       // .addHeaderParam("device_id", DeviceUtils.getDeviceId())
                        .addParam("deviceId", AppUtils.getDeviceId(BaseApplication.instance))//设备号
                        .addParam("timeStamp", String.valueOf(System.currentTimeMillis()))//时间戳
                        .addParam("ver", AppUtils.getVersionName(BaseApplication.instance))//版本号
                        .addParam("plat", "android")//版本号
                        .addParam("sign", MD5.GetMD5Code(AppUtils.getDeviceId(BaseApplication.instance)+String.valueOf(System.currentTimeMillis())+ AppUtils.getVersionName(BaseApplication.instance)))
                       // .addQueryParam("api_version", "1.1")
                        .build();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor) //日志，可以配置 level 为 BASIC / HEADERS / BODY
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(basicParamsInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitManage getInstance(){
        if(retrofitManage == null){
            synchronized (RetrofitManage.class){
                if(retrofitManage == null){
                    retrofitManage = new RetrofitManage();
                }
            }
        }
        return retrofitManage;
    }

    /**
     * 获取retrofit对象
     * @return
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }


}
