/******************************************************************************
     * BETA CODE --------------AUTHOR -> SUBAIR P.K. 
	 * Date of Creation : 26 Jun 2011
     * Class for Quotation Revision
 *****************************************************************************/
package org.compiere.process;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;

import org.compiere.util.DB;

public class Process_Qtn_Revision extends SvrProcess{

	private int AD_PInstance_ID=0;
    public PreparedStatement pstmt = null;
    
    protected void prepare() {
    	
     AD_PInstance_ID = getAD_PInstance_ID();
    } // prepare
   
    
    protected String doIt() throws Exception {
    	
    	String sql = "{call Beta_Qtn_Revision(?)}";
        try {
            CallableStatement cstmt = DB.prepareCall(sql);
            cstmt.setInt(1,AD_PInstance_ID);
			cstmt.executeUpdate();
            cstmt.close();
            addLog(0, null, null, "Revised Quotation has been Created Successfully");
            } catch (Exception e) {
             String s=e.getLocalizedMessage();
             System.out.println("Error : " + s);
             String s1[] = s.split(":");
             addLog(0, null, null, "Process Stopped");
             addLog(0, null, null, s1[1]);
            }
        return " ";
     }
}





