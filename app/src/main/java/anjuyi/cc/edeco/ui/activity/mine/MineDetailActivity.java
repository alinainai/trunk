package anjuyi.cc.edeco.ui.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adorkable.iosdialog.ActionSheetDialog;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.https.HttpResult;
import anjuyi.cc.edeco.ui.activity.utils.ImageZoomActivity;
import anjuyi.cc.edeco.util.GlideUtils;
import anjuyi.cc.edeco.util.Loading;
import anjuyi.cc.edeco.util.NetUtils;
import anjuyi.cc.edeco.util.ToastUtils;
import anjuyi.cc.edeco.view.CircleImageView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ly on 2016/6/24.
 * 个人信息
 */
public class MineDetailActivity extends BaseActivity {

    private static final String TAG = MineDetailActivity.class.getName();
    private static final int RESULT_CAPTURE = 100;
    private static final int RESULT_PICK = 101;
    private static final int CROP_PHOTO = 102;
    @BindView(R.id.ll_back)
    LinearLayout llBack;//返回键
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;// 标题
    @BindView(R.id.mine_user_icon_img)
    CircleImageView mineUserIconImg;//用户头像
    @BindView(R.id.detail_headIcon_rl)
    RelativeLayout detailHeadIconRl;//头像选择
    @BindView(R.id.detail_nickName_tv)
    TextView detailNickNameTv;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.detail_nickName_rl)
    RelativeLayout detailNickNameRl;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.img7)
    ImageView img7;
    @BindView(R.id.detail_userName_rl)
    RelativeLayout detailUserNameRl;
    @BindView(R.id.detail_sex_tv)
    TextView detailSexTv;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.detail_sex_rl)
    RelativeLayout detailSexRl;
    @BindView(R.id.detail_birthday_tv)
    TextView detailBirthdayTv;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.detail_birthday_rl)
    RelativeLayout detailBirthdayRl;
    @BindView(R.id.detail_phone_tv)
    TextView detailPhoneTv;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.detail_band_phone_rl)
    RelativeLayout detailBandPhoneRl;
    @BindView(R.id.img6)
    ImageView img6;
    @BindView(R.id.detail_service_address_rl)
    RelativeLayout detailServiceAddressRl;
    private File tempFile;//图片文件
    private String url = "http://10.100.24.108:8080/mvc/statics/image/banner_2.jpg";

    /**
     * @param dirPath
     * @return
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mainCartTitle.setText("个人信息");
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(BaseApplication.AJYFILE_IMG),
                    "icon" + ".jpg");
        }

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {


    }

    @OnClick({R.id.ll_back, R.id.mine_user_icon_img, R.id.detail_headIcon_rl, R.id.detail_nickName_rl, R.id.detail_userName_rl, R.id.detail_sex_tv, R.id.detail_sex_rl, R.id.detail_birthday_rl, R.id.detail_band_phone_rl, R.id.detail_service_address_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.mine_user_icon_img:
                Intent intent = new Intent(context, ImageZoomActivity.class);
                ArrayList<String> urls = new ArrayList<>();
                //测试数据   --------------------------
                urls.add(url);
                intent.putStringArrayListExtra("imgpath", urls);
                startActivity(intent);
                break;
            case R.id.detail_headIcon_rl:
                new ActionSheetDialog(MineDetailActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("从手机相册中选择", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(Intent.createChooser(intent, "请选择图片"), RESULT_PICK);

                                    }
                                })
                        .addSheetItem("通过相机选择", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                                        startActivityForResult(intent, RESULT_CAPTURE);
                                    }
                                }).show();
                break;
            case R.id.detail_nickName_rl:
                break;
            case R.id.detail_userName_rl:
                break;
            case R.id.detail_sex_tv:
                break;
            case R.id.detail_sex_rl:
                break;
            case R.id.detail_birthday_rl:
                break;
            case R.id.detail_band_phone_rl:
                break;
            case R.id.detail_service_address_rl:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case RESULT_CAPTURE:
                if (resultCode == RESULT_OK) {
                    cropRawPhoto(Uri.fromFile(tempFile));
                }
                break;
            case RESULT_PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropRawPhoto(uri);
                }
                break;
            case CROP_PHOTO:

                if (resultCode == RESULT_OK) {
                    if (intent != null) {
                      /*  Uri uri = intent.getData();
                        if (uri == null) {
                            return;
                        }
                        mineUserIconImg.setImageURI(uri);*/
                        setImageToHeadView(intent, tempFile);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PHOTO);
    }


    /**
     * 打开截图界面
     * @param uri 原图的Uri
     *//*
    public void starCropPhoto(Uri uri) {

        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipHeaderActivity.class);
        intent.setData(uri);
        intent.putExtra("side_length", 200);//裁剪图片宽高
        startActivityForResult(intent, CROP_PHOTO);
    }*/

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent, final File f) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            FileOutputStream out = null;
            try {//打开输出流 将图片数据填入文件中
                out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                if (NetUtils.isConnected(context)) {
                    RequestBody imgbody = RequestBody.create(MediaType.parse("multipart/form-data"), f);
                    RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "123456");
//                    RxManager.getInstance().doSubscribe1(NetManager.getInstance().create(AppHttpApi.uploadIcon.class).
//                                    getIconUrl(imgbody, name),
//                            new RxSubscriber<String>(true) {
//                                @Override
//                                protected void _onNext(String url) {
//                                    f.delete();
//                                    GlideUtils.getInstance().displayImage(context, url, mineUserIconImg);
//                                    Loading.destroy();
//                                }
//                                @Override
//                                protected void _onError() {
//                                    ToastUtils.show(context, "请求失败", 0);
//                                }
//                            });

                    Loading.show();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    //设置类型
                    builder.setType(MultipartBody.FORM);
                    //追加参数
                    builder.addFormDataPart("username", "123456");
                    builder.addFormDataPart("attach", "icon.jpg", imgbody);
                    //创建RequestBody
                    RequestBody body = builder.build();
                    //创建Request
                     Request request = new Request.Builder().url("http://10.100.24.108:8080/mvc/user/updateicon.do").post(body).build();
                     Call call = new OkHttpClient().newBuilder().writeTimeout(15, TimeUnit.SECONDS).build().newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Loading.destroy();
                            Log.e(TAG, e.toString());
                            if (e instanceof SocketTimeoutException) {
                                Toast.makeText(context, "网络连接超时，请稍后再试", Toast.LENGTH_SHORT).show();
                            } else if (e instanceof ConnectException) {
                                Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                            } else if (e instanceof IOException) {
                                Toast.makeText(context, "网络链接异常...", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            Loading.destroy();
                            if (response.isSuccessful()) {
                                String string = response.body().string();
                              final  HttpResult bean= new Gson().fromJson( string, HttpResult.class);
                                if(bean.getCode()==1000){
                                    mineUserIconImg.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            GlideUtils.getInstance().displayImage(context,(String) bean.getResults(), mineUserIconImg);
                                        }
                                    });
                                }
                                Log.e(TAG, "response ----->" + string);
                            } else {
                                ToastUtils.show(context, "参数异常，上传头像失败", 0);
                            }
                        }
                    });

                } else {
                    ToastUtils.show(context, "网络状态异常", 0);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
