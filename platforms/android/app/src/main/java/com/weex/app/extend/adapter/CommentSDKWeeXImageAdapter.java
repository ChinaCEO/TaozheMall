package com.weex.app.extend.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

public class CommentSDKWeeXImageAdapter implements IWXImgLoaderAdapter {

    public CommentSDKWeeXImageAdapter() {}

    @Override
    public void setImage(final String url, final ImageView view,
                         WXImageQuality quality, final WXImageStrategy strategy) {

        WXSDKManager.getInstance().postOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (view == null || view.getLayoutParams() == null) {
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }
                if (url.startsWith("file://")) {
                    Glide.with(WXEnvironment.getApplication()).load(url).into(view);
                    return;
                }
                String temp = url;
                if (url.startsWith("//")) {
                    temp = "http:" + url;
                }

                if (temp.contains("/images/") && temp.startsWith("src")) {
                    temp = "file:///android_asset/images/" + url.substring(url.lastIndexOf("/")+1);
                    Log.d("ImageAdapter", "url:" + temp);
                }

                if (!TextUtils.isEmpty(strategy.placeHolder)) {
                    Picasso.Builder builder = new Picasso.Builder(WXEnvironment.getApplication());
                    Picasso picasso = builder.build();
                    picasso.load(Uri.parse(strategy.placeHolder)).into(view);

                    view.setTag(strategy.placeHolder.hashCode(), picasso);
                }
                
                Glide.with(WXEnvironment.getApplication()).load(temp).asBitmap().into(new WeeXImageTarget(strategy, url, view));
            }
        }, 0);
    }

    private class WeeXImageTarget extends SimpleTarget<Bitmap> {

        private WXImageStrategy mWXImageStrategy;
        private String mUrl;
        private ImageView mImageView;

        WeeXImageTarget(WXImageStrategy strategy, String url, ImageView imageView) {
            mWXImageStrategy = strategy;
            mUrl = url;
            mImageView = imageView;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            mImageView.setImageBitmap(resource);
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {}
    }
}
