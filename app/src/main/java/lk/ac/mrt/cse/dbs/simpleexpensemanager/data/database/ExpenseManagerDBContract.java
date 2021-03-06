package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.provider.BaseColumns;

/**
 * Created by Ashan on 11/20/2016.
 */
public final class ExpenseManagerDBContract {
    private ExpenseManagerDBContract(){}

    public static class AccountTable implements BaseColumns {
        public static final String TABLE_NAME = "accounts";
        public static final String COLUMN_NAME_ACCOUNT_NO="account_no";
        public static final String COLUMN_NAME_BANK="bank";
        public static final String COLUMN_NAME_ACCOUNT_HOLDER="account_holder";
        public static final String COLUMN_NAME_INITIAL_BALANCE="init_balance";
    }

    public static class TransactionTable implements BaseColumns{
        public static final String TABLE_NAME = "transactions";
        public static final String COLUMN_NAME_ACCOUNT_NO="account_no";
        public static final String COLUMN_NAME_TRANSACTION_TYPE="transaction_type";
        public static final String COLUMN_NAME_AMOUNT="amount";
        public static final String COLUMN_NAME_DATE="date";
    }
}
