package BottomFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lostandfoudfirst.R;

/**
 * 建立丢失物品的信息
 * 需求分析
 * 1.先确定是否有注册账号
 * 2.实现各种逻辑
 * 3.传递数据给需要适配的recycleView
 * Created by fulxtaw on 2017/12/9.
 */

public class FragmentAddInfo extends Fragment {
    private ImageView etGoodsImage;
    private EditText etGoodsName;
    private EditText etLostPlace;
    private EditText etLostTime;
    private EditText etLostInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add,container,false);
        return view;
    }

    private void initView(View view){
        etGoodsImage = view.findViewById(R.id.etGoodsImage);
        etGoodsName = view.findViewById(R.id.etGoodsName);
        etLostPlace = view.findViewById(R.id.etLostPlace);
        etLostTime = view.findViewById(R.id.etLostTime);
        etLostInfo = view.findViewById(R.id.etLostInfo);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        Log.d("AddFragment","______________CreatedAddInfo_______________");

        //

    }


}
