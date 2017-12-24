package com.example.lostandfoudfirst;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import Bean.Goods;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * 自定义用户体系
 * Created by Administrator on 2017/12/23.
 */

public class CustomGoodsProvider implements LCChatProfileProvider{

    private static CustomGoodsProvider customUserProvider;
    private List<Goods> allGoods = null;

    //一个类中只需要一个,并设为同步
    public synchronized static CustomGoodsProvider getInstance(){
        if (null == customUserProvider){
            customUserProvider = new CustomGoodsProvider();
        }
        return customUserProvider;
    }


    //私有化构造方法
    private CustomGoodsProvider(){}

    private static List<LCChatKitUser> partUsers = new ArrayList<>();

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack lcChatProfilesCallBack) {

    }

    public void setAllGoods() {
        AVQuery query = new AVQuery("Goods");
        query.include("owner");
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, AVException e) {
                allGoods = list;
            }

            @Override
            protected void internalDone0(Object o, AVException e) {

            }
        });
    }

    public List<Goods> getAllGoods() {
        return allGoods;
    }
}
