package anjuyi.cc.edeco.ui.activity.test.httptest;

import java.util.List;

import anjuyi.cc.edeco.https.HttpResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 电影票房接口
 * Created by Jing on 2016/5/24.
 */
public interface BoxOfficeApi {
    @GET("boxoffice/rank")
    Observable<HttpResult<List<BoxOffice>>> getBoxOfficeData(@Query("key") String key, @Query("area") String area);
}
