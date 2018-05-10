package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.libs.MCrypt;
import gomocart.application.com.model.facebook;
import gomocart.application.com.model.order;
import gomocart.application.com.model.user;

public class DaftarActivity extends AppCompatActivity {

    ImageView back;
    MyTextView signup, signin, term_kondisi, or;
    CheckBox setuju;


    Dialog dialog_informasi;
    MyTextView btn_ok;
    MyTextView text_title;
    MyTextView text_informasi;

    //MyEditText firstname;
    //MyEditText lastname;
    MyEditText nama;
    MyEditText nohp;
    MyEditText email;
    MyEditText password;
    MyEditText konfirmasi;

    Dialog dialog_loading;

    user data;
    Context context;

    private facebook user_facebook;

    private CallbackManager callBackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private LoginButton btnLoginFacebook;
    private FrameLayout frameLayoutLoginFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = DaftarActivity.this;

        FacebookSdk.sdkInitialize(context);
        setContentView(R.layout.activity_daftar);


        back = (ImageView) findViewById(R.id.back);
        btnLoginFacebook = (LoginButton) findViewById(R.id.fb_l);
        frameLayoutLoginFacebook = (FrameLayout) findViewById(R.id.frame_layout_btn_login_facebook);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        callBackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                if (newAccessToken==null) {
                    Log.e("FACEBOOK ACCESSTOKEN", "Please Login!");
                }
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if(newProfile==null) {
                    Log.e("FACEBOOK PROFILE", "Please Login!");
                }
            }
        };

        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile, user_friends, email"));
        frameLayoutLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isLoggedIn()) {
                    LoginManager.getInstance().logOut();
                }

                btnLoginFacebook.setSoundEffectsEnabled(false);
                btnLoginFacebook.performClick();
                btnLoginFacebook.setPressed(true);
                btnLoginFacebook.invalidate();
                btnLoginFacebook.registerCallback(callBackManager, callBack);

            }
        });

        //firstname = (MyEditText) findViewById(R.id.edit_firstname);
        //lastname  = (MyEditText) findViewById(R.id.edit_lastname);
        nama        = (MyEditText) findViewById(R.id.nama);
        nohp        = (MyEditText) findViewById(R.id.nohp);
        email       = (MyEditText) findViewById(R.id.email);
        password    = (MyEditText) findViewById(R.id.edit_password);
        konfirmasi  = (MyEditText) findViewById(R.id.edit_konfirmasi);

        signin = (MyTextView) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.putExtra("go_to", "login");
                setResult(RESULT_OK, i);
                finish();
            }
        });

        signup = (MyTextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setuju.isChecked()) {
                    new prosesSingUp().execute();
                } else {
                    text_informasi.setText("Ceklist Syarat & Ketentuan!");
                    text_title.setText("KESALAHAN");
                    dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_informasi.show();
                }
            }
        });

        or = (MyTextView) findViewById(R.id.or);
        term_kondisi = (MyTextView) findViewById(R.id.termc);
        term_kondisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TermKondisiActivity.class);
                startActivity(intent);
            }
        });

        setuju = (CheckBox) findViewById(R.id.checkbox_term);

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
            user_facebook = (facebook) getIntent().getSerializableExtra("user_facebook");

        }
    }

    public void openDialogLoading() {
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user_facebook!=null) {
            email.setText(user_facebook.getUseremail());
            nama.setText(user_facebook.getFirstname() + " " + user_facebook.getLastname());
            //firstname.setText(user_facebook.getFirstname());
            //lastname.setText(user_facebook.getLastname());

            frameLayoutLoginFacebook.setVisibility(View.INVISIBLE);
            or.setVisibility(View.INVISIBLE);
            password.requestFocus();
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private FacebookCallback<LoginResult> callBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            /*accessTokenTracker.startTracking();
            profileTracker.startTracking();

            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                //get data here
                Log.e("FACEBOOK PRO", profile.getFirstName());
            }
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if(accessToken != null) {
                GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {
                            // handle error
                        } else {
                            String id = me.optString("id");
                            String email =  .asMap().get("email");

                            Toast.makeText(context, "Facebook id: "+id+"\nFacebook email: "+email, Toast.LENGTH_LONG).show();

                            // send email and id to your web server
                        }
                    }
                }).executeAsync();


            }*/

            final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {

                    String userid = "";
                    String useremail = "";
                    String userfirstname = "";
                    String userlastname = "";
                    String userpicture = "";

                    try {
                        if (object.has("id")) {
                            userid = object.optString("id");
                            userpicture = "https://graph.facebook.com/" + object.optString("id") + "/picture?type=large&redirect=false";
                        }

                        if (object.has("email")) {
                            useremail = object.optString("email");
                        }

                        if (object.has("first_name")) {
                            userfirstname = object.optString("first_name");
                        }

                        if (object.has("last_name")) {
                            userlastname = object.optString("last_name");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    user_facebook = new facebook(userid, useremail, userfirstname, userlastname, userpicture);
                    new prosesFacebookSingUp(user_facebook, false).execute();


                }
            });

            final Bundle parameters = new Bundle();
            parameters.putString("fields", "id,email,first_name,last_name");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            Log.d("FACEBOOK ERROR", error.getMessage());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBackManager.onActivityResult(requestCode, resultCode, data);
    }


    class prosesSingUp extends AsyncTask<String, Void, JSONObject> {

        boolean success;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
            //.setMessage("Sign Up...");
            //progDailog.show();

        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            JSONParser token_json = new JSONParser();
            JSONObject token = token_json.getJSONFromUrl(CommonUtilities.SERVER_URL + "/store/token.php", null, null);
            String cookies = token_json.getCookies();

            String security_code = "";
            try {
                security_code = token.isNull("security_code")?"":token.getString("security_code");
                MCrypt mCrypt = new MCrypt();
                security_code = new String(mCrypt.decrypt(security_code));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject jObj = null;
            if(security_code.length()>0) {
                try {
                    String url = CommonUtilities.SERVER_URL + "/store/androidSignup.php";
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(url);

                    String nama_lengkap = nama.getText().toString().trim();
                    String[] temps = nama_lengkap.split(" ");
                    String firstname = temps[0];
                    String lastname  = "";
                    for(int i=1; i<temps.length; i++) {
                        lastname+=temps[i]+" ";
                    }
                    lastname = lastname.trim();

                    MultipartEntity reqEntity = new MultipartEntity();
                    reqEntity.addPart("first_name", new StringBody(firstname));
                    reqEntity.addPart("last_name", new StringBody(lastname));
                    reqEntity.addPart("email", new StringBody(email.getText().toString()));
                    reqEntity.addPart("nohp", new StringBody(nohp.getText().toString()));
                    reqEntity.addPart("password", new StringBody(password.getText().toString()));
                    reqEntity.addPart("konfirmasi", new StringBody(konfirmasi.getText().toString()));
                    reqEntity.addPart("user_picture", new StringBody(user_facebook!=null?user_facebook.getUserpicture():""));
                    reqEntity.addPart("security_code", new StringBody(security_code));

                    httppost.setHeader("Cookie", cookies);
                    httppost.setEntity(reqEntity);
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity resEntity = response.getEntity();
                    InputStream is = resEntity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;


                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    String json = sb.toString();
                    System.out.println(json);

                    jObj = new JSONObject(json);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();


                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return jObj;
        }

        @Deprecated
        @Override
        protected void onPostExecute(JSONObject result) {

            dialog_loading.dismiss();

            success = false;
            message = "Proses sign up gagal.";
            if(result!=null) {
                try {
                    success = result.isNull("success")?false:result.getBoolean("success");
                    message = result.isNull("message")?message:result.getString("message");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if(success) {
                String nama_lengkap = nama.getText().toString().trim();
                String[] temps = nama_lengkap.split(" ");
                String firstname = temps[0];
                String lastname  = "";
                for(int i=1; i<temps.length; i++) {
                    lastname+=temps[i]+" ";
                }
                lastname = lastname.trim();

                user_facebook = new facebook("", email.getText().toString(), firstname, lastname, "");
                new prosesFacebookSingUp(user_facebook, true).execute();
            } else {
                text_informasi.setText(message);
                text_title.setText("KESALAHAN");
                dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_informasi.show();
            }
        }
    }

    class prosesFacebookSingUp extends AsyncTask<String, Void, Boolean> {

        facebook akun_facebook;
        boolean tidak_aktif;
        boolean success;
        boolean do_registrasi;
        String message;

        prosesFacebookSingUp(facebook data, boolean do_registrasi) {
            this.akun_facebook = data;
            this.do_registrasi = do_registrasi;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
            //progDailog.setMessage("Sign Up...");
            //progDailog.show();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            JSONParser token_json = new JSONParser();
            JSONObject token = token_json.getJSONFromUrl(CommonUtilities.SERVER_URL + "/store/token.php", null, null);
            String cookies = token_json.getCookies();

            String security_code = "";
            try {
                security_code = token.isNull("security_code")?"":token.getString("security_code");
                MCrypt mCrypt = new MCrypt();
                security_code = new String(mCrypt.decrypt(security_code));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            success = false;
            tidak_aktif = false;
            message = "Proses masuk gagal. Cobalah lagi.";

            if(security_code.length()>0) {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("email", akun_facebook.getUseremail()));
                params.add(new BasicNameValuePair("do_registrasi", do_registrasi?"YES":"NO"));
                params.add(new BasicNameValuePair("security_code", security_code));

                String url = CommonUtilities.SERVER_URL + "/store/androidFacebookSignin.php";
                JSONObject result = new JSONParser().getJSONFromUrl(url, params, cookies);

                if (result != null) {
                    try {
                        success = result.isNull("success") ? false : result.getBoolean("success");
                        message = result.isNull("message") ? message : result.getString("message");
                        if (success) {
                            data = new user(
                                    result.isNull("id")?0:result.getInt("id"),
                                    result.isNull("first_name")?"":result.getString("first_name"),
                                    result.isNull("last_name")?"":result.getString("last_name"),
                                    result.isNull("email")?"":result.getString("email"),
                                    result.isNull("phone")?"":result.getString("phone"),
                                    result.isNull("dropship_name")?"":result.getString("dropship_name"),
                                    result.isNull("dropship_phone")?"":result.getString("dropship_phone"),
                                    result.isNull("jenis_user")?"":result.getString("jenis_user"),
                                    result.isNull("photo")?"":result.getString("photo")
                            );

                            CommonUtilities.setSettingUser(context, data);

                            //ORDER LIST
                            JSONArray order_list = result.isNull("order_list")?null:result.getJSONArray("order_list");
                            //Log.e("ORDER LIST LENGTH", order_list.length()+"   ~~~~~~~~~~~");
                            DatabaseHandler dh = new DatabaseHandler(context);
                            dh.clearOrderlist();
                            if(order_list!=null) {
                                for (int i = 0; i < order_list.length(); i++) {
                                    JSONObject rec = order_list.getJSONObject(i);
                                    String no_transaksi = rec.isNull("no_transaksi")?"":rec.getString("no_transaksi");
                                    String tgl_transaksi = rec.isNull("tgl_transaksi")?"":rec.getString("tgl_transaksi");
                                    int pembayaran = rec.isNull("pembayaran")?0:rec.getInt("pembayaran");
                                    String nama = rec.isNull("nama")?"":rec.getString("nama");
                                    int qty = rec.isNull("qty")?0:rec.getInt("qty");
                                    double jumlah = rec.isNull("jumlah")?0:rec.getInt("jumlah");
                                    String estimasi = rec.isNull("estimasi")?"":rec.getString("estimasi");
                                    String kurir = rec.isNull("kurir")?"":rec.getString("kurir");
                                    String noresi = rec.isNull("noresi")?"":rec.getString("noresi");
                                    String gambar = rec.isNull("gambar")?"":rec.getString("gambar");
                                    int status = rec.isNull("no_hp")?0:rec.getInt("status");
                                    int user_id = rec.isNull("user_id")?0:rec.getInt("user_id");

                                    order data_order = new order(no_transaksi, tgl_transaksi, pembayaran, nama, qty, jumlah, estimasi, kurir, noresi, gambar, status, user_id);
                                    dh.insertOrderlist(data_order);
                                }
                            }
                        } else {
                            tidak_aktif = result.isNull("tidak_aktif") ? tidak_aktif : result.getBoolean("tidak_aktif");
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog_loading.dismiss();

            if(!success) {
                if(tidak_aktif) {
                    text_informasi.setText(message);
                    text_title.setText("KESALAHAN");
                    dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_informasi.show();
                } else {
                    email.setText(user_facebook.getUseremail());
                    nama.setText(user_facebook.getFirstname() + " " + user_facebook.getLastname());

                    //firstname.setText(user_facebook.getFirstname());
                    //lastname.setText(user_facebook.getLastname());

                    frameLayoutLoginFacebook.setVisibility(View.INVISIBLE);
                    or.setVisibility(View.INVISIBLE);
                    password.requestFocus();
                }
            } else {
                CommonUtilities.setNoHpAktivasi(context, nohp.getText().toString());
                if(do_registrasi) {
                    Toast.makeText(context, "Proses registrasi berhasil!", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent();
                i.putExtra("is_login", true);
                setResult(RESULT_OK, i);
                finish();
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }
}
