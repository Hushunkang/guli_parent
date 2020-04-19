package com.atguigu.eduservice.client;

import com.atguigu.util.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月19日
 */
@Component
@FeignClient("service-vod")//service-vod为要被调用的微服务名称
//spring cloud微服务间调用的流程（RPC流程），大致会经过如下几个组件的配合
//1、消费者（即微服务的调用者）
//2、接口化请求调用：通俗的讲通过设置服务名和请求地址来具体定位到调用哪个服务的哪个方法，这一步只是做了接口的定义
//3、feign：声明式、模板化的HTTP客户端，feign来做远程接口调用，根据服务名和接口地址来RPC，这一步其实是服务的发现
//4、hystrix：熔断器（断路器），当edu微服务调用vod微服务的时候，假设vod微服务挂掉了，这个时候就会触发熔断机制，就不会再调用vod微服务了，如果一切正常，熔断机制就不会被触发，这是一种保护机制
//5、ribbon：应对集群、负载均衡的负载均衡器，挑选合适的服务提供端
//6、httpclient技术（或者其它apache http components、okhttp）：根据服务提供端真正的ip和port进行远程服务调用
//7、生产者（即微服务的提供者）
public interface VodClient {

    //根据阿里云视频点播服务为每一个视频生成的视频ID来删除云端视频，支持删除至少为零个的视频
    @ApiOperation(value = "删除云端视频")
    @DeleteMapping("/vodservice/video/removeVideo/{videoSourceIds}")
    R removeVideo(@ApiParam(name = "videoSourceIds", value = "云端视频IDS", required = true) @PathVariable("videoSourceIds") String videoSourceIds);

}
