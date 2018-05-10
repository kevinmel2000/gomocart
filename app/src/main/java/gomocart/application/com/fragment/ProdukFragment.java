package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.EndlessScrollListener;

public class ProdukFragment extends Fragment {

	public static MyTextView filter;
	public static MyTextView sortby;
	public static GridView gridview;
	public static ListView listview;

	public static ImageView changeview;

	public static SwipeRefreshLayout swipeRefreshLayout_gridview;
	public static SwipeRefreshLayout swipeRefreshLayout_listview;

	public static LinearLayout retry;
	public static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_produk, container, false);

		changeview = (ImageView) rootView.findViewById(R.id.changeview);

		gridview = (GridView) rootView.findViewById(R.id.gridview);
		gridview.setAdapter(MainActivity.produkgridAdapter);

		listview = (ListView) rootView.findViewById(R.id.listview);
		listview.setAdapter(MainActivity.produklistAdapter);

		filter = (MyTextView) rootView.findViewById(R.id.filter);
		sortby = (MyTextView) rootView.findViewById(R.id.sortby);

		swipeRefreshLayout_gridview = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_gridview);
		swipeRefreshLayout_listview = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_listview);

		retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload = (Button) rootView.findViewById(R.id.btnReload);
		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).loadDataProduk();
			}
		});

		swipeRefreshLayout_gridview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipeRefreshLayout_gridview.setRefreshing(false);
				((MainActivity) getActivity()).RefreshDataProduk();
			}
		});

		swipeRefreshLayout_listview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipeRefreshLayout_listview.setRefreshing(false);
				((MainActivity) getActivity()).RefreshDataProduk();
			}
		});

		filter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)	getActivity()).openFilterProduk();
			}
		});

		sortby.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)	getActivity()).openDialogSortBy();
			}
		});

		gridview.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public boolean onLoadMore(int page, int totalItemsCount) {

				((MainActivity) getActivity()).loadDataProduk();

				return true;
			}
		});

		listview.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public boolean onLoadMore(int page, int totalItemsCount) {

				((MainActivity) getActivity()).loadDataProduk();

				return true;
			}
		});

		changeview.setImageResource(MainActivity.show_produk_in.equalsIgnoreCase("grid")?R.drawable.listview:R.drawable.gridview);
		swipeRefreshLayout_gridview.setVisibility(MainActivity.show_produk_in.equalsIgnoreCase("grid")?View.VISIBLE:View.GONE);
		swipeRefreshLayout_listview.setVisibility(MainActivity.show_produk_in.equalsIgnoreCase("list")?View.VISIBLE:View.GONE);

		changeview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(MainActivity.show_produk_in.equalsIgnoreCase("grid")) {
					MainActivity.show_produk_in = "list";
					changeview.setImageResource(R.drawable.gridview);
					swipeRefreshLayout_gridview.setVisibility(View.GONE);
					swipeRefreshLayout_listview.setVisibility(View.VISIBLE);
				} else {
					MainActivity.show_produk_in = "grid";
					changeview.setImageResource(R.drawable.listview);
					swipeRefreshLayout_gridview.setVisibility(View.VISIBLE);
					swipeRefreshLayout_listview.setVisibility(View.GONE);
				}
			}
		});

		((MainActivity) getActivity()).loadDataProduk();
		return rootView;
	}
}
