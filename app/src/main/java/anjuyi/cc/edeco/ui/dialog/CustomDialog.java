package anjuyi.cc.edeco.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import anjuyi.cc.edeco.R;

public abstract class CustomDialog {


    private Context context;
    protected Dialog dialog;


   public  abstract void convert();

    public CustomDialog(Activity activity, int style,int resId) {
        this.context = activity;
        if (dialog == null) {
            View view = activity.getLayoutInflater().inflate(
                    resId, null);

            view.findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    convert();
                }
            });
            dialog = new Dialog(activity,
                    style == 0 ? R.style.progress_dialog_info : style);
            dialog.setContentView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER); // 设置生效
            dialogWindow.setWindowAnimations(R.style.zoom_anim_style);
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

}
