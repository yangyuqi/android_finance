/*
 *   Copyright 2012 Hai Bison
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.youzheng.zhejiang.financebooking.Widget.gesture;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

public class ResourceUtils {


    /**
     * 获取颜色值
     */
    public static int getColor(@NonNull Context context, @ColorRes int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }


    /**
     * 适配px
     */
    public static float getDimensionInPx(@NonNull Context context, @DimenRes int dimenRes) {
        return context.getResources().getDimension(dimenRes);
    }
}
