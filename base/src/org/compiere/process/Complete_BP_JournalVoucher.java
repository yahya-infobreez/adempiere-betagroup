package org.compiere.process;

//*****************(Beta) BP Journal Voucher Complete Process --Added by Subair Dtd:14.10.2012**********************************

import java.math.BigDecimal;



import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class Complete_BP_JournalVoucher extends SvrProcess{
	
	
	private int record_id = 0;

    protected void prepare() {
    	
     record_id = getRecord_ID();
		
    } // prepare

    
    protected String doIt() throws Exception {
    	
    	Timestamp dateTrx = null;
    	int clientID = 0 ;
    	int updatepost =0;
    	BigDecimal totDr =Env.ZERO;
    	BigDecimal totCr =Env.ZERO;
    	
    	//**********************************Validate the Date (Period Closed or Not)************************************************ 
    	String sqlRec = "Select docdate, ad_client_id from beta_bp_journal where beta_bp_journal_id = ?" ;
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
    	
    	String sqlPeriodCtrl = "select nvl(periodstatus,'N') from c_periodcontrol where docbasetype='GLJ' and c_period_id = ? ";
    	String periodStatus = DB.getSQLValueString(get_TrxName(), sqlPeriodCtrl, periodID) ;
    	
    	if (!periodStatus.equalsIgnoreCase("O"))
    	{
    		addLog(" The Transaction Date is Belongs to a Closed Period");
	 		return " ";
    	}
    	//**************************************************************************************************************
    	
    	//*******************************Validate Debit & Credit Amount***************************************************
    	String sqlAmt = "Select sum(dramt),sum(cramt) from beta_bp_journalline where beta_bp_journal_id = ?" ;
    	PreparedStatement pstmtAmt = null;
		ResultSet rsAmt = null;
    		
		try // 
   	 	{
			pstmtAmt = DB.prepareStatement(sqlAmt, null);
			pstmtAmt.setInt(1,record_id);
			rsAmt = pstmtAmt.executeQuery();
	  
   	 		if (rsAmt.next())
   	 		{
   	 			totDr = rsAmt.getBigDecimal(1);
   	 			totCr = rsAmt.getBigDecimal(2);
   	 		}
   	 	    rsAmt.close();
   	 	    pstmtAmt.close();
   	 	}catch(Exception e){} //
   	 	finally 
   	 	{
   	 		DB.close(rsAmt, pstmtAmt);
   	 	}
    	
		if (totDr.compareTo(totCr) != 0) 
   	 	{
			addLog("Debit Amount and Credit Amount is not Tallying");
   	 		return " ";
   	 	}
    	
    	//**************************************************************************************************************
		//call Posting procedure  **added on 18/07/2018 for web access issue
				String sql = "{call BETA_BPJRN_POST(?)}";
		        try {
		            CallableStatement cstmt = DB.prepareCall(sql);
		            cstmt.setInt(1,getRecord_ID());
		            updatepost = cstmt.executeUpdate();
		            cstmt.close();
		            addLog(0, null, null, "Process Completed Successfully");
		            } catch (Exception e) {
		             String s=e.getLocalizedMessage();
		             System.out.println("Error : " + s);
		             String s1[] = s.split(":");
		             addLog(0, null, null, "Process Stopped");
		             addLog(0, null, null, s1[1]);
		            }
		//
		
		if (updatepost!=0)
		{
	    	String sql1 = "update beta_bp_journal set docstatus = 'CO', docaction = 'CL', " +
	    			"processed = 'Y',isapproved = 'Y'" +
	    			"  where beta_bp_journal_id = " + getRecord_ID();
		    
	    	String sql2 = "update beta_bp_journalline set processed = 'Y' " + 
	    				  "  where beta_bp_journal_id = " + getRecord_ID();
	
			try
			{
				int updatercpt = DB.executeUpdate(sql1, null);
				int updatercpt2 = DB.executeUpdate(sql2, null);
				
			}catch (Exception e ){ 	
		    	    };
		}
		
        return " ";
    }
	

}
