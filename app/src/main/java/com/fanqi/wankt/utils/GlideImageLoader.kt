package com.fanqi.wankt.utils

import android.content.Context
import android.widget.ImageView
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by fanqi on 2019-12-23.
 * Description:
 */
class GlideImageLoader : ImageLoader() {

    private lateinit var colorList: List<ColorInfo>
    private lateinit var palette: Palette

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        context?.let { imageView?.let { it1 -> Glide.with(it).load(path).into(it1) } };
    }
}