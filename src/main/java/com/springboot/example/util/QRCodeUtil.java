package com.example.demo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 二维码工具类
 *
 * @author zhangyonghong
 * @date 2019.6.13
 */
public class QRCodeUtil {

    private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

    /**
     * 生成二维码
     *
     * @param url            二维码内容，一般为 url 地址
     * @param width          二维码图片宽度
     * @param height         二维码图片高度
     * @param qrCodeFilePath 二维码图片文件地址
     * @param type           图片类型
     */
    public static void encode(String url, int width, int height, String qrCodeFilePath, String type) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height);
        OutputStream os = new FileOutputStream(new File(qrCodeFilePath));
        MatrixToImageWriter.writeToStream(matrix, type, os);
    }

    /**
     * 解析二维码
     *
     * @param qrCodeFilePath 二维码图片文件地址
     */
    public static String decode(String qrCodeFilePath) throws Exception {
        QRCodeReader reader = new QRCodeReader();
        BufferedImage bufferedImage = ImageIO.read(new File(qrCodeFilePath));
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = reader.decode(bitmap);
        logger.info(">>>>> RESULT: {}", result.toString());
        return result.toString();
    }

    @Test
    public void encode() throws Exception {
        // 生成二维码
        // String url = "https://qr.alipay.com/fkx09314x4cvcllpxtmr3bd";
        String url = "weixin://wxpay/bizpayurl?pr=ZaC5KPJ";
        int width = 258;
        int height = 258;
        String qrCodeFilePath = "D:\\weixinPay.png";
        String type = "png";
        encode(url, width, height, qrCodeFilePath, type);
        logger.info(">>>>> WRITE QR_CODE TO {}, DONE", qrCodeFilePath);
    }

    @Test
    public void decode() throws Exception {
        // 解析二维码
        String qrCodeFilePath = "D:\\weixinPay.png";
        String result = decode(qrCodeFilePath);
        logger.info(">>>>> RESULT: {}", result);
    }

}
