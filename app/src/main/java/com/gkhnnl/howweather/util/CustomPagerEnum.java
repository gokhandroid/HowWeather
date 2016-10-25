package com.gkhnnl.howweather.util;

import com.gkhnnl.howweather.R;

/**
 * Created by gkhnnl on 06/09/16.
 */
public enum CustomPagerEnum {

    HOURLY(R.layout.layout_hourly),
    DAILY(R.layout.layout_daily);

    private final int layoutResId;

    CustomPagerEnum(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

}
