package org.compiere.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class Complete_ProjReceipt extends SvrProcess {
     
    private int record_id = 0;

    protected void prepare() {
    	
     record_id = getRecord_ID();
		
    } // prepare

    
    protected String doIt() throws Exception {
    	
    	//*****************Added by Subair to check the period is open or not @10.11.2011**********************************
    	
    	Timestamp dateTrx = null;
    	int clientID = 0 ;
    	
    	String sqlRec = "Select datetrx, ad_client_id from c_projectreceipt where c_projectreceipt_id = ?" ;
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
    		
		try // 
   	 	{
			pstmt = DB.prepareStatement(sqlRec, null);
			pstmt.setInt(1,record_id);
			rs = pstmt.executeQuery();
	  
   	 		if (rs.next())
   	 		{
   	 			dateTrx = rs.getTimestamp(1);
   	 			clientID = rs.getInt(2);
   	 		}
   	 		rs.close();
   	 		pstmt.close();
   	 	}catch(Exception e){} //
   	 	finally 
   	 	{
   	 		DB.close(rs, pstmt);
   	 	}
   	 	
    	String sqlChk = "Select c_period_id from c_period where ? between STARTDATE and ENDDATE and ad_client_id=? "; 
    	BigDecimal periodID = DB.getSQLValueBD(get_TrxName(), sqlChk, dateTrx,clientID);
    	
    	String sqlPeriodCtrl = "select nvl(periodstatus,'N') from c_periodcontrol where docbasetype='PJR' and c_period_id = ? ";
    	String periodStatus = DB.getSQLValueString(get_TrxName(), sqlPeriodCtrl, periodID) ;
    	
    	if (!periodStatus.equalsIgnoreCase("O"))
    	{
    		//JOptionPane.showMessageDialog(null, " The Transaction Date is Belongs to a Closed Period");
    		addLog(" The Transaction Date is Belongs to a Closed Period");
	 		return " ";
    	}
    	//**************************************************************************************************************
    	
    	
    	String sql1 = "update c_projectreceipt set docstatus = 'CO', docaction = 'CL', " +
    			"processed = 'Y', processing = 'N'" +
    			"  where c_projectreceipt_id = " + getRecord_ID();
	

		try
		{
			int updatercpt = DB.executeUpdate(sql1, null);
			
		}catch (Exception e ){ 	
	    	    };
        return " ";
    }
}

