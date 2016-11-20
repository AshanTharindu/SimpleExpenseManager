package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

/**
 * Created by Ashan on 11/20/2016.
 */
public class PersistentExpenseManager extends ExpenseManager {

    private Context contextA;
    public PersistentExpenseManager(Context context) throws ExpenseManagerException {
        this.contextA=context;
        setup();
    }



    @Override
    public void setup() throws ExpenseManagerException {
            /*** Begin generating dummy data for In-Memory implementation ***/
            TransactionDAO inMemoryTransactionDAO = new PersistentTransactionDAO(contextA);
            setTransactionsDAO(inMemoryTransactionDAO);

            AccountDAO inMemoryAccountDAO = new PersistentAccountDAO(contextA);
            setAccountsDAO(inMemoryAccountDAO);
    }
}
