package com.tentcoo.pineapple.core.dispatch.view.hybrid;

import android.content.Context;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;
import com.tentcoo.pineapple.core.dispatch.view.BasicViewContainer;

public interface IBridgeHandlerAdapter extends ICore{
	void handle(Context context,String data,TransactionEnvironment environment);
	void setViewContainer(BasicViewContainer viewContainer);
}
