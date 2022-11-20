package org.syvasoft.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.DBException;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Util;
import org.syvasoft.model.MNotification;



public class ProcessScheduledNotification extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	String hdrSql = "";		
	String SqlQuery = "";
	String ftrSql = "";
	String hdrMessage = "";
	String Message="";
	String ftrMessage = "";
	String Unicode="";
	
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String whereClause = "IsScheduled = 'Y' AND IsActive='Y'  AND  to_timestamp(deliverytime,'HH24:MI') :: time = to_timestamp(to_char(now(),'HH24:MI'),'HH24:MI')::time";
		List<MNotification> list = new Query(getCtx(), MNotification.Table_Name, whereClause, get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list();
		
		for(MNotification msg : list) {
			
			if(msg.get_Table_ID() > 0) {
				MTable t = MTable.get(getCtx(), msg.getAD_Table_ID());
				String sql = "SELECT COUNT(*) FROM " + t.getTableName() + " WHERE 1 = 1" ; 
				
				if(!Util.isEmpty(msg.getWhereClause())) {
					sql = sql + " AND (" + msg.getWhereClause() + ")";
				}
				
				try {
					int count = DB.getSQLValue(get_TrxName(), sql);
					if(count > 0) {
						msg.notifyMessage("");
					}
				}
				catch (Exception e)
				{
					log.log(Level.WARNING, "Notification Mesaage", e);
				}
			}
			else {
				msg.notifyMessage("");
			}
			
		}
		
		return null;
	}


}
