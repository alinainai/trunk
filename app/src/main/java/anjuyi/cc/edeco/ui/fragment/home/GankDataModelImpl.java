package anjuyi.cc.edeco.ui.fragment.home;

import java.util.List;

import anjuyi.cc.edeco.https.net.NetManager;
import rx.Observable;

/**
 * 作者：Mr.Lee on 2016-11-9 15:17
 * 邮箱：569932357@qq.com
 */

public class GankDataModelImpl implements IGankDataModel{
    @Override
    public Observable<BeautyResult<List<Beauty>>> getGankBeautyData(String suburl) {
        BeautyService service = NetManager.getInstance().create(BeautyService.class);
        return service.getGankBeautyData(suburl);
    }
}
