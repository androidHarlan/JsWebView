package com.tentcoo.pineapple.core.dispatch.view;

import com.tentcoo.pineapple.core.dispatch.Transaction;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;

public interface IDispatcher {
	void dispatch(Transaction transaction,TransactionEnvironment environment);
}
