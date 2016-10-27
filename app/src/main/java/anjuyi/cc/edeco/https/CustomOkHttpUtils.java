package anjuyi.cc.edeco.https;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 * 以OkHttp做架构的请求网络工具类
 * 
 * @author li
 * 
 */
public class CustomOkHttpUtils {


	private  static final String CER_12306 = "-----BEGIN CERTIFICATE-----\n" +
			"MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n" +
			"BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n" +
			"DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n" +
			"bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n" +
			"DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n" +
			"9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n" +
			"D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n" +
			"tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n" +
			"LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n" +
			"x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n" +
			"23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n" +
			"og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" +
			"-----END CERTIFICATE-----";

	protected static final String TAG = "TAG";
	private static Context mContext;
	public static void init(Application context) {
		mContext = context;
		// 封装方法
		HttpsUtils.SSLParams sslParams = null;
		try {

			InputStream inputStream= new ByteArrayInputStream(CER_12306.getBytes("UTF-8"));
			InputStream[] InputStreams=new InputStream[]{inputStream};
			sslParams = HttpsUtils.getSslSocketFactory(InputStreams, null, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS)
				.addInterceptor(new LoggerInterceptor("TAG"))
				.hostnameVerifier(new HostnameVerifier()
				{
					@Override
					public boolean verify(String hostname, SSLSession session)
					{
						return true;
					}
				})
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				.build();


		OkHttpUtils.initClient(okHttpClient);
	}

	/**
	 * get方式异步请求网络字符串
	 *
	 * @param url
	 *            //路径
	 * @param callback
	 *            回调接口
	 */

	public static void getForJsonString(String url, StringCallback callback) {
		OkHttpUtils.get().url(url).build().execute(callback);
	}

	/**
	 * post方式异步请求网络字符串
	 *
	 * @param url
	 * @param callback
	 * @param params
	 */
	public static void postForJsonString(String url, StringCallback callback,
			Map<String, String> params) {
		OkHttpUtils//
				.post()// post请求
				.url(url)// 请求地址
				.params(params)// 参数
				.build()// 创建请求方式
				.execute(callback);// 开始执行
	}

	/**
	 * 通过post方式上传文件
	 *
	 * @param url
	 *            地址
	 * @param file
	 *            文件
	 * @param params
	 *            Map<String, String> 字段
	 * @param callback
	 *            回调接口
	 */
	public static void postFile(String url, File file,String fileName,String formParam,
			Map<String, String> params, StringCallback callback) {
		if (!file.exists()) {
			Toast.makeText(mContext, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		OkHttpUtils.post().url(url).params(params).addFile(formParam, fileName, file).build()
				.execute(callback);
	}

	/**
	 * get方式下载图片，请求成功后返回bitmap数组
	 *
	 * @param con  上下文
	 * @param url  图片地址
	 * @param callback 图片下载的回调
	 *
	 */

	public static void getImage(Context con,String url, BitmapCallback callback) {
		OkHttpUtils.get().url(url).tag(con).build().connTimeOut(20000)
				.readTimeOut(20000).writeTimeOut(20000).execute(callback);
	}

	// 图片下载的回调接口
	/*
	 * BitmapCallback b = new BitmapCallback() {
	 *
	 * @Override public void onError(Call call, Exception e) {
	 *
	 * }
	 *
	 * @Override public void onResponse(Bitmap bitmap) {
	 *
	 * } };
	 */

	/**
	 * get方式文件下载
	 *
	 * @param url 文件地址
	 * @param callback 文件回调
	 */
	public static void downloadFile(String url, FileCallBack callback) {
		OkHttpUtils.get().url(url).build().execute(callback);
	}

	// 文件下载的回调接口的实例

	/*FileCallBack f = new FileCallBack(pathName, fileName) {

		@Override
		public void onBefore(Request request) {
			super.onBefore(request);
		}

		@Override
		public void inProgress(float progress) {

		}

		@Override
		public void onError(Call call, Exception e) {
			Log.e(TAG, "onError :" + e.getMessage());
		}

		@Override
		public void onResponse(File file) {
			Log.e(TAG, "onResponse :" + file.getAbsolutePath());
		}
	};*/



}
