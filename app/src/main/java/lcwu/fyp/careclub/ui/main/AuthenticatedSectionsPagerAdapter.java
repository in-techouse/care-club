package lcwu.fyp.careclub.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.fragment.My_Donations;
import lcwu.fyp.careclub.fragment.My_Products;
import lcwu.fyp.careclub.fragment.My_Profile;
import lcwu.fyp.careclub.fragment.Ngos;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class AuthenticatedSectionsPagerAdapter extends FragmentPagerAdapter {

    My_Profile profile=new My_Profile();
    My_Donations donation=new My_Donations();
    My_Products product=new My_Products();
    Ngos ngo=new Ngos();

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.NGOs, R.string.My_Products,R.string.My_Donations,R.string.My_profile};
    private final Context mContext;

    public AuthenticatedSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}