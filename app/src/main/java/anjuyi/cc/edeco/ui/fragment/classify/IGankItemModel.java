package anjuyi.cc.edeco.ui.fragment.classify;


import java.util.List;

import anjuyi.cc.edeco.https.HttpResult;
import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public interface IGankItemModel {
    Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl);
}
