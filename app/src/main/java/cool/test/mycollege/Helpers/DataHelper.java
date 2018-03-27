package cool.test.mycollege.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import cool.test.mycollege.MyDataTypes.MyAttendanceData;

/**
 * Created by Vipin soni on 23-12-2017.
 */

public class DataHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Coolness.db";
    String day="MONDAY";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
/*
    public DataHelper(Context context,String a) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.day=a;
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {


        Log.e("Awe", "Ok On create");
        String SQL_CREATE_ENTRIES="CREAT TABLE "+day+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,SUBJECT TEXT,ATTENDED INTEGER,TOTAL INTEGER)" ;
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP="DROP TABLE"+day;
        db.execSQL(DROP);
        onCreate(db);

    }

    public void addSub(MyAttendanceData woh)
    {
        ContentValues values=new ContentValues();
        values.put("SUBJECT",woh.getSub().toString());
        values.put("ATTENDED",woh.getAttended());
        values.put("TOTAL",woh.getTotal());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(day,null,values);
        db.close();
    }

    public void deleteSub(String woh){
        SQLiteDatabase db=getWritableDatabase();
        String DROP="DELETE FROM "+day+"WHERE SUBJECT =\""+woh+"\";";
        db.execSQL(DROP);
    }

    public String arraySub()
    {
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+day+" WHERE 1";
        ArrayList<String> loc=new ArrayList<String>();
        String helper=new String();
        helper="";

if (!db.isReadOnly()) {
    Cursor c = db.rawQuery(query, null);
    c.moveToFirst();
    while (!c.isAfterLast()) {
        if (c.getString(c.getColumnIndex("SUBJECT")) != null)
            helper += c.getString(c.getColumnIndex("SUBJECT"));

    }
}

return helper;
    }


    public int getAttended(String helper)
    {
        int a=0;
        String query="SELECT ATTENDED FROM "+day+" WHERE NAME=\""+helper+"\";";
        SQLiteDatabase db=getWritableDatabase();
      //  db.execSQL(query);
        Cursor c = db.rawQuery("SELECT ATTENDED FROM "+day+" WHERE SUBJECT =\""+helper+"\";",null);

        a = c.getInt(c.getColumnIndex("ATTENDED"));
        db.close();
        return a;
    }

    public int getTotal(String helper)
    {
        int a=0;
        return a;

    }
}
