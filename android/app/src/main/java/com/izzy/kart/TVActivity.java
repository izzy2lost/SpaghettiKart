package com.izzy.kart;

import android.view.KeyEvent;
import android.view.InputDevice;

public class TVActivity extends MainActivity{
    @Override
    protected boolean isTVActivity() {
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            int source = event.getSource();

            boolean isDpad = (source & InputDevice.SOURCE_DPAD) == InputDevice.SOURCE_DPAD;
            boolean isGamepad = (source & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD;

            if (isDpad && !isGamepad) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                return true;
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
