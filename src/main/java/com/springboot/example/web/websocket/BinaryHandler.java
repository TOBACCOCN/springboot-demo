package com.springboot.example.web.websocket;

import com.alibaba.fastjson.JSONObject;
import com.springboot.example.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * websocket 上传二进制数据处理类
 *
 * @author zhangyonghong
 * @date 2019.7.10
 */
@Slf4j
public class BinaryHandler implements Runnable {

    // private static Logger logger = LoggerFactory.getLogger(BinaryHandler.class);

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
            log.info(">>>>> NO ENOUGH SPACE IN BLOCKING_QUEUE");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (filename != null) {
                    File file = new File(UPLOAD_DIR,  md5 + "-" + filename);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }

                    FileOutputStream fos = new FileOutputStream(file);
                    // 服务端缓冲用的字节数组
                    byte[] buf = new byte[BUFFER_LENGTH];
                    ByteBuffer byteBuffer;
                    // 参数 10 这个值值得考量，根据网络环境调整
                    while ((byteBuffer = blockingQueue.poll(10, TimeUnit.MILLISECONDS)) != null) {
                        // 客户端传过来的字节数组
                        byte[] array = byteBuffer.array();

                        if (BUFFER_LENGTH >= array.length) {
                            // 客户端传过来的字节数组长度没有超过服务端缓冲用的字节数组长度，直接将客户端字节数组写入到文件
                            System.arraycopy(array, 0, buf, 0, array.length);
                            fos.write(buf, 0, array.length);
                        } else {
                            // 客户端传过来的字节数组长度超过服务端缓冲用的字节数组长度，那就分多次将客户端字节数组写入到文件
                            int srcPos = 0;
                            while (array.length > srcPos) {
                                if (array.length - srcPos > BUFFER_LENGTH) {
                                    System.arraycopy(array, srcPos, buf, 0, BUFFER_LENGTH);
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
                    JSONObject message = new JSONObject();
                    if (!md5.equals(DigestUtils.md5DigestAsHex(fis))) {
                        fis.close();
                        file.delete();
                        message.put("code", 305);
                        message.put("message", "Md5 not match");
                    } else {
                        message.put("code", 607);
                        message.put("message", "Upload " + filename + " success");
                    }
                    ServerSessionManager.getSession(id).getBasicRemote().sendText(message.toString());
                    break;
                }
                // 每次获取 filename 后线程需要 sleep 一下，不然 cpu 一直不停地执行此任务，没有时间更新 filename 状态
                Thread.sleep(10);
            }
        } catch (Exception e) {
            try {
                JSONObject message = new JSONObject();
                message.put("code", 508);
                message.put("message", "Error occurred");
                ServerSessionManager.getSession(id).getBasicRemote().sendText(message.toString());
            } catch (IOException ex) {
                ErrorPrintUtil.printErrorMsg(log, ex);
            }
            ErrorPrintUtil.printErrorMsg(log, e);
        }
    }
}
