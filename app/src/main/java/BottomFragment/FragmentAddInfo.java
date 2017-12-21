package BottomFragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.lostandfoudfirst.R;

import java.util.Timer;
import java.util.TimerTask;

import MUtils.DrawableUtils;
import MainTabFragment.FragmentAll;

/**
 * 建立丢失物品的信息
 * 需求分析
 * 1.先确定是否有注册账号
 * 2.实现各种逻辑
 * 3.传递数据给需要适配的recycleView
 * Created by fulxtaw on 2017/12/9.
 */

public class FragmentAddInfo extends Fragment {
    private static final int IMAGE_CAPTURE = 0;
    private static final int GET_CONTENT = 1;
    private ImageView ivGoodsImage;
    private EditText etGoodsName;
    private EditText etGoodsPlace;
    private EditText etGoodsTime;
    private EditText etGoodsInfo;
    private Activity infoActivity;
    private Button ibCommit;
    private SwitchCompat swhIsLost;
    private TextView tvSwitchTip;
    private FragmentManager manager;
    private ImageButton ibInfoImageAdd;
    private LayoutInflater inflater;
    private View selectPicView;
    private AlertDialog dialog;
    private TextView tvCamera;
    private TextView tvAlbum;

    /**
     * 定义跳转意图
     */
    final Intent[] intents = new Intent[3];
    private byte[] imageData ;
    private WindowManager windowManager;
    private Display display;

// TODO: 2017/12/21 设置返回键

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        infoActivity = getActivity();
        getDisplay();
        View fragmentAddInfoView = inflater.inflate(R.layout.fragment_add,container,false);
        return fragmentAddInfoView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = getFragmentManager();

        initView(view);
        //假如没有登入先登入
        if (isLogin()){
            skipToLogin();
        }else {

            ibCommit.setOnClickListener(new View.OnClickListener() {
                boolean count = true;
                @Override
                public void onClick(View view) {
                    if (count){
                        count = false;
                        Toast.makeText(infoActivity,"再次点击确认无误",Toast.LENGTH_SHORT).show();
                    }else {
                        count = true;
                        commitToInternet();
                    }
                }
            });

            setImageAddAnimator();

            ibInfoImageAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
            //建立swtich的小tip
            setTips();

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //取选择照片的返回数据
        switch (requestCode){
            case IMAGE_CAPTURE:
                if(data != null){
                    //只会返回bitmap
                    Bundle bundle = data.getExtras();
                    //取图片
                    Bitmap bitmap = bundle.getParcelable("data");
                    imageData = DrawableUtils.bitmapToByte(bitmap);
                    dialog.dismiss();
                    ivGoodsImage.setImageBitmap(bitmap);
                    ibInfoImageAdd.setVisibility(View.GONE);
                } else {
                    return;
                }
                break;
            case GET_CONTENT:
                if (data != null){
                    Uri uri = data.getData();
                    Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(uri));
                    imageData = DrawableUtils.bitmapToByte(bitmap);
                    dialog.dismiss();
                    ivGoodsImage.setImageURI(uri);
                }
                break;

        }
    }

    /**
     * 获取到屏幕信息进行适配
     */
    private void getDisplay() {
        if (infoActivity != null){
            windowManager = infoActivity.getWindowManager();
            display = windowManager.getDefaultDisplay();
        }

    }

    private void initView(View view){
        ibInfoImageAdd = view.findViewById(R.id.ibInfoImageAdd);
        ivGoodsImage = view.findViewById(R.id.ivGoodsImage);
        etGoodsName = view.findViewById(R.id.etGoodsName);
        etGoodsPlace = view.findViewById(R.id.etGoodsPlace);
        etGoodsTime = view.findViewById(R.id.etGoodsTime);
        etGoodsInfo = view.findViewById(R.id.etGoodsInfo);
        ibCommit = view.findViewById(R.id.ibCommit);
        tvSwitchTip = view.findViewById(R.id.tvSwitchTip);
        swhIsLost = view.findViewById(R.id.swhIsLost);
        swhIsLost.setChecked(false);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivGoodsImage.getLayoutParams();
        params.height = (int) (display.getHeight() * 0.25);
        ivGoodsImage.setLayoutParams(params);

        setVisibilityTip();
    }

    /**
     * 设置tip的是否出现
     */
    private void setVisibilityTip() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvSwitchTip.setVisibility(View.GONE);
            }
        },10000);
    }


    /**
     * 将dialog展现出来
     *
     */
    private void showDialog() {
        initDialogView();
        initEventListen();
    }

    /**
     * 设置对dialog的监听事件
     */
    private void initEventListen() {


        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intents[0] = new Intent();
                intents[0].setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intents[0],IMAGE_CAPTURE);
            }
        });

        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intents[1] = new Intent();
                intents[1].setType("image/*");
                intents[1].setAction(intents[1].ACTION_GET_CONTENT);
                startActivityForResult(intents[1],GET_CONTENT);
            }
        });

    }


    /**
     * 初始化选取照片的方法的dialog的view
     */
    private void initDialogView() {
        selectPicView = inflater.inflate(R.layout.dailog_select_pic,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(infoActivity);
        dialog = builder.create();
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setContentView(selectPicView);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (display.getWidth() * 0.9);
        dialogWindow.setAttributes(lp);
        tvCamera = selectPicView.findViewById(R.id.tvCamera);
        tvAlbum = selectPicView.findViewById(R.id.tvAlbum);
    }

    /**
     * 社会图片循环的动画
     */
    private void setImageAddAnimator() {
        TranslateAnimation animator =new TranslateAnimation(-35,35,0,0);
        //设置持续时间
        animator.setDuration(1500);
        //设置重复次数
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //设置方向直行
        animator.setRepeatMode(Animation.REVERSE);
        ibInfoImageAdd.startAnimation(animator);
    }

    /**
     * 设置切换的tips，一开始为lost
     */
    private void setTips() {
        swhIsLost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tvSwitchTip.setText("found");
                    tvSwitchTip.setVisibility(View.VISIBLE);
                    setVisibilityTip();
                }else {
                    tvSwitchTip.setText("lost");
                    tvSwitchTip.setVisibility(View.VISIBLE);
                    setVisibilityTip();
                }
            }
        });
    }

    /**
     * 将信息数据提交到网络
     */
    private void commitToInternet() {
        AVUser user = AVUser.getCurrentUser();
        AVObject goods = new AVObject("Goods");
        goods.put("name",etGoodsName.getText().toString());
        goods.put("place",etGoodsPlace.getText().toString());
        goods.put("time",etGoodsTime.getText().toString());
        if (imageData != null){
            goods.put("image",new AVFile(etGoodsName.getText().toString()+"image",imageData));
        }else {
            goods.put("File",null);
        }
        goods.put("isLost",swhIsLost.isChecked());
        goods.put("owner",user);
        goods.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                skipToMain();
                Toast.makeText(infoActivity,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * 跳转到首页
     */
    private void skipToMain() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(FragmentAddInfo.this).add(R.id.layout_contain,new FragmentAll());
        transaction.commitAllowingStateLoss();
    }

    /**
     * 跳转到登入界面
     */
    private void skipToLogin() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(FragmentAddInfo.this).add(R.id.layout_contain,new FragmentPerson());
        transaction.commitAllowingStateLoss();
    }


    /**
     * @return 返回没有登入为真
     */
    public boolean isLogin() {
        return AVUser.getCurrentUser() == null;
    }

}
