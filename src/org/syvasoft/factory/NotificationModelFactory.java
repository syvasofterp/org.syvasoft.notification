package org.syvasoft.factory;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.syvasoft.model.MNotificationRecipient;

public class NotificationModelFactory implements IModelFactory{

	@Override
	public Class<?> getClass(String tableName) {
		// TODO Auto-generated method stub
		if (MNotificationRecipient.Table_Name.equals(tableName))
			return MNotificationRecipient.class;
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		// TODO Auto-generated method stub
		Properties ctx = Env.getCtx();
		if (MNotificationRecipient.Table_Name.equals(tableName))
			return new MNotificationRecipient(ctx, Record_ID, trxName);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		// TODO Auto-generated method stub
		Properties ctx = Env.getCtx();
		if (MNotificationRecipient.Table_Name.equals(tableName))
			return new MNotificationRecipient(ctx, rs, trxName);
		return null;
	}

}
