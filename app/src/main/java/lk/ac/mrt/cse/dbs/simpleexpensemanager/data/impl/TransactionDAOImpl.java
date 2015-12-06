package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Koshiya Epaliyaa on 12/4/2015.
 */
public class TransactionDAOImpl implements TransactionDAO {

    private SQLiteDatabase db;

    public TransactionDAOImpl(SQLiteDatabase db){
        this.db=db;
    }
    @Override
    public void logTransaction(String date, String accountNo, ExpenseType expenseType, double amount) {
        String insert="INSERT INTO BTransaction VALUES (?,?,?,?) ";
        String exType;
        if(expenseType.equals(ExpenseType.EXPENSE)){
            exType="Expense";
        }
        else{
            exType="Income";
        }
        db.execSQL(insert,new Object[]{date,accountNo,exType,amount});


    }



    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor cr=db.rawQuery("SELECT * FROM BTransaction",new String[]{});
        List<Transaction> list1=new ArrayList<>();
        if(cr.moveToFirst()) {
            do {
                String date= cr.getString(cr.getColumnIndex("date"));
                String accountNo = cr.getString(cr.getColumnIndex("accountNo"));
                String exType= cr.getString(cr.getColumnIndex("expenseType"));
                double amount= cr.getDouble(cr.getColumnIndex("amount"));
                ExpenseType expenseType;
                if (exType.equals("Expense")) {
                    expenseType = ExpenseType.EXPENSE;
                } else {
                    expenseType = ExpenseType.INCOME;
                }
                list1.add(new Transaction(date, accountNo, expenseType, amount));
            } while (cr.moveToNext());
        }
        return list1;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        int n = 0;
        Cursor cr = db.rawQuery("SELECT * FROM BTransaction", new String[]{});
        List<Transaction> list2 = new ArrayList<>();
        if (cr.moveToFirst()) {
            do {
                n++;
                String date = cr.getString(cr.getColumnIndex("date"));
                String accountNo = cr.getString(cr.getColumnIndex("accountNo"));
                String exType = cr.getString(cr.getColumnIndex("expenseType"));
                double amount = cr.getDouble(cr.getColumnIndex("amount"));
                ExpenseType expenseType;
                if (exType.equals("Expense")) {
                    expenseType = ExpenseType.EXPENSE;
                } else {
                    expenseType = ExpenseType.INCOME;
                }
                list2.add(new Transaction(date, accountNo, expenseType, amount));
                if (n >= limit) {
                    break;
                }
            } while (cr.moveToNext());
        }
        return list2;
    }

}

