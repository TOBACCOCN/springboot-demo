package com.springboot.example.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QRCodeUtilTests {

    private static Logger logger = LoggerFactory.getLogger(QRCodeUtilTests.class);

    @Test
    public void encode() throws Exception {
        // 生成二维码
        // String url = "https://qr.alipay.com/fkx09314x4cvcllpxtmr3bd";
        String url = "weixin://wxpay/bizpayurl?pr=ZaC5KPJ";
        int width = 258;
        int height = 258;
        String qrCodeFilePath = "D:\\weixinPay.png";
        String type = "png";
        QRCodeUtil.encode(url, width, height, qrCodeFilePath, type);
        logger.info(">>>>> WRITE QR_CODE TO {}, DONE", qrCodeFilePath);
    }

    @Test
    public void decode() throws Exception {
        // 解析二维码
        String qrCodeFilePath = "D:\\weixinPay.png";
        String result = QRCodeUtil.decode(qrCodeFilePath);
        logger.info(">>>>> RESULT: {}", result);
    }

}
