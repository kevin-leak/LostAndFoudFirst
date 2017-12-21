package BottomFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.example.lostandfoudfirst.Bean.LostOrFoundInfo;
import com.example.lostandfoudfirst.R;

import java.io.File;

import MainTabFragment.FragmentPerson;

/**
 * 建立丢失物品的信息
 * 需求分析
 * 1.先确定是否有注册账号
 * 2.实现各种逻辑
 * 3.传递数据给需要适配的recycleView
 * Created by fulxtaw on 2017/12/9.
 */

public class FragmentAddInfo extends Fragment {
    private ImageView ivGoodsImage;
    private EditText etGoodsName;
    private EditText etGoodsPlace;
    private EditText etGoodsTime;
    private EditText etGoodsInfo;
    private Activity infoActivity;
    private Button ibCommit;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        infoActivity = getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add,container,false);
        return view;
    }

    private void initView(View view){
        ivGoodsImage = view.findViewById(R.id.ivGoodsImage);
        etGoodsName = view.findViewById(R.id.etGoodsName);
        etGoodsPlace = view.findViewById(R.id.etGoodsPlace);
        etGoodsTime = view.findViewById(R.id.etGoodsTime);
        etGoodsInfo = view.findViewById(R.id.etGoodsInfo);
        ibCommit = view.findViewById(R.id.ibCommit);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //假如没有登入先登入
        if (isLogin()){
            skipToLogin();
        }else{
            ibCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commitToInternet();
                }
            });
        }

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
        goods.put("info",etGoodsInfo.getText().toString());
        goods.put("image",new AVFile(etGoodsName.getText().toString()+"image",null));
        goods.put("owner",user);
        goods.saveInBackground();

    }

    /**
     * 跳转到登入界面
     */
    private void skipToLogin() {
        Intent intent = new Intent();
        intent.putExtra("FragmentAddInfo","Login");
        intent.setClass(infoActivity, FragmentPerson.class);
        startActivity(intent);
    }


    /**
     * @return 返回没有登入为真
     */
    public boolean isLogin() {
        return AVUser.getCurrentUser() == null;
    }
}
