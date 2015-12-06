package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by Koshiya Epaliyaa on 12/4/2015.
 */
public class AccountDAOImpl implements AccountDAO {

    public SQLiteDatabase db;

    public AccountDAOImpl(SQLiteDatabase db){
        this.db=db;
    }
    @Override
    public List<String> getAccountNumbersList() {
        Cursor cr=db.rawQuery("SELECT accountNo FROM Account",new String[]{});
        List<String> list1=new ArrayList<>();
        if(cr.moveToFirst()){
            do{
                String accountNo= cr.getString(cr.getColumnIndex("accountNo"));
                list1.add(accountNo);
            }while(cr.moveToNext());
        }
        return list1;
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor cr=db.rawQuery("SELECT * FROM Account ",new String[]{});
        List<Account> list2=new ArrayList<>();
        if(cr.moveToFirst()) {
            do{
                String accountNo= cr.getString(cr.getColumnIndex("accountNo"));
                String bankName = cr.getString(cr.getColumnIndex("bankName"));
                String accountHolderName = cr.getString(cr.getColumnIndex("accountHolderName"));
                double balance = cr.getDouble(cr.getColumnIndex("balance"));
                list2.add(new Account(accountNo,bankName,accountHolderName,balance));
            }while(cr.moveToNext());
        }
        return list2;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor cr=db.rawQuery("SELECT * FROM Account WHERE accountNo=?",new String[]{});
        if(cr.moveToFirst()){
            String bankName = cr.getString(cr.getColumnIndex("bankName"));
            String accountHolderName = cr.getString(cr.getColumnIndex("accountHolderName"));
            double balance = cr.getDouble(cr.getColumnIndex("balance"));
            return new Account(accountNo,bankName,accountHolderName,balance);
        }
        return null;
    }

    @Override
    public void addAccount(Account account) {
        String insert="INSERT IN TO Account VALUES (\'"+account.getAccountNo()+"\',\'"+account.getBankName()+"\',\'"+ account.getAccountHolderName()+"\',\'"+account.getBalance()+")";
        db.execSQL(insert);

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String delete="DELETE FROM Account WHERE accountNo=\'"+accountNo+"\'";
        db.execSQL(delete);

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        if(expenseType.equals(ExpenseType.EXPENSE)){
            amount=-1*amount;
        }
        String update="UPDATE Account SET balance = balance+ '+amount+' WHERE accountNo=\'"+accountNo+"\'";
        db.execSQL(update);
    }
}
