package com.example.lostandfoudfirst.myView.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.example.lostandfoudfirst.R;

import java.util.Arrays;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationFragment;
import cn.leancloud.chatkit.cache.LCIMConversationItemCache;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.chatkit.utils.LCIMConversationUtils;
import cn.leancloud.chatkit.utils.LCIMLogUtils;


/**
 * Created by Administrator on 2017/12/23.
 */

public class ConversationActivity extends AppCompatActivity{
    protected LCIMConversationFragment conversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        this.getWindow().setAttributes(params);
        setContentView(R.layout.conversation_activity);
        conversationFragment = (LCIMConversationFragment) getSupportFragmentManager().findFragmentById(cn.leancloud.chatkit.R.id.fragment_chat);
        initByIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initByIntent(intent);
    }

    private void initByIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (AVUser.getCurrentUser() == null  && extras.get(LCIMConstants.PEER_ID) != AVUser.getCurrentUser().getObjectId()) {
            showToast("please login first!");
            finish();
            return;
        }

        if (null != extras) {
            if (extras.containsKey(LCIMConstants.PEER_ID)) {
                getConversation(extras.getString(LCIMConstants.PEER_ID));
            } else if (extras.containsKey(LCIMConstants.CONVERSATION_ID)) {
                String conversationId = extras.getString(LCIMConstants.CONVERSATION_ID);
                updateConversation(LCChatKit.getInstance().getClient().getConversation(conversationId));
            } else {
                showToast("memberId or conversationId is needed");
                finish();
            }
        }
    }

    /**
     * 设置 actionBar title 以及 up 按钮事件
     *
     * @param title
     */
    protected void initActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            if (null != title) {
                actionBar.setTitle(title);
            }
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            finishActivity(RESULT_OK);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 主动刷新 UI
     *
     * @param conversation
     */
    protected void updateConversation(AVIMConversation conversation) {
        if (null != conversation) {
            conversationFragment.setConversation(conversation);
            LCIMConversationItemCache.getInstance().insertConversation(conversation.getConversationId());
            LCIMConversationUtils.getConversationName(conversation, new AVCallback<String>() {
                @Override
                protected void internalDone0(String s, AVException e) {
                    if (null != e) {
                        LCIMLogUtils.logException(e);
                    } else {
                        initActionBar(s);
                    }
                }
            });
        }
    }

    /**
     * 获取 conversation
     * 为了避免重复的创建，createConversation 参数 isUnique 设为 true·
     */
    protected void getConversation(final String memberId) {
        // TODO: 2017/12/11 建立对话的核心方法
        if (memberId == AVUser.getCurrentUser().getObjectId()){
            return;
        }
        LCChatKit.getInstance().getClient().createConversation(
                Arrays.asList(memberId), "", null, false, true, new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation avimConversation, AVIMException e) {
                        if (null != e) {
                            showToast(e.getMessage());
                        } else {
                            updateConversation(avimConversation);
                        }
                    }
                });
    }

    /**
     * 弹出 toast
     *
     * @param content
     */
    private void showToast(String content) {
        Toast.makeText(ConversationActivity.this, content, Toast.LENGTH_SHORT).show();
    }
}
