package com.example.lostandfoudfirst.myView.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;

import java.io.ByteArrayOutputStream;

import com.example.lostandfoudfirst.MainActivity;
import com.example.lostandfoudfirst.R;
import com.example.lostandfoudfirst.utils.DrawableUtils;

/**
 * 此类用于建立客户端和登入注册界面
 * Created by Administrator on 2017/12/22.
 */

public class LoginAndRegisterActivity extends Activity {

    /**
     * 传递消息的名字
     */
    public static final String SHOULD_DO = "should do";
    public static final String INFO_SHOULD_DO = "INFO";

    /**
     * 收到其他界面的消息实现登入控制
     */
    public static final String LOGIN = "Sign In";
    public static final String REGISTER = "Sign Up";
    public static final String INFO_LOGIN = "info login";

    /**
     * 返回数据状态码
     */
    public static final int REGISTER_CODE = 1;
    public static final int LOGIN_CODE = 2;

    /**
     * 第二个布局，通过他来动态的根据屏幕的宽度来调整布局的with
     */
    private LinearLayout llLoginAndRegister;

    private Display display;
    private TextView tvUserTitle;
    private EditText etAccount;
    private EditText etPassword;
    private Button btnSaveBack;
    private Intent intent;
    private TextView tvSwitchTo;
    private EditText etNickName;
    private ImageButton ibProblem;
    private LinearLayout llCode;
    private Button btnGetCode;
    private EditText etSetCode;
    private ImageView ivUserAvatar;
    private ProgressBar pbCommit;
    /**
     * 设置一个开关，动态的添加View，和View之间的切换
     */
    private boolean switchView = true;
    /**
     * 一个开关动态的控制，回退
     */
    private String state;

