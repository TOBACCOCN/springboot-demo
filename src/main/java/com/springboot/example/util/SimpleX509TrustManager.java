package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSession;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.*;
import java.util.Collection;
import java.util.List;

/**
 * X509 证书管理器
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class SimpleX509TrustManager implements X509TrustManager {

	private String hostname;

	public SimpleX509TrustManager() {
	}

	public SimpleX509TrustManager(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) {
		// nothing need to do
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		X509Certificate cert = chain[0];
		try {
			// 验证是否有效
			cert.verify(cert.getPublicKey());
			verifyHostname(cert);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 验证有效期
		cert.checkValidity();
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// 不能返回 null，会导致 okHttpClient 执行 https 请求抛空指针异常
		return new X509Certificate[]{};
	}

	private void verifyHostname(X509Certificate cert) throws Exception {
		// 获取服务器证书中的主题备用名称（Subject Alternative Name）或通用名称（Common Name）
		Collection<List<?>> subjAltNames = cert.getSubjectAlternativeNames();
		String commonName = cert.getSubjectX500Principal().getName();

		// 检查实际主机名与证书中的主机名是否匹配
		if (subjAltNames != null && !subjAltNames.isEmpty()) {
			for (List<?> entry : subjAltNames) {
				// entry 的第一个元素是主题备用名称的类型，第二个元素是具体的值
				Integer type = (Integer) entry.get(0);
				Object value = entry.get(1);

				// 根据类型进行处理
				if (type == 2) { // DNS 名称类型
					String dnsName = (String) value;
					System.out.println("DNS Name: " + dnsName);
					if (hostname.equalsIgnoreCase(dnsName)) {
						return; // 匹配到了就可以结束循环
					}
				} else if (type == 7) { // IP 地址类型
					byte[] ipAddressBytes = (byte[]) value;
					// 将字节数组转换为字符串表示的 IP 地址
					String ipAddress = javax.xml.bind.DatatypeConverter.printHexBinary(ipAddressBytes);
					log.debug("IP Address: " + ipAddress);
				}
				// 还可以处理其他类型的主题备用名称...
			}
		} else {
			if (commonName != null && !commonName.trim().isEmpty()) {
				String[] array = commonName.split(",");
				for (String s : array) {
					String[] kv = s.split("=");
					if (kv.length == 2 && hostname.equalsIgnoreCase(kv[1])) {   // 匹配通用名称
						return;
					}
				}
			}
		}
		throw new Exception("主机名不匹配"); // 主机名不匹配
	}

}