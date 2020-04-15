package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 用于生成树结构的数据模型，一级分类
 *
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月14日
 */
@Data
public class SubjectLevelOneVo {

    private String id;
    private String title;
    //模拟表示一个一级课程分类下面可以有多个二级课程分类
    private List<SubjectLevelTwoVo> children = new ArrayList<>();

}
