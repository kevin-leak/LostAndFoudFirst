package MyAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.example.lostandfoudfirst.CustomGoodsProvider;
import com.example.lostandfoudfirst.R;

import java.util.ArrayList;
import java.util.List;

import Bean.Goods;
import Holder.ContactItemHolder;

/**
 * Created by Administrator on 2017/12/23.
 */

public class MembersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;


    /**
     * 设置页面跳转传过来的值
     */
    public static final int ALL = 0;
    public static final int LOST = 1;
    public static final int FOUND = 2;
    public static final int IMPORTANT = 3;
    public static final int MYALL = 4;

    private int type ;
    private List<Goods> allList = new ArrayList<>();
    private List<Goods> lostList = new ArrayList<Goods>();
    private List<Goods> foundList = new ArrayList<Goods>();
    private List<Goods> importantList = new ArrayList<Goods>();
    private List<Goods> myList = new ArrayList<Goods>();

    /**
     * 所有的list集合
     */
    private List<List> ll = new ArrayList<List>();




    /**
     * 定义一个存放的当前信息集合
     */
    private List<Goods> goodsList = new ArrayList<Goods>();

    private int layout;

    public MembersAdapter(int layout, int type, Context context) {
        this.context = context;
        this.type = type;
        this.layout = layout;
        setMemberList(CustomGoodsProvider.getInstance().getAllGoods());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //传入当前的目录，并传入根部局，进行item的构建
        return new ContactItemHolder(parent.getContext(),parent,layout, type);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (goodsList.size() > 0){
            ((ContactItemHolder) holder).bindData(goodsList.get(position));
        }else {
            ((ContactItemHolder) holder).bindData(null);
        }
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    /**
     *
     * @param goods
     */
    public void setMemberList(List<Goods> goods) {

        String[] valuable = null;
        if (context.getResources() != null){
            valuable = context.getResources().getStringArray(R.array.valuableThings);
        }

        //清空当前adapter中的user方便更新
        clearList();
        if (null != goods){
            allList.addAll(goods);
            for (Goods g: goods){
                if ( g.getIsLost() == false ){
                    lostList.add(g);
                }else {
                    foundList.add(g);
                }
                if (AVUser.getCurrentUser() != null ){
                        myList.add(g);
                }else {
                    Toast.makeText(context,"login",Toast.LENGTH_SHORT).show();
                }

                if (g.getGoodsName() != null){
                    String parent = g.getGoodsName();
                    for (String v: valuable){
                        if (parent.indexOf(v,0) == 0){
                            importantList.add(g);
                        }
                    }
                }

            }
        }
        fillList();
    }


    /**
     * 将所有的list封装到一个list里面
     */
    private void fillList() {
        ll.add(allList);
        ll.add(lostList);
        ll.add(foundList);
        ll.add(importantList);
        ll.add(myList);
        goodsList = ll.get(type);
    }

    /**
     * 每次更新数据前进一次清楚
     */
    private void clearList() {
        allList.clear();
        lostList.clear();
        foundList.clear();
        importantList.clear();
        myList.clear();
        goodsList.clear();
    }
}
