package com.atguigu.demo;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月17日
 */
public class VodTest {

    /*根据阿里云视频点播服务为每一个视频生成的视频ID来获取视频播放地址*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("e9344b7ac517466ebeaa73c990742742");
        return client.getAcsResponse(request);
    }

    /*获取视频播放地址*/
    @Test
    public void test01() throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI4FcYecA2Hueh7ugWSYr5", "TcZ6OKAigVaDdVUCwrzOU0xgnyfCgu");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /*根据阿里云视频点播服务为每一个视频生成的视频ID来获取视频播放凭证*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("e9344b7ac517466ebeaa73c990742742");
        return client.getAcsResponse(request);
    }

    /*获取视频播放凭证*/
    //课程的视频都需要加密，要不然别人拿到你的视频地址就可以播放看，那还咋做课程收费，加密视频后能保证视频安全
    //在阿里云视频点播控制台里面配置转码模板组可以对上传的视频进行加密，即使别人拿到你的视频地址，也是不能播放
    //加密后的视频要特殊的方式才可以播放，通过视频播放凭证整合阿里云视频播放器可以播放加密视频
    @Test
    public void test2() throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI4FcYecA2Hueh7ugWSYr5", "TcZ6OKAigVaDdVUCwrzOU0xgnyfCgu");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /*本地上传*/
    @Test
    public void test03(){
        String accessKeyId = "LTAI4FcYecA2Hueh7ugWSYr5";
        String accessKeySecret = "TcZ6OKAigVaDdVUCwrzOU0xgnyfCgu";
        String title = "Local Upload_What If I Want to Move Faster";//上传之后的文件名称
        String fileName = "C:\\Users\\27529\\Desktop\\essential\\What If I Want to Move Faster.mp4";//本地文件的路径和文件的名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

}
