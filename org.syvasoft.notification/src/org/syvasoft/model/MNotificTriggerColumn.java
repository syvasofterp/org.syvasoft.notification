package org.syvasoft.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MNotificTriggerColumn extends X_AD_NotificTriggerColumn{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MNotificTriggerColumn(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MNotificTriggerColumn(Properties ctx, int AD_NotificTriggerColumn_ID, String trxName,
			String[] virtualColumns) {
		super(ctx, AD_NotificTriggerColumn_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MNotificTriggerColumn(Properties ctx, int AD_NotificTriggerColumn_ID, String trxName) {
		super(ctx, AD_NotificTriggerColumn_ID, trxName);
		// TODO Auto-generated constructor stub
	}

}
