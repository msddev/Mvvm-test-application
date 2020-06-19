package com.mkdev.cafebazaarandroidtest.utils.permissionManager.callbacks;

import com.mkdev.cafebazaarandroidtest.utils.permissionManager.PermissionResult;

public interface DeniedCallback {
    void onDenied(PermissionResult result);
}
