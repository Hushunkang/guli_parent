package com.atguigu.baseservice.exception;

import com.atguigu.commonutil.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * 全局异常处理器，aop的编程思想，底层使用代理模式
 * aop指的是程序运行期间动态的将某段代码切入到指定类指定方法指定位置进行运行的编程思想
 *
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月05日 17时42分41秒
 */
//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//指定出现什么样的异常，会执行这个方法
//    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理器...");
    }

}
