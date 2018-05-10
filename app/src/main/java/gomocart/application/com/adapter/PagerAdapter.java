package gomocart.application.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import gomocart.application.com.fragment.DaftarPesananBatalFragment;
import gomocart.application.com.fragment.DaftarPesananBelumBayarFragment;
import gomocart.application.com.fragment.DaftarPesananSedangKirimFragment;
import gomocart.application.com.fragment.DaftarPesananSedangProsesFragment;
import gomocart.application.com.fragment.DaftarPesananSelesaiFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new DaftarPesananBelumBayarFragment();
            case 1:
                return new DaftarPesananSedangProsesFragment();
            case 2:
                return new DaftarPesananSedangKirimFragment();
            case 3:
                return new DaftarPesananSelesaiFragment();
            case 4:
                return new DaftarPesananBatalFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}