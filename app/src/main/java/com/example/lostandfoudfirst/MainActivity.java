package com.example.lostandfoudfirst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

import BottomFragment.FragmentAddInfo;
import BottomFragment.FragmentMain;
import BottomFragment.FragmentPerson;

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
        initview1();
    }
    private void initview1(){
        //TODO 还可设置顶部导航栏的效果
        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.bottom_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_info_in_blue_200_24dp,"发布")).setActiveColor(R.color.colorbuttom)
                .addItem(new BottomNavigationItem(R.drawable.ic_main_blue_200_24dp,"")).setActiveColor(R.color.colorbuttom)
                .addItem(new BottomNavigationItem(R.drawable.ic_personinfo_blue_200_24dp,"个人信息")).setActiveColor(R.color.colorbuttom)
                .setFirstSelectedPosition(1)
                .initialise();


        setDefaultFragment();
        fragments= getFragments();

        //使Fragment能加载
        bottomNavigationBar.setTabSelectedListener(this);
    }
    private void setDefaultFragment() {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentMain= new FragmentMain();
        fragmentTransaction.replace(R.id.layout_contain,fragmentMain)
                .commitAllowingStateLoss();
    }

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
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fragmentManager= getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();

                //当前的fragment
                Fragment fragmetnfrom = fragmentManager.findFragmentById(R.id.layout_contain);
                //点击即将跳转的fragment
                Fragment fragmentto = fragments.get(position);
                //下面的不懂
                if (fragmentto.isAdded()) {
                    // 隐藏当前的fragment，显示下一个
                    fragmentTransaction.hide(fragmetnfrom).show(fragmentto);
                } else {
                    // 隐藏当前的fragment，add下一个到Activity中
                    fragmentTransaction.hide(fragmetnfrom).add(R.id.layout_contain, fragmentto);
                    if (fragmentto.isHidden()) {
                        fragmentTransaction.show(fragmentto);
                        //Log.d("","被隐藏了");
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
            }
        }

    }

    @Override
    public void onTabReselected(int position) {

    }
}
