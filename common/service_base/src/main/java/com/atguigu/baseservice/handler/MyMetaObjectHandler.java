package com.atguigu.baseservice.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月05日
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //使用mp实现添加操作，这个方法就会执行
    @Override
    public void insertFill(MetaObject metaObject) {//metaObject：元数据对象
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    //使用mp实现修改操作，这个方法就会执行
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

}
