package org.syvasoft.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.DBException;
import org.compiere.model.MClient;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.syvasoft.model.MNotificationRecipient;


public class ProcessNotification extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	String subSql = "";		
	String SqlQuery = "";
	String subMessage = "";
	String Message="";
	
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		
		String Sql="SELECT * FROM AD_Notification WHERE IsActive='Y' AND IsEmail='Y' AND  to_timestamp(deliverytime,'HH24:MI') :: time = to_timestamp(to_char(now(),'HH24:MI'),'HH24:MI')::time";
		PreparedStatement pstmt =  null;
		PreparedStatement rpPstmt = null;
		ResultSet rs = null;
		ResultSet rpRs = null;
		try	{
			pstmt = DB.prepareStatement(Sql, get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String recipientSQL = rs.getString("RecipientSQL");
				if(recipientSQL != null && recipientSQL.length() > 0) {
					rpPstmt = DB.prepareStatement (recipientSQL, get_TrxName());
					rpRs = rpPstmt.executeQuery();									
					
					while (rpRs.next())
					{
						sendEMailByRecipientSQL(rs,rpRs);
					}
				}
				else {
					sendEmailToConfiguredRecipients(rs);
				}
			}
		}
		catch (SQLException e) {
			rollback();
			//log.log(Level.SEVERE, "", e);
			throw new DBException(e, Sql);
		}
		finally	{
			DB.close(rs, pstmt);
			DB.close(rpRs, rpPstmt);		
			rs = null; pstmt = null;
			rpRs = null; rpPstmt = null;
		}
		return null;
	}
	
	
	private String parseSQL(ResultSet recipientRS, String sql) throws SQLException {
		if(sql == null)
			return null;
		ResultSetMetaData rpMD = recipientRS.getMetaData();
		for(int i=1;i<=rpMD.getColumnCount();i++) {
			String columnName = rpMD.getColumnName(i); 
			sql =sql.replace("@"+columnName+"@", recipientRS.getString(columnName));
		}
		return sql;	
	}
	
	private void sendEMailByRecipientSQL(ResultSet rs, ResultSet recipientRS) {
				
		PreparedStatement pstmt1 =  null;
		ResultSet rs1 = null;
		PreparedStatement hdrPstmt =  null;
		ResultSet hdrRs = null;	
		try	{			
			
				pstmt1 = null;
				rs1 = null;
				subSql = parseSQL(recipientRS, rs.getString("SubjectSql"));
				SqlQuery = parseSQL(recipientRS, rs.getString("Sql"));	
				subMessage = rs.getString("SubjectMsg");
				Message=rs.getString("Message");

				//Header
				if(subSql != null && subSql.length() > 0) {
					hdrPstmt = DB.prepareStatement (subSql, get_TrxName());
					hdrRs = hdrPstmt.executeQuery();
					ResultSetMetaData hdrMD = hdrRs.getMetaData();					
					while (hdrRs.next())
					{
						for(int i=1;i<=hdrMD.getColumnCount();i++) {
							String columnName = hdrMD.getColumnName(i); 
							subMessage=subMessage.replace("{"+columnName+"}", hdrRs.getString(columnName));
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
				if(subMessage != null)
					msgBody.append(subMessage);
				
				while (rs1.next())
				{
					String msgDetail = Message;	
					for(int i=1;i<=md.getColumnCount();i++) {
						String columnName = md.getColumnName(i); 
						msgDetail=msgDetail.replace("{"+columnName+"}", rs1.getString(columnName));
					}
					msgBody.append(msgDetail);
					row++;
				}								

				String emailaddress = recipientRS.getString("email");	
				MClient client = new MClient(getCtx(),Env.getAD_Client_ID(getCtx()),get_TrxName());
				EMail email = client.createEMail(emailaddress,subMessage, msgBody.toString());
				if (EMail.SENT_OK.equals(email.send()))
				{
				}			
		}
		catch (SQLException e) {
			rollback();
			//log.log(Level.SEVERE, "", e);
			throw new DBException(e);
		}
		finally	{
			rs1 = null; pstmt1 = null;
			hdrRs = null; hdrPstmt = null;
		}
	}
private void sendEmailToConfiguredRecipients(ResultSet rs) {
			
		PreparedStatement pstmt1 =  null;
		ResultSet rs1 = null;
		PreparedStatement hdrPstmt =  null;
		ResultSet hdrRs = null;
		PreparedStatement ftrPstmt =  null;
		ResultSet ftrRs = null;		
		try	{			
			
				pstmt1 = null;
				rs1 = null;
				subSql = rs.getString("SubjectSql");
				SqlQuery=rs.getString("Sql");	
				subMessage = rs.getString("SubjectMsg");
				Message=rs.getString("Message");
				//Header
				if(subSql != null && subSql.length() > 0) {
					hdrPstmt = DB.prepareStatement (subSql, get_TrxName());
					hdrRs = hdrPstmt.executeQuery();
					ResultSetMetaData hdrMD = hdrRs.getMetaData();					
					while (hdrRs.next())
					{
						for(int i=1;i<=hdrMD.getColumnCount();i++) {
							String columnName = hdrMD.getColumnName(i); 
							subMessage=subMessage.replace("{"+columnName+"}", hdrRs.getString(columnName));
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
				if(subMessage != null)
					msgBody.append(subMessage);
				
				while (rs1.next())
				{
					String msgDetail = Message;	
					for(int i=1;i<=md.getColumnCount();i++) {
						String columnName = md.getColumnName(i); 
						msgDetail=msgDetail.replace("{"+columnName+"}", rs1.getString(columnName));
					}
					msgBody.append(msgDetail);
					row++;
				}								
								
				//Find Receipients
				List<MNotificationRecipient> recipients = new Query(getCtx(), MNotificationRecipient.Table_Name, "AD_Notification_ID = ?", get_TrxName())
						.setClient_ID()						
						.setParameters(rs.getInt("AD_Notification_ID"))
						.setOnlyActiveRecords(true)
						.list();
				//Sql="SELECT mobileno FROM TF_SmsRecipient Where IsActive='Y' AND TF_SmsNotification_ID="+rs.getInt("TF_SmsNotification_ID");
				//pstmt2 = DB.prepareStatement(Sql, get_TrxName());
				//rs2 = pstmt2.executeQuery();			
				
				String Receipient="";
				for(MNotificationRecipient recipient : recipients)
				{
					Receipient=recipient.getEMail();					
					MClient client = new MClient(getCtx(),Env.getAD_Client_ID(getCtx()),get_TrxName());
					EMail email = client.createEMail(Receipient,subMessage, msgBody.toString());
					if (EMail.SENT_OK.equals(email.send()))
					{
					}	
				}				
			
		}
		catch (SQLException e) {
			rollback();
			//log.log(Level.SEVERE, "", e);
			throw new DBException(e);
		}
		finally	{
			rs1 = null; pstmt1 = null;
			hdrRs = null; hdrPstmt = null;
			ftrRs = null; ftrPstmt = null;
		}
	}
	
}