package anjuyi.cc.edeco.util;

/**
 * 作者：Mr.Lee on 2016-10-19 15:53
 * 邮箱：569932357@qq.com
 */

public class DownOrUpLoadUtils {


//    private static final  String TAG="DownOrUpLoadUtils";
//    private static final  String BASE_URL="DownOrUpLoadUtils";
//    private OkHttpClient mOkHttpClient;
//    private  MediaType MEDIA_OBJECT_STREAM = MediaType.parse("multipart/form-data");
//    /**
//     * 上传文件
//     * @param actionUrl 接口地址
//     * @param filePath  本地文件地址
//     */
//    public <T> void upLoadFile(String actionUrl, String filePath, final ReqCallBack<T> callBack) {
//        //补全请求地址
//        String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
//        //创建File
//        File file = new File(filePath);
//        //创建RequestBody
//        RequestBody body = RequestBody.create(MEDIA_OBJECT_STREAM, file);
//        //创建Request
//        final Request request = new Request.Builder().url(requestUrl).post(body).build();
//        final Call call = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, e.toString());
//                failedCallBack("上传失败", callBack);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String string = response.body().string();
//                    Log.e(TAG, "response ----->" + string);
//                    successCallBack((T) string, callBack);
//                } else {
//                    failedCallBack("上传失败", callBack);
//                }
//            }
//        });
//    }
//
//    /**
//     *上传文件  带参数上传文件
//     * @param actionUrl 接口地址
//     * @param paramsMap 参数
//     * @param callBack 回调
//     * @param <T>
//     */
//    public <T>void upLoadFile(String actionUrl, HashMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {
//        try {
//            //补全请求地址
//            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
//            MultipartBody.Builder builder = new MultipartBody.Builder();
//            //设置类型
//            builder.setType(MultipartBody.FORM);
//            //追加参数
//            for (String key : paramsMap.keySet()) {
//                Object object = paramsMap.get(key);
//                if (!(object instanceof File)) {
//                    builder.addFormDataPart(key, object.toString());
//                } else {
//                    File file = (File) object;
//                    builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
//                }
//            }
//            //创建RequestBody
//            RequestBody body = builder.build();
//            //创建Request
//            final Request request = new Request.Builder().url(requestUrl).post(body).build();
//            //单独设置参数 比如读取超时时间
//            final Call call = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.e(TAG, e.toString());
//                    failedCallBack("上传失败", callBack);
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (response.isSuccessful()) {
//                        String string = response.body().string();
//                        Log.e(TAG, "response ----->" + string);
//                        successCallBack((T) string, callBack);
//                    } else {
//                        failedCallBack("上传失败", callBack);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//    }
//   //带参数带进度上传文件
//    /**
//     *上传文件
//     * @param actionUrl 接口地址
//     * @param paramsMap 参数
//     * @param callBack 回调
//     * @param <T>
//     */
//    public <T> void upLoadFile(String actionUrl, HashMap<String, Object> paramsMap, final ReqProgressCallBack<T> callBack) {
//        try {
//            //补全请求地址
//            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
//            MultipartBody.Builder builder = new MultipartBody.Builder();
//            //设置类型
//            builder.setType(MultipartBody.FORM);
//            //追加参数
//            for (String key : paramsMap.keySet()) {
//                Object object = paramsMap.get(key);
//                if (!(object instanceof File)) {
//                    builder.addFormDataPart(key, object.toString());
//                } else {
//                    File file = (File) object;
//                    builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file, callBack));
//                }
//            }
//            //创建RequestBody
//            RequestBody body = builder.build();
//            //创建Request
//            final Request request = new Request.Builder().url(requestUrl).post(body).build();
//            final Call call = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.e(TAG, e.toString());
//                    failedCallBack("上传失败", callBack);
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (response.isSuccessful()) {
//                        String string = response.body().string();
//                        Log.e(TAG, "response ----->" + string);
//                        successCallBack((T) string, callBack);
//                    } else {
//                        failedCallBack("上传失败", callBack);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//    }
//   //创建带进度RequestBody
//    /**
//     * 创建带进度的RequestBody
//     * @param contentType MediaType
//     * @param file  准备上传的文件
//     * @param callBack 回调
//     * @param <T>
//     * @return
//     */
//    public <T> RequestBody createProgressRequestBody(final MediaType contentType, final File file, final ReqProgressCallBack<T> callBack) {
//        return new RequestBody() {
//            @Override
//            public MediaType contentType() {
//                return contentType;
//            }
//
//            @Override
//            public long contentLength() {
//                return file.length();
//            }
//
//            @Override
//            public void writeTo(BufferedSink sink) throws IOException {
//                Source source;
//                try {
//                    source = Okio.source(file);
//                    Buffer buf = new Buffer();
//                    long remaining = contentLength();
//                    long current = 0;
//                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
//                        sink.write(buf, readCount);
//                        current += readCount;
//                        Log.e(TAG, "current------>" + current);
//                        progressCallBack(remaining, current, callBack);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }
//    //不带进度文件下载
//    /**
//     * 下载文件
//     * @param fileUrl 文件url
//     * @param destFileDir 存储目标目录
//     */
//    public <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqCallBack<T> callBack) {
//        final String fileName = MD5.GetMD5Code(fileUrl);
//        final File file = new File(destFileDir, fileName);
//        if (file.exists()) {
//            successCallBack((T) file, callBack);
//            return;
//        }
//        final Request request = new Request.Builder().url(fileUrl).build();
//        final Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, e.toString());
//                failedCallBack("下载失败", callBack);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                try {
//                    long total = response.body().contentLength();
//                    Log.e(TAG, "total------>" + total);
//                    long current = 0;
//                    is = response.body().byteStream();
//                    fos = new FileOutputStream(file);
//                    while ((len = is.read(buf)) != -1) {
//                        current += len;
//                        fos.write(buf, 0, len);
//                        Log.e(TAG, "current------>" + current);
//                    }
//                    fos.flush();
//                    successCallBack((T) file, callBack);
//                } catch (IOException e) {
//                    Log.e(TAG, e.toString());
//                    failedCallBack("下载失败", callBack);
//                } finally {
//                    try {
//                        if (is != null) {
//                            is.close();
//                        }
//                        if (fos != null) {
//                            fos.close();
//                        }
//                    } catch (IOException e) {
//                        Log.e(TAG, e.toString());
//                    }
//                }
//            }
//        });
//    }
//   //带进度文件下载
//    /**
//     * 下载文件
//     * @param fileUrl 文件url
//     * @param destFileDir 存储目标目录
//     */
//    public <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqProgressCallBack<T> callBack) {
//        final String fileName = MD5.GetMD5Code(fileUrl);
//        final File file = new File(destFileDir, fileName);
//        if (file.exists()) {
//            successCallBack((T) file, callBack);
//            return;
//        }
//        final Request request = new Request.Builder().url(fileUrl).build();
//        final Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, e.toString());
//                failedCallBack("下载失败", callBack);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                try {
//                    long total = response.body().contentLength();
//                    Log.e(TAG, "total------>" + total);
//                    long current = 0;
//                    is = response.body().byteStream();
//                    fos = new FileOutputStream(file);
//                    while ((len = is.read(buf)) != -1) {
//                        current += len;
//                        fos.write(buf, 0, len);
//                        Log.e(TAG, "current------>" + current);
//                        progressCallBack(total, current, callBack);
//                    }
//                    fos.flush();
//                    successCallBack((T) file, callBack);
//                } catch (IOException e) {
//                    Log.e(TAG, e.toString());
//                    failedCallBack("下载失败", callBack);
//                } finally {
//                    try {
//                        if (is != null) {
//                            is.close();
//                        }
//                        if (fos != null) {
//                            fos.close();
//                        }
//                    } catch (IOException e) {
//                        Log.e(TAG, e.toString());
//                    }
//                }
//            }
//        });
//    }
//
//
//   //接口ReqProgressCallBack.java实现
//    public interface ReqProgressCallBack<T>  extends ReqCallBack<T>{
//        /**
//         * 响应进度更新
//         */
//        void onProgress(long total, long current);
//    }
//   //进度回调实现
//    /**
//     * 统一处理进度信息
//     * @param total    总计大小
//     * @param current  当前进度
//     * @param callBack
//     * @param <T>
//     */
//    private <T> void progressCallBack(final long total, final long current, final ReqProgressCallBack<T> callBack) {
//        okHttpHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (callBack != null) {
//                    callBack.onProgress(total, current);
//                }
//            }
//        });
//    }


}
