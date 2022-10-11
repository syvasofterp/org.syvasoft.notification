/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.syvasoft.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for AD_NotificTriggerColumn
 *  @author iDempiere (generated) 
 *  @version Release 9 - $Id$ */
@org.adempiere.base.Model(table="AD_NotificTriggerColumn")
public class X_AD_NotificTriggerColumn extends PO implements I_AD_NotificTriggerColumn, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20221010L;

    /** Standard Constructor */
    public X_AD_NotificTriggerColumn (Properties ctx, int AD_NotificTriggerColumn_ID, String trxName)
    {
      super (ctx, AD_NotificTriggerColumn_ID, trxName);
      /** if (AD_NotificTriggerColumn_ID == 0)
        {
        } */
    }

    /** Standard Constructor */
    public X_AD_NotificTriggerColumn (Properties ctx, int AD_NotificTriggerColumn_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_NotificTriggerColumn_ID, trxName, virtualColumns);
      /** if (AD_NotificTriggerColumn_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_AD_NotificTriggerColumn (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_AD_NotificTriggerColumn[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_ID)
			.getPO(getAD_Column_ID(), get_TrxName());
	}

	/** Set Column.
		@param AD_Column_ID Column in the table
	*/
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1)
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Notification getAD_Notification() throws RuntimeException
	{
		return (I_AD_Notification)MTable.get(getCtx(), I_AD_Notification.Table_ID)
			.getPO(getAD_Notification_ID(), get_TrxName());
	}

	/** Set Notification.
		@param AD_Notification_ID Notification
	*/
	public void setAD_Notification_ID (int AD_Notification_ID)
	{
		if (AD_Notification_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Notification_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Notification_ID, Integer.valueOf(AD_Notification_ID));
	}

	/** Get Notification.
		@return Notification	  */
	public int getAD_Notification_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Notification_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_NotificTriggerColum_UU.
		@param AD_NotificTriggerColum_UU AD_NotificTriggerColum_UU
	*/
	public void setAD_NotificTriggerColum_UU (String AD_NotificTriggerColum_UU)
	{
		set_Value (COLUMNNAME_AD_NotificTriggerColum_UU, AD_NotificTriggerColum_UU);
	}

	/** Get AD_NotificTriggerColum_UU.
		@return AD_NotificTriggerColum_UU	  */
	public String getAD_NotificTriggerColum_UU()
	{
		return (String)get_Value(COLUMNNAME_AD_NotificTriggerColum_UU);
	}

	/** Set Notification Trigger Column.
		@param AD_NotificTriggerColumn_ID Notification Trigger Column
	*/
	public void setAD_NotificTriggerColumn_ID (int AD_NotificTriggerColumn_ID)
	{
		if (AD_NotificTriggerColumn_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_NotificTriggerColumn_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_NotificTriggerColumn_ID, Integer.valueOf(AD_NotificTriggerColumn_ID));
	}

	/** Get Notification Trigger Column.
		@return Notification Trigger Column	  */
	public int getAD_NotificTriggerColumn_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_NotificTriggerColumn_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}