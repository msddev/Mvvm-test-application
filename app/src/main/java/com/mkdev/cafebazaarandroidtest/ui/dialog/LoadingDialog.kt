package com.mkdev.cafebazaarandroidtest.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.mkdev.cafebazaarandroidtest.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(
    context: Context,
    private val cancelable: Boolean = false,
    private var content: String = context.getString(R.string.please_wait)
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(cancelable)
        tvContent.text = content
    }
}


