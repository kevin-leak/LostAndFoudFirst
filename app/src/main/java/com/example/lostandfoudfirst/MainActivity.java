package com.example.lostandfoudfirst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;

import BottomFragment.FragmentAddInfo;
import BottomFragment.FragmentMain;
import MainTabFragment.FragmentPerson;

public class MainActivity extends AppCompatActivity implements  BottomNavigationBar.OnTabSelectedListener{

    //顶部导航栏部分
    private BottomNavigationBar bottomNavigationBar;
    private FragmentMain fragmentMain;
    private FragmentAddInfo fragmentAddInfo;
    private FragmentPerson fragmentPerson;
    //优化Fragment的加载
    private FragmentTransaction fragmentTransaction;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();

        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });
    }


    private void initView(){
        //TODO 还可设置顶部导航栏的效果
        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.bottom_bar);

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_info_in_blue_200_24dp,"发布")).setActiveColor(R.color.colorButton)
                .addItem(new BottomNavigationItem(R.drawable.ic_main_blue_200_24dp,"")).setActiveColor(R.color.colorButton)
                .addItem(new BottomNavigationItem(R.drawable.ic_personinfo_blue_200_24dp,"个人信息")).setActiveColor(R.color.colorButton)
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
    public void onTabSelected(int position) {
         if(fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fragmentManager= getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();

                //当前的fragment
                Fragment fragmetnfrom = fragmentManager.findFragmentById(R.id.layout_contain);
                //点击即将跳转的fragment
                Fragment fragmentto = fragments.get(position);
                //下面的不懂
                if (fragmentto.isAdded()) {//确认是否加入framgment
                    // 隐藏当前的fragment，显示下一个
                    fragmentTransaction.hide(fragmetnfrom).show(fragmentto);
                    if (position == 0){
                        /*当点击到发布页面我们将底边的切换按钮隐藏*/
                        bottomNavigationBar.setVisibility(View.GONE);
                    }
                } else {
                    // 隐藏当前的fragment，add下一个到Activity中
                    fragmentTransaction.hide(fragmetnfrom).add(R.id.layout_contain, fragmentto);
                    if (fragmentto.isHidden()) {
                        fragmentTransaction.show(fragmentto);
                        //Log.d("","被隐藏了");
                    }

                    /*当点击到发布页面我们将底边的切换按钮隐藏*/
                    if (position == 0){
                        bottomNavigationBar.setVisibility(View.GONE);
                    }
                }

            }
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
    @Override
    public void onTabUnselected(int position) {
        //这儿也要操作隐藏，否则Fragment会重叠
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = fragments.get(position);
                // 隐藏当前的fragment
                ft.hide(fragment).commitAllowingStateLoss();

                /*当点击到发布页面我们将底边的切换按钮隐藏*/
                if (position == 0){
                    bottomNavigationBar.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public void onTabReselected(int position) {

    }
}
