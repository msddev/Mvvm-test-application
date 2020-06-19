package com.mkdev.cafebazaarandroidtest.utils.permissionManager

import android.content.pm.PackageManager
import android.os.Build

fun androidx.fragment.app.Fragment.askPermission(
    vararg permissions: String,
    acceptedBlock: (PermissionResult) -> Unit
): KotlinRuntimePermission {
    return KotlinRuntimePermission(
        RuntimePermission.askPermission(activity)
            .request(permissions.toList())
            .onAccepted(acceptedBlock)
    )
}

fun androidx.fragment.app.FragmentActivity.askPermission(
    vararg permissions: String,
    acceptedBLock: (PermissionResult) -> Unit
): KotlinRuntimePermission {
    return KotlinRuntimePermission(
        RuntimePermission.askPermission(this)
            .request(permissions.toList())
            .onAccepted(acceptedBLock)
    )
}

class KotlinRuntimePermission(var runtimePermission: RuntimePermission) {

    init {
        runtimePermission.ask()
    }

    fun onDeclined(block: ((PermissionResult) -> Unit)): KotlinRuntimePermission {
        runtimePermission.onResponse {
            if (it.hasDenied() || it.hasForeverDenied()) {
                block(it)
            }
        }
        return this
    }
}

private fun useRunTimePermissions(): Boolean {
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
}

fun androidx.fragment.app.FragmentActivity.hasPermission(permission: String): Boolean {
    return if (useRunTimePermissions()) {
        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    } else true
}

fun androidx.fragment.app.Fragment.hasPermission(permission: String): Boolean {
    return if (useRunTimePermissions()) {
        context?.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    } else true
}
