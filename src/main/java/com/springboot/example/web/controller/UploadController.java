package com.springboot.example.web.controller;

import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.util.IOUtil;
import com.springboot.example.util.ParamUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
@Controller
@Api("上传文件相关 api")
@Slf4j
public class UploadController {

    // private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    private static final String UPLOADED = "uploaded";
    private static final String UPLOAD_DIR = "D:/upload/";

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
    //         log.info(">>>>> UPLOAD_FILE_SIZE: [{}]", file.getBytes().length);
    //         String filePath = UPLOAD_DIR + file.getOriginalFilename();
    //         File destFile = new File(filePath);
    //         if (!destFile.getParentFile().exists()) {
    //             destFile.getParentFile().mkdirs();
    //         }
    //         file.transferTo(destFile);
    //         log.info(">>>>> UPLOAD SUCCESS");
    //         redirectAttributes.addFlashAttribute("message", "successfully upload '" + file.getOriginalFilename() + "'");
    //     } catch (IOException e) {
    //         log.info(">>>>> UPLOAD FAIL");
    //         redirectAttributes.addFlashAttribute("message", "failed upload '" + file.getOriginalFilename() + "'");
    //         ErrorPrintUtil.printErrorMsg(log, e);
    //     }
    //     return "redirect:uploadStatus";
    // }

    // paramType: header，query，path，body，form
    @ApiOperation(value = "上传单个文件", notes = "使用 POST （application/octet-stream）上传单个文件")
    @PostMapping("/upload")
    @ResponseBody
    // public Object upload(HttpServletRequest request) {
    public Mono<Object> upload(HttpServletRequest request) {
        String filename = request.getParameter("filename");
        log.info(">>>>> FILENAME: [{}]", filename);
        Map<String, String> map = new HashMap<>();
        map.put("filename", filename);
        map.put(UPLOADED, "SUCCESS");
        try {
            ServletInputStream inputStream = request.getInputStream();
            IOUtil.writeStream2File(inputStream, UPLOAD_DIR + filename);
            inputStream.close();
            log.info(">>>>> UPLOAD SUCCESS");
        } catch (IOException e) {
            log.info(">>>>> UPLOAD FAIL");
            map.put(UPLOADED, "FAIL");
            ErrorPrintUtil.printErrorMsg(log, e);
        }
        // return map;
        return Mono.create(monoSink -> monoSink.success(map));
    }

    @ApiOperation(value = "上传多个文件", notes = "使用 POST （application/form-data）上传多个文件")
    @PostMapping("/multipartUpload")
    @ResponseBody
    // public Object multipartUpload(MultipartFile[] files, HttpServletRequest request) {
    public Mono<Object> multipartUpload(MultipartFile[] files, HttpServletRequest request) {
        log.info(">>>>> PARAM_MAP: [{}]", ParamUtil.getMap(request.getParameterMap()));
        Map<String, String> map = new HashMap<>();
        map.put(UPLOADED, "SUCCESS");
        try {
            for (MultipartFile multipartFile : files) {
                String filename = multipartFile.getOriginalFilename();
                log.info(">>>>> FILENAME: [{}], SIZE: [{}]", filename, multipartFile.getBytes().length);
                String filePath = UPLOAD_DIR + filename;
                File destFile = new File(filePath);
                if (!destFile.getParentFile().exists()) {
                    log.info(">>>>> DIRS_MAKE_RESULT: [{}]", destFile.getParentFile().mkdirs());
                }
                multipartFile.transferTo(destFile);
            }
            log.info(">>>>> UPLOAD SUCCESS");
        } catch (IOException e) {
            log.info(">>>>> UPLOAD FAIL");
            map.put(UPLOADED, "FAIL");
            ErrorPrintUtil.printErrorMsg(log, e);
        }
        // return map;
        return Mono.create(monoSink -> monoSink.success(map));
    }

}
