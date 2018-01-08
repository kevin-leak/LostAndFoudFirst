package Bean;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;

import cn.leancloud.chatkit.LCChatKitUser;

/**
 * 后端存储数据的格式，先存user，在建立UserInfo表，里面获取相关的user avatar，id,name
 * Created by Administrator on 2017/12/23.
 */

final public class Goods {
    private String goodsImageUrl;
    private String goodsName;
    private String goodsPlace;
    private String goodsTime;
    private String goodsInfo;
    private boolean isLost;
    private String goodsId;
    private LCChatKitUser user;


    public Goods(final AVObject object)  {
        goodsName = object.getString("name");
        goodsPlace = object.getString("place");
        goodsTime = object.getString("time");
        goodsInfo = object.getString("info");
        isLost = object.getBoolean("isLost");
        goodsId = object.getObjectId();
        AVFile file = object.getAVFile("image");
        goodsImageUrl = file.getUrl();
        String ownerInfoId = object.getString("ownerInfoId");
        AVQuery<AVObject> query = new AVQuery<AVObject>("UserInfo");
        query.getInBackground(ownerInfoId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                setUserInfo(avObject);
            }
        });
    }

    public void setUserInfo(AVObject info) {
        String userId = info.getString("userId");
        String userName = info.getString("userName");
        String phone = info.getString("userPhone");
        AVFile avatarFile = info.getAVFile("avatarFile");
        String avatarUrl = avatarFile.getUrl();
        user = new LCChatKitUser(userId,userName,avatarUrl);
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsPlace() {
        return goodsPlace;
    }

    public String getGoodsTime() {
        return goodsTime;
    }

    public boolean getIsLost() {
        return isLost;
    }

    public String getGoodsId() {
        return goodsId;
    }


    public LCChatKitUser getUser() {
        return user;
    }


    public String getGoodsInfo() {
        return goodsInfo;
    }
}
