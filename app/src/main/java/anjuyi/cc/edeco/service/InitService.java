package anjuyi.cc.edeco.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.https.AppHttpApi;
import anjuyi.cc.edeco.https.net.NetManager;
import anjuyi.cc.edeco.https.rx.RxManager;
import anjuyi.cc.edeco.https.rx.RxSubscriber;
import anjuyi.cc.edeco.util.SDCardUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static anjuyi.cc.edeco.base.BaseApplication.AJYFILE;
import static anjuyi.cc.edeco.base.BaseApplication.AJYFILE_IMG;
import static anjuyi.cc.edeco.base.BaseApplication.AJYFILE_LOG;
import static anjuyi.cc.edeco.base.BaseApplication.AJYFILE_LOG_TXT;

/**
 * 作者：Mr.Lee on 2016-10-13 14:24
 * 邮箱：569932357@qq.com
 */

public class InitService extends Service {


    private String match = "^\\d{13}\\.txt$";

    private File file_log_txt; //记录日志文件
    private File file_log_new; //上传日志文件

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("TAG","initService---onStartCommand");

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
                }

            }
        }else{
            try {
                file_log_txt.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        if (loadLong(mContext, BaseApplication.LOG_FILENAME, 0) == 0) { //第一次进入时  或者   用户清除数据时
//            final File file = new File(AJYFILE_LOG);
//            if (file.listFiles() != null && file.listFiles().length > 0) {  //AJYFILE_LOG有文件时
//                List<MultipartBody.Part> parts = new ArrayList<>();
//                final String[] files = file.list();
//                for (String f : files) {
//                    if (f.matches(match)) {
//                        File file_log = new File(AJYFILE_LOG, f);
//                        if (file_log.length() > 0) {
//                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_log);
//                            MultipartBody.Part part = MultipartBody.Part.createFormData("attachs", file_log.getName(), requestBody);
//                            parts.add(part);
//                        }
//                    }
//                }
//                if (parts.size() > 0) {    //AJYFILE_LOG有日志文件时
//                    RxManager.getInstance().doSubscribe1(NetManager.getInstance().create(AppHttpApi.uploadLog.class).uploadFilesWithParts(parts)
//                            , new RxSubscriber<Boolean>(false) {
//                                @Override
//                                protected void _onNext(Boolean isRi) {
//                                    if (isRi) {
//                                        ToastUtils.show(mContext, "请求成功", 0);
//                                        //删除AJYFILE_LOG存在的文件
//                                        for (String f : files) {
//                                            File file_log = new File(AJYFILE_LOG, f);
//                                            if (!file_log.isDirectory()) {
//                                                file_log.delete();
//                                            }
//                                        }
//                                        //创建新的上传的文件
//                                        try {
//                                            long log_name = System.currentTimeMillis();
//                                            File file_log_new = new File(AJYFILE_LOG, String.valueOf(log_name) + ".txt");
//                                            file_log_new.createNewFile();
//                                            SPUtils.saveLong(mContext, BaseApplication.LOG_FILENAME, log_name);
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    } else {
//                                        ToastUtils.show(mContext, "请求失败", 0);
//                                    }
//                                }
//
//                                @Override
//                                protected void _onError() {
//                                    ToastUtils.show(context, "请求失败", 0);
//                                }
//                            });
//                }else{//AJYFILE_LOG没有日志文件时
//                    try {
//                        long log_name = System.currentTimeMillis();
//                        File file_log_new = new File(AJYFILE_LOG, String.valueOf(log_name) + ".txt");
//                        file_log_new.createNewFile();
//                        SPUtils.saveLong(mContext, BaseApplication.LOG_FILENAME, log_name);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            } else { // AJYFILE_LOG没有文件时
//                try {
//                    long log_name = System.currentTimeMillis();
//                    File file_log_new = new File(AJYFILE_LOG, String.valueOf(log_name) + ".txt");
//                    file_log_new.createNewFile();
//                    SPUtils.saveLong(mContext, BaseApplication.LOG_FILENAME, log_name);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //SPUtils.saveLong(mContext,BaseApplication.LOG_FILENAME,System.currentTimeMillis());
//                //以下是测试数据
////                try {
////                    File file1 = new File(AJYFILE_LOG,"1476348033016.txt");
////                    file1.createNewFile();
////                    BufferedWriter output = new BufferedWriter(new FileWriter(file1));
////                    output.write("1476348033016.txt");
////                    output.close();
////                    File file2 = new File(AJYFILE_LOG,"1476348033017.txt");
////                    file2.createNewFile();
////                    BufferedWriter output2 = new BufferedWriter(new FileWriter(file2));
////                    output2.write("1476348033016.txt");
////                    output2.close();
////                    File file3 = new File(AJYFILE_LOG,"1476348033018.txt");
////                    file3.createNewFile();
////                    BufferedWriter output3 = new BufferedWriter(new FileWriter(file3));
////                    output3.write("1476348033016.txt");
////                    output3.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//            }
//
//        } else {  //有BaseApplication.LOG_FILENAME存储时
//
//            final File file_log_exists = new File(AJYFILE_LOG, String.valueOf(loadLong(mContext, BaseApplication.LOG_FILENAME, 0) + ".txt"));
//            if (file_log_exists.exists()) {  //有log日志文件时
//                    if (file_log_exists.length() > 0) {
//                        List<MultipartBody.Part> parts = new ArrayList<>();
//                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_log_exists);
//                        MultipartBody.Part part = MultipartBody.Part.createFormData("attachs", file_log_exists.getName(), requestBody);
//                        parts.add(part);
//                        RxManager.getInstance().doSubscribe1(NetManager.getInstance().create(AppHttpApi.uploadLog.class).uploadFilesWithParts(parts)
//                                , new RxSubscriber<Boolean>(false) {
//                                    @Override
//                                    protected void _onNext(Boolean isRi) {
//                                        if (isRi) {
//                                            Log.e("TAG", "日志文件上传成功");
//                                            try {
//                                                long log_name = System.currentTimeMillis();
//                                                File file_log_new = new File(AJYFILE_LOG, String.valueOf(log_name) + ".txt");
//                                                file_log_new.createNewFile();
//                                                file_log_exists.delete();
//                                                SPUtils.saveLong(mContext, BaseApplication.LOG_FILENAME, log_name);
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//
//                                        } else {
//                                            Log.e("TAG", "日志文件上传失败");
//                                        }
//                                    }
//
//                                    @Override
//                                    protected void _onError() {
//                                        Log.e("TAG", "日志文件上传失败");
//                                    }
//                                });
//                    }
//                } else {  //没有log日志文件时
//                    try {
//                        file_log_exists.createNewFile();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//        }
        stopSelf();
            return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("TAG","initService---destroy");
        super.onDestroy();
    }


}
