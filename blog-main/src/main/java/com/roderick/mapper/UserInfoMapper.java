package com.roderick.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roderick.pojo.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
