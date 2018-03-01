package cn.v1.unionc_user.tecent_qcloud;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;

import cn.v1.unionc_user.R;
import cn.v1.unionc_user.tecent_qcloud.tim_util.TIMFileUtil;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class ImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        initView();
    }

    private void initView() {
        RelativeLayout root = (RelativeLayout) findViewById(R.id.root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String file = getIntent().getStringExtra("filename");
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Bitmap bitmap = getImage(TIMFileUtil.getCacheFilePath(file));
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getImage(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int reqWidth, reqHeight, width = options.outWidth, height = options.outHeight;
        if (width > height) {
            reqWidth = getWindowManager().getDefaultDisplay().getWidth() / 2;
            reqHeight = (reqWidth * height) / width;
        } else {
            reqHeight = getWindowManager().getDefaultDisplay().getHeight() / 2;
            reqWidth = (width * reqHeight) / height;
        }
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        try {
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            Matrix mat = new Matrix();
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            if (bitmap == null) {
                showTost("未找到文件，请稍后重试");
                return null;
            }
            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    mat.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    mat.postRotate(180);
                    break;
            }
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
        } catch (IOException e) {
            return null;
        }
    }
}
