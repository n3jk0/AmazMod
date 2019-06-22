package com.amazmod.service.ui;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.amazmod.service.springboard.LauncherWearGridActivity;
import com.amazmod.service.util.DeviceUtil;

import org.tinylog.Logger;


public class Overlay_launcher extends Service implements OnClickListener {

    private View bottomRightView;

    private Button overlayedButton;
    private WindowManager wm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        overlayedButton = new Button(this);
        overlayedButton.setText("                                            ");
        overlayedButton.setBackgroundColor(0x00fe4444);
        overlayedButton.setOnClickListener(this);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        if (DeviceUtil.isVerge()) {
            params.height = 35;
        }
        else
            params.height = 20;
        params.x = 0;
        params.y = 0;
        wm.addView(overlayedButton, params);

        bottomRightView = new View(this);
        WindowManager.LayoutParams bottomRightParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        bottomRightParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        bottomRightParams.x = 0;
        bottomRightParams.y = 0;
        bottomRightParams.width = 0;
        bottomRightParams.height = 0;
        wm.addView(bottomRightView, bottomRightParams);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayedButton != null) {
            wm.removeView(overlayedButton);
            wm.removeView(bottomRightView);
            overlayedButton = null;
            bottomRightView = null;
        }
    }
    @Override
    public void onClick(View notification) {
        Intent intent = new Intent(this, LauncherWearGridActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra(LauncherWearGridActivity.MODE, LauncherWearGridActivity.NOTIFICATIONS);
        this.startActivity(intent);
        Logger.debug ("AmazMod overlay button click");
    }
}

