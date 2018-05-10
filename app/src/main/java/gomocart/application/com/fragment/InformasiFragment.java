package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.EndlessScrollListener;

public class InformasiFragment extends Fragment {

	SwipeRefreshLayout swipeRefreshLayout;

	public static ListView listview;
	public static LinearLayout retry;
	public static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_informasi, container, false);

		swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		listview = (ListView) rootView.findViewById(R.id.listview);
		retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload = (Button) rootView.findViewById(R.id.btnReload);
		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				((MainActivity) getActivity()).loadDataInformasi(false);
			}
		});

		listview.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public boolean onLoadMore(int page, int totalItemsCount) {

				((MainActivity) getActivity()).loadDataInformasi(false);

				return true;
			}
		});

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(false);
				((MainActivity) getActivity()).loadDataInformasi(true);
			}
		});
		

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((MainActivity) getActivity()).loadDataInformasi(true);
	}
}
