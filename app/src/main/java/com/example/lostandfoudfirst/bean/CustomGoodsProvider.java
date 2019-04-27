package com.example.lostandfoudfirst.bean;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * 自定义用户体系
 * Created by Administrator on 2017/12/23.
 */

public class CustomGoodsProvider implements LCChatProfileProvider{

    private static CustomGoodsProvider customUserProvider;
    private List<Goods> allGoods = new ArrayList<>();

    //一个类中只需要一个,并设为同步
    public synchronized static CustomGoodsProvider getInstance(){
        if (null == customUserProvider){
            customUserProvider = new CustomGoodsProvider();
        }
        return customUserProvider;
    }


    //私有化构造方法
    private CustomGoodsProvider(){
        setAllGoods();
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<>();


    public void setAllGoods() {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Goods");
        query.include("goodOwner");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                postDealGoods(list);
            }
        });

    }



    private void postDealGoods(List<AVObject> list) {
            allGoods.clear();
        // TODO: 2017/12/30 don't have internet ,it will error
        if (list != null){
            for (AVObject object: list){
                Goods goods = new Goods(object);
                allGoods.add(goods);
            }
        }

    }

    public List<Goods> getAllGoods() {
        setAllGoods();
        return allGoods;
    }

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        if (allGoods != null){
            for (Goods g : allGoods) {
                userList.add(g.getUser());
            }
            callBack.done(userList, null);
        }
    }

}
