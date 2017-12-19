package MainTabFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fulxtaw on 2017/12/8.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] myTitles = new String[]{"所有信息", "失物", "招领", "贵重物品"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        //此处根据不同的position返回不同的Fragment
        if (position == 1) {
            return new FragmentLost();
        } else if (position == 2) {
            return new FragmentFound();
        } else if (position == 3) {
            return new FragmentImportant();
        } else {
            return new FragmentAll();
        }
    }

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
}