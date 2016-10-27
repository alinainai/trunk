package anjuyi.cc.edeco.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.umeng.socialize.PlatformConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.https.AppHttpApi;
import anjuyi.cc.edeco.https.CustomOkHttpUtils;
import anjuyi.cc.edeco.https.net.NetManager;
import anjuyi.cc.edeco.https.rx.RxManager;
import anjuyi.cc.edeco.https.rx.RxSubscriber;
import anjuyi.cc.edeco.ui.activity.login.GuesterSetActivity;
import anjuyi.cc.edeco.util.Loading;
import anjuyi.cc.edeco.util.SDCardUtils;
import anjuyi.cc.edeco.util.SPUtils;
import cn.jpush.android.api.JPushInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ly on 2016/5/27 11:39.
 * Application基类(没写完)
 */
public class BaseApplication extends Application {

    public static BaseApplication instance;
    public User user;

    public static final String AJYFILE= Environment.getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"httpUrlfile";  //缓存文件夹
    public static final String AJYFILE_IMG= Environment.getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"imagefile";  //图片文件夹
    public static final String AJYFILE_LOG= Environment.getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"logfile";     //日志文件夹
    public static final String AJYFILE_LOG_TXT= "file_log_txt.txt";     //日志文件

    public static final String LOG_FILENAME= "LOG_FILENAME_MD5";     //日志文件

    private int activityCount;//activity的count数
    public boolean isBackGround=false;//是否在前台

    //private Intent init_intent=new Intent(BaseApplication.this, InitService.class);


    @Override
    public void onCreate() {
        super.onCreate();

     //  init();
        user =new User();//初始化用户类
        instance = this;//初始化appliacation
        CustomOkHttpUtils.init(this);
        Loading.init(this);//封装loading
//        MyUncaughtExceptionHandler.getInstance().init(this);
        //极光推送
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }
            @Override
            public void onActivityStarted(Activity activity) {
                if(isBackGround){
                    if( System.currentTimeMillis()- SPUtils.loadLong(BaseApplication.this,"locktime",0)>1000*10){

                        if(!"TestActivity".equals(activity.getClass().getSimpleName()) &&
                                !"SplashActivity".equals(activity.getClass().getSimpleName()) &&
                                !"LoginActivity".equals(activity.getClass().getSimpleName()) &&
                                !"RegisterActivity".equals(activity.getClass().getSimpleName()) &&
                                !"MineDetailActivity".equals(activity.getClass().getSimpleName()) &&
                                !"GuesterSetActivity".equals(activity.getClass().getSimpleName())
                                ){
                            Intent intent = new Intent(activity, GuesterSetActivity.class);
                            intent.putExtra("from",activity.getClass().getSimpleName());
                            activity.startActivity(intent);
                        }
                    }
                }
                activityCount++;
                isBackGround=false;
            }
            @Override
            public void onActivityResumed(Activity activity) {

            }
            @Override
            public void onActivityPaused(Activity activity) {

            }
            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if(0==activityCount){
                    isBackGround=true;
                    SPUtils.saveLong(BaseApplication.this,"locktime",System.currentTimeMillis());
                }
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }
            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public User getUser() {
        return user;
    }
    public  void setUser(User user) {
        this.user = user;
    }

    private File file_log_txt; //记录日志文件
    private File file_log_new; //上传日志文件


    private void init(){


        file_log_txt = new File(AJYFILE,AJYFILE_LOG_TXT);
        file_log_new = new File(AJYFILE_LOG, AJYFILE_LOG_TXT);

        if (!SDCardUtils.fileIsExists(AJYFILE)) {
            File file = new File(AJYFILE);
            file.mkdirs();
        }
        if (!SDCardUtils.fileIsExists(AJYFILE_IMG)) {
            File file = new File(AJYFILE_IMG);
            file.mkdirs();
        }
        if (!SDCardUtils.fileIsExists(AJYFILE_LOG)) {
            File file = new File(AJYFILE_LOG);
            file.mkdirs();
        }
        if(file_log_txt.exists()){ //记录日志文件是否存在
            if(file_log_txt.length()>0){ //日志文件有记录

                FileInputStream in =null;
                FileOutputStream out=null;
                FileOutputStream out_repeat=null;
                try {
                    if(!file_log_new.exists()){ //创建上传日志文件
                        file_log_new.createNewFile();
                    }
                    //将日志内容写入上传日志文件
                    in = new FileInputStream(file_log_txt);
                    out = new FileOutputStream(file_log_new,true);//创建输出流
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        out.write(i);//每读一个数据输入到1.txt中
                    }
                    out.flush();
                    //重置日志文件  file_log_txt
                    out_repeat = new FileOutputStream(file_log_txt);//创建输出流
                    out_repeat.write("".getBytes());
                    out_repeat.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        out_repeat.close();
                        out.close();
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(file_log_new.exists() && file_log_new.length()>0){
                    List<MultipartBody.Part> parts = new ArrayList<>();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_log_new);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("attachs", String.valueOf(System.currentTimeMillis())+".txt", requestBody);
                    parts.add(part);
                    try{
                        RxManager.getInstance().doSubscribe1(NetManager.getInstance().create(AppHttpApi.uploadLog.class).uploadFilesWithParts(parts)
                                , new RxSubscriber<Boolean>(false) {
                                    @Override
                                    protected void _onNext(Boolean isRi) {
                                        if (isRi) {
                                            Log.e("TAG", "日志文件上传成功");
                                            file_log_new.delete();
                                        } else {
                                            Log.e("TAG", "日志文件上传失败");
                                        }
                                    }
                                    @Override
                                    protected void _onError() {
                                        Log.e("TAG", "日志文件上传失败");
                                    }
                                });

                    }catch (Exception e){

                        Log.e("TAG", "日志文件上传失败");

                    }

                }
            }
        }else{
            try {
                file_log_txt.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
