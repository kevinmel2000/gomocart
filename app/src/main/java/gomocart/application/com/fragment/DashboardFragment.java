package gomocart.application.com.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import gomocart.application.com.adapter.PagerKategoriAdapter;
import gomocart.application.com.adapter.RecyclerViewKategoriAdapter;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.ChildAnimationExample;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.SliderLayout;
import gomocart.application.com.libs.StickyScrollView;
import gomocart.application.com.model.banner;
import gomocart.application.com.model.produk_kategori;

public class DashboardFragment extends Fragment {

    SwipeRefreshLayout swipe_container;

	static StickyScrollView scroll;
	static LinearLayout retry;
	static Button btnReload;

	static SliderLayout mBannerSlider;
	static RecyclerView rv_category;

	static TabLayout tabLayout;
	static ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        swipe_container  = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);

        scroll          = (StickyScrollView) rootView.findViewById(R.id.scroll);
		retry           = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload       = (Button) rootView.findViewById(R.id.btnReload);

		mBannerSlider     = (SliderLayout) rootView.findViewById(R.id.slider);
		rv_category     = (RecyclerView) rootView.findViewById(R.id.rv_categories);

		tabLayout       = (TabLayout) rootView.findViewById(R.id.tab_layout);
		viewPager       = (ViewPager) rootView.findViewById(R.id.pager);

        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipe_container.setRefreshing(false);
				loadDashboard(true);
            }
        });


        btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loadDashboard(true);
			}
		});

		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				final int position = tab.getPosition();
				viewPager.setCurrentItem(position, true);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		mBannerSlider.setPresetTransformer(SliderLayout.Transformer.Default);
		mBannerSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mBannerSlider.setCustomAnimation(new ChildAnimationExample());
		mBannerSlider.setDuration(4000);
		mBannerSlider.addOnPageChangeListener((MainActivity) getActivity());

		rv_category.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

		ViewTreeObserver viewTreeObserver = scroll.getViewTreeObserver();
		viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@Override
				public void onGlobalLayout() {
					scroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					ViewGroup.LayoutParams params = viewPager.getLayoutParams();
					params.height = scroll.getMeasuredHeight()-tabLayout.getMeasuredHeight();
					params.width = viewPager.getMeasuredWidth();
					viewPager.setLayoutParams(params);
				}
			});


		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		loadDashboard(false);
	}

	public void loadDashboard(boolean reset) {
		retry.setVisibility(View.GONE);
		scroll.setVisibility(View.INVISIBLE);
		((MainActivity) getActivity()).loadDataDashboard(reset);
	}

	public static void resultLoadDashboard(Context context, boolean success) { //, ArrayList<banner> list_banner, ArrayList<kategori> list_kategori, ArrayList<produk_kategori> list_tab_kategori) {

		if(!success) {
			retry.setVisibility(View.VISIBLE);
		} else {
			//banner
			mBannerSlider.removeAllSliders();
			for (banner data_banner : MainActivity.dashboard_list_banner) {

				TextSliderView textSliderView = new TextSliderView(context);
				textSliderView
						.image(CommonUtilities.SERVER_URL+"/uploads/banner/"+data_banner.getUrl_image())
						.setScaleType(BaseSliderView.ScaleType.CenterCrop);

				mBannerSlider.addSlider(textSliderView);
			}

			//kategori
			rv_category.setAdapter(new RecyclerViewKategoriAdapter(context, MainActivity.dashboard_list_kategori));

			//tab kategori
			tabLayout.removeAllTabs();
			tabLayout.setTabMode(MainActivity.dashboard_list_tab_kategori.size()<5?TabLayout.MODE_FIXED:TabLayout.MODE_SCROLLABLE);
			for (produk_kategori tab_kategori: MainActivity.dashboard_list_tab_kategori) {
				tabLayout.addTab(tabLayout.newTab().setText(tab_kategori.getNama()));
			}
			PagerKategoriAdapter pagerKategoriAdapter = new PagerKategoriAdapter(context, ((MainActivity) context).getSupportFragmentManager(), MainActivity.dashboard_list_tab_kategori);
			viewPager.setAdapter(pagerKategoriAdapter);

			scroll.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onStop() {
		mBannerSlider.stopAutoCycle();
		super.onStop();
	}
}
