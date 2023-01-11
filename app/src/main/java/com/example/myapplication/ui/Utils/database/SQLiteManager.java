package com.example.myapplication.ui.Utils.database;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.ManageTopUps;
import com.example.myapplication.ui.Utils.ManageVisited;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.RatingManage;
import com.example.myapplication.ui.Utils.UsersManage;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "ProjBase";
    private static final int DATABASE_VERSION = 1;

    //

    private static final String USERS_TABLE_NAME = "Users";

    private static final String USERS_ID_FIELD = "id";
    private static final String USERS_EMAIL = "email";
    private static final String USERS_ROLE = "role";
    private static final String USERS_PASSWORD = "password";
    private static final String USERS_NAME = "name";
    private static final String USERS_LOCATION = "location";
    private static final String USERS_Gender = "gender";
    private static final String USERS_PHONE_NUMBER = "phoneNumber";
    private static final String USERS_BIRTHDAY = "birthday";
    private static final String USERS_ACTIVE_ORDER = "activeOrder";
    private static final String USERS_IMAGE = "image";
    private static final String USERS_OTP = "otp";

    //


    private static final String ORDERS_TABLE_NAME = "orders";

    private static final String ORDERS_USER_ID = "user_id";
    private static final String ORDERS_ACCEPTED_USER = "accept_user";
    private static final String ORDERS_ID_FIELD = "id";
    private static final String ORDERS_TITLE_FIELD = "title";
    private static final String ORDERS_DESC_FIELD = "desc";
    private static final String ORDERS_DATE = "date";
    private static final String ORDERS_PRICE = "price";
    private static final String ORDERS_TYPE = "type";
    private static final String ORDERS_CATEGORY = "category";
    private static final String ORDERS_STATUS = "status";

    //


    private static final String RATING_TABLE_NAME = "ratings";

    private static final String RATING_ID = "id";
    private static final String RATING_TO = "accept_user";
    private static final String RATING_FROM = "give_user";
    private static final String RATING_VALUE = "rating";
    private static final String RATING_DESCRIPTION = "description";


    private static final String CATEGORY_TABLE_NAME = "categories";

    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";


    private static final String WALLET_TABLE_NAME = "wallet";

    private static final String WALLET_ID = "id";
    private static final String WALLET_BANK_ACCOUNT = "bank";
    private static final String WALLET_USER_ID = "user_id";
    private static final String WALLET_BALANCE = "balance";
    private static final String WALLET_PASSWORD = "password";


    private static final String TopUp_TABLE_NAME = "topUps";

    private static final String TOPUP_ID = "id";
    private static final String TOPUP_WALLET_ID = "wallet_id";
    private static final String TOPUPS_BALANCE = "balance";
    private static final String TOPUPS_DATE = "date";


    private static final String VISITED_TABLE_NAME = "visits";

    private static final String VISITED_ID = "id";
    private static final String VISITED_USER = "user_id";
    private static final String VISITED_ORDER = "order_id";
    private static final String VISITED_STATUS = "status";


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            StringBuilder UserSql;
            UserSql = new StringBuilder()
                    .append("CREATE TABLE ").append(USERS_TABLE_NAME).append("(").append(USERS_ID_FIELD)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(USERS_NAME).append(" TEXT, ")
                    .append(USERS_EMAIL).append(" TEXT, ").append(USERS_PASSWORD).append(" TEXT, ")
                    .append(USERS_ROLE).append(" TEXT, ").append(USERS_BIRTHDAY).append(" TEXT, ")
                    .append(USERS_LOCATION).append(" TEXT, ").append(USERS_Gender).append(" TEXT, ")
                    .append(USERS_PHONE_NUMBER).append(" TEXT, ").append(USERS_ACTIVE_ORDER).append(" INT, ")
                    .append(USERS_IMAGE).append(" BLOB, ").append(USERS_OTP).append(" TEXT)");

            sqLiteDatabase.execSQL(UserSql.toString());
        } catch (SQLiteException e) {
            Log.e(TAG, "Error creating table1 database", e);
        }

        try {
            StringBuilder sql;
            sql = new StringBuilder()
                    .append("CREATE TABLE ").append(ORDERS_TABLE_NAME).append("(").append(ORDERS_ID_FIELD)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(ORDERS_USER_ID).append(" INT, ")
                    .append(ORDERS_CATEGORY).append(" TEXT, ").append(ORDERS_TYPE).append(" TEXT, ")
                    .append(ORDERS_TITLE_FIELD).append(" TEXT, ").append(ORDERS_DESC_FIELD).append(" TEXT, ")
                    .append(ORDERS_DATE).append(" TEXT, ").append(ORDERS_PRICE).append(" REAL, ")
                    .append(ORDERS_ACCEPTED_USER).append(" INT, ").append(ORDERS_STATUS).append(" INT) ");


            sqLiteDatabase.execSQL(sql.toString());
        } catch (SQLiteException e) {
            Log.e(TAG, "Error creating table2 database", e);
        }

        try {
            StringBuilder sql2;
            sql2 = new StringBuilder()
                    .append("CREATE TABLE ").append(RATING_TABLE_NAME).append("(").append(RATING_ID)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(RATING_TO).append(" INT, ")
                    .append(RATING_FROM).append(" INT, ").append(RATING_VALUE).append(" INT, ")
                    .append(RATING_DESCRIPTION).append(" TEXT) ");

            sqLiteDatabase.execSQL(sql2.toString());
        } catch (SQLiteException e) {
            Log.e(TAG, "Error creating table3 database", e);
        }


        try {
            StringBuilder sql3;
            sql3 = new StringBuilder()
                    .append("CREATE TABLE ").append(CATEGORY_TABLE_NAME).append("(").append(CATEGORY_ID)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(CATEGORY_NAME).append(" TEXT) ");

            sqLiteDatabase.execSQL(sql3.toString());
        } catch (SQLiteException e) {
            Log.e(TAG, "Error creating table4 database", e);
        }
        try {
            StringBuilder sql4;
            sql4 = new StringBuilder()
                    .append("CREATE TABLE ").append(WALLET_TABLE_NAME).append("(").append(WALLET_ID)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(WALLET_USER_ID).append(" INT, ")
                    .append(WALLET_BANK_ACCOUNT).append(" TEXT, ").append(WALLET_BALANCE).append(" REAL, ")
                    .append(WALLET_PASSWORD).append(" TEXT) ");

            sqLiteDatabase.execSQL(sql4.toString());
        } catch (SQLiteException e) {
            Log.e(TAG, "Error creating table5 database", e);
        }

        try {
            StringBuilder sql5;
            sql5 = new StringBuilder()
                    .append("CREATE TABLE ").append(TopUp_TABLE_NAME).append("(").append(TOPUP_ID)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(TOPUP_WALLET_ID).append(" INT, ")
                    .append(TOPUPS_BALANCE).append(" REAL, ").append(TOPUPS_DATE).append(" TEXT) ");

            sqLiteDatabase.execSQL(sql5.toString());
        } catch (SQLiteException e) {
            Log.e(TAG, "Error creating table6 database", e);
        }

        try {
            StringBuilder sql6;
            sql6 = new StringBuilder()
                    .append("CREATE TABLE ").append(VISITED_TABLE_NAME).append("(").append(VISITED_ID)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(VISITED_USER).append(" INT, ")
                    .append(VISITED_ORDER).append(" INT, ").append(VISITED_STATUS).append(" INT) ");

            sqLiteDatabase.execSQL(sql6.toString());
        } catch (SQLiteException e) {
            Log.e(TAG, "Error creating table7 database", e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addUserToDatabase(UsersManage note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(USERS_ID_FIELD, note.getId());
            contentValues.put(USERS_EMAIL, note.getEmail());
            contentValues.put(USERS_PASSWORD, note.getPassword());
            contentValues.put(USERS_ROLE, note.getRole());
            contentValues.put(USERS_NAME, note.getName());
            contentValues.put(USERS_LOCATION, note.getLocation());
            contentValues.put(USERS_PHONE_NUMBER, note.getPhoneNumber());
            contentValues.put(USERS_Gender, note.getGender());
            contentValues.put(USERS_BIRTHDAY, note.getBirthday());
            contentValues.put(USERS_ACTIVE_ORDER, note.getActiveOrder());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap image = note.getImage();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imageByteArray = byteArrayOutputStream.toByteArray();
            contentValues.put(USERS_IMAGE , imageByteArray);
            contentValues.put(USERS_OTP, note.getOtp());

            sqLiteDatabase.insert(USERS_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error adding data to database", e);
        }

    }

    public void addOrderToDatabase(OrdersManage note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(ORDERS_ID_FIELD, note.getId());
            contentValues.put(ORDERS_USER_ID, note.getUser_id());
            contentValues.put(ORDERS_ACCEPTED_USER, note.getAccepted_user_id());
            contentValues.put(ORDERS_CATEGORY, note.getCategory());
            contentValues.put(ORDERS_TYPE, note.getType());
            contentValues.put(ORDERS_TITLE_FIELD, note.getTitle());
            contentValues.put(ORDERS_DESC_FIELD, note.getDescription());
            contentValues.put(ORDERS_PRICE, note.getPrice());
            contentValues.put(ORDERS_DATE, note.getDate());
            contentValues.put(ORDERS_STATUS, note.getStatus());

            sqLiteDatabase.insert(ORDERS_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error adding data2 to database", e);
        }
    }

    public void addRatingToDatabase(RatingManage note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(RATING_ID, note.getId());
            contentValues.put(RATING_TO, note.getTo_user());
            contentValues.put(RATING_FROM, note.getFrom_user());
            contentValues.put(RATING_VALUE, note.getValue());
            contentValues.put(RATING_DESCRIPTION, note.getDesc());

            sqLiteDatabase.insert(RATING_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error adding data3 to database", e);
        }
    }

    public void addCategoryToDatabase(CategoryManage note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(CATEGORY_ID, note.getId());
            contentValues.put(CATEGORY_NAME, note.getText());

            sqLiteDatabase.insert(CATEGORY_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error adding data4 to database", e);
        }
    }

    public void addWallet(ManageWallet note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(WALLET_ID, note.getId());
            contentValues.put(WALLET_USER_ID, note.getUser_id());
            contentValues.put(WALLET_BANK_ACCOUNT, note.getBankAccountNumber());
            contentValues.put(WALLET_BALANCE, note.getBalance());
            contentValues.put(WALLET_PASSWORD, note.getPassword());


            sqLiteDatabase.insert(WALLET_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error adding data5 to database", e);
        }
    }

    public void addTopUPS(ManageTopUps note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(TOPUP_ID, note.getId());
            contentValues.put(TOPUP_WALLET_ID, note.getWallet_id());
            contentValues.put(TOPUPS_BALANCE, note.getValue());
            contentValues.put(TOPUPS_DATE, note.getString());


            sqLiteDatabase.insert(TopUp_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error adding data6 to database", e);
        }
    }

    public void addVisits(ManageVisited note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(VISITED_ID, note.getId());
            contentValues.put(VISITED_USER, note.getUser_id());
            contentValues.put(VISITED_ORDER, note.getOrder_id());
            contentValues.put(VISITED_STATUS, note.getStatus());


            sqLiteDatabase.insert(VISITED_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error adding data7 to database", e);
        }
    }

    public void populateUserListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + USERS_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String name = result.getString(1);
                    String email = result.getString(2);
                    String password = result.getString(3);
                    String role = result.getString(4);
                    String birthday = result.getString(5);
                    String location = result.getString(6);
                    String gender = result.getString(7);
                    String phone = result.getString(8);
                    int active = result.getInt(9);
                    byte[] image = result.getBlob(10);
                    String otp = result.getString(11);
                    Bitmap image1 = BitmapFactory.decodeByteArray(image, 0, image.length);

                    UsersManage note = new UsersManage(id, role, email, password, birthday, name, location, gender, phone, active,image1, otp);
                    UsersManage.UsersList.add(note);
                }
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void populateOrderListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + ORDERS_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    int user_id = result.getInt(1);
                    String category = result.getString(2);
                    String type = result.getString(3);
                    String title = result.getString(4);
                    String desc = result.getString(5);
                    String date = result.getString(6);
                    double price = result.getDouble(7);
                    int accepted_user_id = result.getInt(8);
                    int status = result.getInt(9);
                    OrdersManage order = new OrdersManage(id, user_id, accepted_user_id, type, title, desc, price, date, status, category);
                    OrdersManage.orderArrayList.add(order);
                }
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void populateRatingListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + RATING_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    int to_user = result.getInt(1);
                    int from_user = result.getInt(2);
                    int value = result.getInt(3);
                    String desc = result.getString(4);
                    RatingManage note = new RatingManage(id, to_user, from_user, value, desc);
                    RatingManage.ratingsArrayList.add(note);
                }
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void populateCategoryListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + CATEGORY_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String name = result.getString(1);
                    CategoryManage note = new CategoryManage(id, name);
                    CategoryManage.categoryManages.add(note);
                }
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void populateWalletArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + WALLET_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    int user_id = result.getInt(1);
                    String bank = result.getString(2);
                    float balance = result.getFloat(3);
                    String password = result.getString(4);
                    ManageWallet note = new ManageWallet(id, bank, user_id, balance, password);
                    ManageWallet.WalletList.add(note);
                }
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void populateTopUps() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TopUp_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    int wallet_id = result.getInt(1);
                    float balance = result.getFloat(2);
                    String date = result.getString(3);
                    ManageTopUps note = new ManageTopUps(id, wallet_id, balance, date);
                    ManageTopUps.TopUpsList.add(note);
                }
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void populateVisitsListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + VISITED_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    int user_id = result.getInt(1);
                    int order_id = result.getInt(2);
                    int status = result.getInt(3);
                    ManageVisited note = new ManageVisited(id, user_id, order_id, status);
                    ManageVisited.VisitsList.add(note);
                }
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void deleteUser(int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            sqLiteDatabase.delete(USERS_TABLE_NAME, USERS_ID_FIELD + "=?", new String[]{String.valueOf(id)});
            sqLiteDatabase.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void deleteOrder(int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            sqLiteDatabase.delete(ORDERS_TABLE_NAME, ORDERS_ID_FIELD + "=?", new String[]{String.valueOf(id)});
            sqLiteDatabase.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void UpdateUser(UsersManage note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USERS_ID_FIELD, note.getId());
            contentValues.put(USERS_EMAIL, note.getEmail());
            contentValues.put(USERS_PASSWORD, note.getPassword());
            contentValues.put(USERS_ROLE, note.getRole());
            contentValues.put(USERS_NAME, note.getName());
            contentValues.put(USERS_LOCATION, note.getLocation());
            contentValues.put(USERS_PHONE_NUMBER, note.getPhoneNumber());
            contentValues.put(USERS_Gender, note.getGender());
            contentValues.put(USERS_BIRTHDAY, note.getBirthday());
            contentValues.put(USERS_ACTIVE_ORDER, note.getActiveOrder());
            //add for OTP -- for user to update their password
            sqLiteDatabase.update(USERS_TABLE_NAME, contentValues, USERS_ID_FIELD + " =? ", new String[]{String.valueOf(note.getId())});
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void UpdateOrder(OrdersManage note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ORDERS_ID_FIELD, note.getId());
            contentValues.put(ORDERS_USER_ID, note.getUser_id());
            contentValues.put(ORDERS_ACCEPTED_USER, note.getAccepted_user_id());
            contentValues.put(ORDERS_CATEGORY, note.getCategory());
            contentValues.put(ORDERS_TYPE, note.getType());
            contentValues.put(ORDERS_TITLE_FIELD, note.getTitle());
            contentValues.put(ORDERS_DESC_FIELD, note.getDescription());
            contentValues.put(ORDERS_PRICE, note.getPrice());
            contentValues.put(ORDERS_DATE, note.getDate());
            contentValues.put(ORDERS_STATUS, note.getStatus());

            sqLiteDatabase.update(ORDERS_TABLE_NAME, contentValues, ORDERS_ID_FIELD + " =? ", new String[]{String.valueOf(note.getId())});
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void UpdateWallet(ManageWallet note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(WALLET_ID, note.getId());
            contentValues.put(WALLET_USER_ID, note.getUser_id());
            contentValues.put(WALLET_BANK_ACCOUNT, note.getBankAccountNumber());
            contentValues.put(WALLET_BALANCE, note.getBalance());
            contentValues.put(WALLET_PASSWORD, note.getPassword());

            sqLiteDatabase.update(WALLET_TABLE_NAME, contentValues, WALLET_ID + " =? ", new String[]{String.valueOf(note.getId())});
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }

    public void UpdateVisit(ManageVisited note) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(VISITED_ID, note.getId());
            contentValues.put(VISITED_USER, note.getUser_id());
            contentValues.put(VISITED_ORDER, note.getOrder_id());
            contentValues.put(VISITED_STATUS, note.getStatus());

            sqLiteDatabase.update(VISITED_TABLE_NAME, contentValues, VISITED_ID + " =? ", new String[]{String.valueOf(note.getId())});
        } catch (SQLiteException e) {
            Log.e(TAG, "Error reading data to database", e);
        }
    }
}

















