package cool.test.mycollege.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Augustine on 3/15/2018.
 */

public class TodayDB extends SQLiteOpenHelper {
    private static final String dataBaseName ="todayDB";

    public TodayDB(Context context) {
        super(context, "", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + dataBaseName + " (subjectname TEXT )";
        db.execSQL(query);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + dataBaseName;
        db.execSQL(query);
        onCreate(db);

    }


    public void insertData(String mydamnString)
    {
        SQLiteDatabase crap =this.getWritableDatabase();
        if(crap==null)
        {
            String query = "CREATE TABLE " + dataBaseName + " (subjectname TEXT )";//
            crap.execSQL(query);
        }
        ContentValues values = new ContentValues();
        values.put("subjectname",mydamnString);


        // Inserting Row
        crap.insert(dataBaseName, null, values);
        crap.close();
    }

    public void deleteData(String theSubject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dataBaseName, "subjectname" + " = ?", new String[]{theSubject});
        db.close();
    }


    public ArrayList<String> getthedamnList(){
        ArrayList<String> myfuckingList =new ArrayList<String>();

        SQLiteDatabase myfuckingdatabase= this.getReadableDatabase();

        String myfuckingQuery="SELECT * FROM todayDB";
        Cursor cursor = myfuckingdatabase.rawQuery(myfuckingQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                myfuckingList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        myfuckingdatabase.close();

        return myfuckingList;


    }



}
