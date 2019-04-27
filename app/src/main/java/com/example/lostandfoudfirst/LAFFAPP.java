package com.example.lostandfoudfirst;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.lostandfoudfirst.bean.CustomGoodsProvider;
import com.example.lostandfoudfirst.myView.activity.MessageActivity;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by Administrator on 2017/12/20.
 */

public class LAFFAPP extends Application {

    String ID = "8rPdKaoSbUhcLzW9byBbRm8U-gzGzoHsz";
    String key = "hWTkEm4mbOTYtturSiRTKVjY";

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,ID, key);
        if (AVUser.getCurrentUser() != null){
            AVIMClient.getInstance(AVUser.getCurrentUser()).open(new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    LCChatKit.getInstance().setProfileProvider(CustomGoodsProvider.getInstance());
                }
            });
        }

//         初始化参数依次为 this, AppId, AppKey
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVIMClient.setAutoOpen(true);
        AVOSCloud.setDebugLogEnabled(true);
        //这里值得考虑 todo
        PushService.setDefaultPushCallback(this, MessageActivity.class);
    }
}
