package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * HttpURLConnection 工具类
 *
 * @author zhangyonghong
 * @date 2019.6.13
 */
@Slf4j
public class HttpURLConnectionUtil {

    // private static Logger logger = LoggerFactory.getLogger(HttpURLConnectionUtil.class);

    /**
     * 获取连接对象 HttpURLConnection
     *
     * @param url 连接地址
     * @return 连接对象
     */
    private static HttpURLConnection getConnection(String url) throws Exception {
        URL serverUrl = new URL(url);
        if (url.toLowerCase().startsWith("https")) {
            HttpsURLConnection.setDefaultHostnameVerifier(new SimpleHostnameVerifier());
            HttpsURLConnection connection = (HttpsURLConnection) serverUrl.openConnection();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManager = {new SimpleX509TrustManager()};
            sslContext.init(null, trustManager, null);
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            return connection;
        }
        return (HttpURLConnection) serverUrl.openConnection();
    }

    /**
     * 发送请求，获取响应消息
     *
     * @param connection 连接对象
     * @return 响应消息
     */
    private static String getResponse(HttpURLConnection connection) throws IOException {
        // connection.connect();
        int responseCode = connection.getResponseCode();
        log.info(">>>>> RESPONSE_CODE: {}", responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            log.info(">>>>> RESPONSE_MESSAGE: {}", connection.getResponseMessage());
            return "";
        } else {
            log.info(">>>>> RESPONSE SUCCESS");
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            connection.disconnect();
            return new String(baos.toByteArray(), StandardCharsets.UTF_8);
        }
    }

    /**
     * 将文件写到请求的输出流
     *
     * @param file         文件
     * @param outputStream 请求的输出流
     */
    private static void writeFile2OutputStream(File file, OutputStream outputStream) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        int len;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }
        inputStream.close();
    }

    /**
     * GET 请求
     *
     * @param url 请求地址
     * @return 响应消息
     */
    public static String httpGet(String url) throws Exception {
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("GET");
        return getResponse(connection);
    }

    /**
     * POST 请求
     *
     * @param url         请求地址
     * @param headerMap 请求头参数
     * @param param       请求参数
     * @return 响应消息
     */
    public static String httpPost(String url, Map<String, String> headerMap, String param) throws Exception {
        HttpURLConnection connection = getConnection(url);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", StandardCharsets.UTF_8.toString());
        headerMap.forEach(connection::addRequestProperty);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(param.getBytes());
        return getResponse(connection);
    }

    /**
     * 上传文件
     *
     * @param url      请求地址
     * @param filePath 文件路径
     * @return 响应消息
     */
    public static String upload(String url, String filePath) throws Exception {
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "application/octet-stream");

        OutputStream outputStream = connection.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(filePath);
        int length;
        byte[] buf = new byte[1024];
        while ((length = fileInputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, length);
        }
        fileInputStream.close();
        outputStream.close();

        return getResponse(connection);
    }

    /**
     * 上传文件附带其他请求参数
     *
     * @param url          请求地址
     * @param filePathList 文件路径集合
     * @param paramMap     请求参数
     * @return 响应消息
     */
    public static String multipartUpload(String url, List<String> filePathList, Map<String, Object> paramMap) throws Exception {
        String BOUNDARY = UUID.randomUUID().toString().replaceAll("-", "").substring(16);
        String TWO_HYPHENS = "--";
        String LINE_END = "\r\n";

        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Charset", StandardCharsets.UTF_8.toString());
        connection.setRequestProperty("Content-Type", "multipart/form-data; BOUNDARY=" + BOUNDARY);

        OutputStream outputStream = connection.getOutputStream();
        // 拼接请求参数
        if (paramMap.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (String key : paramMap.keySet()) {
                builder.append(TWO_HYPHENS).append(BOUNDARY).append(LINE_END);
                builder.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END);
                builder.append("Content-Type: text/plain; charset=\"").append(StandardCharsets.UTF_8.toString())
                        .append("\"").append(LINE_END);
                // 请求参数的值与其对应的头信息之间必须有一行空行
                builder.append(LINE_END);
                builder.append(paramMap.get(key)).append(LINE_END);
            }
            outputStream.write(builder.toString().getBytes());
        }

        // 拼接文件数据
        if (filePathList.size() > 0) {
            for (String filePath : filePathList) {
                StringBuilder builder = new StringBuilder();
                File file = new File(filePath);
                if (!file.exists()) {
                    continue;
                }
                builder.append(TWO_HYPHENS).append(BOUNDARY).append(LINE_END);
                builder.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getName())
                        .append("\"").append(LINE_END);
                builder.append("Content-Type: application/octet-stream; charset=")
                        .append(StandardCharsets.UTF_8.toString()).append(LINE_END);
                // 每个文件的字节数组与其对应的头信息之间必须有一行空行
                builder.append(LINE_END);
                outputStream.write(builder.toString().getBytes());
                writeFile2OutputStream(file, outputStream);
                outputStream.write(LINE_END.getBytes());
            }
        }

        StringBuilder builder = new StringBuilder();
        // 最后一个 BOUNDARY 之后必须跟上预定数量的连字符
        builder.append(TWO_HYPHENS).append(BOUNDARY).append(TWO_HYPHENS).append(LINE_END);
        outputStream.write(builder.toString().getBytes());
        outputStream.flush();

        return getResponse(connection);
    }

    /**
     * 下载文件
     *
     * @param url 请求地址
     * @param downloadDir 下载文件存储目录
     */
    public static void download(String url, String downloadDir) throws Exception {
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        log.info(">>>>> RESPONSE_CODE: {}", responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            log.info(">>>>> RESPONSE_MESSAGE: {}", connection.getResponseMessage());
        } else {
            log.info(">>>>> RESPONSE SUCCESS");

            // 1.下载动态资源时，从头信息中获取文件名
            String filename = URLDecoder.decode(connection.getHeaderField("content-Disposition"),
                    StandardCharsets.UTF_8.toString());
            // 2.下载静态资源时，从请求地址中获取文件名
            // String filename = url.substring(url.lastIndexOf("/") + 1);

            String externalName = filename.substring(filename.lastIndexOf("."));
            if (!filename.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$")) {
                filename = System.currentTimeMillis() + externalName;
            }
            log.info(">>>>> FILENAME: {}", filename);
            IOUtil.writeStream2File(connection.getInputStream(), downloadDir + filename);
        }
        connection.disconnect();
    }

}
