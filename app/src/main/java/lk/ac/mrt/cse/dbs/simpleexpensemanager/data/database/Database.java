package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Ashan on 11/20/2016.
 */
public class Database {
    ExpenseManagerDBHelper expenseManagerDBHelper;
    SQLiteDatabase sqLiteDatabase;
    private static Database database;

    public Database(Context context){
        expenseManagerDBHelper=new ExpenseManagerDBHelper(context);
        sqLiteDatabase=expenseManagerDBHelper.getWritableDatabase();
    }


    public void write(String sqlStmnt){

        Log.e("****",sqlStmnt);
        sqLiteDatabase.execSQL(sqlStmnt);
    }

    public Cursor read(String sqlStmnt){
        Cursor resultSet=sqLiteDatabase.rawQuery(sqlStmnt,null);
        return  resultSet;
    }
}
