package anjuyi.cc.edeco.https;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.util.Loading;
import anjuyi.cc.edeco.util.MD5;
import anjuyi.cc.edeco.util.SDCardUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import okhttp3.Call;

public class VolleyRequest {


    /**
     * 生成存储url的文件名
     *
     * @param url
     * @param params
     * @return
     */
    public static String generateSecretName(String url, Map<String, String> params) {
        return MD5.GetMD5Code(url + params);
    }

    /**
     * 获取本地缓存的网络数据
     *
     * @param context    //上下文
     * @param tag        //缓存的参数
     * @param expiryTime //缓存的有效时间  int 以小时为单位
     * @return //如果返回空    就执行访问网络的方法
     */
    public static String postForStaticData(Context context, String tag, int expiryTime) {

        long count = 3600000;
        byte[] buffer = null;
        File file = new File(BaseApplication.AJYFILE, tag + ".txt");
        buffer = SDCardUtils.readFileFromSDCard(BaseApplication.AJYFILE, tag + ".txt");
        if (buffer == null || System.currentTimeMillis() - file.lastModified() > expiryTime * 3600000) {
            return null;
        }
        return new String(buffer);
    }

    /**
     * 基于okhttp的网络请求框架
     *
     * @param context       //上下文
     * @param url           //地址
     * @param params        //参数
     * @param callback      //回调方式
     * @param isShowLoading //是否显示进度条

     * @param isCache       //是否需要缓存
     * @param expiryTime    //缓存的有效时间
     */
    public static void RequestPost(Context context, String url,
                                   final Map<String, String> params, MyStringCallback callback, boolean isShowLoading, Boolean isCache, float expiryTime) {
        //获取设备ID
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        params.put("device", deviceId);
        params.put("version", "1.0");
        params.put("os", "2");

        if (isShowLoading) {//是否显示进度条   思路：下拉刷新过和上啦加载时不显示
            Loading.show();
        }
        CustomOkHttpUtils.postForJsonString(url, callback, params);
    }

    public static abstract class MyStringCallback extends StringCallback {

        private Context context;
        private boolean isCache;
        private String tag;

        public MyStringCallback(Context con, boolean isCache, String tag) {
            this.context = con;
            this.isCache = isCache;
            this.tag = tag;
        }
        public abstract void onSuccess(JSONObject json);
        public abstract void onFailure();
        @Override
        public void onResponse(String response, int id) {
            Log.i("TAG", "网络数据：" + response);
            if (response == null || "".equals(response)) {
                ToastUtils.showShort(context, "服务器错误");
                return;
            }
            JSONObject responseJSONObject = null;
            try {
                responseJSONObject = new JSONObject(response);
                if (responseJSONObject.getInt("stat") == 0000) {
                    if (isCache) {
                        SDCardUtils.saveFileToSDCard(BaseApplication.AJYFILE, tag + ".txt", responseJSONObject.optString("message"));
                    }
                    onSuccess(responseJSONObject);
                } else if (responseJSONObject.getInt("stat") == 999) {//token失效
                    onSuccess(null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Loading.destroy();
            }
        }
        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.showShort(context, "网络请求发生错异常");
            Loading.destroy();
            onFailure();
        }
    }


}
