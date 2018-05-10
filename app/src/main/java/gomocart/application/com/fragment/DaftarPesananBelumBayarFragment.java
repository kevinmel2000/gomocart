package gomocart.application.com.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.ExpandableHeightListView;

public class DaftarPesananBelumBayarFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;

    public static ExpandableHeightListView listViewOrder;
    public static LinearLayout retry;
    public static MyTextView btnReload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_taborderlist, container, false);

        listViewOrder = (ExpandableHeightListView) rootView.findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
        btnReload = (MyTextView) rootView.findViewById(R.id.btnReload);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((MainActivity) getActivity()).loadOrderlistBelumBayar();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                ((MainActivity) getActivity()).loadOrderlistBelumBayar();
            }
        });

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).initialOrderlistBelumBayar();
    }
}
