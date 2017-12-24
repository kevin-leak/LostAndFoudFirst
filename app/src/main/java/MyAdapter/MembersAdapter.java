package MyAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Bean.Goods;

/**
 * Created by Administrator on 2017/12/23.
 */

public class MembersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    /**
     * 定义一个存放的信息集合
     */
    private List<Goods> goodsList = new ArrayList<Goods>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //传入当前的目录，并传入根部局，进行item的构建
        return new ContactItemHolder(parent.getContext(),parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //传入一个对象将数据进行填充
        ((ContactItemHolder) holder).bindData(goodsList.get(position));
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    /**
     * 设置成员列表
     * @param goods
     */
    public void setMemberList(List<Goods> goods) {
        //清空当前adapter中的user方便更新
        goodsList.clear();
        if (null != goods){
            goodsList = goods;
        }
    }

}
