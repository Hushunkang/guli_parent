package com.atguigu.baseservice.handler;

import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.commonutil.ExceptionUtils;
import com.atguigu.commonutil.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * 定义异常处理器，aop的编程思想，底层使用代理模式
 * aop指的是程序运行期间动态的将某段代码切入到指定类指定方法指定位置进行运行的编程思想
 *
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月05日 17时42分41秒
 */
//@ControllerAdvice
@RestControllerAdvice
@Slf4j//可以程序编译的时候给我们隐式的生成一个log属性，说白了就是最终的.class字节码文件里面有这个属性
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//指定出现什么样的异常，会执行这个方法
//    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();//在控制台打印异常的栈追踪信息
        return R.error().message("执行了全局异常处理方法...");
    }
    //全局异常处理

    @ExceptionHandler(ArithmeticException.class)
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了特定异常ArithmeticException处理方法...");
    }
    //特定异常处理

    //执行原则：精确匹配优先原则

    @ExceptionHandler(GuliException.class)
    public R error(GuliException e){
//        log.error(e.getMessage());
        log.error(ExceptionUtils.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
    //用户自定义异常处理（一般是项目里面的业务逻辑之类的异常）

}
