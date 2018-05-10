package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.pengiriman;

public class PengirimanAdapter extends BaseAdapter {

	ArrayList<pengiriman> listDataPengiriman = new ArrayList<>();
	Context context;

	public PengirimanAdapter(Context context, ArrayList<pengiriman> listDataPengiriman) {
		this.context = context;
		this.listDataPengiriman = listDataPengiriman;
	}

	public void UpdatePengirimanAdapter(ArrayList<pengiriman> listDataPengiriman) {
		this.listDataPengiriman = listDataPengiriman;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listDataPengiriman.size();
	}

	@Override
	public Object getItem(int position) {
		return listDataPengiriman.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {

		public MyTextView tanggal_jam;
		public MyTextView keterangan;

		public int position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {

		final ViewHolder view;
		LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
		if(convertView==null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.pengiriman_item_list, null);

			view.tanggal_jam = (MyTextView) convertView.findViewById(R.id.tanggla_jam);
			view.keterangan  = (MyTextView) convertView.findViewById(R.id.keterangan);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		final pengiriman info = listDataPengiriman.get(position);
		view.position = listDataPengiriman.indexOf(info);

		view.tanggal_jam.setText(info.getTanggal()+" | "+info.getJam());
		view.keterangan.setText(info.getKeterangan());

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View convertView) {
			}
		});

		return convertView;
	}
}
