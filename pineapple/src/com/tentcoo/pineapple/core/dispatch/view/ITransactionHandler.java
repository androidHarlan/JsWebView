package com.tentcoo.pineapple.core.dispatch.view;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.dispatch.Transaction;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;

public  interface  ITransactionHandler extends ICore {
	 void transactionWillDispatch(Transaction transaction,TransactionEnvironment environment);
	 void transactionDidDispatch(Transaction transaction,TransactionEnvironment environment);

}
