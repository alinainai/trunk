package anjuyi.cc.edeco.ui.activity.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adorkable.iosdialog.AlertDialog;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.https.CustomOkHttpUtils;
import anjuyi.cc.edeco.service.DownLoadService;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class DownLoadActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.btn2)
    Button btn2;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_down_load;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

//    https://www.anjuyi.com.cn/Api*/Version/version
//    请求方式：
//
//    POST
//    参数：
//
//    参数名	必选	类型	说明
//    version_num	是	int	版本号 例如1000
//            source_id





    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:

                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = tm.getDeviceId();

                Map<String ,String> params = new HashMap<>();
                params.put("device", deviceId);
                params.put("version", "1.0");
                params.put("os", "2");
                params.put("version_num", "1000");
                params.put("source_id", "2");
                CustomOkHttpUtils.postForJsonString("https://www.anjuyi.com.cn/Api/Version/version", new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        text.setText(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        text.setText(response);
                    }
                },params);




                break;
            case R.id.btn2:

                new AlertDialog(context).builder().setTitle("版本更新")
                        .setMsg("发现新版本是否进行下载？")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //   Intent intent = new Intent("com.lxsoft.service.download");
                                context.startService(new Intent(context, DownLoadService.class));

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();


                break;
        }
    }
}
