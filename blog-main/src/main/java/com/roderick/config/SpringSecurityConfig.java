package com.roderick.config;

import com.roderick.handler.MyAuthenticationFailureHandler;
import com.roderick.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .antMatchers("/js/**", "/images/**", "/css/**", "/image/**", "/**/*.ico").permitAll()
                //markdown插件
                .antMatchers("/editor/**").permitAll()
                //博客社区
                .antMatchers(HttpMethod.GET,"/blog/write/**").authenticated();
                //所有请求被拦截 --试试不拦截全部请求，只是验证权限
                /*.anyRequest().authenticated();*/

        //防止iframe markdown 跨域问题
        http.headers().frameOptions().disable();

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
