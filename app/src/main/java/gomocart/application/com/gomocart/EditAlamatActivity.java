package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.city;
import gomocart.application.com.model.province;
import gomocart.application.com.model.subdistrict;
import gomocart.application.com.model.user;

public class EditAlamatActivity extends AppCompatActivity {

    Context context;

    int province_id = 0;
    int city_id = 0;
    int subdistrict_id = 0;

    MyEditText edit_nama;
    MyEditText edit_alamat;
    MyEditText edit_province;
    MyEditText edit_city;
    MyEditText edit_state;
    MyEditText edit_kodepos;
    MyEditText edit_nohp;
    CheckBox chbox_default;
    MyTextView lanjutkan;

    ArrayList<province> listProvince = new ArrayList<>();
    ArrayList<city> listCity = new ArrayList<>();
    ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

    float downX = 0, downY = 0, upX, upY;
    Dialog dialog_listview;
    ListView listview;
    String action;
    DatabaseHandler dh;
    user data;
    alamat data_selected_alamat;

    ImageView back;

    //ProgressDialog progDailog;
    Dialog dialog_loading;
    //FrameLayout frame_loading;

    Dialog dialog_informasi;
    MyTextView btn_ok;
    MyTextView text_title;
    MyTextView text_informasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        context = EditAlamatActivity.this;
        data = CommonUtilities.getSettingUser(context);
        dh = new DatabaseHandler(context);

