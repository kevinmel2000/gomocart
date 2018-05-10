package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class KategoriFragment extends Fragment {

	SwipeRefreshLayout swipe_container;

	public static GridView gridViewKategori;
    public static LinearLayout retry;
	public static Button btnReload;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_kategori, container, false);

		swipe_container  = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        gridViewKategori = (GridView) rootView.findViewById(R.id.gridViewKategori);
		retry            = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload        = (Button) rootView.findViewById(R.id.btnReload);

		swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipe_container.setRefreshing(false);
				((MainActivity) getActivity()).loadKategoriProduk();
			}
		});

        btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).loadKategoriProduk();

			}
		});

        return rootView;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((MainActivity) getActivity()).loadKategoriProduk();
	}
}
