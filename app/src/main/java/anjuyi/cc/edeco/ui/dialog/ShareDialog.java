package anjuyi.cc.edeco.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import anjuyi.cc.edeco.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class ShareDialog {

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.view_head)
    View viewHead;
    @BindView(R.id.ll_share_moments)
    LinearLayout llShareMoments;
    @BindView(R.id.ll_share_wechat)
    LinearLayout llShareWechat;
    @BindView(R.id.ll_share_sina)
    LinearLayout llShareSina;
    @BindView(R.id.ll_share_qq)
    LinearLayout llShareQq;
    @BindView(R.id.ll_share_qzone)
    LinearLayout llShareQzone;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    private Context context;
    protected Dialog dialog;

    public abstract void sure(String a);

    public ShareDialog(Activity activity, int style) {
        this.context = activity;
        if (dialog == null) {
            View view = activity.getLayoutInflater().inflate(
                    R.layout.dialog_product_view, null);
            ButterKnife.bind(this, view);
            dialog = new Dialog(activity,
                    style == 0 ? R.style.progress_dialog_info : style);
            dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER); // 设置生效
            dialogWindow.setWindowAnimations(R.style.main_menu_anim_style);
            WindowManager.LayoutParams wl = dialogWindow.getAttributes();
            wl.x = 0;
            wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = LayoutParams.MATCH_PARENT;
            wl.height = LayoutParams.WRAP_CONTENT;
            // 设置显示位置
            // 设置点击外围解散
            dialog.setCanceledOnTouchOutside(true);

        }

    }


    public void show() {

        if(isShowing()){
          return;
        }
        dialog.show();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void dismiss() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    @OnClick({R.id.btn_cancel, R.id.ll_share_moments, R.id.ll_share_wechat, R.id.ll_share_sina, R.id.ll_share_qq, R.id.ll_share_qzone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.ll_share_moments:



                break;
            case R.id.ll_share_wechat:

                break;
            case R.id.ll_share_sina:

                break;
            case R.id.ll_share_qq:

                break;
            case R.id.ll_share_qzone:

                break;
        }
    }
}
