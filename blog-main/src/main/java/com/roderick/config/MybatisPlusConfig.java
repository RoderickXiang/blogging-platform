package com.roderick.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.roderick.mapper")
public class MybatisPlusConfig {

}
