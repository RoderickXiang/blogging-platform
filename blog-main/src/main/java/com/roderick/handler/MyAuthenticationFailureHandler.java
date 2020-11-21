package com.roderick.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的SpringSecurity失败跳转处理器，为了配合异步的登入请求
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "error");
        new ObjectMapper().writeValue(writer, map);
    }
}
