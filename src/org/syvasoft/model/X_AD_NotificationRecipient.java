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
import org.compiere.model.*;

/** Generated Model for AD_NotificationRecipient
 *  @author iDempiere (generated) 
 *  @version Release 9 - $Id$ */
@org.adempiere.base.Model(table="AD_NotificationRecipient")
public class X_AD_NotificationRecipient extends PO implements I_AD_NotificationRecipient, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20221120L;

    /** Standard Constructor */
    public X_AD_NotificationRecipient (Properties ctx, int AD_NotificationRecipient_ID, String trxName)
    {
      super (ctx, AD_NotificationRecipient_ID, trxName);
      /** if (AD_NotificationRecipient_ID == 0)
        {
        } */
    }

    /** Standard Constructor */
    public X_AD_NotificationRecipient (Properties ctx, int AD_NotificationRecipient_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_NotificationRecipient_ID, trxName, virtualColumns);
      /** if (AD_NotificationRecipient_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_AD_NotificationRecipient (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_NotificationRecipient[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Notification Recipient.
		@param AD_NotificationRecipient_ID Notification Recipient
	*/
	public void setAD_NotificationRecipient_ID (int AD_NotificationRecipient_ID)
	{
		if (AD_NotificationRecipient_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_NotificationRecipient_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_NotificationRecipient_ID, Integer.valueOf(AD_NotificationRecipient_ID));
	}

	/** Get Notification Recipient.
		@return Notification Recipient	  */
	public int getAD_NotificationRecipient_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_NotificationRecipient_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_NotificationRecipient_UU.
		@param AD_NotificationRecipient_UU AD_NotificationRecipient_UU
	*/
	public void setAD_NotificationRecipient_UU (String AD_NotificationRecipient_UU)
	{
		set_Value (COLUMNNAME_AD_NotificationRecipient_UU, AD_NotificationRecipient_UU);
	}

	/** Get AD_NotificationRecipient_UU.
		@return AD_NotificationRecipient_UU	  */
	public String getAD_NotificationRecipient_UU()
	{
		return (String)get_Value(COLUMNNAME_AD_NotificationRecipient_UU);
	}

	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Role)MTable.get(getCtx(), org.compiere.model.I_AD_Role.Table_ID)
			.getPO(getAD_Role_ID(), get_TrxName());
	}

	/** Set Role.
		@param AD_Role_ID Responsibility Role
	*/
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0)
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getAD_User_ID(), get_TrxName());
	}

	/** Set User/Contact.
		@param AD_User_ID User within the system - Internal or Business Partner Contact
	*/
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WhatsApp Chat Id.
		@param ChatId WhatsApp Chat Id
	*/
	public void setChatId (String ChatId)
	{
		set_Value (COLUMNNAME_ChatId, ChatId);
	}

	/** Get WhatsApp Chat Id.
		@return WhatsApp Chat Id	  */
	public String getChatId()
	{
		return (String)get_Value(COLUMNNAME_ChatId);
	}

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set EMail Address.
		@param EMail Electronic Mail Address
	*/
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail()
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Mobile No.
		@param MobileNo Mobile No
	*/
	public void setMobileNo (String MobileNo)
	{
		set_Value (COLUMNNAME_MobileNo, MobileNo);
	}

	/** Get Mobile No.
		@return Mobile No	  */
	public String getMobileNo()
	{
		return (String)get_Value(COLUMNNAME_MobileNo);
	}

	/** BCC = B */
	public static final String RECIPIENTTYPE_BCC = "B";
	/** CC = C */
	public static final String RECIPIENTTYPE_CC = "C";
	/** From = F */
	public static final String RECIPIENTTYPE_From = "T";
	/** To = T */
	public static final String RECIPIENTTYPE_To = "T";
	/** Set Recipient Type.
		@param RecipientType Recipient Type
	*/
	public void setRecipientType (String RecipientType)
	{

		set_Value (COLUMNNAME_RecipientType, RecipientType);
	}

	/** Get Recipient Type.
		@return Recipient Type	  */
	public String getRecipientType()
	{
		return (String)get_Value(COLUMNNAME_RecipientType);
	}
}