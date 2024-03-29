package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.exampractice.Message;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DBname = "exam.db";

    private final static String createQuery1 = "CREATE TABLE " + Tables.User.TABLE_NAME + " (" +
            Tables.User._ID + " INTEGER PRIMARY KEY," +
            Tables.User.COLUMN_NAME + " TEXT," +
            Tables.User.COLUMN_PASSWORD + " TEXT," +
            Tables.User.COLUMN_TYPE + " TEXT)";

    private final static  String createQuery2 = "CREATE TABLE " + Tables.Message.TABLE_NAME + " (" +
            Tables.Message._ID + " INTEGER PRIMARY KEY," +
            Tables.Message.COLUMN_USER + " TEXT," +
            Tables.Message.COLUMN_SUBJECT + " TEXT," +
            Tables.Message.COLUMN_MESSAGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + Tables.User.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_MESSAGE =
            "DROP TABLE IF EXISTS " + Tables.Message.TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DBname, null, 1);



    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createQuery1);
        sqLiteDatabase.execSQL(createQuery2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_USER);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_MESSAGE);

        onCreate(sqLiteDatabase);

    }

    public long AddNewUser(String username , String password , String type){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Tables.User.COLUMN_NAME,username);
        contentValues.put(Tables.User.COLUMN_PASSWORD,password);
        contentValues.put(Tables.User.COLUMN_TYPE,type);

        long i = db.insert(Tables.User.TABLE_NAME,null,contentValues);

        return i;


    }

    public String[] login(String username , String password){

        SQLiteDatabase db = getReadableDatabase();
        String[] login = {"null","null"};

        String[] projection = {Tables.User.COLUMN_TYPE, Tables.User.COLUMN_NAME};

        String selection = Tables.User.COLUMN_NAME + " = ?  AND " + Tables.User.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username,password };

        Cursor cursor = db.query(Tables.User.TABLE_NAME,projection,selection,selectionArgs,null,null,null);

        if(cursor.moveToNext()){
          login[0]= cursor.getString(cursor.getColumnIndexOrThrow(Tables.User.COLUMN_NAME));
            login[1]= cursor.getString(cursor.getColumnIndexOrThrow(Tables.User.COLUMN_TYPE));
        }
        return login;
    }

    public long AddNewMessage(String username , String subject  , String message){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Tables.Message.COLUMN_USER,username);
        contentValues.put(Tables.Message.COLUMN_SUBJECT,subject);
        contentValues.put(Tables.Message.COLUMN_MESSAGE,message);

        long i = db.insert(Tables.Message.TABLE_NAME,null,contentValues);

        return i;


    }

    public Cursor getLatestMessage(){
     SQLiteDatabase db = getReadableDatabase();
     String[] projection = {Tables.Message.COLUMN_USER, Tables.Message.COLUMN_SUBJECT , Tables.Message.COLUMN_MESSAGE};
        String orderBY = Tables.Message._ID +" DESC" ;
     Cursor c = db.query(Tables.Message.TABLE_NAME,projection,null,null,null,null,orderBY);
     return c;
    }

    public List getAllMessage(){
        SQLiteDatabase db = getReadableDatabase();
        List<Message> mlist = new ArrayList<>();
        Message message = new Message();
        String[] projection = {Tables.Message._ID,Tables.Message.COLUMN_USER, Tables.Message.COLUMN_SUBJECT , Tables.Message.COLUMN_MESSAGE};
        Cursor c = db.query(Tables.Message.TABLE_NAME,projection,null,null,null,null,null);

        while (c.moveToNext()){
            message.setId(c.getString(c.getColumnIndexOrThrow(Tables.Message._ID)));
            message.setMessage(c.getString(c.getColumnIndexOrThrow(Tables.Message.COLUMN_MESSAGE)));

            message.setSubject(c.getString(c.getColumnIndexOrThrow(Tables.Message.COLUMN_SUBJECT)));
            message.setUname(c.getString(c.getColumnIndexOrThrow(Tables.Message.COLUMN_USER)));

            mlist.add(message);
        }



        return mlist;
    }

    public Cursor getAllmessagesCursor(){
        SQLiteDatabase db = getReadableDatabase();


        String orderBY = Tables.Message._ID +" DESC" ;
        String[] projection = {Tables.Message._ID,Tables.Message.COLUMN_USER, Tables.Message.COLUMN_SUBJECT , Tables.Message.COLUMN_MESSAGE};
        Cursor c = db.query(Tables.Message.TABLE_NAME,projection,null,null,null,null,orderBY);
        return c;
    }

    public Message getClickedmessage(String id){
        SQLiteDatabase db = getReadableDatabase();

        Message message = new Message();
        String orderBY = Tables.Message._ID +" DESC" ;
        String[] projection = {Tables.Message._ID,Tables.Message.COLUMN_USER, Tables.Message.COLUMN_SUBJECT , Tables.Message.COLUMN_MESSAGE};
        String selection = Tables.Message._ID +"=?";
        String[] selectionArgs ={id};
        Cursor c = db.query(Tables.Message.TABLE_NAME,projection,selection,selectionArgs,null,null,orderBY);

        if (c.moveToNext()){
            message.setId(c.getString(c.getColumnIndexOrThrow(Tables.Message._ID)));
            message.setMessage(c.getString(c.getColumnIndexOrThrow(Tables.Message.COLUMN_MESSAGE)));

            message.setSubject(c.getString(c.getColumnIndexOrThrow(Tables.Message.COLUMN_SUBJECT)));
            message.setUname(c.getString(c.getColumnIndexOrThrow(Tables.Message.COLUMN_USER)));

            return message;
        }

        return null;
    }

}
