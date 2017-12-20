package com.example.lostandfoudfirst;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2017/12/20.
 */

public class LostAndFoundFirst extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String ID = "H5AUPg4bLDOxftm6K3rbbuta-gzGzoHsz";
        String key = "p7bDIpEGEPHR4VDEQaSgLUWY";

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"H5AUPg4bLDOxftm6K3rbbuta-gzGzoHsz", "p7bDIpEGEPHR4VDEQaSgLUWY");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }
}
