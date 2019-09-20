package com.springboot.example.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO 工具类
 *
 * @author zhangyonghong
 * @date 2019.8.16
 */
public class IOUtil {

    public static void writeStream2File(InputStream inputStream, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file, true);
        int len;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.close();
    }

}
