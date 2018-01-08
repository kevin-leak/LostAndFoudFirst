package com.example.lostandfoudfirst;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import MyAdapter.MembersAdapter;
import MyView.SpacesItemDecoration;

/**
 * Created by Administrator on 2017/12/23.
 */

public class GoodsActivity extends Activity{
    private SwipeRefreshLayout srlMessageOrGoods;
    private RecyclerView rvMessageOrGoods;
    LinearLayoutManager layoutManager;
    private MembersAdapter itemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_goods);
//        // TODO: 2017/12/25
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        srlMessageOrGoods = findViewById(R.id.srlMessageOrGoods);
        rvMessageOrGoods = findViewById(R.id.rvMessageOrGoods);

        layoutManager = new LinearLayoutManager(this);

        int spacingInPixels = 8;

        rvMessageOrGoods.setLayoutManager(layoutManager);
        //rvMessageOrGoods.addItemDecoration(new LCIMDividerItemDecoration(getApplicationContext()));
        rvMessageOrGoods.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        itemAdapter = new MembersAdapter(R.layout.holder_all,MembersAdapter.MYALL,getApplicationContext());
        rvMessageOrGoods.setAdapter(itemAdapter);
        srlMessageOrGoods.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMembers();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMembers();
    }

    private void refreshMembers() {
        itemAdapter.setMemberList(CustomGoodsProvider.getInstance().getAllGoods());
        itemAdapter.notifyDataSetChanged();
        srlMessageOrGoods.setRefreshing(false);
    }
}
