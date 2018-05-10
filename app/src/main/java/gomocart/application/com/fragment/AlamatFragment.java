package gomocart.application.com.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;

import customfonts.MyEditText;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class AlamatFragment extends Fragment {

	public static ListView listview;
	public static ImageView search, sortby, tambah;

	public static LinearLayout linear_utama;
	public static RelativeLayout linear_search;

	public static MyEditText edit_search;
	public static ImageButton btn_close;

	public static SwipeRefreshLayout swipeRefreshLayout;
	public static ProgressBar loading;
	public static LinearLayout retry;
	public static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_alamatlist, container, false);

		search = (ImageView) rootView.findViewById(R.id.search);
		sortby = (ImageView) rootView.findViewById(R.id.orderby);
		tambah = (ImageView) rootView.findViewById(R.id.tambah);
		listview = (ListView) rootView.findViewById(R.id.listview);
		swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		loading = (ProgressBar) rootView.findViewById(R.id.pgbarLoading);
		retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload = (Button) rootView.findViewById(R.id.btnReload);

		linear_utama  = (LinearLayout) rootView.findViewById(R.id.linear_utama);
		linear_search = (RelativeLayout) rootView.findViewById(R.id.cardview_search);
		edit_search   = (MyEditText) rootView.findViewById(R.id.searchtext);
		btn_close     = (ImageButton) rootView.findViewById(R.id.btn_close);


		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(false);
				((MainActivity) getActivity()).loadAlamatlist();
			}
		});

		sortby.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)	getActivity()).openDialogSortByAlamat();
			}
		});

		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				linear_search.setVisibility(View.VISIBLE);
				linear_utama.setVisibility(View.GONE);
				edit_search.requestFocus();

				View view = getActivity().getCurrentFocus();
				if (view != null) {
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				}
			}
		});

		btn_close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				edit_search.setText("");
				linear_search.setVisibility(View.GONE);
				linear_utama.setVisibility(View.VISIBLE);

				View view = getActivity().getCurrentFocus();
				if (view != null) {
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}

				((MainActivity) getActivity()).showListAlamat();
			}
		});

		edit_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length() != 0) {
					((MainActivity) getActivity()).showListAlamat();
				}
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).loadAlamatlist();
			}
		});
		listview.setAdapter(MainActivity.alamatAdapter);

		tambah.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).addNewAlamat();
			}
		});

		((MainActivity) getActivity()).loadAlamatlist();
		return rootView;
	}
}
