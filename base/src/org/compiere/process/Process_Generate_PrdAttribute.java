/******************************************************************************
     * BETA CODE --------------AUTHOR -> SUBAIR P.K. 
	 * Date of Creation : 29 Dec 2010
     * Class for Generate Product Attribute
 *****************************************************************************/

package org.compiere.process;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import org.compiere.util.DB;

public class Process_Generate_PrdAttribute extends SvrProcess{

	private int AD_PInstance_ID=0;
    public PreparedStatement pstmt = null;
    
    protected void prepare() {
    	
     AD_PInstance_ID = getAD_PInstance_ID();
    } // prepare
   
    
    protected String doIt() throws Exception {
    	
    	String sql = "{call BETA_GEN_PRDATTR(?)}";
        try {
            CallableStatement cstmt = DB.prepareCall(sql);
            cstmt.setInt(1,AD_PInstance_ID);
			cstmt.executeUpdate();
            cstmt.close();
            addLog(0, null, null, "Process Completed Successfully");
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
