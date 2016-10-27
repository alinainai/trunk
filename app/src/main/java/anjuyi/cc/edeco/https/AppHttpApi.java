package anjuyi.cc.edeco.https;

import java.util.List;
import java.util.Map;

import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.ui.activity.test.httptest.BoxOffice;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：Mr.Lee on 2016-8-15 18:30
 * 邮箱：569932357@qq.com
 */
public class AppHttpApi {


    public interface BoxOfficeApi {
        @GET("boxoffice/rank")
        Observable<HttpResult<List<BoxOffice>>> getBoxOfficeData(@Query("key") String key, @Query("area") String area);
    }

    public interface LoginApi {
        @POST("loanUserApi/login")
        Observable<HttpResult<User>> getLoginData(@Query("password") String password, @Query("telephone") String telephone);
    }

    public interface InfoService {
        @Multipart
        @POST("loanUserApi/updateHead")
        Observable<HttpResult<Boolean>> completeAddress(@PartMap Map<String, RequestBody> params);
    }
    //登录信息
    public interface LoginData {
        String BASE_URL = "http://10.100.24.108:8080/mvc/";
        @POST("user/login.do")
        Observable<HttpResult<User>> getLoginData(@Query("password") String password, @Query("username") String username);
    }
    //测试信息
    public interface show {
        String BASE_URL = "http://10.100.24.108:8080/mvc/";
        @POST("user/show.do")
        Observable<User> getUserData(@Query("password") String password, @Query("username") String username);
    }
    //测试信息
    public interface uploadIcon {
        String BASE_URL = "http://10.100.24.108:8080/mvc/";
        @Multipart
        @POST("user/updateicon.do")
        Observable<HttpResult<String>> getIconUrl(@Part("attach\";filename=\"icon.jpg") RequestBody  file , @Part("username") RequestBody username);
    }

    public interface uploadLog {
        String BASE_URL = "http://10.100.24.108:8080/mvc/";
        @Multipart
        @POST("user/uploadlog.do")
        Observable<HttpResult<Boolean>> uploadFilesWithParts(@Part() List<MultipartBody.Part> attachs);
    }

    public interface DownLoadApk {
        String BASE_URL = "http://10.100.24.108:8080/mvc/";
        @Multipart
        @GET("statics/data/test.apk")
        Observable<ResponseBody> downLoadApk();
    }

    public interface getWchatMessage{
        //grant_type=client_credential&appid=wx073db7c5d9fa46bd&secret=0411ec37b5ca9d1772a476abc7e96eed
        String   BASE_URL="https://api.weixin.qq.com/cgi-bin/";
        @GET("token")
        Observable<ResponseBody> getAccessToken(@Query("grant_type") String grant_type, @Query("appid") String appid, @Query("secret") String secret);
        //https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN
        //http请求方式: GET   https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN

        @GET("material/get_materialcount")
        Observable<ResponseBody> getMessageCount(@Query("access_token") String access_token);

        @POST("material/batchget_material")
        Observable<ResponseBody> getMessageList(@Query("access_token") String access_token, @Query("type") String type, @Query("offset") String offset,@Query("count") String count);


    }


}
