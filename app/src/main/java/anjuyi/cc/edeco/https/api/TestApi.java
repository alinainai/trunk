package anjuyi.cc.edeco.https.api;

import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：Mr.Lee on 2017-2-13 17:41
 * 邮箱：569932357@qq.com
 */

public interface TestApi {

    String BASE_URL = "https://kyfw.12306.cn/";
    //用户普通登录
    @POST("otn/")
    Observable<String> getLogin();

}
