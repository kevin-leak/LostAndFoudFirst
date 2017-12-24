package BottomFragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.example.lostandfoudfirst.LoginAndRegisterActivity;
import com.example.lostandfoudfirst.MessageAndGoodsActivity;
import com.example.lostandfoudfirst.R;

/**
 *
 * Created by fulxtaw on 2017/12/9.
 */

public class FragmentPerson extends Fragment {

    private FragmentActivity personActivity;
    private Display display;
    private LinearLayout llPerson;
    private ImageView ivUserAvater;
    private TextView tvUserName;
    private TextView tvDetails;
    private TextView tvMessage;
    private TextView tvGoods;
    private TextView tvAddAccount;
    private TextView tvSetting;
    private Intent intent = new Intent();
    private RelativeLayout rlUserInfo;
    private ImageView ibChange;
    private TextView tvOut;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person,null);
        initView(view);

        return view;
    }

    private void initView(View view) {
        llPerson = view.findViewById(R.id.llPerson);
        personActivity = getActivity();
        WindowManager manager = personActivity.getWindowManager();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llPerson.getLayoutParams();
        display = manager.getDefaultDisplay();
        params.height = (int) (display.getHeight() * 0.38);
        llPerson.setLayoutParams(params);


        //用户注册信息
        rlUserInfo = view.findViewById(R.id.rlUserInfo);
        ivUserAvater = view.findViewById(R.id.ivUserAvater);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvDetails = view.findViewById(R.id.tvDetails);
        ibChange = view.findViewById(R.id.ibChange);

        tvMessage = view.findViewById(R.id.tvMessage);
        tvGoods = view.findViewById(R.id.tvGoods);
        tvAddAccount = view.findViewById(R.id.tvAddAccount);
        tvSetting = view.findViewById(R.id.tvSetting);
        tvOut = view.findViewById(R.id.tvOut);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        addAccount();

        toLogin();

        changeInfo();

        outLine();


        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personActivity.startActivity(new Intent(personActivity, MessageAndGoodsActivity.class));
            }
        });

        tvGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personActivity.startActivity(new Intent(personActivity, MessageAndGoodsActivity.class));
            }
        });
    }

    /**
     * 离线
     */
    private void outLine() {
        tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser.getCurrentUser().logOut();
                Toast.makeText(personActivity,"已经离线",Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 修改个人信息
     */
    private void changeInfo() {
        ibChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoDialog();
            }
        });
    }

    /**
     * 登入逻辑
     */
    private void toLogin() {
        rlUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AVUser.getCurrentUser() == null){
                    intent.putExtra(LoginAndRegisterActivity.SHOULD_DO,LoginAndRegisterActivity.LOGIN);
                    intent.setClass(personActivity,LoginAndRegisterActivity.class);
                    //要加上getActivity()获取的对象
                    personActivity.startActivityForResult(intent, LoginAndRegisterActivity.LOGIN_CODE);
                }else {
                    Toast.makeText(personActivity,"已经登入",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }

    /**
     * 展现个人信息
     */
    private void showInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(personActivity);
        // TODO: 2017/12/23 展现修改界面
    }

    /**
     * 添加账户
     */
    private void addAccount() {
        tvAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra(LoginAndRegisterActivity.SHOULD_DO,LoginAndRegisterActivity.REGISTER);
                intent.setClass(personActivity,LoginAndRegisterActivity.class);
                personActivity.startActivityForResult(intent, LoginAndRegisterActivity.REGISTER_CODE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: 2017/12/23 处理登入和注册的返回数据
        if (requestCode == LoginAndRegisterActivity.LOGIN_CODE){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