        edit_nama     = (MyEditText) findViewById(R.id.edit_nama);
        edit_alamat   = (MyEditText) findViewById(R.id.edit_alamat);
        edit_province = (MyEditText) findViewById(R.id.edit_provice);
        edit_city     = (MyEditText) findViewById(R.id.edit_city);
        edit_state    = (MyEditText) findViewById(R.id.edit_kecamatan);
        edit_kodepos  = (MyEditText) findViewById(R.id.edit_kodepos);
        edit_nohp     = (MyEditText) findViewById(R.id.edit_phone);
        chbox_default = (CheckBox) findViewById(R.id.checkboxdefault);
        lanjutkan     = (MyTextView) findViewById(R.id.next);
        back          = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = checkedBeforeNext();
                if(message.length()==0) {
                    new prosesSaveAlamat().execute();
                } else {
                    text_informasi.setText(message);
                    text_title.setText("KESALAHAN");
                    dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_informasi.show();
                }

            }
        });

        edit_province.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
                            action = "province";
                            loadListArray();
                            dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog_listview.show();
                        }

                        break;
                }

                return false;
            }
        });

        edit_city.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
                            if(edit_province.getText().toString().length()==0) {
                                openDialogMessage("Propinsi tujuan harus diisi!", false);
                            } else {
                                loadDialogListView("city");
                            }
                        }

                        break;
                }

                return false;
            }
        });


        edit_state.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
                            if(edit_city.getText().toString().length()==0) {
                                openDialogMessage("Kabupaten / kota tujuan harus diisi!", false);
                            } else {
                                loadDialogListView("subdistrict");
                            }
                        }

                        break;
                }

                return false;
            }
        });

        dialog_listview = new Dialog(context);
        dialog_listview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_listview.setCancelable(true);
        dialog_listview.setContentView(R.layout.list_dialog);

        listview = (ListView) dialog_listview.findViewById(R.id.listViewDialog);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog_listview.dismiss();
                if(action.equalsIgnoreCase("province")) {
                    edit_province.setText(listProvince.get(position).getProvince());
                    edit_city.setText("");
                    edit_state.setText("");

                    province_id = listProvince.get(position).getProvince_id();
                    city_id = 0;
                    subdistrict_id = 0;

                    new loadCity().execute();
                    new loadSubdistrict().execute();

                } else if(action.equalsIgnoreCase("city")) {
                    edit_city.setText(listCity.get(position).getCity());
                    edit_state.setText("");

                    city_id = listCity.get(position).getCity_id();
                    subdistrict_id = 0;

                    new loadSubdistrict().execute();

                } else if(action.equalsIgnoreCase("subdistrict")) {
                    edit_state.setText(listSubDistrict.get(position).getSubdistrict());
                    subdistrict_id = listSubDistrict.get(position).getSubdistrict_id();
                }
                action = "";
            }
        });

        new loadProvince().execute();
        //loadFieldAlamat(dh.getAlamat());

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

        text_title = (MyTextView) dialog_informasi.findViewById(R.id.text_title);
        text_informasi = (MyTextView) dialog_informasi.findViewById(R.id.text_dialog);

        if(savedInstanceState==null) {
            data_selected_alamat = (alamat) getIntent().getSerializableExtra("alamat");
            if(data_selected_alamat!=null) {
                loadFieldAlamat(data_selected_alamat);
            }
        }
    }

    public void openDialogMessage(String message, boolean status) {
        text_informasi.setText(message);
        text_title.setText(status?"BERHASIL":"KESALAHAN");
        dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_informasi.show();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadEkspedisiReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadEkspedisiReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadEkspedisiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(dialog_loading.isShowing()) {
                loadListArray();
                dialog_loading.dismiss();
                dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_listview.show();
            }
        }
    };

    public void loadDialogListView(String act) {
        action = act;
        if(action.equalsIgnoreCase("province") && listProvince.size()==0) {
            openDialogLoadingEkspedisi();
        } else if(action.equalsIgnoreCase("city") && listCity.size()==0) {
            openDialogLoadingEkspedisi();
        } else if(action.equalsIgnoreCase("subdistrict") && listSubDistrict.size()==0) {
            openDialogLoadingEkspedisi();
        } else {
            loadListArray();
            dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_listview.show();
        }
    }


    public void openDialogLoading() {
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }


    public void openDialogLoadingEkspedisi() {
        dialog_loading.setCancelable(true);
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    private String checkedBeforeNext() {
        
        if(edit_nama.getText().toString().length()==0) {
            return "Nama harus diisi.";
        }

        if(edit_alamat.getText().toString().length()==0) {
            return "Alamat harus diisi.";
        }

        if(edit_nohp.getText().toString().length()==0) {
            return "No HP harus diisi.";
        }

        if(province_id==0) {
            return "Propinsi harus harus diisi.";
        }

        if(city_id==0) {
            return "Kota harus diisi.";
        }

        if(subdistrict_id==0) {
            return "Kecamatan harus diisi.";
        }


        return "";
    }
    
    /*private void saveAlamat() {

        String nama = edit_nama.getText().toString();
        String alamat = edit_alamat.getText().toString();
        String province = edit_province.getText().toString();
        String city_name = edit_city.getText().toString();
        String subdistrict_name = edit_state.getText().toString();
        String kode_pos = edit_kodepos.getText().toString();
        String no_hp = edit_nohp.getText().toString();


        //dh.insertAlamat(new alamat(nama, alamat, province_id, province, city_id, city_name, subdistrict_id, subdistrict_name, kode_pos, no_hp, is_dropship, dropship_name, dropship_phone, email_notifikasi, kurir_id, kurir, layanan, etd, tarif, nominal));
    }*/

    private void loadFieldAlamat(alamat selected_alamat) {

        edit_nama.setText(selected_alamat.getNama());
        edit_alamat.setText(selected_alamat.getAlamat());
        edit_province.setText(selected_alamat.getProvince());
        province_id = selected_alamat.getProvince_id();
        edit_city.setText(selected_alamat.getCity_name());
        city_id = selected_alamat.getCity_id();
        edit_state.setText(selected_alamat.getSubdistrict_name());
        subdistrict_id = selected_alamat.getSubdistrict_id();
        edit_kodepos.setText(selected_alamat.getKode_pos());
        edit_nohp.setText(selected_alamat.getNo_hp());
        chbox_default.setChecked(selected_alamat.getAsDefaultAlamat());

        new loadCity().execute();
        new loadSubdistrict().execute();
    }

    public class prosesSaveAlamat extends AsyncTask<String, Void, Boolean> {

        int id;
        boolean success;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            openDialogLoading();
            //progDailog.setMessage("Proses...");
            //progDailog.show();
        }

        @Override
        protected Boolean doInBackground(String... urls) {

            String url = CommonUtilities.SERVER_URL + "/store/androidSaveAlamat.php";
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id", (data_selected_alamat!=null?data_selected_alamat.getId():0)+""));
            params.add(new BasicNameValuePair("id_member", data.getId()+""));
            params.add(new BasicNameValuePair("nama", edit_nama.getText().toString()));
            params.add(new BasicNameValuePair("alamat", edit_alamat.getText().toString()));
            params.add(new BasicNameValuePair("id_propinsi", province_id+""));
            params.add(new BasicNameValuePair("nama_propinsi", edit_province.getText().toString()));
            params.add(new BasicNameValuePair("id_kota", city_id+""));
            params.add(new BasicNameValuePair("nama_kota", edit_city.getText().toString()));
            params.add(new BasicNameValuePair("id_kecamatan", subdistrict_id+""));
            params.add(new BasicNameValuePair("nama_kecamatan", edit_state.getText().toString()));
            params.add(new BasicNameValuePair("kode_pos", edit_kodepos.getText().toString()));
            params.add(new BasicNameValuePair("no_hp", edit_nohp.getText().toString()));
            params.add(new BasicNameValuePair("as_default", chbox_default.isChecked()?"1":"0"));

            id = 0;
            success = false;
            message = "Gagal melakukan sign out. Silahkan coba lagi!";

            JSONObject result = new JSONParser().getJSONFromUrl(url, params, null);
            if(result!=null) {
                try {
                    id = result.isNull("id")?0:result.getInt("id");
                    success = result.isNull("success")?false:result.getBoolean("success");
                    message = result.isNull("message")?message:result.getString("message");

                    data_selected_alamat = new alamat(
                            id,
                            edit_nama.getText().toString(),
                            edit_alamat.getText().toString(),
                            province_id,
                            edit_province.getText().toString(),
                            city_id,
                            edit_city.getText().toString(),
                            subdistrict_id,
                            edit_state.getText().toString(),
                            edit_kodepos.getText().toString(),
                            edit_nohp.getText().toString(),
                            chbox_default.isChecked(),
                            false, "", "", ""
                    );

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            dialog_loading.dismiss();
            if(success) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("alamat", data_selected_alamat);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                text_informasi.setText(message);
                text_title.setText("KESALAHAN");
                dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_informasi.show();
            }


        }
    }

    public class loadProvince extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {

            listProvince = new ArrayList<>();
            String url = CommonUtilities.SERVER_URL + "/store/androidPropinsiDataStore.php";
            JSONObject json = new JSONParser().getJSONFromUrl(url, null, null);
            if(json!=null) {
                try {
                    JSONArray data = json.isNull("topics")?null:json.getJSONArray("topics");
                    for (int i=0; i<data.length(); i++) {
                        JSONObject rec= data.getJSONObject(i);

                        int province_id = rec.isNull("province_id")?0:rec.getInt("province_id");
                        String province = rec.isNull("province")?"":rec.getString("province");

                        listProvince.add(new province(province_id, province));
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
            sendBroadcast(i);

            return null;
        }
    }

    public class loadCity extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {

            listCity = new ArrayList<>();
            if(province_id>0) {
                String url = CommonUtilities.SERVER_URL + "/store/androidCityDataStore.php";
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("province_id", province_id+""));
                JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
                if(json!=null) {
                    try {
                        JSONArray data = json.isNull("topics")?null:json.getJSONArray("topics");
                        for (int i=0; i<data.length(); i++) {
                            JSONObject rec= data.getJSONObject(i);

                            int city_id = rec.isNull("city_id")?0:rec.getInt("city_id");
                            int province_id = rec.isNull("province_id")?0:rec.getInt("province_id");
                            String city = rec.isNull("city_name")?"":rec.getString("city_name");

                            listCity.add(new city(city_id, province_id, city));
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
            sendBroadcast(i);

            return null;
        }
    }

    public class loadSubdistrict extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {

            listSubDistrict = new ArrayList<>();
            if(city_id>0) {
                String url = CommonUtilities.SERVER_URL + "/store/androidSubdistrictDataStore.php";
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("city_id", city_id+""));
                JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
                if(json!=null) {
                    try {
                        JSONArray data = json.isNull("topics")?null:json.getJSONArray("topics");
                        for (int i=0; i<data.length(); i++) {
                            JSONObject rec= data.getJSONObject(i);

                            int subdistrict_id = rec.isNull("subdistrict_id")?0:rec.getInt("subdistrict_id");
                            int city_id = rec.isNull("city_id")?0:rec.getInt("city_id");
                            String subdistrict = rec.isNull("subdistrict_name")?"":rec.getString("subdistrict_name");

                            listSubDistrict.add(new subdistrict(subdistrict_id, city_id, subdistrict));
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
            sendBroadcast(i);

            return null;
        }
    }

    private void loadListArray() {
        String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
        int[] to = new int[] { R.id.txt_title };

        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        if(action.equalsIgnoreCase("province")) {
            for (province data : listProvince) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getProvince());

                fillMaps.add(map);
            }
        } else if(action.equalsIgnoreCase("city")) {
            for (city data : listCity) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getCity());

                fillMaps.add(map);
            }
        } else if(action.equalsIgnoreCase("subdistrict")) {
            for (subdistrict data : listSubDistrict) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getSubdistrict());

                fillMaps.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
        listview.setAdapter(adapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
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
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
