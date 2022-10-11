package org.syvasoft.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MNotificationRecipient extends X_AD_NotificationRecipient{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MNotificationRecipient(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MNotificationRecipient(Properties ctx, int AD_NotificationRecipient_ID, String trxName,
			String[] virtualColumns) {
		super(ctx, AD_NotificationRecipient_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MNotificationRecipient(Properties ctx, int AD_NotificationRecipient_ID, String trxName) {
		super(ctx, AD_NotificationRecipient_ID, trxName);
		// TODO Auto-generated constructor stub
	}

}
