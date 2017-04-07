package anjuyi.cc.edeco.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.base.Const;
import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.ui.activity.mine.SettingActivity;
import anjuyi.cc.edeco.util.SPUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import anjuyi.cc.edeco.view.badgeview.Badge;
import anjuyi.cc.edeco.view.badgeview.QBadgeView;
import anjuyi.cc.edeco.view.pulltozoomview.PullToZoomScrollViewEx;
import butterknife.BindView;

/**
 * Created by ly on 2016/5/30 11:07.
 * 我的fragment
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{
    public static final String TAG = "MineFragment";

    @BindView(R.id.main_title_tv)
    TextView mainTitleTv;//我的标题
    @BindView(R.id.scrollView)
    PullToZoomScrollViewEx scrollView;

    private ImageView mineUserIconImg;//头像图标
    private TextView mineNickNameTv;//姓名
    private TextView mineNameTypeTv;//账号类别
    private TextView mine_obligation_tv;//账号类别
    private TextView mine_service_tv;//账号类别
    private TextView mine_evaluate_tv;//账号类别
    private TextView mine_assist_tv;//账号类别

    private User user;
    private QBadgeView badge;

    public static MineFragment newInstance() {
        return new MineFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStae) {
        View headerview = inflater.inflate(R.layout.view_mine_header, null);
        View zoomview = inflater.inflate(R.layout.view_mine_bg, null);
        View contentview = inflater.inflate(R.layout.view_mine_con, null);
        scrollView.setHeaderView(headerview);
        scrollView.setZoomView(zoomview);
        scrollView.setScrollContentView(contentview);

        mineUserIconImg = (ImageView) scrollView.findViewById(R.id.mine_user_icon_img);
        mineNickNameTv = (TextView) scrollView.findViewById(R.id.mine_nickName_tv);
        mineNameTypeTv = (TextView) scrollView.findViewById(R.id.mine_nameType_tv);

        scrollView.findViewById(R.id.mine_setting_tv).setOnClickListener(this);
        scrollView.findViewById(R.id.mine_order_tv).setOnClickListener(this);
        scrollView.findViewById(R.id.mine_message_tv).setOnClickListener(this);
        scrollView.findViewById(R.id.mine_collection_tv).setOnClickListener(this); //我的收藏
        scrollView.findViewById(R.id.mine_sharemoney_tv).setOnClickListener(this);
        scrollView.findViewById(R.id.mine_coupons_tv).setOnClickListener(this); //我的优惠券
        scrollView.findViewById(R.id.mine_title_bar_rl).setOnClickListener(this); //个人信息
        scrollView.findViewById(R.id.mine_obligation_tv).setOnClickListener(this);
        scrollView.findViewById(R.id.mine_subscribe_tv).setOnClickListener(this);
        scrollView.findViewById(R.id.mine_service_tv).setOnClickListener(this);
        scrollView.findViewById(R.id.mine_evaluate_tv).setOnClickListener(this);
        scrollView.findViewById(R.id.mine_assist_tv).setOnClickListener(this);

        mine_service_tv=(TextView) scrollView.findViewById(R.id.mine_service_tv);
        mine_evaluate_tv=(TextView) scrollView.findViewById(R.id.mine_evaluate_tv);
        mine_obligation_tv=(TextView) scrollView.findViewById(R.id.mine_obligation_tv);
        mine_assist_tv=(TextView) scrollView.findViewById(R.id.mine_assist_tv);

        //如果有账号登录
        if (SPUtils.loadBoolean(context, Const.LOGIN_STATE, false) && null != BaseApplication.instance.getUser()) {

            user = BaseApplication.instance.getUser();
            mineNickNameTv.setText(user.getNickname());
            mineNameTypeTv.setText("高级用户");
            if (!TextUtils.isEmpty(user.getIconImg())) {
                Glide.with(context)
                        .load(user.getIconImg())
                        .asBitmap()
                        .placeholder(R.mipmap.user_icon)
                        .error(R.mipmap.user_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .into(mineUserIconImg);
            }
            //如果没有账号登录
        } else {
            mineNickNameTv.setText("未登录");
            mineNameTypeTv.setText("普通用户");
        }

        new QBadgeView(getActivity()).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeNumber(3).bindTarget(mine_evaluate_tv);
        new QBadgeView(getActivity()).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeNumber(3).bindTarget(mine_obligation_tv);
        new QBadgeView(getActivity()).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeNumber(3).setExactMode(true).setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {

            }
        }).bindTarget(mine_assist_tv);

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mainTitleTv.setText("我的信息");
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.mine_setting_tv: //设置

                intent=new Intent(getActivity(), SettingActivity.class);
               // getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), scrollView.findViewById(R.id.tv_setting), "share").toBundle());

                break;
            case R.id.mine_order_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_order_tv");
                break;
            case R.id.mine_message_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_message_tv");
                break;
            case R.id.mine_collection_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_collection_tv");
                break;
            case R.id.mine_sharemoney_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_sharemoney_tv");
                break;
            case R.id.mine_coupons_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_coupons_tv");
                break;
            case R.id.mine_title_bar_rl: //设置
                ToastUtils.showShort(getActivity(),"mine_title_bar_rl");
                break;
            case R.id.mine_obligation_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_obligation_tv");
                break;
            case R.id.mine_subscribe_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_subscribe_tv");
                break;
            case R.id.mine_service_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_service_tv");
                break;
            case R.id.mine_evaluate_tv: //设置
                ToastUtils.showShort(getActivity(),"mine_evaluate_tv");
                break;
            case R.id.mine_assist_tv: //设置


                break;

        }



    }
}
