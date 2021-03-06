package com.mkdev.cafebazaarandroidtest.utils.permissionManager.callbacks;

import com.mkdev.cafebazaarandroidtest.utils.permissionManager.PermissionResult;

import java.util.List;

public interface PermissionListener {
    void onAccepted(
            PermissionResult permissionResult,
            List<String> accepted
    );
    void onDenied(
            PermissionResult permissionResult,
            List<String> denied,
            List<String> foreverDenied
    );
}
