package com.example.game21.other;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.game21.R;

/**
 * Created by Yuann72 on 2017/11/3.
 */

public class database extends SQLiteOpenHelper {
    private final static String DB_NAME = "game21.db";
    private final static int VERSION = 3;
    private Context mContext;

    public database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public database(Context context) {
        this(context, DB_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = mContext.getResources().getString(R.string.createTableSql);
        String sql2 = mContext.getResources().getString(R.string.createEXPTableSql);
        String sql3 = "INSERT INTO game_exp VALUES (?,?)";
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3, new Object[]{0,0});
//        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS game_history");
        db.execSQL("DROP TABLE IF EXISTS game_exp");
        onCreate(db);
    }

    public database insertData(Object data[]) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO game_history VALUES (?,?,?,?,?,?,?,?)";
        db.execSQL(sql, data);
//        db.close();
        return this;
    }

    public database updateEXP(int EXP) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE game_exp SET exp = ? WHERE id = 0";
        db.execSQL(sql, new Object[]{EXP});
//        db.close();
        return this;
    }
    public database addEXP(int add) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from game_exp where id=0", null);
        cursor.moveToNext();
        int currEXP = cursor.getInt(1) + add;
        String sql = "UPDATE game_exp SET exp = ? WHERE id = 0";
        db.execSQL(sql, new Object[]{currEXP});
//        db.close();
        return this;
    }
    public int getEXP() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from game_exp where id=0", null);
        cursor.moveToNext();
        int EXP = cursor.getInt(1);
//        db.close();
        return EXP;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from game_history ORDER BY timestamp DESC ", null);
        return cursor;
    }

    public database removeAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM game_history WHERE timestamp = 'test0' ");
//        db.close();
        return this;
    }
}

