package anjuyi.cc.edeco.ui.fragment.home;

import java.util.List;

import anjuyi.cc.edeco.base.mvp.BasePresenter;
import anjuyi.cc.edeco.https.rx.RxManager;
import rx.Subscriber;

/**
 * 作者：Mr.Lee on 2016-11-9 14:41
 * 邮箱：569932357@qq.com
 */

public class GankDataPresenter extends BasePresenter<GankDataView> {


    private IGankDataModel mModel;

    public GankDataPresenter() {
        mModel = new GankDataModelImpl();
    }

    public void getBeautyData(String suburl) {
        mSubscription = RxManager.getInstance().doSubscribe(mModel.getGankBeautyData(suburl), new Subscriber<BeautyResult<List<Beauty>>>() {

            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                mView.onError();
            }

            @Override
            public void onNext(BeautyResult<List<Beauty>> gankItemDatas) {
                mView.onSuccess(gankItemDatas.getResults());
            }
        });
    }






}
