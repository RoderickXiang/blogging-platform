package com.roderick.util;

import org.springframework.stereotype.Component;

@Component
public class PageUtil {

    /**
     * 通过分页结果获取总页数
     *
     * @param total 结果条数
     * @param size  每页大小
     * @return 总页数
     */
    public Long getTotalPage(Long total, Long size) {
        return total % size == 0 ? total / size : total / size + 1;
    }
}
