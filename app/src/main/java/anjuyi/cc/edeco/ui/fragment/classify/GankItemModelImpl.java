package anjuyi.cc.edeco.ui.fragment.classify;


import java.util.List;

import anjuyi.cc.edeco.https.HttpResult;
import anjuyi.cc.edeco.https.net.NetManager;
import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public class GankItemModelImpl implements IGankItemModel {

    @Override
    public Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl) {
        GankItemService service = NetManager.getInstance().create(GankItemService.class);
        return service.getGankItemData(suburl);
    }
}
