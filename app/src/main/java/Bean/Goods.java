package Bean;

import cn.leancloud.chatkit.LCChatKitUser;

/**
 * Created by Administrator on 2017/12/23.
 */

public class Goods {
    private LCChatKitUser user;
    private String goodsImageURl;
    private String goodsName;
    private String goodsPlace;
    private String goodsTime;
    private String GoodsInfo;


    public Goods(LCChatKitUser user, String goodsImageURl,
                 String goodsName, String goodsPlace,
                 String goodsTime, String goodsInfo){
        this.user = user;
        this.goodsImageURl = goodsImageURl;
        this.goodsName = goodsName;
        this.goodsPlace = goodsPlace;
        this.goodsTime = goodsTime;
        this.GoodsInfo = goodsInfo;
    }


    public String getGoodsImageURl() {
        return goodsImageURl;
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

    public String getGoodsInfo() {
        return GoodsInfo;
    }

    public LCChatKitUser getUser() {
        return user;
    }
}
