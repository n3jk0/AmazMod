package com.amazmod.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
            Logger.debug("AmazMod LauncherStateReceiver", "LauncherStateReceiver action : " + intent.getAction());
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
                Logger.debug("AmazMod LauncherStateReceiver", "action inWatchFace : " + this.mInWatchFace);
            }
        }
    }
    public boolean inDeepAmbient() {

        return this.mInDeepAmbient;
    }

    public boolean inWatchFace() {
        Logger.debug("Amazmod LauncherStateReceiver", "LauncherStateReceiver action inWatchFace : " + this.mInWatchFace);
        return this.mInWatchFace;
    }

    public void register(Context context) {
        this.mContext = context;
        IntentFilter localIntentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        localIntentFilter.addAction("com.google.android.wearable.action.DEEP_AMBIENT_STARTED");
        localIntentFilter.addAction("com.huai.watch.launcher.action.WatchFace");
        localIntentFilter.addAction("com.huami.watch.POWER_KEY_WAKE_UP");
        localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
        LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this, localIntentFilter);
        this.mContext.registerReceiver(this, localIntentFilter);
    }

    public void register(Context context, PowerKeyCallback callback) {
        register(context);
        this.mCallback = callback;
    }

    public void unregister() {
        LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this);
        this.mCallback = null;
        this.mContext.unregisterReceiver(this);
    }

}
