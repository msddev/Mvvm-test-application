package com.mkdev.cafebazaarandroidtest.core

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mkdev.cafebazaarandroidtest.R
import com.mkdev.cafebazaarandroidtest.utils.GlideApp
import com.mkdev.cafebazaarandroidtest.utils.extensions.*

@BindingAdapter("app:visibility")
fun setVisibilty(view: View, isVisible: Boolean) {
    view.hide()
    if (isVisible) {
        view.show()
    } else {
        view.hide()
    }
}

@BindingAdapter("app:setDrawableLink")
fun setDrawableLink(view: ImageView, link: String?) {
    if (link.isNullOrEmpty())
        return
    GlideApp.with(view.context)
        .load(link)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .transition(GenericTransitionOptions.with(R.anim.fade_in))
        .customRequest(R.drawable.shape_circle_blue, R.drawable.shape_circle_red)
        .rounded((16f).dpToPx().toInt())
        .into(view)
}

@BindingAdapter("app:setDrawableLinkRec")
fun setDrawableLinkRec(view: ImageView, link: String?) {
    if (link.isNullOrEmpty())
        return
    GlideApp.with(view.context)
        .load(link)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .transition(GenericTransitionOptions.with(R.anim.fade_in))
        .customRequest(R.color.colorAccent, R.color.red)
        .rounded((16f).dpToPx().toInt())
        .into(view)
}