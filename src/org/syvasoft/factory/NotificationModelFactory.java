package org.syvasoft.factory;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.syvasoft.model.MNotification;
import org.syvasoft.model.MNotificationRecipient;
import org.syvasoft.model.MNotificationTriggerColumn;

public class NotificationModelFactory implements IModelFactory{

	@Override
	public Class<?> getClass(String tableName) {
		
		if (MNotificationRecipient.Table_Name.equals(tableName))
			return MNotificationRecipient.class;
		else if (MNotification.Table_Name.equals(tableName))
			return MNotification.class;
		else if (MNotificationTriggerColumn.Table_Name.equals(tableName))
			return MNotificationTriggerColumn.class;
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		
		Properties ctx = Env.getCtx();
		if (MNotificationRecipient.Table_Name.equals(tableName))
			return new MNotificationRecipient(ctx, Record_ID, trxName);
		else if (MNotification.Table_Name.equals(tableName))
			return new MNotification(ctx, Record_ID, trxName);
		else if (MNotificationTriggerColumn.Table_Name.equals(tableName))
			return new MNotificationTriggerColumn(ctx, Record_ID, trxName);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		
		Properties ctx = Env.getCtx();
		if (MNotificationRecipient.Table_Name.equals(tableName))
			return new MNotificationRecipient(ctx, rs, trxName);
		else if (MNotification.Table_Name.equals(tableName))
			return new MNotification(ctx, rs, trxName);
		else if (MNotificationTriggerColumn.Table_Name.equals(tableName))
			return new MNotificationTriggerColumn(ctx, rs, trxName);
		return null;
	}

}
