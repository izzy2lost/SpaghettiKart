package com.izzy.kart;

import android.os.Bundle;

public class TVActivity extends MainActivity{
    @Override
    protected boolean isTVActivity() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
