package com.atguigu.eduservice.enums;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月28日
 */
public enum VideoStatusEnum {

    EMPTY("Empty","课程小节的视频未上传"),
    TRANSCODING("Transcoding","课程小节的视频转码中"),
    NORMAL("Normal","课程小节的视频正常");

    private String status;//课程小节所对应的视频状态
    private String description;//状态的描述

    VideoStatusEnum(String status,String description){
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
