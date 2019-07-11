package com.springboot.example.web.controller;

import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.util.ParamUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api("上传文件相关 api")
@Controller
public class UploadController {

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    private static String UPLOAD_DIR = "D:/upload/";

    // @GetMapping("/upload")
    // public String upload() {
    //     return "/upload";
    // }
    //
    // @GetMapping("/uploadStatus")
    // public String uploadStatus() {
    //     return "/uploadStatus";
    // }
    //
    // @PostMapping("/multipartUpload")
    // public String multipartUpload(MultipartFile file, RedirectAttributes redirectAttributes) {
    //     try {
    //         logger.info(">>>>> UPLOAD_FILE_SIZE: {}", file.getBytes().length);
    //         String filePath = UPLOAD_DIR + file.getOriginalFilename();
    //         File destFile = new File(filePath);
    //         if (!destFile.getParentFile().exists()) {
    //             destFile.getParentFile().mkdirs();
    //         }
    //         file.transferTo(destFile);
    //         logger.info(">>>>> UPLOAD SUCCESS");
    //         redirectAttributes.addFlashAttribute("message", "successfully upload '" + file.getOriginalFilename() + "'");
    //     } catch (IOException e) {
    //         logger.info(">>>>> UPLOAD FAIL");
    //         redirectAttributes.addFlashAttribute("message", "failed upload '" + file.getOriginalFilename() + "'");
    //         ErrorPrintUtil.printErrorMsg(logger, e);
    //     }
    //     return "redirect:uploadStatus";
    // }

    // paramType: header，query，path，body，form
    @ApiOperation(value = "上传单个文件", notes = "使用 POST （application/octet-stream）上传单个文件")
    @PostMapping("/upload")
    @ResponseBody
    public Object upload(HttpServletRequest request) {
        String filename = request.getParameter("filename");
        logger.info(">>>>> FILENAME: {}", filename);
        Map<String, String> map = new HashMap<>();
        map.put("filename", filename);
        map.put("uploaded", "SUCCESS");
        try {
            ServletInputStream inputStream = request.getInputStream();
            File file = new File(UPLOAD_DIR + filename);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            int len;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            inputStream.close();
            fos.close();
            logger.info(">>>>> UPLOAD SUCCESS");
        } catch (IOException e) {
            logger.info(">>>>> UPLOAD FAIL");
            map.put("uploaded", "FAIL");
            ErrorPrintUtil.printErrorMsg(logger, e);
        }
        return map;
    }

    @ApiOperation(value = "上传多个文件", notes = "使用 POST （application/form-data）上传多个文件")
    @PostMapping("/multipartUpload")
    @ResponseBody
    public Object multipartUpload(MultipartFile[] file, HttpServletRequest request) {
        logger.info(">>>>> PARAM_MAP: {}", ParamUtil.getMap(request.getParameterMap()));
        Map<String, String> map = new HashMap<>();
        map.put("uploaded", "SUCCESS");
        try {
            for (MultipartFile multipartFile : file) {
                String filename = multipartFile.getOriginalFilename();
                logger.info(">>>>> FILENAME: {}, SIZE: {}", filename, multipartFile.getBytes().length);
                String filePath = UPLOAD_DIR + filename;
                File destFile = new File(filePath);
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                multipartFile.transferTo(destFile);
            }
            logger.info(">>>>> UPLOAD SUCCESS");
        } catch (IOException e) {
            logger.info(">>>>> UPLOAD FAIL");
            map.put("uploaded", "FAIL");
            ErrorPrintUtil.printErrorMsg(logger, e);
        }
        return map;
    }

}
