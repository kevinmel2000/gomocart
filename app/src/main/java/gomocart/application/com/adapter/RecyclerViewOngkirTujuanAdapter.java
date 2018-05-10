package gomocart.application.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.DetailProdukActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.ongkir_tujuan;


public class RecyclerViewOngkirTujuanAdapter extends RecyclerView.Adapter<RecyclerViewOngkirTujuanAdapter.DataObjectHolder> {

    Context context;
    ArrayList<ongkir_tujuan> ongkir_tujuan_list;

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MyTextView title;
        ImageView next;

        public DataObjectHolder(View itemView) {
            super(itemView);

            title = (MyTextView) itemView.findViewById(R.id.title);
            next = (ImageView) itemView.findViewById(R.id.next);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((DetailProdukActivity) context).openOngkir();
        }

    }

    public RecyclerViewOngkirTujuanAdapter(Context context, ArrayList<ongkir_tujuan> ongkir_tujuan_list) {
        this.context = context;
        this.ongkir_tujuan_list = ongkir_tujuan_list;
    }

    public void UpdateData(ArrayList<ongkir_tujuan> ongkir_tujuan_list) {
        this.ongkir_tujuan_list = ongkir_tujuan_list;
        notifyDataSetChanged();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcatongkirtujuan, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.title.setText(ongkir_tujuan_list.get(position).getNama());
        holder.next.setVisibility(ongkir_tujuan_list.get(position).getIs_last()?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return ongkir_tujuan_list.size();
    }
}
