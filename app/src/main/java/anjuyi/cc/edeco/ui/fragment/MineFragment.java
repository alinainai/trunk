package anjuyi.cc.edeco.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.base.BaseFragment;
import anjuyi.cc.edeco.base.Const;
import anjuyi.cc.edeco.bean.user.User;
import anjuyi.cc.edeco.ui.activity.extra.CollectionActivity;
import anjuyi.cc.edeco.ui.activity.extra.CouponActivity;
import anjuyi.cc.edeco.ui.activity.login.GuesterActivity;
import anjuyi.cc.edeco.ui.activity.login.LoginActivity;
import anjuyi.cc.edeco.ui.activity.mine.MessageCenterActivity;
import anjuyi.cc.edeco.ui.activity.mine.MineDetailActivity;
import anjuyi.cc.edeco.ui.activity.mine.SettingActivity;
import anjuyi.cc.edeco.ui.activity.order.OrderManagerActivity;
import anjuyi.cc.edeco.util.SPUtils;
import anjuyi.cc.edeco.view.badgeview.QBadgeView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ly on 2016/5/30 11:07.
 * 我的fragment
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG ="MineFragment";

    @BindView(R.id.mine_setting_tv)
    TextView mineSettingTv;//设置
    @BindView(R.id.mine_detail_ll)
    LinearLayout mineDetailLl;//我的账户
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;//登录显示
    @BindView(R.id.mine_user_icon_img)
    ImageView mineUserIconImg;//头像图标
    @BindView(R.id.mine_nickName_tv)
    TextView mineNickNameTv;//姓名
    @BindView(R.id.mine_nameType_tv)
    TextView mineNameTypeTv;//账号类别
    @BindView(R.id.mine_collection_service_tv)
    TextView mineCollectionServiceTv;//收藏服务
    @BindView(R.id.mine_collection_shop_tv)
    TextView mineCollectionShopTv;//收藏商品
    @BindView(R.id.mine_browse_history_tv)
    TextView mineBrowseHistoryTv;//浏览历史
    @BindView(R.id.mine_title_bar_rl)
    LinearLayout mineTitleBarRl;
    @BindView(R.id.mine_order_tv)
    TextView mineOrderTv;//我的订单
    @BindView(R.id.mine_obligation_tv)
    TextView mineObligationTv;
    @BindView(R.id.obligation_num)
    TextView obligationNum;
    @BindView(R.id.mine_service_tv)
    TextView mineServiceTv;//我的服务
    @BindView(R.id.service_num)
    TextView serviceNum;//服务订单的数量
    @BindView(R.id.mine_evaluate_tv)
    TextView mineEvaluateTv;
    @BindView(R.id.evaluate_num)
    TextView evaluateNum;
    @BindView(R.id.mine_assist_tv)
    TextView mineAssistTv;
    @BindView(R.id.assist_num)
    TextView assistNum;
    @BindView(R.id.mine_subscribe_tv)
    TextView mineSubscribeTv;
    @BindView(R.id.mine_coupons_tv)
    TextView mineCouponsTv;
    @BindView(R.id.mine_sharemoney_tv)
    TextView mineSharemoneyTv;//分享赚钱
    @BindView(R.id.mine_collection_tv)
    TextView mineCollectionTv;//我的收藏
    @BindView(R.id.mine_message_tv)
    TextView mineMessageTv;//我的消息
    @BindView(R.id.main_title_tv)
    TextView mainTitleTv;//我的标题
    @BindView(R.id.main_right_img)
    ImageView mainRightImg;//消息界面


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
    public void initView() {

        //如果有账号登录
        if(SPUtils.loadBoolean(context, Const.LOGIN_STATE,false)&& null!=BaseApplication.instance.getUser()){

            user=BaseApplication.instance.getUser();
            mineNickNameTv.setText(user.getNickname());
            mineNameTypeTv.setText("高级用户");
            if(!TextUtils.isEmpty(user.getIconImg())){
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
        }else{
            mineNickNameTv.setText("未登录");
            mineNameTypeTv.setText("普通用户");
        }


        badge= new QBadgeView(getContext());
        badge.bindTarget(mainRightImg);
        badge.setBadgeGravity(Gravity.END | Gravity.TOP);
        badge.setBadgeNumber(3);

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {


    }
    @Override
    public void initData(Bundle savedInstanceState) {
        mainTitleTv.setText("我的信息");
    }


    @OnClick({R.id.mine_collection_tv,R.id.mine_setting_tv, R.id.mine_detail_ll, R.id.ll_detail,
            R.id.mine_user_icon_img,R.id.mine_collection_service_tv, R.id.mine_collection_shop_tv,
            R.id.mine_browse_history_tv, R.id.mine_order_tv, R.id.mine_obligation_tv, R.id.obligation_num,
            R.id.mine_service_tv, R.id.mine_evaluate_tv, R.id.mine_assist_tv, R.id.mine_subscribe_tv,
            R.id.mine_coupons_tv, R.id.mine_sharemoney_tv, R.id.mine_message_tv, R.id.main_right_img})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.mine_setting_tv://设置
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.mine_detail_ll://我的账户
            case R.id.ll_detail://登录显示
            case R.id.mine_user_icon_img://头像图标
                if(SPUtils.loadBoolean(context, Const.LOGIN_STATE,false)&& null!=user){
                    intent = new Intent(getActivity(), MineDetailActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else{
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
                break;
            case R.id.mine_collection_service_tv://收藏服务
                intent = new Intent(getActivity(), CollectionActivity.class);
                intent.putExtra("collection_type",0);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.mine_collection_shop_tv://收藏商品

                break;
            case R.id.mine_browse_history_tv://浏览历史

                break;
            case R.id.mine_order_tv://我的订单
                intent = new Intent(getActivity(), OrderManagerActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.mine_obligation_tv:
                break;
            case R.id.obligation_num:
                break;
            case R.id.mine_service_tv:
                break;
            case R.id.mine_evaluate_tv:
                break;
            case R.id.mine_assist_tv://进入测试界面
                intent = new Intent(getActivity(), GuesterActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.mine_subscribe_tv:
                break;
            case R.id.mine_coupons_tv://优惠券
                intent = new Intent(getActivity(), CouponActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.mine_sharemoney_tv://分享赚钱

                break;
            case R.id.mine_message_tv://我的消息
            case R.id.main_right_img:
                intent = new Intent(getActivity(), MessageCenterActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.mine_collection_tv:

                break;
        }
    }



}
