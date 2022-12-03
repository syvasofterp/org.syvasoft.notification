package org.syvasoft.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Zip;
import org.chatapi.whatsapp.api.WhatsAppUtil;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MClient;
import org.compiere.model.MImage;
import org.compiere.model.MNote;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.tools.FileUtil;
import org.compiere.util.DB;
import org.compiere.util.EMail;

public class MNotification extends X_AD_Notification {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7211450842876080363L;

	public MNotification(Properties ctx, int TF_SmsNotification_ID, String trxName) {
		super(ctx, TF_SmsNotification_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MNotification(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
		
	public static MNotification get(Properties ctx, String Name, String trxName) {
		String whereClause = "Name = ?";
		MNotification msg = new Query(ctx, Table_Name, whereClause, trxName)
				.setClient_ID()
				.setParameters(Name)
				.first();
		return msg;
	}
	
	String hdrSql = "";		
	String SqlQuery = "";
	String ftrSql = "";
	String hdrMessage = "";
	String Message="";
	String ftrMessage = "";
	String Unicode="";
	boolean EnabledWhatsApp = MSysConfig.getBooleanValue("WHATSAPP_ENABLED", true);
	
	String fromEmail = null;
	List<String> _phoneNos = new ArrayList<String>();
	List<String> _ToEmails = new ArrayList<String>();
	List<String> _CCEmails = new ArrayList<String>();
	List<String> _BCCEmails = new ArrayList<String>();
	
	public String getMessage(String ID) {
		
		int maxRowMsgBody = MSysConfig.getIntValue("SN_MAX_ROW_FOR_MSG_BODY", 5);
		
		PreparedStatement pstmt1 =  null;
		ResultSet rs1 = null;
		PreparedStatement hdrPstmt =  null;
		ResultSet hdrRs = null;
		PreparedStatement ftrPstmt =  null;
		ResultSet ftrRs = null;		
		try	{			
			
				pstmt1 = null;
				rs1 = null;
				hdrSql = getHeaderSql() != null ? getHeaderSql().replace("@ID@", ID) : "";
				SqlQuery= getSql().replace("@ID@", ID);	
				ftrSql = getFooterSql() != null ? getFooterSql().replace("@ID@", ID) : "";
				hdrMessage = getHeaderMsg();
				Message= getMessage();
				ftrMessage = getFooterMsg();
				Unicode= isUnicode() ? "Y" : "N";

				//Header
				if(hdrSql != null && hdrSql.length() > 0) {
					hdrPstmt = DB.prepareStatement (hdrSql, get_TrxName());
					hdrRs = hdrPstmt.executeQuery();
					ResultSetMetaData hdrMD = hdrRs.getMetaData();					
					while (hdrRs.next())
					{
						for(int i=1;i<=hdrMD.getColumnCount();i++) {
							String columnName = hdrMD.getColumnName(i); 
							hdrMessage=hdrMessage.replace("{"+columnName+"}", hdrRs.getString(columnName));
						}
						break;
					}
				}
				
				//Detail
				int row = 0;
				pstmt1 = DB.prepareStatement (SqlQuery.toString(), get_TrxName());
				rs1 = pstmt1.executeQuery();
				ResultSetMetaData md = rs1.getMetaData();
				StringBuilder msgBody = new StringBuilder("");
				if(hdrMessage != null)
					msgBody.append(hdrMessage);
				
				while (rs1.next())
				{
					String msgDetail = Message;	
					for(int i=1;i<=md.getColumnCount();i++) {
						String columnName = md.getColumnName(i); 
						String data = rs1.getString(columnName) == null ? "" : rs1.getString(columnName);
						msgDetail=msgDetail.replace("{"+columnName+"}", data );
					}
					msgBody.append(msgDetail);
					row++;
					if(row >= maxRowMsgBody)
						break;
				}								
				
				//Footer Message
				if(ftrSql != null && ftrSql.length() > 0) {
					ftrPstmt = DB.prepareStatement (ftrSql, get_TrxName());
					ftrRs = ftrPstmt.executeQuery();
					ResultSetMetaData ftrMD = ftrRs.getMetaData();
					while (ftrRs.next())
					{
						for(int i=1;i<=ftrMD.getColumnCount();i++) {
							String columnName = ftrMD.getColumnName(i); 
							ftrMessage = ftrMessage.replace("{"+columnName+"}", ftrRs.getString(columnName));
						}
						msgBody.append(ftrMessage);
						break;
					}
				}
								
			return msgBody.toString();
		}
		catch (SQLException e) {
						
			throw new DBException(e);
		}
		finally	{
			rs1 = null; pstmt1 = null;
			hdrRs = null; hdrPstmt = null;
			ftrRs = null; ftrPstmt = null;
		}		
	}
	
	public String getSubject(String ID) {
		PreparedStatement pstmt1 =  null;
		ResultSet rs1 = null;
		String Subject = getSubjectMsg(); 
		try	{			
			
			pstmt1 = null;
			rs1 = null;
			String subSql = getSubjectSql() != null ? getSubjectSql().replace("@ID@", ID) : "";
			
			if(subSql != null && subSql.length() > 0) {
				pstmt1 = DB.prepareStatement (subSql, get_TrxName());
				rs1 = pstmt1.executeQuery();
				ResultSetMetaData subMD = rs1.getMetaData();					
				while (rs1.next())
				{
					for(int i=1;i<=subMD.getColumnCount();i++) {
						String columnName = subMD.getColumnName(i); 
						Subject = Subject.replace("{"+columnName+"}", rs1.getString(columnName));
					}
					break;
				}
			}
			
		}		
		catch (SQLException e) {
			
			throw new DBException(e);
		}
		finally	{
			rs1 = null; pstmt1 = null;			
		}		
		
		return Subject;
	}
	
	public void buildEmails(String ID) {
		if(!isEmail())
			return;
		
		//From SQL
		PreparedStatement pstmt =  null;
		ResultSet rs = null;
		String recipientSql = getRecipientSQL();
		try	{			
						
			recipientSql = recipientSql != null ? recipientSql.replace("@ID@", ID) : "";
			
			if(recipientSql != null && recipientSql.length() > 0) {
				pstmt = DB.prepareStatement (recipientSql, get_TrxName());
				rs = pstmt.executeQuery();	
				
				ResultSetMetaData subMD = rs.getMetaData();
				boolean existsRecipientType = false;
				boolean existsAD_User_ID = false;
				boolean existsAD_Role_ID = false;
				boolean existsEmail = false;
				
				for(int i=1;i<=subMD.getColumnCount();i++) {
					String columnName = subMD.getColumnName(i); 
					if(columnName.toLowerCase().equals("recipienttype")) 
						existsRecipientType = true;
					if(columnName.toLowerCase().equals("ad_user_id")) 
						existsAD_User_ID = true;
					if(columnName.toLowerCase().equals("ad_role_id")) 
						existsAD_Role_ID = true;
					if(columnName.toLowerCase().equals("email")) 
						existsEmail = true;
				}
				
				while (rs.next()) {
					try {
						String recipientType = existsRecipientType ? rs.getString("RecipientType") : "T";
						int ad_user_id = existsAD_User_ID ? rs.getInt("AD_User_ID") : 0;
						int ad_role_id = existsAD_Role_ID ? rs.getInt("AD_Role_ID") : 0;
						MUser u = MUser.get(getCtx(), ad_user_id);
						String email = existsEmail ?  rs.getString("Email") : "";
						if(email == null)
							email = u.getEMail();
						
						if(email != null && email.length() > 0) {
							if(recipientType.equals(MNotificationRecipient.RECIPIENTTYPE_From))
								fromEmail = email;
							
							if(recipientType.equals(MNotificationRecipient.RECIPIENTTYPE_To))
								_ToEmails.add(email);
							
							if(recipientType.equals(MNotificationRecipient.RECIPIENTTYPE_CC))
								_CCEmails.add(email);
							
							if(recipientType.equals(MNotificationRecipient.RECIPIENTTYPE_BCC))
								_BCCEmails.add(email);
						}
						
						//add emails from user
						if(ad_user_id > 0) {
							u = MUser.get(getCtx(), ad_user_id);
							if(u.getEMail() != null) {
								if(recipientType.equals(MNotificationRecipient.RECIPIENTTYPE_To))
									_ToEmails.add(u.getEMail());
								
								if(recipientType.equals(MNotificationRecipient.RECIPIENTTYPE_CC))
									_CCEmails.add(u.getEMail());
								
								if(recipientType.equals(MNotificationRecipient.RECIPIENTTYPE_BCC))
									_BCCEmails.add(u.getEMail());
							}								
						}
						
						//add from role
						
					}
					catch(SQLException ex) {
						log.warning(ex.getMessage());
					}
				}
			}
			
		}		
		catch (SQLException e) {
			
			throw new DBException(e);
		}
		finally	{
			rs = null; pstmt = null;			
		}
		buildEmails();
	}
	
	public List<MNotificationRecipient> getRecipients() {
		//Find Receipients
		List<MNotificationRecipient> recipients = new Query(getCtx(), MNotificationRecipient.Table_Name, "AD_Notification_ID = ?", get_TrxName())
				.setClient_ID()						
				.setParameters(getAD_Notification_ID())
				.setOnlyActiveRecords(true)
				.list();
		/*if(recipients.size() == 0)
			throw new AdempiereException("Recipients not configured!");*/
		
		
		return recipients;
	}
	
	public void notifyMessage(String ID) {
		String msg = getMessage(ID);		
		//Send WhatsApp / SMS
		for(String phoneNo : getPhoneNos()) {
			if(isWhatsApp() && EnabledWhatsApp) {
				WhatsAppUtil waUtil = new WhatsAppUtil();
				waUtil.sendMessage(getAD_Client_ID(), getAD_Org_ID(),getAD_Notification_ID(), null, phoneNo, msg);
			}
		}
		
		//Send Emails
		if(isEmail()) {
			buildEmails(ID);
			
			String subject = getSubject(ID);
			String from = fromEmail;
			MClient client = MClient.get(getAD_Client_ID());
			EMail mail = client.createEMailFrom(from, " ", subject, msg, true);
			
			//Set To
			for(String to : getToEmails())
				mail.addTo(to);
			
			//Set CC
			for(String cc : getCCEmails())
				mail.addCc(cc);
			
			//Set BCC 
			for(String bcc : getBCCEmails()) 
				mail.addBcc(bcc);
			 
			//Set Attachment
			mail = setAttachment(mail, ID);
			
			//Send Email
			if (!EMail.SENT_OK.equals(mail.send()))
			{
				//Write Log
			}	
		}
		
		/*
		if(isNoticeFlag()) {
			MNote note = new MNote(getCtx(), 0, get_TrxName());
			
			note.setAD_Org_ID(getAD_Org_ID());
			note.setAD_Message_ID(getAD_Message_ID());
			note.setAD_User_ID(getAD_User_ID());
			note.setAD_Table_ID(getAD_Table_ID());
			note.setRecord_ID(Integer.parseInt(ID));
			note.setDescription(getRequiredAction());
			note.setTextMsg(msg);
			note.saveEx();
		}
		*/
	}
	
	public EMail setAttachment(EMail email, String ID) {
		PreparedStatement pstmt1 =  null;
		ResultSet rs1 = null;
		 
		try	{			
			
			pstmt1 = null;
			rs1 = null;
			String attSql = getAttachmentSQL() != null ? getAttachmentSQL().replace("@ID@", ID) : "";
			
			if(attSql != null && attSql.length() > 0) {
				pstmt1 = DB.prepareStatement (attSql, get_TrxName());
				rs1 = pstmt1.executeQuery();
				ResultSetMetaData attMD = rs1.getMetaData();
				
				boolean existsTableID = false;
				boolean existsRecordID = false;
				boolean existsLogoID = false;
				boolean existsZipfilename = false;
				
								
				int fileCount = 0;
				
				for(int i=1;i<=attMD.getColumnCount();i++) {
					String columnName = attMD.getColumnName(i); 
					if(columnName.toLowerCase().equals("ad_table_id")) 
						existsTableID = true;
					if(columnName.toLowerCase().equals("record_id")) 
						existsRecordID = true;
					if(columnName.toLowerCase().equals("logo_id")) 
						existsLogoID = true;
					if(columnName.toLowerCase().equals("zipfilename"))
						existsZipfilename = true;
				}
				File tempfolder = null; 
				try {
					Path tempPath = Files.createTempDirectory("att_" + ID);
					tempfolder = tempPath.toFile();
				} catch (IOException e1) {
					throw new AdempiereException("Unable to create temp folder", e1);
				}
				
				while (rs1.next())
				{
					//Attach Logo
					if(existsLogoID) {
						int logo_id = rs1.getInt("logo_id");
						if(logo_id > 0) {						
							MImage img = MImage.get(getCtx(), logo_id);				
							File image = new File(tempfolder, img.getImageURL());
							FileOutputStream destinationFileOutputStream  = null;
							
							try {
								image.createNewFile();
								destinationFileOutputStream = new FileOutputStream(image);
								byte[] buffer = img.getData();
								destinationFileOutputStream.write(buffer);
								if(!isAttachAsZIP())
									email.addAttachment(image);
								fileCount++;
							}				
							catch( java.io.FileNotFoundException f ) {
								throw new AdempiereException("File not found exception : " + image.getName() + " : " + f);
							} 
							catch( java.io.IOException e ) {
								throw new AdempiereException("IOException : " + e);
							} finally {
								try {
									if (destinationFileOutputStream != null)
										destinationFileOutputStream.close();
								} catch(Exception e) { 
									throw new AdempiereException("Exception : " + e);
								}
							}
						}
					}
					
					//Attach from attachments
					if(existsTableID && existsRecordID) {
						int table_id = rs1.getInt("ad_table_id");
						int record_id = rs1.getInt("record_id");
						
						MAttachment att = MAttachment.get(getCtx(), table_id, record_id, get_TrxName());
						if(att == null)
							continue;
						
						MAttachmentEntry[] entries = att.getEntries();
						MAttachmentEntry entry = null;
						int index = 0;

						for (int i = 0; i < entries.length; i++) {
							entry = entries[i];
							index = i;
							File attachedFile = new File(tempfolder, entry.getName());
							FileUtil.copy(att, attachedFile, index);
							
							if(!isAttachAsZIP())
								email.addAttachment(attachedFile);							
							fileCount++;
						}
					}
					
					if(isAttachAsZIP()) {
						if(fileCount == 0)
							return email;
						String zipFileName = "attachment";
						if(existsZipfilename) {
							zipFileName = rs1.getString("zipfilename");
						}
						File zipFile = null;
						try {
							zipFile = File.createTempFile(zipFileName, ".zip");
						} catch (Throwable e) {
							throw new AdempiereException("Unable to create temp file", e);
						}
						zipFile.delete();
						
						Zip zipper = new Zip();
						zipper.setDestFile(zipFile);
						zipper.setBasedir(tempfolder);
						zipper.setUpdate(true);
						zipper.setCompress(true);
						zipper.setCaseSensitive(false);
						zipper.setFilesonly(true);
						zipper.setTaskName("zip");
						zipper.setTaskType("zip");
						zipper.setProject(new Project());
						zipper.setOwningTarget(new Target());
						zipper.execute();
						
						email.addAttachment(zipFile);
					}
					
					try {
						if(isAttachAsZIP())
							FileUtil.deleteDirectory(tempfolder);
					} catch (IOException e) {}
					
				}
			}
			
		}		
		catch (SQLException e) {
			
			throw new DBException(e);
		}
		finally	{
			rs1 = null; pstmt1 = null;			
		}		
		
		return email;
	}
	
	public void buildPhoneNos() {
		if(!isWhatsApp())
			return;
		
		//From Recipients
		for(MNotificationRecipient r : getRecipients()) {
			if(r.getMobileNo() != null)
				_phoneNos.add(r.getMobileNo());
			
			//add from user
			
			// add from roles
		}
		
		
		//From SQL
		
		
	}
	
	public void buildEmails() {
		if(!isEmail())
			return;
		
		//From Recipients
		for(MNotificationRecipient r : getRecipients()) {
			//From
			if(r.getRecipientType() != null && r.getRecipientType().equals(MNotificationRecipient.RECIPIENTTYPE_From)) {
				if(r.getEMail() != null)
					fromEmail = r.getEMail();
				
				//add from user
				if(r.getAD_User_ID() > 0) {
					MUser u = MUser.get(getCtx(), r.getAD_User_ID());
					if(u.getEMail() != null)
						fromEmail = u.getEMail();
				}
				
			}
			
			//To
			if(r.getRecipientType() != null && r.getRecipientType().equals(MNotificationRecipient.RECIPIENTTYPE_To)) {
				if(r.getEMail() != null)
					_ToEmails.add(r.getEMail());
				
				//add from user
				
				//add from roles 
			}
			
			//CC
			if(r.getRecipientType() != null && r.getRecipientType().equals(MNotificationRecipient.RECIPIENTTYPE_CC)) {
				if(r.getEMail() != null)
					_CCEmails.add(r.getEMail());
				
				//add from user
				
				//add from roles 
			}
			
			//BCC
			if(r.getRecipientType() != null && r.getRecipientType().equals(MNotificationRecipient.RECIPIENTTYPE_BCC)) {
				if(r.getEMail() != null)
					_BCCEmails.add(r.getEMail());
				
				//add from user
				
				//add from roles
			}			
			
		}	
	}
	
	public List<String> getPhoneNos() {
		if(_phoneNos.size() == 0)
			buildPhoneNos();
		
		return _phoneNos;
	}
	
	public List<String> getToEmails() {
		if(_ToEmails.size() == 0)
			buildEmails();
		
		return _ToEmails;
	}
	
	public List<String> getCCEmails() {
		return _CCEmails;
	}
	
	public List<String> getBCCEmails() {
		return _BCCEmails;
	}
	
	public String getFromEmail() {
		return null;
	}
	
	public static void notifyMessage(Properties ctx, String Name, String ID, String trxName) {
		MNotification msg = get(ctx, Name, trxName);
		msg.notifyMessage(ID);
	}

	public List<MNotificationTriggerColumn> getTriggerColumns() {
		String where = "AD_Notification_ID = " + getAD_Notification_ID();
		
		List<MNotificationTriggerColumn> triggerColumns = new Query(getCtx(), MNotificationTriggerColumn.Table_Name, where, get_TrxName())
						.setClient_ID().setOnlyActiveRecords(true).list();
		
		return triggerColumns;
	}
}
