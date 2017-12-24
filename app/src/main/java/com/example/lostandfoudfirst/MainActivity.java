package com.example.lostandfoudfirst;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;

import BottomFragment.FragmentAddInfo;
import BottomFragment.FragmentMain;
import BottomFragment.FragmentPerson;

public class MainActivity extends AppCompatActivity implements  BottomNavigationBar.OnTabSelectedListener{

    //顶部导航栏部分
    public static BottomNavigationBar bottomNavigationBar;
    private FragmentMain fragmentMain;
    private FragmentAddInfo fragmentAddInfo;
    private FragmentPerson fragmentPerson;
    //优化Fragment的加载
    private FragmentTransaction fragmentTransaction;
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView(){
        //TODO 还可设置顶部导航栏的效果
        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.bottom_bar);

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_info_in_blue_200_24dp,"发布")).setActiveColor(R.color.colorWhite)
                .addItem(new BottomNavigationItem(R.drawable.ic_main_blue_200_24dp,"")).setActiveColor(R.color.colorWhite)
                .addItem(new BottomNavigationItem(R.drawable.ic_personinfo_blue_200_24dp,"个人信息")).setActiveColor(R.color.colorWhite)
                .setFirstSelectedPosition(1)
                .initialise();


        setDefaultFragment();
        fragments= getFragments();

        //使Fragment能加载
        bottomNavigationBar.setTabSelectedListener(this);
    }

    /**
     * 设置初始的framgment，在这里设置为fragmentMain
     */
    private void setDefaultFragment() {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentMain= new FragmentMain();
        fragmentTransaction.replace(R.id.layout_contain,fragmentMain)
                .commitAllowingStateLoss();
    }

    /**
     * 用于对framgment的适配
     * @return 返回底部framgment的list集合
     */
    private ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> fragments= new ArrayList<Fragment>();

        //顺序,这个顺序和显示的顺序要保持一致
        fragments.add(new FragmentAddInfo());
        fragments.add(fragmentMain);
        fragments.add(new FragmentPerson());
        return fragments;

    }


    @Override
    public void onTabSelected(int newPosition) {
         if(fragments != null) {
            if (newPosition < fragments.size()) {

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                //当前的fragment
                Fragment fragmentFrom = fragmentManager.findFragmentById(R.id.layout_contain);
                //点击即将跳转的fragment
                Fragment fragmentTo = fragments.get(newPosition);

                //处理此时需要登入才能发消息的逻辑,启动登入界面
                if (newPosition == 0 && AVUser.getCurrentUser() == null){
                    Intent intent = new Intent();
                    intent.putExtra(LoginAndRegisterActivity.INFO_SHOULD_DO,LoginAndRegisterActivity.INFO_LOGIN );
                    intent.setClass(this,LoginAndRegisterActivity.class);
                    startActivity(intent);
                }

                //确定点击的是不是同一个
                if (fragmentTo.isAdded()) {//确认是否已经加入framgment
                    // 隐藏当前的fragment，再显示
                    fragmentTransaction.hide(fragmentFrom).show(fragmentTo);

                } else {
                    // 隐藏当前的fragment，add下一个到fragment中
                    fragmentTransaction.hide(fragmentFrom).add(R.id.layout_contain, fragmentTo);
                    if (fragmentTo.isHidden()) {
                        fragmentTransaction.show(fragmentTo);
                        //Log.d("","被隐藏了");
                    }
                }
            }
            //要设置动画对象，来动态化的改变跳转页面
             //修改为不可存储状态的fragment，更安全，并有助于发布info时成功后的清空
            fragmentTransaction.commit();
            //这个方法是调用是的底部栏可以动态化选择
             bottomNavigationBar.selectTab(newPosition);
        }
    }


    /**
     * @param position 指的是被选择的老的postion
     */
    @Override
    public void onTabUnselected(int position) {
        //这儿也要操作隐藏，否则Fragment会重叠
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = fragments.get(position);
                // 隐藏当前的fragment
                ft.hide(fragment).commitAllowingStateLoss();

            }
        }
    }

    /**
     * @param position 新的position
     */
    @Override
    public void onTabReselected(int position) {

    }

}
