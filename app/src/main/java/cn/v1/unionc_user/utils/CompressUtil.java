package cn.v1.unionc_user.utils;

import android.content.Context;
import android.graphics.Bitmap;

import news.jaywei.com.compresslib.CompressTools;
import news.jaywei.com.compresslib.FileUtil;

/**
 * Created by qy on 2018/1/2.
 */

public class CompressUtil {

    public static CompressTools getCompressTool(Context context) {
        return new CompressTools.Builder(context)
                // .setMaxWidth(1280) // 默认最大宽度为720
                // .setMaxHeight(850) // 默认最大高度为960
                .setQuality(50) // 默认压缩质量为60,60足够清晰
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setKeepResolution(true)// 设置保持原图分辨率，则设置的最大宽高就无效了。不需要设置最大宽高了。设置也不会报错了，该参数默认false
                .setFileName("qmDoctor" + System.currentTimeMillis())
                .setDestinationDirectoryPath(FileUtil.getPhotoFileDir().getAbsolutePath())
                .build();
    }
}
