package com.atguigu.eduservice.entity.subject;

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
 * @create 2020年04月14日 17时39分30秒
 */
@Data
public class SubjectLevelOne {

    private String id;
    private String title;
    //模拟表示一个一级分类下面可以有多个二级分类
    private List<SubjectLevelTwo> children = new ArrayList<>();

}
