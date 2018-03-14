package cn.v1.unionc_user.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.DoctorInfoData;
import cn.v1.unionc_user.model.UpdateFileData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.PictureFileUtil;
import cn.v1.unionc_user.view.CircleImageView;

public class UpdateAvatorActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.img_update_avator)
    CircleImageView imgAvator;
    @Bind(R.id.tv_album)
    TextView tvAlbum;
    @Bind(R.id.tv_camera)
    TextView tvCamera;

    private String urlpath = "";
    private String avator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avator);
        ButterKnife.bind(this);
        initData();
    }


    @OnClick({R.id.img_back, R.id.tv_right, R.id.tv_album, R.id.tv_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_right:
                uploadImage();
                break;
            case R.id.tv_album:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent1, 1);
                break;
            case R.id.tv_camera:
                Intent intent2 = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                //下面这句指定调用相机拍照后的照片存储的路径
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                        .fromFile(new File(Environment
                                .getExternalStorageDirectory(),
                                "qmavator.jpg")));
                startActivityForResult(intent2, 2);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                // 如果是直接从相册获取
                case 1:
                    startPhotoZoom(data.getData());
                    break;
                // 如果是调用相机拍照时
                case 2:
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/qmavator.jpg");
                    startPhotoZoom(Uri.fromFile(temp));
                    break;
                // 取得裁剪后的图片
                case 3:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
                default:
                    break;

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void initData() {
        if (getIntent().hasExtra("avator")) {
            avator = getIntent().getStringExtra("avator");
            Glide.with(context).load(avator)
                    .placeholder(R.drawable.icon_default_avator)
                    .error(R.drawable.icon_default_avator)
                    .into(imgAvator);
        }

    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            //图片路径
            urlpath = PictureFileUtil.saveFile(context, "temphead.jpg", photo);
            Glide.with(context).load("file://" + urlpath).into(imgAvator);
        }
    }


    /**
     * 头像上传
     */
    private void uploadImage() {
        showDialog("头像上传中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.uploadImge(token, new File(urlpath)), new BaseObserver<UpdateFileData>(context) {
            @Override
            public void onResponse(UpdateFileData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    updateUserInfo(data.getPath() + "");
                } else {
                    showTost(data.getMessage() + "");
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }

    /**
     * 修改用户信息
     */
    private void updateUserInfo(String avator) {
        showDialog("修改用户资料...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.updateUserInfo(token, "", "", avator, "", ""),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (!TextUtils.isEmpty(urlpath)) {
                                Intent intent = new Intent();
                                intent.putExtra("avator", urlpath + "");
                                setResult(Activity.RESULT_OK, intent);
                            }
                            finish();
                        } else {
                            showTost(data.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });
    }


}
