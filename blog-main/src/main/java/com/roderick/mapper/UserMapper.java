package com.roderick.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roderick.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
