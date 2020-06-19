package com.mkdev.cafebazaarandroidtest.utils.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.mkdev.cafebazaarandroidtest.R

inline fun View.snack(
    @StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
): Snackbar {
    return snack(resources.getString(messageRes), length, f)
}

inline fun View.snack(
    message: String, length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
): Snackbar {
    return Snackbar.make(this, message, length).apply {
        f()
        show()
    }
}

fun Snackbar.action(
    @StringRes actionRes: Int, @ColorRes color: Int? = R.color.md_green_300,
    listener: (View) -> Unit
) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(
    action: String, @ColorRes color: Int? = R.color.md_green_300,
    listener: (View) -> Unit
) {
    setAction(action, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}