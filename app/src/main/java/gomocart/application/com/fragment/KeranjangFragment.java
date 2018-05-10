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

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class KeranjangFragment extends Fragment {

	SwipeRefreshLayout swipe_container;

	public static LinearLayout linear_cart;
	public static ListView listview;
	public static MyTextView checkout;
	public static MyTextView edit_qty;
	public static MyTextView edit_jumlah;

	public static LinearLayout retry;
	public static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);

		swipe_container = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		linear_cart     = (LinearLayout) rootView.findViewById(R.id.linear_cart);
		listview        = (ListView) rootView.findViewById(R.id.listview);
		edit_qty        = (MyTextView) rootView.findViewById(R.id.edit_qty);
		edit_jumlah     = (MyTextView) rootView.findViewById(R.id.edit_jumlah);
		checkout        = (MyTextView) rootView.findViewById(R.id.checkout);
		btnReload       = (Button) rootView.findViewById(R.id.btnReload);
		retry           = (LinearLayout) rootView.findViewById(R.id.loadMask);

		swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipe_container.setRefreshing(false);
				((MainActivity) getActivity()).loadCartlist();
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).loadCartlist();
			}
		});

		checkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).cekOrder();
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((MainActivity) getActivity()).loadCartlist();
	}
}
