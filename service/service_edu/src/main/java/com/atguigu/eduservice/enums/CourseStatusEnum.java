package com.atguigu.eduservice.enums;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月17日
 */
public enum CourseStatusEnum {

    DRAFT("Draft","课程未发布"),
    NORMAL("Normal","课程已经发布");

    private String status;//课程状态
    private String description;//课程状态描述

    CourseStatusEnum(String status,String description){
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
