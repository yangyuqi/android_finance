package com.youzheng.zhejiang.financebooking.Widget.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class PublishUtils {
    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

}
