package anjuyi.cc.edeco.ui.fragment.classify;


import java.util.List;

import anjuyi.cc.edeco.base.mvp.BasePresenter;
import anjuyi.cc.edeco.https.rx.RxManager;
import anjuyi.cc.edeco.https.rx.RxSubscriber;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class GankItemPresenter extends BasePresenter<GankItemView> {
    private IGankItemModel mModel;

    public GankItemPresenter() {
        mModel = new GankItemModelImpl();
    }

    public void getGankItemData(String suburl) {
        mSubscription = RxManager.getInstance().doSubscribe1(mModel.getGankItemData(suburl), new RxSubscriber<List<GankItemData>>(false) {
            @Override
            protected void _onNext(List<GankItemData> gankItemData) {
                mView.onSuccess(gankItemData);
            }

            @Override
            protected void _onError() {
                mView.onError();
            }
        });
    }
}
