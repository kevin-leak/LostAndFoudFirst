package com.example.lostandfoudfirst;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.adapter.LCIMCommonListAdapter;
import cn.leancloud.chatkit.cache.LCIMConversationItemCache;
import cn.leancloud.chatkit.event.LCIMConversationItemLongClickEvent;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import cn.leancloud.chatkit.event.LCIMOfflineMessageCountChangeEvent;
import cn.leancloud.chatkit.view.LCIMDividerItemDecoration;
import cn.leancloud.chatkit.viewholder.LCIMConversationItemHolder;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2018/1/1.
 */

public class MessageActivity extends AppCompatActivity {
    public SwipeRefreshLayout refreshLayout;
    public RecyclerView recyclerView;

    public LCIMCommonListAdapter<AVIMConversation> itemAdapter;
    public LinearLayoutManager layoutManager;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.mesage_list_activity);
        getSupportActionBar().hide();
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        this.getWindow().setAttributes(lp);
//        WindowManager.LayoutParams attr = this.getWindow().getAttributes();
//        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.getWindow().setAttributes(attr);


        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.slMessage);
        recyclerView = (RecyclerView) findViewById(R.id.rclMessage);

//        refreshLayout.setSystemUiVisibility(View.GONE);

        refreshLayout.setEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LCIMDividerItemDecoration(this));
        itemAdapter = new LCIMCommonListAdapter<AVIMConversation>(LCIMConversationItemHolder.class);
        recyclerView.setAdapter(itemAdapter);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateConversationList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 收到对方消息时响应此事件
     *
     * @param event
     */
    public void onEvent(LCIMIMTypeMessageEvent event) {
        updateConversationList();
    }

    /**
     * 删除会话列表中的某个 item
     * @param event
     */
    public void onEvent(LCIMConversationItemLongClickEvent event) {
        if (null != event.conversation) {
            String conversationId = event.conversation.getConversationId();
            LCIMConversationItemCache.getInstance().deleteConversation(conversationId);
            updateConversationList();
        }
    }

    /**
     * 刷新页面
     */
    private void updateConversationList() {
        List<String> convIdList = LCIMConversationItemCache.getInstance().getSortedConversationList();
        List<AVIMConversation> conversationList = new ArrayList<>();
        for (String convId : convIdList) {
            conversationList.add(LCChatKit.getInstance().getClient().getConversation(convId));
        }

        itemAdapter.setDataList(conversationList);
        itemAdapter.notifyDataSetChanged();
    }

    /**
     * 离线消息数量发生变化是响应此事件
     * 避免登陆后先进入此页面，然后才收到离线消息数量的通知导致的页面不刷新的问题
     * @param updateEvent
     */
    public void onEvent(LCIMOfflineMessageCountChangeEvent updateEvent) {
        updateConversationList();
    }
}
