package com.riceplant.capstoneproject.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.riceplant.capstoneproject.fragments.BestGameFragment;
import com.riceplant.capstoneproject.fragments.MyLibraryFragment;
import com.riceplant.capstoneproject.fragments.PopularGamesFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                PopularGamesFragment tab1 = new PopularGamesFragment();
                return tab1;
            case 1:
                MyLibraryFragment tab2 = new MyLibraryFragment();
                return tab2;
            case 2:
                BestGameFragment tab3 = new BestGameFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overridden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
