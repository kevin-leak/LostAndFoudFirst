package com.example.lostandfoudfirst.myView.fragment.BottomFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostandfoudfirst.R;

import com.example.lostandfoudfirst.myView.fragment.BottomFragment.MainTabFragment.MyFragmentPagerAdapter;

/**
 * Created by fulxtaw on 2017/12/9.
 */

public class FragmentMain extends Fragment {

    private TextView textview;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private TabLayout tabLayout;
    private TabLayout.Tab tabone,tabtwo,tabthree,tabfour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        setHasOptionsMenu(true);
        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


//        //指定Tab的位置
//        tabone = tabLayout.getTabAt(0);
//        tabtwo = tabLayout.getTabAt(1);
//        tabthree = tabLayout.getTabAt(2);
//        tabfour = tabLayout.getTabAt(3);
        //默认
        tabLayout.getTabAt(2).select();

        /*设置tab的监听事件*/
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).select();
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Log.d("MainFragment","______________onViewCreated_______________");

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itCharger){
            // TODO: 2018/1/1 管理
        }else {
            // TODO: 2018/1/1 退出
        }
        return super.onOptionsItemSelected(item);
    }

}
