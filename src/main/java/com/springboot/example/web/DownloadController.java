package com.springboot.example.web;

import com.springboot.example.util.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Controller
public class DownloadController {

    private static Logger logger = LoggerFactory.getLogger(DownloadController.class);

    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> map = ParamUtil.getMap(request.getParameterMap());
        logger.info(">>>>> PARAM_MAP: {}", map);
        // TODO 根据请求参数获取或者组装出下载文件

        File file = new File("D:/download/AliPayQR.png");
        response.setHeader("content-Disposition", "attachment; filename = " + file.getName());
        FileInputStream fis = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();
        int len;
        byte[] buf = new byte[1024];
        while ((len = fis.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }
    }


}
