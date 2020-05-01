package lcwu.fyp.careclub.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.fragment.MyDonations;
import lcwu.fyp.careclub.fragment.MyProducts;
import lcwu.fyp.careclub.fragment.MyProfile;
import lcwu.fyp.careclub.fragment.Ngos;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class AuthenticatedSectionsPagerAdapter extends FragmentPagerAdapter {

    MyProfile profile = new MyProfile();
    MyDonations donation = new MyDonations();
    MyProducts product = new MyProducts();
    Ngos ngo = new Ngos();

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.NGOs, R.string.My_Products, R.string.My_Donations, R.string.My_profile};
    private final Context mContext;

    public AuthenticatedSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0: {
                return ngo;

            }
            case 1: {
                return product;
            }
            case 2: {
                return donation;
            }
            case 3: {
                return profile;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
}