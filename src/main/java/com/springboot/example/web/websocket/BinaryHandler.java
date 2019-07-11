package com.springboot.example.web.websocket;

import com.alibaba.fastjson.JSONObject;
import com.springboot.example.util.ErrorPrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * websocket 上传二进制文件数据处理类
 *
 * @author zhangyonghong
 * @date 2019.7.10
 */
public class BinaryHandler implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(BinaryHandler.class);

    private static final String UPLOAD_DIR = "D:/upload/";
    private static final int BUFFER_LENGTH = 1024 * 8;

    private ArrayBlockingQueue<ByteBuffer> blockingQueue = new ArrayBlockingQueue<>(200);

    private String filename;
    private String md5;
    private String id;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setId(String id) {
        this.id = id;
    }

    void addBinary(ByteBuffer byteBuffer) {
        if (!blockingQueue.offer(byteBuffer)) {
            logger.info(">>>>> NO ENOUGH SPACE IN BLOCKING_QUEUE");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (filename != null) {
                    File file = new File(UPLOAD_DIR, filename);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    ByteBuffer byteBuffer;
                    byte[] buf = new byte[BUFFER_LENGTH];
                    // 参数 10 这个值值得考量，根据网络环境调整
                    while ((byteBuffer = blockingQueue.poll(200, TimeUnit.MILLISECONDS)) != null) {
                        byte[] array = byteBuffer.array();
                        if (buf.length  >= array.length) {
                            System.arraycopy(array, 0, buf, 0, array.length);
                            fos.write(buf, 0, array.length);
                        } else {
                            int srcPos = 0;
                            while (array.length >= srcPos) {
                                if (array.length - srcPos > buf.length) {
                                    System.arraycopy(array, srcPos, buf, 0, buf.length);
                                    fos.write(buf);
                                } else {
                                    System.arraycopy(array, srcPos, buf, 0, array.length - srcPos);
                                    fos.write(buf, 0, array.length - srcPos);
                                }
                                srcPos += BUFFER_LENGTH;
                            }
                        }
                    }
                    // 拿不到缓冲字节数组，说明二进制数据上传完毕
                    fos.close();
                    // 校验 md5
                    FileInputStream fis = new FileInputStream(file);
                    JSONObject json = new JSONObject();
                    if (!md5.equals(DigestUtils.md5DigestAsHex(fis))) {
                        file.delete();
                        json.put("message", "MD5 NOT MATCH");
                        SessionManager.getSessionMap().get(id).getBasicRemote().sendText(json.toString());
                    } else {
                        json.put("message", "UPLOAD " + filename + " SUCCESS");
                        SessionManager.getSessionMap().get(id).getBasicRemote().sendText(json.toString());
                    }
                    break;
                }
                // 每次获取 filename 后线程需要 sleep 一下，不然 cpu 一直不停地执行此任务，没有时间更新 filename 状态
                Thread.sleep(10);
            }
        } catch (Exception e) {
            try {
                JSONObject json = new JSONObject();
                json.put("message", "ERROR OCCURRED");
                SessionManager.getSessionMap().get(id).getBasicRemote().sendText(json.toString());
            } catch (IOException ex) {
                ErrorPrintUtil.printErrorMsg(logger, ex);
            }
            ErrorPrintUtil.printErrorMsg(logger, e);
        }
    }
}
