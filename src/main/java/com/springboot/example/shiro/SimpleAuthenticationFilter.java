package com.springboot.example.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * shiro 认证过滤器
 *
 * @author zhangyonghong
 * @date 2019.6.11
 */
public class SimpleAuthenticationFilter extends AuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        // ajax 请求
        if (null != httpServletRequest.getHeader("X-Requested-With")
                && "XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
            // TODO 将 MESSAGE 替换成合适的 javaBean
            Object message = "MESSAGE";
            httpServletResponse.getWriter().write(JSON.toJSONString(message));
            return false;
        }

        // 其他请求跳转到登录页
        StringBuilder loginUrl = new StringBuilder(getLoginUrl());
        // Request-Url 是前置的 nginx 添加的头信息，在对应的 server 下的 location 标签内，
        // proxy_set_header     Request-Url     $scheme://$host:$server_port$uri;
        String backUrl = httpServletRequest.getHeader("Request-Url");
        if (StringUtils.isEmpty(backUrl) || "unknown".equals(backUrl)) {
            backUrl = httpServletRequest.getRequestURL().toString();
        }
        String queryString = httpServletRequest.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            backUrl += "?" + queryString;
        }
        // TODO 在跳转到的登录页处理 back_url
        loginUrl.append("?").append("back_url").append("=")
                .append(URLEncoder.encode(backUrl, StandardCharsets.UTF_8.toString()));
        WebUtils.toHttp(response).sendRedirect(loginUrl.toString());
        return false;
    }

}
