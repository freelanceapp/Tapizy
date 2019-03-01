package ibt.com.tapizy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Url table name
    private static final String TABLE_CHATBOT = "chatbot_table";
    // Url Table Columns names

    private static final String KEY_ID = "id";
    public static final String TABLE_RELATE_ID = "TABLE_RELATE_ID";
    public static final String TABLE_TYPE = "TABLE_TYPE";
    public static final String TABLE_TEXT = "TABLE_TEXT";

    public DatabaseHandler(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_URL_TABLE = "CREATE TABLE " + TABLE_CHATBOT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + TABLE_RELATE_ID + " TEXT," + TABLE_TYPE + " TEXT," + TABLE_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_URL_TABLE);
        Log.e("Table", CREATE_URL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATBOT);
        onCreate(db);
    }

   /*
    //Adding new Url
    public void addItemCart(ConversationList urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_RELATE_ID, urlModal.getRelateId());
        values.put(TABLE_TYPE, urlModal.getType());
        values.put(TABLE_TEXT, urlModal.getText());

        // Inserting InstructionsRow
        db.insert(TABLE_CHATBOT, null, values);
        db.close(); // Closing database connection
    }

    // Getting single url
    public ConversationList getSingleItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHATBOT, new String[]{KEY_ID, TABLE_RELATE_ID, TABLE_TYPE,
                        TABLE_TEXT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new ConversationList(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3));
    }

    //Getting all Url list
    public ArrayList<ConversationList> getAllUrlList() {
        ArrayList<ConversationList> urlModalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CHATBOT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ConversationList urlModal = new ConversationList();
                urlModal.setId(Integer.parseInt(cursor.getString(0)));
                urlModal.setRelateId(cursor.getString(1));
                urlModal.setText(cursor.getString(2));
                urlModal.setType(cursor.getString(3));
                urlModalList.add(urlModal);
            } while (cursor.moveToNext());
        }

        return urlModalList;
    }

    // Updating single urlModal
    public int updateUrl(ConversationList urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, urlModal.getId());
        values.put(TABLE_RELATE_ID, urlModal.getRelateId());
        values.put(TABLE_TYPE, urlModal.getType());
        values.put(TABLE_TEXT, urlModal.getText());

        int updateValue = db.update(TABLE_CHATBOT, values, KEY_ID + " = ?", new String[]{String.valueOf(urlModal.getId())});
        db.close();
        return updateValue;
    }

    // Deleting single url
    public void deleteContact(ProductDetail urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHATBOT, KEY_ID + " = ?",
                new String[]{String.valueOf(urlModal.getKeyId())});
        db.close();
    }

    public void deleteallCart(DatabaseHandler databaseCart) {
        SQLiteDatabase db = databaseCart.getWritableDatabase();
        db.execSQL("delete from " + TABLE_CHATBOT);
        db.close();
    }

    // Getting url Count
    public boolean getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CHATBOT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int i = cursor.getCount();
        cursor.close();
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    //check exist data
    public boolean verification(String _username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = -1;
        Cursor c = null;
        try {
            String query = "SELECT COUNT(*) FROM " + TABLE_CHATBOT + " WHERE " + TABLE_RELATE_ID + " = ?";
            c = db.rawQuery(query, new String[]{_username});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            return count > 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }*/
}