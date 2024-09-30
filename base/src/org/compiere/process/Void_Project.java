package org.compiere.process;

import java.sql.CallableStatement;
import org.compiere.util.DB;


public class Void_Project extends SvrProcess {
     
    private int record_id = 0;
    private int table_id = 203;

    protected void prepare() {
    	
     record_id = getRecord_ID();
		
    } // prepare

    
    protected String doIt() throws Exception {
    	

		String sql = "{call proc_void_project(?) }";
		String sql2 = "update c_project set posted='N',processed='N'" +
				"  where c_project_id = " + getRecord_ID();
		try {
		    CallableStatement cstmt = DB.prepareCall(sql);
            cstmt.setInt(1, getRecord_ID());
            cstmt.executeUpdate();
            cstmt.close();
          int updprj = DB.executeUpdate(sql2,null);
           
        	} 
        catch (Exception e) 
        	{
            String s = e.getMessage();
           	}
	
		return "";
	}	   	
    }



