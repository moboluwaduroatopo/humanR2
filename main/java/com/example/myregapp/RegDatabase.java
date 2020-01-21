package com.example.myregapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;




public class RegDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "hr.db";
    public static final String TABLE_NAME1 = "employees";
    public static final String TABLE_NAME2 = "departments";
    public static final String COL_20 = "_id";
    public static final String COL_21 = "department_name";
    public static final String COL_10 = "_id";
    public static final String COL_11 = "full_name";
 public static final String COL_14 = "dept_id";
    public static final String COL_12 = "password";
    public static final String COL_13 = "email";
    public String error = "";

    public static final int DATABASE_VERSION = 11;


    public RegDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //, dept_id INTEGER, FOREIGN KEY(dept_id) REFERENCES " + TABLE_NAME2 + "(" + COL_20 + ")
            String userTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + " (" + COL_10 + " INTEGER " +
                    "PRIMARY KEY " +
                    "AUTOINCREMENT," + COL_11 + " VARCHAR NOT NULL," + COL_12 + " VARCHAR NOT NULL," + COL_13 + " VARCHAR NOT NULL,"+COL_14+" INTEGER, FOREIGN KEY("+COL_14+") REFERENCES \" + TABLE_NAME2 + \"(\" + COL_20 + \"));";
            String depTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " (" + COL_20 + " INTEGER " +
                    "PRIMARY KEY AUTOINCREMENT," + COL_21 + " VARCHAR);";
            db.execSQL(depTable);
            db.execSQL(userTable);

        }
        catch (Exception e)
        {
            error = e.toString();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        onCreate(db);
    }

    public long addUser(String fName, String pwd, String email,String deptid)
    {
        long response = 0;
        try {
            SQLiteDatabase mydb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COL_11, fName);
            cv.put(COL_12,pwd);
            cv.put(COL_13,email);
            cv.put(COL_14,deptid);
            response = mydb.insert(TABLE_NAME1, null, cv);
            mydb.close();


        }
        catch (Exception e)
        {
            error = e.toString();
        }
        return response;
    }

    public Cursor viewUsers()
    {
        SQLiteDatabase mydb = this.getWritableDatabase();
//        String vDept = "SELECT * FROM "+TABLE_NAME1;
        String vDept = "SELECT * FROM "+TABLE_NAME1+" JOIN "+TABLE_NAME2+" ON "+TABLE_NAME2+"."+COL_20+"="+TABLE_NAME1+"."+COL_10+"";
        Cursor c = mydb.rawQuery(vDept,null);
        return c;
    }
    public Cursor login(String email,String pwd)
    {
        String[] sel={email,pwd};
        SQLiteDatabase mylog=this.getWritableDatabase();

        String logUser="SELECT * FROM "+TABLE_NAME1+" WHERE "+COL_13+" =? AND "+COL_12+" =?";

        Cursor l = mylog.rawQuery(logUser,sel);
        return l;
    }
    public Cursor checkEmail(String email)
    {
        String[] sel={email};
        SQLiteDatabase myLog=this.getWritableDatabase();

        String cEmail="SELECT * FROM "+TABLE_NAME1+" WHERE "+COL_13+" =? ";

        Cursor l = myLog.rawQuery(cEmail,sel);
        return l;
    }
    public Cursor checkDeptname(String Dname)
    {
        String[] sel={Dname};
        SQLiteDatabase myLog=this.getWritableDatabase();

        String cDname="SELECT * FROM "+TABLE_NAME2+" WHERE "+COL_21+" =? ";

        Cursor dn = myLog.rawQuery(cDname,sel);
        return dn;
    }
    public Cursor checkDeptid(String Did)
    {
        String[] sel={Did};
        SQLiteDatabase myLog=this.getWritableDatabase();

        String cDname="SELECT * FROM "+TABLE_NAME2+" WHERE "+COL_20+" =? ";

        Cursor dn = myLog.rawQuery(cDname,sel);
        return dn;
    }
    public Cursor checkUserid(String Uid)
    {
        String[] sel={Uid};
        SQLiteDatabase myLog=this.getWritableDatabase();

        String cDname="SELECT * FROM "+TABLE_NAME1+" WHERE "+COL_10+" =? ";

        Cursor dn = myLog.rawQuery(cDname,sel);
        return dn;
    }
    public Cursor viewemployeBydept(String Did)
    {
        String[] sel={Did};
        SQLiteDatabase myLog=this.getWritableDatabase();

        String cDname="SELECT * FROM "+TABLE_NAME1+" WHERE "+COL_14+" =? ";

        Cursor dn = myLog.rawQuery(cDname,sel);
        return dn;
    }
    public long addDepartment(String dName)
    {
        long response = 0;
        try {
            SQLiteDatabase mydb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COL_21, dName);
            response = mydb.insert(TABLE_NAME2, null, cv);
            mydb.close();


        }
        catch (Exception e)
        {
            error = e.toString();
        }
        return response;
    }

    public Cursor viewDepartment()
    {
        SQLiteDatabase mydb = this.getWritableDatabase();
        String vDept = "SELECT * FROM "+TABLE_NAME2;
        Cursor c = mydb.rawQuery(vDept,null);
        return c;
    }

    public long deleteDepartment(String dpid)
    {
        SQLiteDatabase mydb = this.getWritableDatabase();
        long c = mydb.delete(TABLE_NAME2,COL_20 +"="+ dpid,null);
        return c;
    }
    public long deleteUser(String uid)
    {
        SQLiteDatabase mydb = this.getWritableDatabase();
        long c = mydb.delete(TABLE_NAME1,COL_10 +"="+ uid,null);
        return c;
    }
    public long updateProfile(String fName,String pid)
    {
        long response = 0;
        try {
            SQLiteDatabase mydb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COL_11, fName);
            cv.put(COL_10,pid);
            response = mydb.update(TABLE_NAME1, cv,COL_10 +"="+ pid,null);
            mydb.close();

        }
        catch (Exception e)
        {
            error = e.toString();
        }
        return response;
    }
    public long updateDepartment(String dName,String did)
    {
        long response = 0;
        try {
            SQLiteDatabase mydb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COL_21, dName);
            response = mydb.update(TABLE_NAME2, cv,COL_20 +"="+ did,null);
            mydb.close();

        }
        catch (Exception e)
        {
            error = e.toString();
        }
        return response;
    }
    public long updateUser(String uName,String did)
    {
        long response = 0;
        try {
            SQLiteDatabase mydb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COL_11, uName);
            response = mydb.update(TABLE_NAME1, cv,COL_10 +"="+ did,null);
            mydb.close();

        }
        catch (Exception e)
        {
            error = e.toString();
        }
        return response;
    }
}




