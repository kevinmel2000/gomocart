/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gomocart.application.com.gomocart;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.model.informasi;
import gomocart.application.com.model.perpesanan;
import gomocart.application.com.model.message;
import gomocart.application.com.model.notifikasi;
import gomocart.application.com.model.setting;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String tipe = remoteMessage.getData().get("tipe");
            String msgs = remoteMessage.getData().get("message");
            Log.i(TAG, "Received message: "+msgs);

            setting data_setting = CommonUtilities.getSettingApplikasi(getApplicationContext());
            if(tipe.equalsIgnoreCase("message")) {
                String laporans = remoteMessage.getData().get("perpesanan");
                Log.i(TAG, "Received perpesanan: "+laporans);

                try {
                    JSONObject rec = new JSONObject(laporans);

                    int laporan_id              = rec.isNull("id") ? 0 : rec.getInt("id");
                    int laporan_id_produk       = rec.isNull("id_produk") ? 0 : rec.getInt("id_produk");
                    String laporan_kode         = rec.isNull("kode") ? "" : rec.getString("kode");
                    String laporan_nama         = rec.isNull("nama") ? "" : rec.getString("nama");
                    String laporan_gambar       = rec.isNull("gambar") ? "" : rec.getString("gambar");
                    String laporan_pesan        = rec.isNull("pesan") ? "" : rec.getString("pesan");
                    String laporan_tanggal_jam  = rec.isNull("tanggal_jam") ? "" : rec.getString("tanggal_jam");
                    int laporan_from_id         = rec.isNull("from_id") ? 0 : rec.getInt("from_id");
                    String laporan_from_nama    = rec.isNull("from_nama") ? "" : rec.getString("from_nama");
                    String laporan_from_photo   = rec.isNull("from_photo") ? "" : rec.getString("from_photo");
                    int laporan_total_unread    = rec.isNull("total_unread") ? 0 : rec.getInt("total_unread");
                    perpesanan data_perpesanan = new perpesanan(laporan_id, laporan_id_produk, laporan_kode, laporan_nama, laporan_gambar, laporan_pesan, laporan_tanggal_jam, laporan_from_id, laporan_from_nama, laporan_from_photo, laporan_total_unread);

                    rec = new JSONObject(msgs);
                    int message_id          = rec.isNull("id")?0:rec.getInt("id");
                    String message_nama     = rec.isNull("nama")?"":rec.getString("nama");
                    String message_telepon  = rec.isNull("telepon")?"":rec.getString("telepon");
                    String message_photo    = rec.isNull("photo")?"":rec.getString("photo");
                    String message_datetime = rec.isNull("datetime")?"":rec.getString("datetime");
                    String message_pesan    = rec.isNull("message")?"":rec.getString("message");
                    Boolean message_is_self = rec.isNull("is_self")?false:rec.getBoolean("is_self");
                    Boolean message_is_sent = rec.isNull("is_sent")?false:rec.getBoolean("is_sent");
                    message data_message = new message(message_id, message_nama, message_telepon, message_photo, message_datetime, message_pesan, message_is_self, message_is_sent);

                    perpesanan current_perpesanan = CommonUtilities.getCurrentLaporan(getApplicationContext());
                    if(current_perpesanan.getId_produk()== data_perpesanan.getId_produk()) {
                        Intent intent = new Intent("gomocart.application.com.gomocart.NEW_MESSAGE");
                        intent.putExtra("message", data_message);
                        sendBroadcast(intent);
                    } else {
                        //int TotalUnread = CommonUtilities.getTotalUnread(getApplicationContext())+1;
                        //CommonUtilities.setTotalUnread(getApplicationContext(), TotalUnread);
                        //ShortcutBadger.applyCount(getApplicationContext(), TotalUnread);
                        sendBroadcast(new Intent("gomocart.application.com.gomocart.UPDATE_TOTAL_UNREAD"));
                        sendBroadcast(new Intent("gomocart.application.com.gomocart.RELOAD_LAPORAN_LIST"));

                        if(data_setting.getChat()) {
                            sendMessageNotification(getApplicationContext(), data_message, data_perpesanan);
                        }
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            if(tipe.equalsIgnoreCase("pemesanan") || tipe.equalsIgnoreCase("pembayaran")) {
                try {
                    JSONObject rec = new JSONObject(msgs);
                    String tanggal_jam = rec.isNull("tanggal_jam")?null:rec.getString("tanggal_jam");
                    String judul = rec.isNull("title")?null:rec.getString("title");
                    String konten = rec.isNull("konten")?null:rec.getString("konten");

                    notifikasi notif = new notifikasi(0, tanggal_jam, judul, konten, tipe);
                    new DatabaseHandler(getApplicationContext()).addNotifikasi(notif);
                    if(data_setting.getUpdate_pesanan()) {
                        sendNotification(getApplicationContext(), notif);
                    }

                    Intent i = new Intent("gomocart.application.com.gomocart.RELOAD_DATA_CART");
                    sendBroadcast(i);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if(tipe.equalsIgnoreCase("informasi")) {
                try {
                    JSONObject rec = new JSONObject(msgs);

                    int id = rec.isNull("id")?null:rec.getInt("id");
                    String judul = rec.isNull("judul")?null:rec.getString("judul");
                    String tanggal = rec.isNull("tanggal")?null:rec.getString("tanggal");
                    String header = rec.isNull("header")?null:rec.getString("header");
                    String konten = rec.isNull("konten")?null:rec.getString("konten");
                    String gambar = rec.isNull("gambar")?null:rec.getString("gambar");

                    informasi data = new informasi(id, tanggal, judul, header, konten, gambar);
                    notifikasi notif = new notifikasi(0, tanggal, judul, header, tipe);
                    new DatabaseHandler(getApplicationContext()).addNotifikasi(notif);
                    if(data_setting.getInformasi()) {
                        new JSONAsyncTask(getApplicationContext(), data).execute();
                    }

                    Intent i = new Intent("gomocart.application.com.gomocart.RELOAD_DATA_INFORMASI");
                    sendBroadcast(i);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            
        }

        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        /*Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 Request code, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.news_icon)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 ID of notification, notificationBuilder.build());*/
    }

    @Deprecated
    private static void sendMessageNotification(Context context, message data_message, perpesanan data_perpesanan) {
        Intent i = new Intent(context, MessageActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("perpesanan", data_perpesanan);

        String app_name = context.getResources().getString(R.string.app_name);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(app_name)
                .setTicker(data_message.getNama()+": "+data_message.getMessage())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setContentText(data_message.getMessage());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Notification n = mBuilder.build();
        n.defaults |= Notification.DEFAULT_SOUND;
        n.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(data_perpesanan.getId(), n);
    }

    @Deprecated
    private static void sendNotification(Context context, notifikasi notif) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("notifikasi", notif);
        i.putExtra("menu_select", 13);

        String app_name = context.getResources().getString(R.string.app_name);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(app_name)
                .setTicker(notif.getJudul())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setContentText(notif.getKonten());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Notification n = mBuilder.build();
        n.defaults |= Notification.DEFAULT_SOUND;
        n.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(0, n);
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Bitmap> {

        Context context;
        informasi get_informasi;

        public JSONAsyncTask(Context context, informasi get_informasi) {
            this.context = context;
            this.get_informasi = get_informasi;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {

            String url = CommonUtilities.SERVER_URL + "/store/androidLoadDetailInformasi.php";
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id", get_informasi.getId()+""));
            JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
            if(json!=null) {
                try {
                    int id = json.isNull("id")?null:json.getInt("id");
                    String tanggal = json.isNull("tgl")?null:json.getString("tgl");
                    String judul = json.isNull("judul")?null:json.getString("judul");
                    String konten = json.isNull("konten")?null:json.getString("konten");
                    String header = json.isNull("header")?null:json.getString("header");
                    String gambar = json.isNull("gambar")?null:json.getString("gambar");

                    get_informasi = new informasi(id, tanggal, judul, header, konten, gambar);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return get_informasi.getGambar().length()>0?CommonUtilities.getBitmap(CommonUtilities.SERVER_URL+"/uploads/informasi/"+get_informasi.getGambar()):null;
        }

        protected void onPostExecute(Bitmap result) {
            sendInformasiNotification(context, result, get_informasi);

        }
    }

    @Deprecated
    private static void sendInformasiNotification(Context context, Bitmap b, informasi data_informasi) {
        // Creates an explicit intent for an Activity in your app  
        // prepare intent which is triggered if the
        // notification is selected
        Intent i = new Intent(context, DetailInformasiActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("informasi", data_informasi);

        //Intent intent = new Intent(context, DetailActivity.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);

        String app_name = context.getResources().getString(R.string.app_name);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(app_name+": "+data_informasi.getJudul())
                .setTicker(app_name+": "+data_informasi.getJudul())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(b)
                .setContentIntent(pIntent)
                .setContentText(data_informasi.getKonten());

        NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
        bigPicStyle.bigPicture(b);
        bigPicStyle.setBigContentTitle(app_name+": "+data_informasi.getJudul());
        mBuilder.setStyle(bigPicStyle);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //  mBuilder.setOngoing(true);
        Notification n = mBuilder.build();
        n.defaults |= Notification.DEFAULT_SOUND;
        n.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(0, n);
    }
}