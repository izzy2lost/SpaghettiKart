package com.izzy.kart;

import android.view.KeyEvent;

public class TVActivity extends MainActivity{
    @Override
    protected boolean isTVActivity() {
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                // Finish the activity
                finish();
                // Kill the app process explicitly to fully terminate the app
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
