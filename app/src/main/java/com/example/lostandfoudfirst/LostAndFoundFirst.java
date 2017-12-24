package com.example.lostandfoudfirst;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.im.v2.AVIMClient;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by Administrator on 2017/12/20.
 */

public class LostAndFoundFirst extends Application {

    String ID = "H5AUPg4bLDOxftm6K3rbbuta-gzGzoHsz";
    String key = "p7bDIpEGEPHR4VDEQaSgLUWY";

    @Override
    public void onCreate() {
        super.onCreate();
        LCChatKit.getInstance().setProfileProvider(CustomGoodsProvider.getInstance());
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,ID, key);
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVIMClient.setAutoOpen(false);
        //这里值得考虑 todo
        PushService.setDefaultPushCallback(this, MessageAndGoodsActivity.class);
    }
}
