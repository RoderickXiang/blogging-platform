package com.roderick.config;

import com.roderick.handler.MyAuthenticationFailureHandler;
import com.roderick.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //处理登入请求的url
                .loginProcessingUrl("/login")   //post
                //自定义登入的页面
                .loginPage("/login")    //get
                //登入成功的页面
                .successHandler(new MyAuthenticationSuccessHandler("/"))
                //登入失败的页面
                .failureHandler(new MyAuthenticationFailureHandler());

        //授权认证
        http.authorizeRequests()
                //添加不需要被拦截的请求
                .antMatchers("/", "/index", "/login").permitAll()
                //不需要拦截静态资源
                .antMatchers("/js/**", "/images/**", "/css/**", "/image/**").permitAll()
                //markdown插件
                .antMatchers("/editor/**").permitAll()
                //所有请求被拦截
                .anyRequest().authenticated();

        //关闭csrf
        http.csrf().disable();
        //注销
        http.logout().logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
