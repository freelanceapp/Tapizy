package infobite.ibt.tapizy.database;

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

}