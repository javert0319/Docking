package com.lib.frame.bindingadapter.image;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import cn.nbhope.imageproxylib.ImageProxy;
import cn.nbhope.imageproxylib.abs.ICreator;

/**
 * 作者：kelingqiu on 17/10/12 16:03
 * 邮箱：42747487@qq.com
 */
public class ViewBindingAdapter {

    public static final String TAG = "Z-ViewBindingAdapter";

    /**
     * @param imageView
     * @param uri
     * @param placeholderImageRes 占位图
     * @param circleImage         圆形图片
     * @param roundAngle          圆角
     * @param errorImageRes       加载错误时候显示的图片
     */
    @BindingAdapter(value = {"glideUri", "placeholderImageRes", "circleImage", "roundAngle", "errorImageRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, Object uri,
                                   @DrawableRes Integer placeholderImageRes, boolean circleImage,
                                   Integer roundAngle, @DrawableRes Integer errorImageRes) {
        ICreator creator;
        if (isNotNull(uri)) {
            creator = ImageProxy.with(imageView.getContext())
                    .load(uri);
        } else {
            creator = ImageProxy.with(imageView.getContext())
                    .load(placeholderImageRes);
        }
        if (errorImageRes != null) {
            creator.error(errorImageRes);
        }else if (placeholderImageRes != null) {
            creator.error(placeholderImageRes);
        }
        if (circleImage) {
            creator.transform(new CircleCrop());
        }
        if (roundAngle != null) {
            creator.transform(new CenterCrop(),
                    new GlideRoundTransform(imageView.getContext(), roundAngle));
        }
        creator.into(imageView);
    }

    private static boolean isNotNull(Object uri) {
        if (uri == null) {
            return false;
        }

        if (uri instanceof String) {
            String uriStr = ((String) uri).trim();
            if (TextUtils.isEmpty(uriStr)) {
                return false;
            } else {
                //处理服务器返回http://192.168.2.9:8080null的情况
                return !(uriStr.endsWith("null"));
            }
        } else if (uri instanceof Integer) {
            return true;
        } else {
            return false;
        }
    }

    @BindingAdapter({"bitmap"})
    public static void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    @BindingAdapter({"bitmapResource"})
    public static void setImageBitmap(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }
}
