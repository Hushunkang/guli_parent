package com.atguigu.eduservice.controller;

import com.atguigu.commonutil.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月07日 08时12分32秒
 */
@Api(description = "后台管理信息系统登录API")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin//解决前端访问后端接口的跨域问题，还可以通过网关（spring cloud getway）来解决跨域问题
public class EduLoginController {

    //用户登录
    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    //获取用户信息
    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
