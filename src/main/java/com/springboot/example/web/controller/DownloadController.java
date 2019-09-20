package com.springboot.example.web.controller;

import com.springboot.example.util.ParamUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

/**
 * 文件下载控制器
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Controller
@Api("文件下载 api")
@Slf4j
public class DownloadController {

    // private static Logger logger = LoggerFactory.getLogger(DownloadController.class);

    @ApiOperation(value = "下载文件")
    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> map = ParamUtil.getMap(request.getParameterMap());
        log.info(">>>>> PARAM_MAP: {}", map);
        // TODO 根据请求参数获取或者组装出下载文件

        long length = Long.parseLong(map.get("length"));
        String filePath = "D:/download/AliPayQR.png";
        File file = new File(filePath);
        response.setHeader("content-Disposition", "attachment; filename = " + file.getName());
        RandomAccessFile accessFile = new RandomAccessFile(filePath, "r");
        accessFile.seek(length);
        response.setHeader("Content-Length", file.length() + "");
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buf = new byte[8192];
        int len;
        while ((len = accessFile.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }
    }

}
