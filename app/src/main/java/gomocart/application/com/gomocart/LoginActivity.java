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
import android.widget.LinearLayout;

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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
import gomocart.application.com.model.remember;
import gomocart.application.com.model.user;

public class LoginActivity extends AppCompatActivity {

    ImageView back;
    MyTextView signin, signup, forgotpass;
    CheckBox checkboremember;

    Dialog dialog_informasi;
    MyTextView btn_ok;
    MyTextView text_title;
    MyTextView text_informasi;

    MyEditText email;
    MyEditText password;

    Dialog dialog_loading;

    Boolean success;
    boolean tidak_aktif;
    String message;
    user data;
    Context context;

    int menu_selected;
    boolean from_checkout = false;

    final int RESULT_FROM_AKTIVASI = 15;

    private CallbackManager callBackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private LoginButton btnLoginFacebook;
    private FrameLayout frameLayoutLoginFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;

        FacebookSdk.sdkInitialize(context);
        setContentView(R.layout.activity_login);

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

        remember data_remember = CommonUtilities.getRememberPassword(context);

        signin     = (MyTextView)findViewById(R.id.signin1);
        signup     = (MyTextView)findViewById(R.id.signup);
        forgotpass = (MyTextView)findViewById(R.id.forgotpass);
        checkboremember = (CheckBox) findViewById(R.id.checkbocremember);

        email      = (MyEditText) findViewById(R.id.email);
        password   = (MyEditText) findViewById(R.id.edit_password);

        email.setText(data_remember.getEmail());
        password.setText(data_remember.getPassword());
        
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DaftarActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkboremember.isChecked()) {
                        remember data = new remember(email.getText().toString(), password.getText().toString(), (checkboremember.isChecked()?"Y":"N"));
                        CommonUtilities.setRememberPassword(context, data);
                    }
                    new prosesSingIn().execute();
                }
        });



        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LupaPasswordActivity.class);
                startActivity(intent);
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

        text_title = (MyTextView) dialog_informasi.findViewById(R.id.text_title);
        text_informasi = (MyTextView) dialog_informasi.findViewById(R.id.text_dialog);

        if(savedInstanceState==null) {
            menu_selected = getIntent().getIntExtra("menu_selected", 0);
            from_checkout = getIntent().getBooleanExtra("from_checkout", false);
        }

        //LinearLayout linear_asguest = (LinearLayout) findViewById(R.id.linear_asguest);
        //MyTextView orasguest = (MyTextView) findViewById(R.id.orasguest);
        //linear_asguest.setVisibility(from_checkout?View.VISIBLE:View.GONE);
        //orasguest.setVisibility(from_checkout?View.VISIBLE:View.GONE);
    
        /*linear_asguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("menu_selected", menu_selected);
                intent.putExtra("from_checkout", from_checkout);
                setResult(RESULT_OK, intent);
                finish();
            }
        });*/
    }

    public void openDialogLoading() {
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
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
                            Log.e("gambar facebook", userpicture);
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

                    facebook user_facebook = new facebook(userid, useremail, userfirstname, userlastname, userpicture);
                    new prosesFacebookSingIn(user_facebook).execute();
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

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2:
                    String go_to_login = data.getStringExtra("go_to");
                    if(go_to_login==null) {
                    /*
                        } else {
                        if(data.getBooleanExtra("is_login", false)) {
                        Intent intent = new Intent();
                        intent.putExtra("is_login", true);
                        intent.putExtra("menu_selected", menu_selected);
                        intent.putExtra("from_checkout", from_checkout);
                        setResult(RESULT_OK, intent);
                        finish();
                    }*/

                        Intent intent = new Intent(context, AktivasiSmsActivity.class);
                        startActivityForResult(intent, RESULT_FROM_AKTIVASI);
                    }
                    break;
            }
        }
    }

    class prosesFacebookSingIn extends AsyncTask<String, Void, Boolean> {

        facebook akun_facebook;
        prosesFacebookSingIn(facebook data) {
            this.akun_facebook = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
            //progDailog.setMessage("Sign In...");
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
                params.add(new BasicNameValuePair("do_registrasi", "NO"));
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
                    Intent intent = new Intent(context, DaftarActivity.class);
                    intent.putExtra("user_facebook", akun_facebook);
                    startActivityForResult(intent, 2);
                }
            } else {
                Intent intent = new Intent();
                intent.putExtra("is_login", true);
                intent.putExtra("menu_selected", menu_selected);
                intent.putExtra("from_checkout", from_checkout);
                setResult(RESULT_OK, intent);
                finish();
            }

        }
    }

    class prosesSingIn extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
            //progDailog.setMessage("Sign In...");
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
            message = "Proses masuk gagal. Cobalah lagi.";

            if(security_code.length()>0) {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("email", email.getText().toString()));
                params.add(new BasicNameValuePair("password", password.getText().toString()));
                params.add(new BasicNameValuePair("security_code", security_code));

                String url = CommonUtilities.SERVER_URL + "/store/androidSignin.php";
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
                text_informasi.setText(message);
                text_title.setText("KESALAHAN");
                dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_informasi.show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("is_login", true);
                intent.putExtra("menu_selected", menu_selected);
                intent.putExtra("from_checkout", from_checkout);
                setResult(RESULT_OK, intent);
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
