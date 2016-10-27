package anjuyi.cc.edeco.ui.fragment.classify;


import java.util.List;

import anjuyi.cc.edeco.https.HttpResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/12 16:50
 */
public interface GankItemService {
    String BASE_URL = "http://gank.io/api/";
    @GET("{suburl}")
    Observable<HttpResult<List<GankItemData>>> getGankItemData(@Path("suburl") String suburl);
}
