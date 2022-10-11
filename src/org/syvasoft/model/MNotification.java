package org.syvasoft.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MNotification extends X_AD_Notification{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MNotification(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MNotification(Properties ctx, int AD_Notification_ID, String trxName, String[] virtualColumns) {
		super(ctx, AD_Notification_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MNotification(Properties ctx, int AD_Notification_ID, String trxName) {
		super(ctx, AD_Notification_ID, trxName);
		// TODO Auto-generated constructor stub
	}

}
