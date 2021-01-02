package com.roderick.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的SpringSecurity成功跳转处理器，为了配合异步的登入请求
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String URL;

    public MyAuthenticationSuccessHandler(String url) {
        this.URL = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "success");
        map.put("url", URL);
        new ObjectMapper().writeValue(writer, map);
    }
}
