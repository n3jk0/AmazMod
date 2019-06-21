package com.amazmod.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import org.tinylog.Logger;

public class LauncherStateReceiver extends BroadcastReceiver {
    private PowerKeyCallback mCallback;
    private Context mContext;
    private boolean mInDeepAmbient;
    private boolean mInWatchFace = true;

    public interface PowerKeyCallback {
        void onPowerKeyPress();
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Logger.debug("LauncherStateReceiver", "LauncherStateReceiver action : " + intent.getAction());
            if (TextUtils.equals("com.huami.watch.POWER_KEY_WAKE_UP", intent.getAction())) {
                if (this.mCallback != null) {
                    this.mCallback.onPowerKeyPress();
                }
            } else if (TextUtils.equals("android.intent.action.SCREEN_ON", intent.getAction())) {
                this.mInDeepAmbient = false;
            } else if (TextUtils.equals("android.intent.action.SCREEN_OFF", intent.getAction())) {
                this.mInDeepAmbient = true;
            } else if (TextUtils.equals(intent.getAction(), "com.huai.watch.launcher.action.WatchFace")) {
                this.mInWatchFace = intent.getBooleanExtra("com.huai.watch.launcher.action.WatchFace", true);
                Logger.debug("LauncherStateReceiver", "action inWatchFace : " + this.mInWatchFace);
            }
        }
    }
    public boolean inDeepAmbient() {
        return this.mInDeepAmbient;
    }

    public boolean inWatchFace() {
        Logger.debug("LauncherStateReceiver", "LauncherStateReceiver action inWatchFace : " + this.mInWatchFace);
        return this.mInWatchFace;
    }

}
