package com.roderick.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roderick.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 增加提问阅读量
     * @param id 文章id
     */
    @Update("UPDATE blog_question_category SET views = views + 1 WHERE id = #{id} AND is_deleted = 0")
    void increaseViewsById(@Param("id") Long id);
}
