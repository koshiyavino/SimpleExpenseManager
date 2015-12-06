package lk.ac.mrt.cse.dbs.simpleexpensemanager.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Koshiya Epaliyaa on 12/4/2015.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DARABASE_VERSION=1;
    private static final String DATABASE_NAME="130154D.db";


    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DARABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ACCOUNT="CREATE TABLE Account (accountNo TEXT PRIMARY KEY, bankName TEXT,accountHolderName TEXT,balance DOUBLE)";
        db.execSQL(CREATE_TABLE_ACCOUNT);
        String CREATE_TABLE_TRANSACTION="CREATE TABLE BTransaction(date TEXT,accountNo TEXT,expenseType TEXT,amount DOUBLE, PRIMARY KEY(date,accountNo))";
        db.execSQL(CREATE_TABLE_TRANSACTION);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS BTransaction");
        onCreate(db);

    }
}
