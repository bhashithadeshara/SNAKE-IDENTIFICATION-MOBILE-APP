package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseAccess{

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static  DataBaseAccess instance;
    Cursor c = null;

    private DataBaseAccess(Context context){

        this.openHelper = new DataBaseOpenHelper(context);


    }

    public static DataBaseAccess getInstance(Context context){

        if(instance == null){
            instance = new DataBaseAccess(context);
        }
        return instance;

    }

    public void open(){
        this.db= openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public String getDescription(String name){

        c = db.rawQuery("select Description from Table1 where Name= '"+name+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String description = c.getString(0);



            buffer.append(""+description);
        }
        return buffer.toString();
    }


}