    /**
     * @param savedInstanceState
     */
    // TODO: 2017/12/22 优化密码和账号输入
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_register);
        //设置键盘弹出的模式
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        llLoginAndRegister = (LinearLayout) findViewById(R.id.llLoginAndRegister);
        WindowManager manager = getWindowManager();
        display = manager.getDefaultDisplay();

        initView();
        intent = getIntent();
        //获取名字为shouldDo里面的信息,辨别是注册还是登入
        String shouldDo = intent.getStringExtra(LoginAndRegisterActivity.SHOULD_DO);
        String infoShouldDO = intent.getStringExtra(LoginAndRegisterActivity.INFO_SHOULD_DO);

        if (infoShouldDO != null){//这个一定要加上否则对于其它intent有误
            if (infoShouldDO.equals(LoginAndRegisterActivity.INFO_LOGIN)){
                state = LoginAndRegisterActivity.INFO_LOGIN;
                login();
            }
        }

        //监听切换
        tvSwitchTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = tvSwitchTo.getText().toString();
                if ((LoginAndRegisterActivity.LOGIN).equals(text)){
                    login();
                }else {
                    register();
                }
            }
        });

        ibProblem.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
                // TODO: 2017/12/23 重置密码
                Toast.makeText(LoginAndRegisterActivity.this, "解决密码问题",Toast.LENGTH_LONG).show();
            }
        });

        if (!TextUtils.isEmpty(shouldDo)){
            if ((LoginAndRegisterActivity.LOGIN).equals(shouldDo) ){
                login();
            }else if ((LoginAndRegisterActivity.REGISTER).equals(shouldDo)){
                register();
            }
        }
    }

    /**
     * 注册逻辑，注册后并实现登入
     */
    private void register() {
        initRegisterView();
        setRegisterListener();
    }

    /**
     * 登入逻辑
     * @param
     */
    private void login() {
        initLoginView();
        setLoginListener();
    }


    /**
     * 初始化注册的界面
     */
    private void initRegisterView() {
        etAccount.setHint("phone");
        etAccount.setInputType(InputType.TYPE_CLASS_PHONE);
        etSetCode.setVisibility(View.VISIBLE);
        if (switchView){
            switchView = false;
            addRegisterView();
        }else {
            etNickName.setVisibility(View.VISIBLE);
            ivUserAvatar.setVisibility(View.VISIBLE);
        }

        tvUserTitle.setVisibility(View.GONE);
        tvSwitchTo.setText("Sign In");
    }

    private void addRegisterView() {
        etNickName = new EditText(getApplicationContext());
        etNickName.setHint("nickName");
        etNickName.setTextColor(Color.GRAY);
        etNickName.setGravity(Gravity.CENTER);
        etNickName.setBackgroundResource(R.color.colorGray);
        etNickName.setAlpha(0.5f);
        etNickName.setWidth(50);
        etNickName.setAlpha(0.5f);
        llCode.setVisibility(View.VISIBLE);
        llLoginAndRegister.addView(etNickName,1);

        ivUserAvatar = new ImageView(getApplicationContext());

        ivUserAvatar.setMaxHeight(50);
        ivUserAvatar.setMaxHeight(50);
//        ivUserAvatar.setImageResource(R.mipmap.ic_default);
        ivUserAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,(int) (display.getWidth()*0.4));
        params.setMargins(0,0,0,20);
        ivUserAvatar.setLayoutParams(params);
        llLoginAndRegister.addView(ivUserAvatar,0);

        ivUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginAndRegisterActivity.this,"切换头像",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 设置注册的监听事件
     */
    private void setRegisterListener() {

        btnSaveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/12/23 利用单例模式来解决 是否正确填写
                String nickName = etNickName.getText().toString();
                String phone = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString();
                final Drawable drawable = ivUserAvatar.getDrawable();
                final byte[] userAvatar = DrawableUtils.DrawableToByte(drawable);
                btnGetCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: 2017/12/23 验证码
//                etSetCode.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                        if (charSequence.length() == 5){
//                            // TODO: 2017/12/23 返回给后端 ，比对后执行注册
//                        }
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//                    }
//                });
                        Toast.makeText(LoginAndRegisterActivity.this,"获取验证码，未开通，服务器要收钱",Toast.LENGTH_SHORT).show();
                    }
                });

                final AVUser user = new AVUser();
                user.setUsername(nickName);
                user.setPassword(password);
                user.setMobilePhoneNumber(phone);
                pbCommit.setVisibility(View.VISIBLE);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null){
                            setUserInfo(user, userAvatar);
                            Toast.makeText(LoginAndRegisterActivity.this,"请登入",Toast.LENGTH_LONG).show();
                            AVUser.getCurrentUser().logOut();
                            login();
                        }else {
                            Toast.makeText(LoginAndRegisterActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                pbCommit.setVisibility(View.GONE);
            }
        });
    }

    /**
     * @param user 传入用户
     * @param userAvatar 传入用户的照片数据
     */
    private void setUserInfo(final AVUser user, byte[] userAvatar) {
        //建立user信息查询表
        final AVFile file = new AVFile(user.getUsername() + "Avatar",getCopress(userAvatar));
        final AVObject userInfo = new AVObject("UserInfo");
        userInfo.put("userName",user.getUsername());
        userInfo.put("userId",user.getObjectId());
        userInfo.put("name",user.getUsername()+"Info");
        userInfo.put("owner",user);
        userInfo.put("userPhone",user.getMobilePhoneNumber());
        userInfo.put("avatarFile",file);
        userInfo.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e != null){
                    Toast.makeText(LoginAndRegisterActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG);
                }else {
                    user.put("userInfo",userInfo.getObjectId());
                    user.saveInBackground();
                }
            }
        });
    }
    private byte[] getCopress(byte[] bs) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,bos);
        byte[] bytes = bos.toByteArray();
        return bytes;
    }
    /**
     * 初始化登入界面
     */
    private void initLoginView() {
        tvUserTitle.setVisibility(View.VISIBLE);
        tvUserTitle.setText("Sign In");
        etAccount.setHint("account");
        if (!switchView){
            etNickName.setVisibility(View.GONE);
            ivUserAvatar.setVisibility(View.GONE);
        }
        tvSwitchTo.setVisibility(View.VISIBLE);
        etAccount.setInputType(InputType.TYPE_CLASS_TEXT);
        tvSwitchTo.setText("Sign Up");
        llCode.setVisibility(View.GONE);
    }

    /**
     * 建立登入的监听事件
     */
    private void setLoginListener() {
        btnSaveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                String phoneMatch = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginAndRegisterActivity.this,"请输入账户和密码",Toast.LENGTH_LONG).show();
                }else {
                    pbCommit.setVisibility(View.VISIBLE);
                    if (!account.matches(phoneMatch)){
                        signInByName(account, password);
                    }else{
                        signInByPhone(account, password);
                    }
                }
                pbCommit.setVisibility(View.GONE);
            }

        });
    }

    /**
     * sign in by name
     * @param account 用户的名字
     * @param password 密码
     */
    private void signInByName(String account, String password) {
        AVUser.logInInBackground(account, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e != null){
                    Toast.makeText(LoginAndRegisterActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }else {
                    //打开客户端
                    //返回数据
                    intentToPersonSet();
                }
            }
        });
    }

    /**
     * sign by phone
     * @param account 用户的电话
     * @param password 用户的密码
     */
    private void signInByPhone(String account, String password) {
        AVUser.loginByMobilePhoneNumberInBackground(account, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e != null){
                    Toast.makeText(LoginAndRegisterActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }else {
                    intentToPersonSet();
                }
            }
        });
    }

    /**
     * 成功打开客户端或返回数据
     */
    private void intentToPersonSet() {
        //返回数据
        intent.putExtra("User", AVUser.getCurrentUser().getObjectId());
        LoginAndRegisterActivity.this.setResult(LoginAndRegisterActivity.LOGIN_CODE,intent);
        LoginAndRegisterActivity.this.finish();
    }



    /**
     * 初始化固有的view
     */
    private void initView() {
        tvUserTitle = findViewById(R.id.tvUserTitle);
        etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        btnSaveBack = findViewById(R.id.btnSaveBack);
        tvSwitchTo = findViewById(R.id.tvSwitchTo);
        ibProblem = findViewById(R.id.ibProblem);
        llCode = findViewById(R.id.llCode);
        btnGetCode = findViewById(R.id.btnGetCode);
        etSetCode = findViewById(R.id.etSetCode);
        pbCommit = findViewById(R.id.pbCommit);
        pbCommit.setVisibility(View.GONE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llLoginAndRegister.getLayoutParams();
        params.width = (int) (display.getWidth()*0.8);
        llLoginAndRegister.setLayoutParams(params);
    }


    /**
     * 重写回退方法
     */
    @Override
    public void onBackPressed() {
        if (state == null){
            super.onBackPressed();
        }else{
            startActivity(new Intent(LoginAndRegisterActivity.this, MainActivity.class));
            LoginAndRegisterActivity.this.finish();
        }
    }
}
