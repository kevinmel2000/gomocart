package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.grosir;

public class ListGrosirAdapter extends BaseAdapter {

	ArrayList<grosir> listgrosir = new ArrayList<>();
	Context context;

	public ListGrosirAdapter(Context context, ArrayList<grosir> listgrosir) {
		this.context = context;
		this.listgrosir = listgrosir;
	}

	public void UpdateListGrosirAdapter(ArrayList<grosir> listgrosir) {
		this.listgrosir = listgrosir;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listgrosir.size();
	}

	@Override
	public Object getItem(int position) {
		return listgrosir.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public MyTextView syarat;
		public MyTextView harga;
		public int position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {

		final ViewHolder view;
		LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
		if(convertView==null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.grosir_item_list, null);

			view.syarat = (MyTextView) convertView.findViewById(R.id.syarat_jumlah);
			view.harga = (MyTextView) convertView.findViewById(R.id.harga_grosir);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		final grosir data = listgrosir.get(position);
		view.syarat.setText(CommonUtilities.getNumberFormat(data.getJumlah_min()) + " - " + CommonUtilities.getNumberFormat(data.getJumlah_max()));
		view.harga.setText(CommonUtilities.getCurrencyFormat(data.getHarga(), "Rp. "));

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View convertView) {

			}
		});

		return convertView;
	}
}
