package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.order;

/**
 * Created by apple on 01/04/16.
 */
public class ListOrderAdapter extends BaseAdapter {


    Context context;
    ArrayList<order> orderlist;

    public ListOrderAdapter(Context context, ArrayList<order> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    public void UpdateListOrderAdapter(ArrayList<order> orderlist) {
        this.orderlist = orderlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderlist.size();
    }

    @Override
    public Object getItem(int position) {
        return orderlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_order,null);

            viewHolder = new ViewHolder();

            viewHolder.no_invoice = (MyTextView)convertView.findViewById(R.id.no_invoice);
            viewHolder.nama_penerima = (MyTextView)convertView.findViewById(R.id.nama_penerima);
            viewHolder.tanggal = (MyTextView)convertView.findViewById(R.id.tanggal);
            viewHolder.ekspedisi = (MyTextView)convertView.findViewById(R.id.ekspedisi);
            viewHolder.no_resi = (MyTextView)convertView.findViewById(R.id.no_resi);
            viewHolder.status = (MyTextView)convertView.findViewById(R.id.status);

            viewHolder.total_pembelian = (MyTextView)convertView.findViewById(R.id.total_pembelian);
            viewHolder.total_pembayaran = (MyTextView)convertView.findViewById(R.id.total_pembayaran);

            viewHolder.batalkan = (MyTextView) convertView.findViewById(R.id.batalkan);
            viewHolder.konfirmasi = (MyTextView) convertView.findViewById(R.id.konfirmasi);
            viewHolder.kirim_pesan = (MyTextView) convertView.findViewById(R.id.kirim_pesan);
            viewHolder.lacak_pesanan = (MyTextView) convertView.findViewById(R.id.lacak_pesanan);
            //viewHolder.beli_lagi = (MyTextView) convertView.findViewById(R.id.beli_lagi);

            viewHolder.rincian =(MyTextView) convertView.findViewById(R.id.rincian);

            convertView.setTag(viewHolder);

        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final order data_order = (order) getItem(position);

        viewHolder.no_invoice.setText("No. Invoice: #" + data_order.getNo_transaksi());
        viewHolder.nama_penerima.setText("Nama Penerima: " +data_order.getNama());
        viewHolder.tanggal.setText("Tanggal: "+data_order.getTgl_transaksi());
        viewHolder.ekspedisi.setText("Ekspedisi: " +data_order.getKurir()+" ("+data_order.getEstimasi()+" Hari)");
        viewHolder.no_resi.setText("No. Resi: " +data_order.getNoresi());
        viewHolder.status.setText("Status: "+/*data_order.getStatus()+"  "+*/(
                data_order.getStatus()==0?"Menunggu Pembayaran":(
                data_order.getStatus()==1?"Pembayaran Tidak Valid":(
                        data_order.getStatus()==2?"Pemesanan Sedang Diproses":(
                                data_order.getStatus()==4?"Pemesanan Dibatalkan":(
                                        data_order.getStatus()==6?"Pemesanan Sudah Dikirim":(
                                                data_order.getStatus()==5?"Pemesanan Sudah Selesai":(
                                                        data_order.getStatus()==3?"Menunggu Validasi oleh Admin":""))))))));

        viewHolder.no_resi.setVisibility(data_order.getStatus()==5||data_order.getStatus()==6?View.VISIBLE:View.GONE);

        viewHolder.total_pembelian.setText("Total Pembelian: "+data_order.getQty()+" Pcs");
        viewHolder.total_pembayaran.setText("Pembayaran "+CommonUtilities.getCurrencyFormat(data_order.getJumlah(), "Rp. "));

        viewHolder.batalkan.setVisibility((data_order.getStatus()==0 || data_order.getStatus()==1 || data_order.getStatus()==3)?View.VISIBLE:View.INVISIBLE);

        viewHolder.konfirmasi.setVisibility((data_order.getStatus()==0 || data_order.getStatus()==1 || data_order.getStatus()==3)?View.VISIBLE:View.GONE);
        viewHolder.kirim_pesan.setVisibility((data_order.getStatus()==2)?View.VISIBLE:View.GONE);
        viewHolder.lacak_pesanan.setVisibility((data_order.getStatus()==6)?View.VISIBLE:View.GONE);
        //viewHolder.beli_lagi.setVisibility((data_order.getStatus()==5)?View.VISIBLE:View.GONE);

        viewHolder.batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).prosesBatalkanPesanan(data_order);
            }
        });

        viewHolder.konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).openKonfirmasiPembayaran(data_order);
            }
        });

        viewHolder.rincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).openDetailOrder(data_order);
            }
        });

        viewHolder.lacak_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] temp = data_order.getKurir().split(" ");
                ((MainActivity) context).lacak_pengiriman(temp[0].trim(), data_order.getNoresi());
            }
        });




        return convertView;
    }

    private class ViewHolder{

        MyTextView no_invoice;
        MyTextView nama_penerima;
        MyTextView tanggal;
        MyTextView ekspedisi;
        MyTextView no_resi;
        MyTextView status;


        MyTextView total_pembelian;
        MyTextView total_pembayaran;

        MyTextView batalkan;
        MyTextView konfirmasi;
        MyTextView kirim_pesan;
        MyTextView lacak_pesanan;
        //MyTextView beli_lagi;
        MyTextView rincian;
    }
}

