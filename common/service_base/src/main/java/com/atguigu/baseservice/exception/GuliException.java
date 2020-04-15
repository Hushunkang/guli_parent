package com.atguigu.baseservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月05日
 */
@NoArgsConstructor//使用lombok生成无参构造器
@AllArgsConstructor//使用lombok生成所有属性参数构造器
@Data
@ToString
public class GuliException extends RuntimeException {

    private Integer code;//异常状态码
    private String msg;//异常信息

}
