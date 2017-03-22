package anjuyi.cc.edeco.https;

import java.util.List;
import java.util.Map;

import anjuyi.cc.edeco.bean.user.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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


    /**
     * 用户模块
     */
    public interface UserApi {

        String BASE_URL = "http://10.100.24.108:8080/mvc/";
        //用户普通登录
        @POST("user/login.do")
        Observable<HttpResult<User>> getLogin(@Query("password") String password, @Query("telephone") String telephone);
        //注册用户
        @POST("loanUserApi/login")
        Observable<HttpResult<User>> getRegister(@Query("password") String password, @Query("telephone") String telephone,@Query("verify") String verify);
        //修改用户信息
        @POST("loanUserApi/login")
        Observable<HttpResult<User>> getUpdate(@Query("password") String password, @Query("telephone") String telephone);

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
        String BASE_URL = "http://10.100.24.122:8080/borrowApply/";
        @Multipart
        @POST("uploadAttach")
        Observable<HttpResult<Boolean>> uploadFilesWithParts(@Part() List<MultipartBody.Part> attachs);
    }


}
