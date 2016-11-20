package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.Database;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDBContract;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by Ashan on 11/20/2016.
 */
public class PersistentAccountDAO implements AccountDAO {


    Context conextA;;
    public PersistentAccountDAO(Context mContext) {
        this.conextA=mContext;
    }


    @Override
    public List<String> getAccountNumbersList() {
        String sql="SELECT "+ ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_NO+" FROM "+ ExpenseManagerDBContract.AccountTable.TABLE_NAME;
        Cursor cursor=new Database(conextA).read(sql);
        List<String> accountNumberList=new ArrayList<>();
        while(cursor.moveToNext()){
            String accountNo=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_NO));

            accountNumberList.add(accountNo);
        }
        return accountNumberList;
    }

    @Override
    public List<Account> getAccountsList() {
        String sql="SELECT * FROM "+ ExpenseManagerDBContract.AccountTable.TABLE_NAME;
        Cursor cursor=new Database(conextA).read(sql);
        List<Account> accountList=new ArrayList<>();
        while(cursor.moveToNext()){
            String accountNo=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_NO));
            String bank=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.AccountTable.COLUMN_NAME_BANK));
            String accountHolder=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_HOLDER));
            double balance=cursor.getDouble(cursor.getColumnIndex(ExpenseManagerDBContract.AccountTable.COLUMN_NAME_INITIAL_BALANCE));
            Account account=new Account(accountNo,bank,accountHolder,balance);
            accountList.add(account);
        }
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String sql="SELECT * FROM "+ ExpenseManagerDBContract.AccountTable.TABLE_NAME+" WHERE "+ ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_NO+" = '"+accountNo+"';";
        Cursor cursor=new Database(conextA).read(sql);
        if(cursor.getCount()==0){
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        else{
            cursor.moveToFirst();
            String bank=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.AccountTable.COLUMN_NAME_BANK));
            String accountHolder=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_HOLDER));
            double balance=cursor.getDouble(cursor.getColumnIndex(ExpenseManagerDBContract.AccountTable.COLUMN_NAME_INITIAL_BALANCE));
            Account account=new Account(accountNo,bank,accountHolder,balance);
            return  account;
        }

    }

    @Override
    public void addAccount(Account account) {
        String sql="INSERT INTO "+ ExpenseManagerDBContract.AccountTable.TABLE_NAME+"("+ ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_NO+","+ ExpenseManagerDBContract.AccountTable.COLUMN_NAME_BANK+","+
                ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_HOLDER+","+ ExpenseManagerDBContract.AccountTable.COLUMN_NAME_INITIAL_BALANCE+") VALUES('"+account.getAccountNo()+"','"+account.getBankName()+"','"+account.getAccountHolderName()+"',"+account.getBalance()+");";
        new Database(conextA).write(sql);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String sql="DELETE FROM "+ ExpenseManagerDBContract.AccountTable.TABLE_NAME+" WHERE "+ ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_NO+" = '"+accountNo+"';";
        new Database(conextA).write(sql);

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        Account account=getAccount(accountNo);



        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }
        String sql="UPDATE "+ ExpenseManagerDBContract.AccountTable.TABLE_NAME+" SET "+ ExpenseManagerDBContract.AccountTable.COLUMN_NAME_INITIAL_BALANCE+" = "+account.getBalance()+" WHERE "+
                ExpenseManagerDBContract.AccountTable.COLUMN_NAME_ACCOUNT_NO+" = '"+accountNo+"' ;"  ;

        new Database(conextA).write(sql);
    }
}
