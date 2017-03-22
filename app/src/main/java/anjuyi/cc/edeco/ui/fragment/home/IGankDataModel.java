package anjuyi.cc.edeco.ui.fragment.home;


import java.util.List;

import rx.Observable;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public interface IGankDataModel {
    Observable<BeautyResult<List<Beauty>>> getGankBeautyData(String suburl);
}
