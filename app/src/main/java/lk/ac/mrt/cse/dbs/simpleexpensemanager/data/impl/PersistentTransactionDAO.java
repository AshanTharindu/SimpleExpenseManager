package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.Database;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDBContract;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Ashan on 11/20/2016.
 */
public class PersistentTransactionDAO implements TransactionDAO {

    Context contextA;
    public PersistentTransactionDAO(Context contextA) {
        this.contextA=contextA;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        String sql="INSERT INTO "+ ExpenseManagerDBContract.TransactionTable.TABLE_NAME+"("+ ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_ACCOUNT_NO+","+ ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_TRANSACTION_TYPE+","+
                ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_DATE+","+ ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_AMOUNT+") VALUES('"+accountNo+"','"+expenseType.name()+"','"+date.toString()+"',"+amount+");";
        new Database(contextA).write(sql);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        String sql="SELECT * FROM "+ ExpenseManagerDBContract.TransactionTable.TABLE_NAME;
        Cursor cursor=new Database(contextA).read(sql);
        List<Transaction> transactionList=new ArrayList<>();
        while(cursor.moveToNext()){
            String accountNo=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_ACCOUNT_NO));
            double amount=cursor.getDouble(cursor.getColumnIndex(ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_AMOUNT));
            String type=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_TRANSACTION_TYPE));

            String date=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_DATE));
            Transaction transaction=new Transaction(new Date(date),accountNo,ExpenseType.valueOf(type),amount);
            transactionList.add(transaction);
        }
        return transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {


        String sql="SELECT * FROM "+ ExpenseManagerDBContract.TransactionTable.TABLE_NAME+" ; ";
        Cursor cursor=new Database(contextA).read(sql);
        List<Transaction> transactionList=new ArrayList<>();
        int i=0;
        while(cursor.moveToNext()){
            String accountNo=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_ACCOUNT_NO));
            double amount=cursor.getDouble(cursor.getColumnIndex(ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_AMOUNT));
            String type=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_TRANSACTION_TYPE));

            String date=cursor.getString(cursor.getColumnIndex(ExpenseManagerDBContract.TransactionTable.COLUMN_NAME_DATE));
            Transaction transaction=new Transaction(new Date(date),accountNo,ExpenseType.valueOf(type),amount);
            transactionList.add(transaction);
            i+=1;
            if(i==limit)break;
        }

        return  transactionList;
    }

}
