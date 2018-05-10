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

public class NotifikasiFragment extends Fragment {

	SwipeRefreshLayout swipeRefreshLayout;

	public static ListView listview;
	public static LinearLayout retry;
	public static Button btnReload;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_notifikasi, container, false);

		swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		listview = (ListView) rootView.findViewById(R.id.listview);
		retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload = (Button) rootView.findViewById(R.id.btnReload);
		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).loadDataNotifikasi(false);

			}
		});

		listview.setOnScrollListener(new EndlessScrollListener() {

			@Override
	        public boolean onLoadMore(int page, int totalItemsCount) {
				
				((MainActivity) getActivity()).loadDataNotifikasi(false);
				
				return true;
			}
			    });

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(false);
				((MainActivity) getActivity()).loadDataNotifikasi(true);
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		((MainActivity) getActivity()).loadDataNotifikasi(true);
	}
}
