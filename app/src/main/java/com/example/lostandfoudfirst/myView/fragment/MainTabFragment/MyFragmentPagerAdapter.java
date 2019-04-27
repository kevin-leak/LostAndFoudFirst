package com.example.lostandfoudfirst.myView.fragment.BottomFragment.MainTabFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fulxtaw on 2017/12/8.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    private FragmentLost lost ;
    private FragmentFound found;
    private FragmentAll all;
    private FragmentImportant important;

    private String[] myTitles = new String[]{ "Lost", "Found","All", "Valuable"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }




    @Override
    public Fragment getItem(int position) {
        //此处根据不同的position返回不同的Fragment
        if (position == 0) {
            return getLost();
        } else if (position == 1) {
            return getFound();
        } else if (position == 2) {
            return getAll();
        } else {
            return getImportant();
        }
    }


//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//
//        return view == object;
//    }

    @Override
    public int getCount() {
        //此处返回Tab的数目
        return myTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //此处返回每个Tab的title
        return myTitles[position];
    }

    public FragmentLost getLost() {
        if (lost == null){
            lost =  new FragmentLost();
        }
        return lost;
    }

    public FragmentFound getFound() {
        if (found == null){
            found = new FragmentFound();
        }
        return found;
    }

    public FragmentAll getAll() {
        if (all == null){
            all = new FragmentAll();
        }
        return all;
    }

    public FragmentImportant getImportant() {
        if (important == null){
            important = new FragmentImportant();
        }
        return important;
    }
}