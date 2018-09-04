/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.test.lib_common.utils;

import android.content.Context;

import java.io.File;

public class FileUtil {
    /**
     * 保存文件
     * @param context 上下文
     */
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
