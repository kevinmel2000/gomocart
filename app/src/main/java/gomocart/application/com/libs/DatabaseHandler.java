package gomocart.application.com.libs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import gomocart.application.com.model.alamat;
import gomocart.application.com.model.grandtotal;
import gomocart.application.com.model.notifikasi;
import gomocart.application.com.model.order;
import gomocart.application.com.model.produk;

public class DatabaseHandler extends SQLiteOpenHelper {

	Context context;
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAMA = "gomocart";
	public static final String TABLE_CART = "cart";
	public static final String TABLE_WISHLIST = "wishlist";
	public static final String TABLE_ALAMAT = "alamat";
	public static final String TABLE_GRANDTOTAL = "grandtotal";
	public static final String TABLE_ORDER = "orders";
	public static final String TABLE_NOTIFIKASI = "inbox";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAMA, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void createTable() {
		SQLiteDatabase db = this.getWritableDatabase();

		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CART + "(" +
				"_id INTEGER PRIMARY KEY, " +
				"id INTEGER, " +
				"kode TEXT, " +
				"nama TEXT, " +
				"id_category INTEGER, " +
				"category_name TEXT, " +
				"penjelasan TEXT, " +
				"foto1_produk TEXT," +
				"harga_beli DOUBLE," +
				"harga_jual DOUBLE," +
				"harga_diskon DOUBLE," +
				"persen_diskon TEXT," +
				"berat INTEGER," +
				"list_ukuran TEXT," +
				"ukuran TEXT," +
				"list_warna TEXT," +
				"warna TEXT," +
				"qty INTEGER," +
				"max_qty INTEGER," +
				"minimum_pesan INTEGER," +
				"produk_promo INTEGER," +
				"produk_featured INTEGER," +
				"produk_terbaru INTEGER," +
				"produk_preorder INTEGER," +
				"produk_soldout INTEGER," +
				"produk_grosir INTEGER," +
				"produk_freeongkir INTEGER," +
				"rating INTEGER," +
				"responden INTEGER," +
				"review INTEGER," +
				"subtotal DOUBLE," +
				"grandtotal DOUBLE)");

		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALAMAT);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ALAMAT+ "(" +

				"nama TEXT, " +
				"alamat TEXT, " +
				"province_id TEXT, " +
				"province TEXT, " +
				"city_id TEXT," +
				"city_name TEXT," +
				"subdistrict_id TEXT," +
				"subdistrict_name TEXT," +
				"kode_pos TEXT," +
				"no_hp TEXT," +

				"is_dropship INTEGER," +
				"dropship_name TEXT," +
				"dropship_phone TEXT," +
				"email_notifikasi TEXT, " +

				"kurir_id INTEGER," +
				"kurir TEXT," +
				"layanan TEXT," +
				"etd TEXT," +
				"tarif TEXT," +
				"nominal INTEGER)");

		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WISHLIST+ "(" +
				"_id INTEGER PRIMARY KEY, " +
				"id INTEGER, " +
				"kode TEXT, " +
				"nama TEXT, " +
				"id_category INTEGER, " +
				"category_name TEXT, " +
				"penjelasan TEXT, " +
				"foto1_produk TEXT," +
				"harga_beli DOUBLE," +
				"harga_jual DOUBLE," +
				"harga_diskon DOUBLE," +
				"persen_diskon TEXT," +
				"berat INTEGER," +
				"list_ukuran TEXT," +
				"ukuran TEXT," +
				"list_warna TEXT," +
				"warna TEXT," +
				"qty INTEGER," +
				"max_qty INTEGER," +
				"minimum_pesan INTEGER," +
				"produk_promo INTEGER," +
				"produk_featured INTEGER," +
				"produk_terbaru INTEGER," +
				"produk_preorder INTEGER," +
				"produk_soldout INTEGER," +
				"produk_grosir INTEGER," +
				"rating INTEGER," +
				"responden INTEGER," +
				"review INTEGER," +
				"subtotal DOUBLE," +
				"grandtotal DOUBLE," +
				"preorder INTEGER)");

		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRANDTOTAL);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_GRANDTOTAL+ "(" +
				"total DOUBLE, " +
				"diskon DOUBLE, " +
				"sub_total DOUBLE, " +
				"voucher DOUBLE, " +
				"pengiriman DOUBLE, " +
				"grand_total DOUBLE)");

		/*db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOUCHER);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_VOUCHER+ "(" +
				"kode TEXT, " +
				"nominal DOUBLE," +
				"tipe_voucher TEXT, " +
				"jenis_voucher TEXT)");*/

		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ORDER+ "(" +
				"no_transaksi TEXT, " +
				"tgl_transaksi TEXT, " +
				"pembayaran INTEGER, " +
				"nama TEXT, " +
				"qty INTEGER, " +
				"jumlah DOUBEL, " +
				"gambar TEXT," +
				"estimasi TEXT," +
				"noresi TEXT," +
				"status INTEGER," +
				"user_id INTEGER)");

		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFIKASI);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFIKASI + "(" +
				"id INTEGER PRIMARY KEY, " +
				"tanggal_jam TEXT," +
				"judul TEXT," +
				"konten TEXT," +
				"tipe TEXT)");

		db.close();
	}

	public int getRowCount(String TABLE_NAME) {
		String countQuery = "SELECT COUNT(*) AS TOTAL FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.moveToFirst();
		int rowCount = cursor.getCount()>0?cursor.getInt(0):0;

		db.close();
		cursor.close();

		return rowCount;
	}

	public void insertCartlist(ArrayList<produk> cart_items) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		for(produk cart_item: cart_items) {
			values.put("_id", cart_item.getId());
			values.put("id", cart_item.getId());
			values.put("kode", cart_item.getKode());
			values.put("nama", cart_item.getNama());
			values.put("id_category", cart_item.getId_category());
			values.put("category_name", cart_item.getCategory_name());
			values.put("penjelasan", cart_item.getPenjelasan());
			values.put("foto1_produk", cart_item.getFoto1_produk());
			values.put("harga_beli", cart_item.getHarga_beli());
			values.put("harga_jual", cart_item.getHarga_jual());
			values.put("harga_diskon", cart_item.getHarga_diskon());
			values.put("persen_diskon", cart_item.getPersen_diskon());
			values.put("berat", cart_item.getBerat());
			values.put("list_ukuran", cart_item.getList_ukuran());
			values.put("ukuran", cart_item.getUkuran());
			values.put("list_warna", cart_item.getList_warna());
			values.put("warna", cart_item.getWarna());
			values.put("qty", cart_item.getQty());
			values.put("max_qty", cart_item.getMax_qty());
			values.put("minimum_pesan", cart_item.getMinimum_pesan());
			values.put("produk_promo", cart_item.getProduk_promo());
			values.put("produk_featured", cart_item.getProduk_featured());
			values.put("produk_terbaru", cart_item.getProduk_terbaru());
			values.put("produk_preorder", cart_item.getProduk_preorder());
			values.put("produk_soldout", cart_item.getProduk_soldout());
			values.put("produk_grosir", cart_item.getProduk_grosir());
			values.put("rating", cart_item.getRating());
			values.put("responden", cart_item.getTotal_responden());
			values.put("review", cart_item.getTotal_review());
			values.put("subtotal", cart_item.getSubtotal());
			values.put("grandtotal", cart_item.getGrandtotal());
			db.insert(TABLE_CART, null, values);
		}
		db.close();
	}

	public void insertCartlist(produk cart_item) {
		int total_qty = getTotalCartItem(cart_item.getId(), cart_item.getUkuran(), cart_item.getWarna());

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("_id", cart_item.getId());
		values.put("id", cart_item.getId());
		values.put("kode", cart_item.getKode());
		values.put("nama", cart_item.getNama());
		values.put("id_category", cart_item.getId_category());
		values.put("category_name", cart_item.getCategory_name());
		values.put("penjelasan", cart_item.getPenjelasan());
		values.put("foto1_produk", cart_item.getFoto1_produk());
		values.put("harga_beli", cart_item.getHarga_beli());
		values.put("harga_jual", cart_item.getHarga_jual());
		values.put("harga_diskon", cart_item.getHarga_diskon());
		values.put("persen_diskon", cart_item.getPersen_diskon());
		values.put("berat", cart_item.getBerat());
		values.put("list_ukuran", cart_item.getList_ukuran());
		values.put("ukuran", cart_item.getUkuran());
		values.put("list_warna", cart_item.getList_warna());
		values.put("warna", cart_item.getWarna());
		values.put("qty", cart_item.getQty());
		values.put("max_qty", cart_item.getMax_qty());
		values.put("minimum_pesan", cart_item.getMinimum_pesan());
		values.put("produk_promo", cart_item.getProduk_promo());
		values.put("produk_featured", cart_item.getProduk_featured());
		values.put("produk_terbaru", cart_item.getProduk_terbaru());
		values.put("produk_preorder", cart_item.getProduk_preorder());
		values.put("produk_soldout", cart_item.getProduk_soldout());
		values.put("produk_grosir", cart_item.getProduk_grosir());
		values.put("rating", cart_item.getRating());
		values.put("responden", cart_item.getTotal_responden());
		values.put("review", cart_item.getTotal_review());
		values.put("subtotal", cart_item.getSubtotal());
		values.put("grandtotal", cart_item.getGrandtotal());

		if(total_qty==0){
			db.insert(TABLE_CART, null, values);
		} else {
			values.put("qty", cart_item.getQty()+total_qty);
			db.update(TABLE_CART, values, "id=? AND ukuran=? AND warna=?", new String[]{ String.valueOf(cart_item.getId()), cart_item.getUkuran(), cart_item.getWarna() });
		}
		db.close();
	}

	public void updateCartlist(produk cart_item) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("_id", cart_item.getId());
		values.put("id", cart_item.getId());
		values.put("kode", cart_item.getKode());
		values.put("nama", cart_item.getNama());
		values.put("id_category", cart_item.getId_category());
		values.put("category_name", cart_item.getCategory_name());
		values.put("penjelasan", cart_item.getPenjelasan());
		values.put("foto1_produk", cart_item.getFoto1_produk());
		values.put("harga_beli", cart_item.getHarga_beli());
		values.put("harga_jual", cart_item.getHarga_jual());
		values.put("harga_diskon", cart_item.getHarga_diskon());
		values.put("persen_diskon", cart_item.getPersen_diskon());
		values.put("berat", cart_item.getBerat());
		values.put("list_ukuran", cart_item.getList_ukuran());
		values.put("ukuran", cart_item.getUkuran());
		values.put("list_warna", cart_item.getList_warna());
		values.put("warna", cart_item.getWarna());
		values.put("qty", cart_item.getQty());
		values.put("max_qty", cart_item.getMax_qty());
		values.put("minimum_pesan", cart_item.getMinimum_pesan());
		values.put("produk_promo", cart_item.getProduk_promo());
		values.put("produk_featured", cart_item.getProduk_featured());
		values.put("produk_terbaru", cart_item.getProduk_terbaru());
		values.put("produk_preorder", cart_item.getProduk_preorder());
		values.put("produk_soldout", cart_item.getProduk_soldout());
		values.put("produk_grosir", cart_item.getProduk_grosir());
		values.put("rating", cart_item.getRating());
		values.put("responden", cart_item.getTotal_responden());
		values.put("review", cart_item.getTotal_review());
		values.put("subtotal", cart_item.getSubtotal());
		values.put("grandtotal", cart_item.getGrandtotal());

		db.update(TABLE_CART, values, "_id=?", new String[]{ String.valueOf(cart_item.get_id()) });

		db.close();
	}

	public alamat getAlamat() {

		alamat select_alamat = new alamat(0, "", "", 0, "", 0, "", 0, "", "", "", false, false, "", "", "");
		try {
			String sql = "SELECT nama, alamat, province_id, province, city_id, city_name, subdistrict_id, subdistrict_name, kode_pos, no_hp, is_dropship, dropship_name, dropship_phone, email_notifikasi, kurir_id, kurir, layanan, etd, tarif, nominal FROM " + TABLE_ALAMAT;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					select_alamat = new alamat(0, cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), false, cursor.getInt(10)==1, cursor.getString(11), cursor.getString(12), cursor.getString(13));
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return select_alamat;
	}

	public void insertAlamat(alamat data) {

		deleteAlamat();

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("nama", data.getNama());
		values.put("alamat", data.getAlamat());
		values.put("province_id", data.getProvince_id());
		values.put("province", data.getProvince());
		values.put("city_id", data.getCity_id());
		values.put("city_name", data.getCity_name());
		values.put("subdistrict_id", data.getSubdistrict_id());
		values.put("subdistrict_name", data.getSubdistrict_name());
		values.put("kode_pos", data.getKode_pos());
		values.put("no_hp", data.getNo_hp());

		values.put("is_dropship", data.getIs_dropship()?1:0);
		values.put("dropship_name", data.getDropship_name());
		values.put("dropship_phone", data.getDropship_phone());
		values.put("email_notifikasi", data.getEmail_notifikasi());

		db.insert(TABLE_ALAMAT, null, values);
		db.close();
	}

	public void deleteAlamat() {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ALAMAT, null, null);
		db.close();
	}

	public grandtotal getGrandtotal() {

		grandtotal gtotal = new grandtotal(0, 0, 0, 0, 0, 0);
		try {
			String sql = "SELECT total, diskon, sub_total, voucher, pengiriman, grand_total FROM " + TABLE_GRANDTOTAL;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					gtotal = new grandtotal(cursor.getDouble(0), cursor.getDouble(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5));
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gtotal;
	}

	public void insertGrandtotal(grandtotal data) {

		deleteGrandtotal();

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("total", data.getTotal());
		values.put("diskon", data.getDiskon());
		values.put("sub_total", data.getSub_total());
		values.put("voucher", data.getVoucher());
		values.put("pengiriman", data.getPengiriman());
		values.put("grand_total", data.getGrand_total());

		db.insert(TABLE_GRANDTOTAL, null, values);
		db.close();
	}

	public void deleteGrandtotal() {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_GRANDTOTAL, null, null);
		db.close();
	}



	public void deleteCartlist(produk cart_item) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CART, "_id=?", new String[] { String.valueOf(cart_item.get_id()) });
		db.close();
	}

	public void deleteCartlist() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CART, null, null);
		db.close();
	}

	public int getTotalBeratCart() {
		int result = 0;

		try {
			String sql = "SELECT SUM(berat*qty) FROM " + TABLE_CART;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getTotalCart() {
		int result = 0;

		try {
			String sql = "SELECT SUM(qty) FROM " + TABLE_CART;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getTotalCartItem(int id, String ukuran, String warna) {
		int result = 0;

		try {
			String sql = "SELECT SUM(qty) FROM " + TABLE_CART + " WHERE id="+id+ " AND ukuran='"+ukuran+"' AND warna='"+warna+"'";
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<produk> getCartlist() {
		ArrayList<produk> result = new ArrayList<produk>();

		try {
			String sql = "SELECT _id, id, kode, nama, id_category, category_name, penjelasan, foto1_produk, harga_beli, harga_jual, harga_diskon, persen_diskon, berat, list_ukuran, ukuran, list_warna, warna, qty, max_qty, minimum_pesan, produk_promo, produk_featured, produk_terbaru, produk_preorder, produk_soldout, produk_grosir, produk_freeongkir, rating, responden, review, subtotal, grandtotal FROM " + TABLE_CART;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					produk prod = new produk(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10), cursor.getInt(11), cursor.getInt(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getInt(17), cursor.getInt(18), cursor.getInt(19), false, cursor.getInt(20), cursor.getInt(21), cursor.getInt(22), cursor.getInt(23), cursor.getInt(24), cursor.getInt(25), cursor.getInt(26), cursor.getInt(27), cursor.getInt(28), cursor.getInt(29));
					prod.set_id(cursor.getInt(0));
					prod.setSubtotal(cursor.getDouble(29));
					prod.setGrandtotal(cursor.getDouble(30));

					result.add(prod);
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getStringCartlist() {
		String result = "";

		try {
			String sql = "SELECT _id, id, ukuran, warna, qty FROM " + TABLE_CART;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					result+=(i>0?"\n":"")+cursor.getString(0) + "\t" + cursor.getString(1) + "\t" + cursor.getString(2) + "\t" + cursor.getString(3) + "\t" + cursor.getString(4);
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getIdWishlist(int id) {
		int result = 0; //jika return 0 maka data bukan wishlist;
		try {
			String sql = "SELECT _id FROM " + TABLE_WISHLIST + " WHERE id="+id;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getIdWishlist(produk item) {
		int result = 0; //jika return 0 maka data bukan wishlist;
		try {
			String sql = "SELECT _id FROM " + TABLE_WISHLIST + " WHERE id="+item.getId();
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void insertWishlist(ArrayList<produk> wishlist_items) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		for(produk wishlist_item: wishlist_items) {
			values.put("_id", wishlist_item.getId());
			values.put("id", wishlist_item.getId());
			values.put("kode", wishlist_item.getKode());
			values.put("nama", wishlist_item.getNama());
			values.put("id_category", wishlist_item.getId_category());
			values.put("category_name", wishlist_item.getCategory_name());
			values.put("penjelasan", wishlist_item.getPenjelasan());
			values.put("foto1_produk", wishlist_item.getFoto1_produk());
			values.put("harga_beli", wishlist_item.getHarga_beli());
			values.put("harga_jual", wishlist_item.getHarga_jual());
			values.put("harga_diskon", wishlist_item.getHarga_diskon());
			values.put("persen_diskon", wishlist_item.getPersen_diskon());
			values.put("berat", wishlist_item.getBerat());
			values.put("list_ukuran", wishlist_item.getList_ukuran());
			values.put("ukuran", wishlist_item.getUkuran());
			values.put("list_warna", wishlist_item.getList_warna());
			values.put("warna", wishlist_item.getWarna());
			values.put("qty", wishlist_item.getQty());
			values.put("max_qty", wishlist_item.getMax_qty());
			values.put("minimum_pesan", wishlist_item.getMinimum_pesan());
			values.put("produk_promo", wishlist_item.getProduk_promo());
			values.put("produk_featured", wishlist_item.getProduk_featured());
			values.put("produk_terbaru", wishlist_item.getProduk_terbaru());
			values.put("produk_preorder", wishlist_item.getProduk_preorder());
			values.put("produk_soldout", wishlist_item.getProduk_soldout());
			values.put("produk_grosir", wishlist_item.getProduk_grosir());
			values.put("rating", wishlist_item.getRating());
			values.put("responden", wishlist_item.getTotal_responden());
			values.put("review", wishlist_item.getTotal_review());
			values.put("subtotal", wishlist_item.getSubtotal());
			values.put("grandtotal", wishlist_item.getGrandtotal());

			db.insert(TABLE_WISHLIST, null, values);
		}
		db.close();
	}

	public void insertWishlist(produk wishlist_item) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("_id", wishlist_item.getId());
		values.put("id", wishlist_item.getId());
		values.put("kode", wishlist_item.getKode());
		values.put("nama", wishlist_item.getNama());
		values.put("id_category", wishlist_item.getId_category());
		values.put("category_name", wishlist_item.getCategory_name());
		values.put("penjelasan", wishlist_item.getPenjelasan());
		values.put("foto1_produk", wishlist_item.getFoto1_produk());
		values.put("harga_beli", wishlist_item.getHarga_beli());
		values.put("harga_jual", wishlist_item.getHarga_jual());
		values.put("harga_diskon", wishlist_item.getHarga_diskon());
		values.put("persen_diskon", wishlist_item.getPersen_diskon());
		values.put("berat", wishlist_item.getBerat());
		values.put("list_ukuran", wishlist_item.getList_ukuran());
		values.put("ukuran", wishlist_item.getUkuran());
		values.put("list_warna", wishlist_item.getList_warna());
		values.put("warna", wishlist_item.getWarna());
		values.put("qty", wishlist_item.getQty());
		values.put("max_qty", wishlist_item.getMax_qty());
		values.put("minimum_pesan", wishlist_item.getMinimum_pesan());
		values.put("produk_promo", wishlist_item.getProduk_promo());
		values.put("produk_featured", wishlist_item.getProduk_featured());
		values.put("produk_terbaru", wishlist_item.getProduk_terbaru());
		values.put("produk_preorder", wishlist_item.getProduk_preorder());
		values.put("produk_soldout", wishlist_item.getProduk_soldout());
		values.put("produk_grosir", wishlist_item.getProduk_grosir());
		values.put("rating", wishlist_item.getRating());
		values.put("responden", wishlist_item.getTotal_responden());
		values.put("review", wishlist_item.getTotal_review());
		values.put("subtotal", wishlist_item.getSubtotal());
		values.put("grandtotal", wishlist_item.getGrandtotal());

		db.insert(TABLE_WISHLIST, null, values);
		db.close();
	}

	public void deleteWishlist() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WISHLIST, null, null);
		db.close();
	}

	public void deleteWishlist(produk data) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WISHLIST, "id=?", new String[] { String.valueOf(data.getId()) });
		db.close();
	}

	public int getTotalWishlist() {
		int result = 0;

		try {
			String sql = "SELECT COUNT(*) FROM " + TABLE_WISHLIST;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<produk> getWishlist() {
		ArrayList<produk> result = new ArrayList<produk>();

		try {
			String sql = "SELECT _id, id, kode, nama, id_category, category_name, penjelasan, foto1_produk, harga_beli, harga_jual, harga_diskon, persen_diskon, berat, list_ukuran, ukuran, list_warna, warna, qty, max_qty, minimum_pesan, produk_promo, produk_featured, produk_terbaru, produk_preorder, produk_soldout, produk_grosir, produk_freeongkir, rating, responden, review, subtotal, grandtotal FROM " + TABLE_WISHLIST;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					produk prod = new produk(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10), cursor.getInt(11), cursor.getInt(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getInt(17), cursor.getInt(18),  cursor.getInt(19), false, cursor.getInt(20), cursor.getInt(21), cursor.getInt(22), cursor.getInt(23), cursor.getInt(24), cursor.getInt(25), cursor.getInt(26), cursor.getInt(27), cursor.getInt(28), cursor.getInt(29));
					prod.set_id(cursor.getInt(0));
					prod.setSubtotal(cursor.getDouble(29));
					prod.setGrandtotal(cursor.getDouble(30));
					result.add(prod);
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getStringWishlist() {
		String result = "";

		try {
			String sql = "SELECT _id, id, ukuran, warna, qty FROM " + TABLE_WISHLIST;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					result+=(i>0?"\n":"")+cursor.getString(0) + "\t" + cursor.getString(1) + "\t" + cursor.getString(2) + "\t" + cursor.getString(3) + "\t" + cursor.getString(4);
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void clearOrderlist() {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ORDER, null, null);
		db.close();
	}

	public void deleteOrderlist(String no_transaksi) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ORDER, "no_transaksi=?", new String[] {no_transaksi});
		db.close();
	}

	public void insertOrderlist(order order_item) {

		deleteOrderlist(order_item.getNo_transaksi());

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("no_transaksi", order_item.getNo_transaksi());
		values.put("tgl_transaksi", order_item.getTgl_transaksi());
		values.put("pembayaran", order_item.getPembayaran());
		values.put("nama", order_item.getNama());
		values.put("qty", order_item.getQty());
		values.put("jumlah", order_item.getJumlah());
		values.put("estimasi", order_item.getEstimasi());
		values.put("noresi", order_item.getNoresi());
		values.put("gambar", order_item.getGambar());
		values.put("status", order_item.getStatus());
		values.put("user_id", order_item.getUser_id());

		db.insert(TABLE_ORDER, null, values);
		db.close();
	}

	public String getStringOrderlist() {
		String result = "";

		try {
			String sql = "SELECT no_transaksi FROM " + TABLE_ORDER;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					result+=(i>0?",":"")+"'" + cursor.getString(0) + "'";
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<notifikasi> getListNotifikasi(int page) {
		ArrayList<notifikasi> result = new ArrayList<notifikasi>();
		try {
			int limit = 10;
			int start = (page-1)*limit;
			String sql = "SELECT id, tanggal_jam, judul, konten, tipe FROM " + TABLE_NOTIFIKASI+ " ORDER BY id DESC LIMIT "+start+", "+limit;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					notifikasi n = new notifikasi(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
					result.add(n);
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


	public void addNotifikasi(notifikasi data) {
		while(true) {
			try {
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("tanggal_jam", data.getTanggal_jam());
				values.put("judul", data.getJudul());
				values.put("konten", data.getKonten());

				db.insert(TABLE_NOTIFIKASI, null, values);
				db.close();

				return;
			} catch(Exception e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
