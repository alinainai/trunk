package anjuyi.cc.edeco.https.rx;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.util.Loading;
import rx.Subscriber;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:45
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private boolean mIsShowLoading;//是否显示加载loading

    public RxSubscriber(boolean isShowLoading) {
        mContext = BaseApplication.instance;
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onCompleted() {
        cancelLoading();
    }

    @Override
    public void onError(Throwable e) {

        cancelLoading();
        //统一处理请求异常的情况



        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络连接超时，请稍后再试", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
            Toast.makeText(mContext, "网络链接异常...", Toast.LENGTH_SHORT).show();
        } else {
            e.printStackTrace();
        }
        _onError();

    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading();
    }

    /**
     * 可在此处统一显示loading view
     */
    private void showLoading() {
        if (mIsShowLoading) {
        }
    }

    private void cancelLoading() {
        Loading.destroy();
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError();

}
