package anjuyi.cc.edeco.ui.activity.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adorkable.iosdialog.ActionSheetDialog;

import java.io.File;
import java.util.ArrayList;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.adapter.ImgSelectorAdapter;
import anjuyi.cc.edeco.base.BaseActivity;
import anjuyi.cc.edeco.base.BaseApplication;
import anjuyi.cc.edeco.util.AppUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：Mr.Lee on 2016-7-26 11:37
 * 邮箱：569932357@qq.com
 */
public class ImageSelectorActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;

    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;



    //图片选择地址
    private ArrayList<String> urls=new ArrayList<>();

    //照相
    private static final int REQUEST_CAPTURE = 100;
    private static final int REQUEST_PICK = 101;
    //图片文件
    private File tempFile;

    private ImgSelectorAdapter adapter;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_imgselector;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
            urls = (ArrayList<String>) savedInstanceState.getStringArrayList("urls");
        }

        adapter=new ImgSelectorAdapter(context,urls,4) {
            @Override
            public void onAddImg() {
                addImg();
            }

            @Override
            public void onDeleteImg(int position) {

                urls.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onShowImg(int position,View view) {
                Intent intent = new Intent(context, ImageZoomActivity.class);
                intent.putStringArrayListExtra("imgpath",urls);
                intent.putExtra("current",position);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,view,"trans");
                startActivity(intent, options.toBundle());
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( new GridLayoutManager(context,2));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
        outState.putStringArrayList("urls",urls);
    }

    private void addImg(){

        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("从手机相册中选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(Intent.createChooser(intent, "请选择图片"),REQUEST_PICK);
                            }
                        })
                .addSheetItem("进行拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tempFile = new File(checkDirPath(BaseApplication.AJYFILE_IMG),
                                        System.currentTimeMillis() + ".jpg");
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                                startActivityForResult(intent, REQUEST_CAPTURE);
                            }
                        }).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK) {
                    urls.add(tempFile.getAbsolutePath().toString());
                    adapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_PICK:
                if (resultCode == RESULT_OK) {
                    if (intent != null) {
                        Uri uri = intent.getData();
                        if (uri == null) {
                            return;
                        }
                        String path = AppUtils.getRealFilePathFromUri(getApplicationContext(), uri);
                        urls.add(path);
                        adapter.notifyDataSetChanged();
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     *
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


}
