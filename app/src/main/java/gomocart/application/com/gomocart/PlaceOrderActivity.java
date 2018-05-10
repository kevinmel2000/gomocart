package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.model.grandtotal;
import gomocart.application.com.model.user;
import gomocart.application.com.model.voucher;

public class PlaceOrderActivity extends AppCompatActivity {

    Context context;
    DatabaseHandler dh;

    grandtotal gtotal;

    MyEditText kode_voucher;
    MyTextView apply;

    MyTextView edit_total;
    MyTextView edit_diskon;
    MyTextView edit_sub_total;
    MyTextView edit_voucher;
    MyTextView edit_pengiriman;
    MyTextView edit_grand_total;
    MyTextView next;

    ImageView back;

    voucher data_voucher;
    boolean success;
    String message;
    user data;

    //ProgressDialog progDailog;
    Dialog dialog_loading;
    //FrameLayout frame_loading;
    
    Dialog dialog_informasi;
    MyTextView btn_ok;
    MyTextView text_title;
    MyTextView text_informasi;

    Typeface fonts1,fonts2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        context = PlaceOrderActivity.this;
        data = CommonUtilities.getSettingUser(context);
        dh = new DatabaseHandler(context);
        gtotal = dh.getGrandtotal();

        kode_voucher = (MyEditText) findViewById(R.id.edit_kode_voucher);
        apply = (MyTextView) findViewById(R.id.apply_voucher);
        edit_total = (MyTextView) findViewById(R.id.edit_total);
        edit_diskon = (MyTextView) findViewById(R.id.edit_diskon);
        edit_sub_total = (MyTextView) findViewById(R.id.edit_subtotal);
        edit_voucher = (MyTextView) findViewById(R.id.edit_diskon_voucher);
        edit_pengiriman = (MyTextView) findViewById(R.id.edit_pengiriman);
        edit_grand_total = (MyTextView) findViewById(R.id.edit_grand_total);
        next = (MyTextView) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new prosesCekVoucher().execute();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); //context, CheckoutAlamatActivity.class);
                intent.putExtra("action", "back");
                if(data_voucher!=null) { intent.putExtra("data_voucher", data_voucher); }
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); //context, CheckoutKonfirmasiActivity.class);
                intent.putExtra("action", "next");
                if(data_voucher!=null) { intent.putExtra("data_voucher", data_voucher); }
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //progDailog = new ProgressDialog(context);
        //progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progDailog.setCancelable(false);
        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);
        //frame_loading = (FrameLayout) dialog_loading.findViewById(R.id.frame_loading);

        dialog_informasi = new Dialog(context);
        dialog_informasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_informasi.setCancelable(true);
        dialog_informasi.setContentView(R.layout.informasi_dialog);

        btn_ok = (MyTextView) dialog_informasi.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog_informasi.dismiss();
            }
        });
        btn_ok.setTypeface(fonts2);

        text_title = (MyTextView) dialog_informasi.findViewById(R.id.text_title);
        text_informasi = (MyTextView) dialog_informasi.findViewById(R.id.text_dialog);
        text_informasi.setTypeface(fonts1);

        data_voucher = (voucher) getIntent().getSerializableExtra("data_voucher");
        loadFieldGrandTotal();
    }
    
    public void openDialogLoading() {
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }
    
    private void loadFieldGrandTotal() {

        double total_belanja = gtotal.getSub_total();
        double total_pengiriman = gtotal.getPengiriman();

        double total_voucher = 0;
        if(data_voucher!=null) {
            total_voucher = (data_voucher.getTipe_voucher().equalsIgnoreCase("persentase") ? ((data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") ? total_pengiriman : total_belanja) * (data_voucher.getNominal() * 0.01)) : data_voucher.getNominal());
            if (data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") && total_voucher > total_pengiriman) {
                total_voucher = total_pengiriman;
            }

            if (data_voucher.getJenis_voucher().equalsIgnoreCase("belanja") && total_voucher > total_belanja) {
                total_voucher = total_belanja;
            }
        }

        double grand = total_belanja + gtotal.getPengiriman() - total_voucher;

        edit_total.setText(CommonUtilities.getCurrencyFormat(gtotal.getTotal(), ""));
        edit_diskon.setText(CommonUtilities.getCurrencyFormat(gtotal.getDiskon(), ""));
        edit_sub_total.setText(CommonUtilities.getCurrencyFormat(total_belanja, ""));
        edit_voucher.setText(CommonUtilities.getCurrencyFormat(total_voucher, ""));
        edit_pengiriman.setText(CommonUtilities.getCurrencyFormat(total_pengiriman, ""));
        edit_grand_total.setText(CommonUtilities.getCurrencyFormat(grand, ""));
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadListCekVoucherReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadListCekVoucherReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadListCekVoucherReceiver, new IntentFilter("gomocart.application.com.gomocart.CEK_VOUCHER"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadListCekVoucherReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            dialog_loading.dismiss();
            if(success) {
                loadFieldGrandTotal();
            } else {
                dialog_loading.dismiss();
                text_informasi.setText(message);
                text_title.setText(success?"BERHASIL":"GAGAL");
                dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_informasi.show();
            }

        }
    };

    public class prosesCekVoucher extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
            //progDailog.setMessage("Proses...");
            //progDailog.show();
        }

        @Override
        protected Void doInBackground(String... urls) {

            success = false;
            message = "Kesalahan koneksi internet.";

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("kode_voucher", kode_voucher.getText().toString()));

            String url = CommonUtilities.SERVER_URL + "/store/androidCekVoucher.php";
            JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

            if(json!=null) {
                try {
                    success = json.isNull("success")?false:json.getBoolean("success");
                    message = json.isNull("message")?message:json.getString("message");

                    String kode = json.isNull("kode_voucher")?"":json.getString("kode_voucher");
                    double nominal = json.isNull("nominal")?0:json.getDouble("nominal");
                    String tipe_voucher = json.isNull("tipe_voucher")?"":json.getString("tipe_voucher");
                    String jenis_voucher = json.isNull("jenis_voucher")?"":json.getString("jenis_voucher");

                    data_voucher = new voucher(kode, nominal, tipe_voucher, jenis_voucher);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            Intent i = new Intent("gomocart.application.com.gomocart.CEK_VOUCHER");
            sendBroadcast(i);

            return null;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(); //context, CheckoutAlamatActivity.class);
            intent.putExtra("action", "back");
            if(data_voucher!=null) { intent.putExtra("data_voucher", data_voucher); }
            setResult(RESULT_OK, intent);
            finish();

            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(); //context, CheckoutAlamatActivity.class);
                intent.putExtra("action", "back");
                if(data_voucher!=null) { intent.putExtra("data_voucher", data_voucher); }
                setResult(RESULT_OK, intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
