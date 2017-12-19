package BottomFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostandfoudfirst.R;

import MainTabFragment.MyFragmentPagerAdapter;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //指定Tab的位置
        tabone = tabLayout.getTabAt(0);
        tabtwo = tabLayout.getTabAt(1);
        tabthree = tabLayout.getTabAt(2);
        tabfour = tabLayout.getTabAt(3);

        Log.d("MainFragment","______________onViewCreated_______________");

        super.onViewCreated(view, savedInstanceState);

    }
}
