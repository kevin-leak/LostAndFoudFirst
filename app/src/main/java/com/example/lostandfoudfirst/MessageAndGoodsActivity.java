package com.example.lostandfoudfirst;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lostandfoudfirst.CustomGoodsProvider;
import com.example.lostandfoudfirst.R;

import MyAdapter.MembersAdapter;
import cn.leancloud.chatkit.view.LCIMDividerItemDecoration;

/**
 * Created by Administrator on 2017/12/23.
 */

public class MessageAndGoodsActivity extends Activity{
    private SwipeRefreshLayout srlMessageOrGoods;
    private RecyclerView rvMessageOrGoods;
    LinearLayoutManager layoutManager;
    private MembersAdapter itemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_goods);

        srlMessageOrGoods = findViewById(R.id.srlMessageOrGoods);
        rvMessageOrGoods = findViewById(R.id.rvMessageOrGoods);

        layoutManager = new LinearLayoutManager(this);
        rvMessageOrGoods.setLayoutManager(layoutManager);
        rvMessageOrGoods.addItemDecoration(new LCIMDividerItemDecoration(getApplicationContext()));
        itemAdapter = new MembersAdapter();
        rvMessageOrGoods.setAdapter(itemAdapter);
        srlMessageOrGoods.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMembers();
            }
        });
    }

    private void refreshMembers() {
        itemAdapter.setMemberList(CustomGoodsProvider.getInstance().getAllGoods());
    }
}
