package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gomocart.application.com.adapter.PagerAdapter;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class DaftarPesananFragment extends Fragment {

	TabLayout tabLayout;
	ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_daftar_pesanan, container, false);

		tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
		viewPager = (ViewPager) rootView.findViewById(R.id.pager);

		tabLayout.addTab(tabLayout.newTab().setText("BELUM BAYAR"));
		tabLayout.addTab(tabLayout.newTab().setText("SEDANG PROSES"));
		tabLayout.addTab(tabLayout.newTab().setText("SEDANG KIRIM"));
		tabLayout.addTab(tabLayout.newTab().setText("SELESAI"));
		tabLayout.addTab(tabLayout.newTab().setText("BATAL"));

		PagerAdapter pagerAdapter = new PagerAdapter(((MainActivity) getActivity()).getSupportFragmentManager(), 5);
		viewPager.setAdapter(pagerAdapter);
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
