package com.roderick.vo;

/**
 * 接受提交的提问表单
 */
public class QuestionForm {

    private String uid;
    private String title;
    private Integer categoryId;
    private String content;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "QuestionForm{" +
                "uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", categoryId=" + categoryId +
                ", content='" + content + '\'' +
                '}';
    }
}
