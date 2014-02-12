package dk.greenticket.greenticket;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by lalan on 12/02/14.
 */
public class GTTicketPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public GTTicketPageAdapter(FragmentManager fragmentManager, List<Fragment> fragments){
        super(fragmentManager);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
