package anjuyi.cc.edeco.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class MessageItemActivity extends BaseActivity {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.img_cart_right)
    ImageView imgCartRight;
    @BindView(R.id.type_item_recyclerview)
    RecyclerView typeItemRecyclerview;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout typeItemSwipfreshlayout;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_message_item;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {


        //IETVC2y938J4Pfu_IMi2WJ-jMZqg5QOqIp3NlcL_MOWNHtVr81MVeIVOu0nX22zu8DQL0aTezz9cQvbyD7JbrXM03F2lZ7ZPlS8Q00N9vv-4HfrJtFiH27eLAhYjwob6WSWaAJAOKO
//        RxManager.getInstance().doSubscribe(NetManager.getInstance().create(AppHttpApi.LoginData.class).
//                        getLoginData(password.getEditableText().toString(),accountNum.getEditableText().toString()),
//                new Subscriber<HttpResult<User>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Loading.destroy();
//
//                        if (throwable instanceof HttpException) {
//                            HttpException error = (HttpException) throwable;
//                        } else if(throwable instanceof JsonSyntaxException){
//                            Log.e("log", "Json转换错误");
//                        }
//                        throwable.printStackTrace();
//
//                    }
//
//                    @Override
//                    public void onNext(HttpResult<User> loginUserServiceResult) {
//                        Loading.destroy();
//                    }
//                });


    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;

        }
    }
}
